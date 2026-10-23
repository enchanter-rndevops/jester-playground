package one.enchanter.rndevops.jester.playground.backend.domain.extension.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.enchanter.rndevops.jester.playground.backend.domain.extension.entity.UserSessionEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.mapper.UserSessionMapper;

/**
 * UserSessionRepository
 */
@Component
public class UserSessionRepository {

    @Autowired
    private UserSessionMapper userSessionMapper;

    public Optional<UserSessionEntity> getValidSession(String id, LocalDateTime now) {

        return userSessionMapper.getValidSession(id, now);
    }

}
