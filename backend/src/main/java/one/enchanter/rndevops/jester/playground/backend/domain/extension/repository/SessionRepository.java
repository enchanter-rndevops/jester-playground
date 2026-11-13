package one.enchanter.rndevops.jester.playground.backend.domain.extension.repository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.enchanter.rndevops.jester.playground.backend.domain.extension.mapper.UserIdentityMapper;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.SessionsEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper.SessionsMapper;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper.UsersMapper;

/**
 * 
 * SessionRepository
 */
@Component
public class SessionRepository {

    @Autowired
    private SessionsMapper sessionsMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UserIdentityMapper userIdentityMapper;

    /**
     * sessions テーブルにセッションレコードを作成。
     * 
     * @param id       セッションID
     * @param sub      ユーザーの識別子。Cognito の sub
     * @param expireAt 有効期限
     * @return 成功または失敗。
     */
    public boolean createSession(String id, Long userId, LocalDateTime expireAt) {

        SessionsEntity sessions = new SessionsEntity();

        sessions.setId(id);
        sessions.setUserId(userId);
        sessions.setCreatedAt(LocalDateTime.now());
        sessions.setExpiresAt(expireAt);

        int resullt = sessionsMapper.insert(sessions);

        return resullt == 1;
    }

    /**
     * ユーザーが存在するかを確認する。
     * 
     * @param privider       認証プロバイダーの識別子。Cognito の場合は "cognito" など。
     * @param providedUserId 認証プロバイダーが発行するユーザーの識別子。Cognito の場合は sub。
     * @return ユーザーが存在する場合はユーザーID、存在しない場合は null。
     */
    public Long getUserId(String privider, String providedUserId) {

        var result = userIdentityMapper.getUserIdentity(privider, providedUserId);

        return (Long) result.map(entity -> entity.getUserId()).orElse(null);

    }

    /**
     * セッション情報を削除する。
     * 
     * @param id セッションID
     */
    public void invalidateSession(String id) {
        sessionsMapper.deleteByPrimaryKey(id);
    }

}
