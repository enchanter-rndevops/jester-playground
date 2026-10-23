
package one.enchanter.rndevops.jester.playground.backend.domain.extension.mapper;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import one.enchanter.rndevops.jester.playground.backend.domain.extension.entity.UserSessionEntity;

@Mapper
public interface UserSessionMapper {

    @SelectProvider(type = UserSessionRepositoryProvider.class)
    public Optional<UserSessionEntity> getValidSession(@Param("id") String id, @Param("now") LocalDateTime now);

    class UserSessionRepositoryProvider implements ProviderMethodResolver {

        public String getValidSession(String id, LocalDateTime now) {

            return """
                    select
                        sessions.user_sub as sub,
                        users.email as  email,
                        users.name as user
                        from sessions
                    join users on users.sub = sessions.user_sub
                    where
                        sessions.id = #{id}
                        and sessions.expires_at > #{now}
                    """;
        }
    }
}
