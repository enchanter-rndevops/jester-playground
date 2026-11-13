
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

        public String getValidSession(@Param("id") String id, @Param("now") LocalDateTime now) {

            return """
                    select
                        sessions.user_id as user_id,
                        sessions.id as session_id,
                        user_identities.provider as provider,
                        user_identities.provided_user_id as provided_user_id,
                        users.email as email,
                        users.name as name
                    from sessions
                    join user_identities on user_identities.user_id = sessions.user_id
                    join users on users.id = sessions.user_id
                    where
                        sessions.id = #{id}
                        and sessions.expires_at > #{now}
                    """;
        }
    }

}
