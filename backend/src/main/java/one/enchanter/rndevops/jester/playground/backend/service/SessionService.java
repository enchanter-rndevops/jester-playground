package one.enchanter.rndevops.jester.playground.backend.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.UserPrincipal;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.entity.UserSessionEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.repository.SessionRepository;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.repository.UserSessionRepository;
import one.enchanter.rndevops.jester.playground.backend.exception.UnauthorizedOperationException;

/**
 * 
 * SessionService
 */
@Service
public interface SessionService {

    /**
     * 新しいセッションを生成する。
     * 
     * @param provider       プロバイダー
     * @param providedUserId プロバイダーのユーザーID
     * @return 新しく精製したセッションID
     */
    public String createSession(String provider, String providedUserId);

    /**
     * 
     * @param sessionId
     */
    public void deleteSession(String sessionId);

    /**
     * 
     * @param sessionId
     */
    public void createAuthentication(String sessionId);

}

/**
 * 
 */
@Service
@Profile({ "aws", "local", "test" })
@Slf4j
class LocalSessionService implements SessionService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    /**
     * 新しいセッションを生成する。
     * 自前で新しいIDを生成してもいいけが、ここではHTTP SessionのIDを使用している。
     * すでにHTTP sessionがある場合、それを無効にしてから新しいセッションを生成する。
     */
    public String createSession(String provider, String providedUserId) {

        // 新しいIDTokenなので、ここでsessionを作り直す。
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        session = request.getSession(true);

        Long userId = sessionRepository.getUserId(provider, providedUserId);

        if (userId == null) {
            log.warn("ユーザーが存在しません。[{}:{}]", provider, providedUserId);
            throw new UnauthorizedOperationException("認証されていません。");
        }

        // この値は expireAt と同じ値にしておく。自前セッション管理で有効な間はHTTP sessionも有効。
        // sessions.expires_at の値と違い、前回のアクセスからの時間であることに注意。
        session.setMaxInactiveInterval(86400);
        session.setAttribute("provider", provider);
        session.setAttribute("providerUserId", providedUserId);

        String sessionId = session.getId();

        LocalDateTime expireAt = LocalDateTime.now().plusDays(1);
        if (sessionRepository.createSession(sessionId, userId, expireAt) == false) {
            log.error("セッションの作成に失敗しました。[{}:{}]", provider, providedUserId);
            throw new UnauthorizedOperationException("認証されていません。");
        }

        createAuthentication(sessionId);

        return sessionId;
    }

    /**
     * 認可情報の作成。
     */
    public void createAuthentication(String sessionId) {

        // 現在は認可はないので、roleは空のリストでよい。
        // 認可を追加する場合、最後の引数をCollections.emptyList() ではなく、ユーザの権限リストにする必要がある。
        // uesrsからrole関連のテーブルをjoinし、権限リストを取得する方法でもいいが、
        // 将来的に、sessions, user_identities,
        // user_rolesをKVMに移行することを踏まえ、sessionsテーブルから検索すること。同じことを、filterで行う必要があるので、filterと同じSQL(ServiceまたはRepository)を使うこと。
        // sessionId をパラメータにし、この部分をべつなメソッドに切り出すと、filterと共通化できる。

        Optional<UserSessionEntity> userSession = userSessionRepository.getValidSession(sessionId,
                LocalDateTime.now());

        if (userSession == null) {
            throw new UnauthorizedOperationException("認証されていません。");
        }

        UserSessionEntity entity = userSession.orElseThrow(() -> {
            throw new UnauthorizedOperationException("認証されていません。");
        });

        UserPrincipal principal = new UserPrincipal(entity.getUserId(), entity.getProvider(),
                entity.getProvidedUserId());

        // 現在は認可はないので、roleは空のリストでよい。
        // 認可を追加する場合、最後の引数をCollections.emptyList() ではなく、ユーザの権限リストにする必要がある。
        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
                principal,
                null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // SecurityContextHolderに統一する。sessionは使わないこと。
        // HttpSession session = request.getSession(false);
        // if (session != null) {
        // session.setAttribute(
        // org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
        // SecurityContextHolder.getContext());
        // }
    }

    /**
     * セッションの削除。
     * sessionsテーブルからセッション情報を削除する。これを呼ぶ場合は、HTTP sessionも無効にしておくといいでしょう。
     * あるいは、HTTP sessionが無効になった場合に自動的にこれが呼ばれるようにしておくといいと思います。
     */
    public void deleteSession(String sessionId) {
        sessionRepository.invalidateSession(sessionId);
    }
}

/*
 * E2Eテスト用SessionService.
 * このProfileは、E2Eテストで有効になります。
 * E2Eテスト用の処理に置き換えます。
 */
@Service
@Profile("e2e")
class E2ESessionService implements SessionService {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    public String createSession(String provider, String providedUserId) {
        throw new RuntimeException("未実装");
    }

    public void createAuthentication(String sessionId) {
        throw new RuntimeException("未実装");
    }

    public void deleteSession(String sessionId) {
        store.remove(sessionId);
    }
}
