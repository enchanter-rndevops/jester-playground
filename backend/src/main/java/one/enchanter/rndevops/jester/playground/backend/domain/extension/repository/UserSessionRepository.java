package one.enchanter.rndevops.jester.playground.backend.domain.extension.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.entity.UserSessionEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.mapper.UserSessionMapper;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper.SessionsMapper;

/**
 * UserSessionRepository
 */
@Component
@RequiredArgsConstructor
public class UserSessionRepository {

    @Autowired
    private final UserSessionMapper userSessionMapper;

    @Autowired
    private final SessionsMapper sessionsMapper;

    /**
     * 
     */
    public Optional<UserSessionEntity> getValidSession(String id, LocalDateTime now) {

        return userSessionMapper.getValidSession(id, now);

    }

    /**
     * 
     * @param sessionId
     * @return
     */
    public int deleteBySessionId(String sessionId) {

        return sessionsMapper.deleteByPrimaryKey(sessionId);

    }
}
