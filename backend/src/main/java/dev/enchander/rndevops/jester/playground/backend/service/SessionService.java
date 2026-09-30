package dev.enchander.rndevops.jester.playground.backend.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import dev.enchander.rndevops.jester.playground.backend.domain.extension.repository.SessionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * SessionService
 */
@Service
public interface SessionService {

    /**
     * 新しいセッションを生成する。
     * 
     * @param sub IDTokenのsub(ユーザーの識別子として使用する値)
     * @return 新しく精製したセッションID
     */
    String createSession(String sub);

    /**
     * 
     * @param sessionId
     * @return
     */
    String getSub(String sessionId);

    /**
     * 
     * @param sessionId
     */
    void deleteSession(String sessionId);
}

/**
 * ローカル用
 * せっかくDB作ったので、テーブルに保存することにする。
 */
@Service
@Profile("local")
@Slf4j
class LocalSessionService implements SessionService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * 新しいセッションを生成する。
     * 自前で新しいIDを生成してもいいけが、ここではHTTP SessionのIDを使用している。
     * すでにHTTP sessionがある場合、それを無効にしてから新しいセッションを生成する。
     */
    public String createSession(String sub) {

        // 新しいIDTokenなので、ここでsessionを作り直す。
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        session = request.getSession(true);

        if (sessionRepository.isUserExists(sub) == false) {
            log.warn("ユーザーが存在しません。[{}]", sub);
            throw new AuthorizationServiceException("認証されていません。");
        }

        // この値は expireAt と同じ値にしておく。自前セッション管理で有効な間はHTTP sessionも有効。
        // sessions.expires_at の値と違い、前回のアクセスからの時間であることに注意。
        session.setMaxInactiveInterval(86400);
        session.setAttribute("sub", sub);

        String sessionId = session.getId();

        // ここでDBに保存。セッションの有効期限は固定になっていますが、プロパティから取得しましょう。
        LocalDateTime expireAt = LocalDateTime.now().plusDays(1);
        sessionRepository.createSession(sessionId, sub, expireAt);

        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
                sub,
                null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(
                org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        return sessionId;
    }

    /**
     * subを取得する。
     * session に保存しているsubを取得するようになっていますが、パラメータsessionIdを使って
     * sessions から取得するべき（expires_atのチェックも行うこと）。
     * subしか返していませんが、ユーザー情報も返すようにしたらいいと思います。
     * そのようなメソッドに変更した場合、メソッド名を変更しましょう。
     */
    public String getSub(String sessionId) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        if (!session.getId().equals(sessionId)) {
            return null;
        }

        Object sub = (String) session.getAttribute("sub");
        return sub == null ? null : sub.toString();

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
 * AWS上で使用するSessionService.
 * いまのところ、localと同じ実装。
 */
@Service
@Profile("aws")
@Slf4j
class AwsLocalSessionService implements SessionService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * 新しいセッションを生成する。
     * 自前で新しいIDを生成してもいいけが、ここではHTTP SessionのIDを使用している。
     * すでにHTTP sessionがある場合、それを無効にしてから新しいセッションを生成する。
     */
    public String createSession(String sub) {

        // 新しいIDTokenなので、ここでsessionを作り直す。
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        session = request.getSession(true);

        if (sessionRepository.isUserExists(sub) == false) {
            log.warn("ユーザーが存在しません。[{}]", sub);
            throw new AuthorizationServiceException("認証されていません。");
        }

        // この値は expireAt と同じ値にしておく。自前セッション管理で有効な間はHTTP sessionも有効。
        // sessions.expires_at の値と違い、前回のアクセスからの時間であることに注意。
        session.setMaxInactiveInterval(86400);
        session.setAttribute("sub", sub);

        String sessionId = session.getId();

        // ここでDBに保存。セッションの有効期限は固定になっていますが、プロパティから取得しましょう。
        LocalDateTime expireAt = LocalDateTime.now().plusDays(1);
        sessionRepository.createSession(sessionId, sub, expireAt);

        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
                sub,
                null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(
                org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        return sessionId;
    }

    /**
     * subを取得する。
     * session に保存しているsubを取得するようになっていますが、パラメータsessionIdを使って
     * sessions から取得するべき（expires_atのチェックも行うこと）。
     * subしか返していませんが、ユーザー情報も返すようにしたらいいと思います。
     * そのようなメソッドに変更した場合、メソッド名を変更しましょう。
     */
    public String getSub(String sessionId) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        if (!session.getId().equals(sessionId)) {
            return null;
        }

        Object sub = session.getAttribute("sub");
        if (sub == null) {
            return null;
        }
        return sub.toString();

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

    public String createSession(String sub) {

        throw new RuntimeException("未実装");

    }

    public String getSub(String sessionId) {
        return store.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        store.remove(sessionId);
    }
}
