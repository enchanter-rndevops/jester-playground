
package one.enchanter.rndevops.jester.playground.backend.domain.extension.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import one.enchanter.rndevops.jester.playground.backend.domain.extension.entity.UserIdentityEntity;

@Mapper
public interface UserIdentityMapper {

    @SelectProvider(type = UserIdentitiesRepositoryProvider.class)
    public Optional<UserIdentityEntity> getUserIdentity(@Param("provider") String provider,
            @Param("providedUserId") String providedUserId);

    class UserIdentitiesRepositoryProvider implements ProviderMethodResolver {

        public String getUserIdentity(@Param("provider") String provider,
                @Param("providedUserId") String providedUserId) {

            return """
                    select
                        users.id as userId,
                        users.email as email,
                        users.name as user
                    from users
                    inner join user_identities on users.id = user_identities.user_id
                    where
                        user_identities.provider = #{provider}
                        and user_identities.provided_user_id = #{providedUserId}
                    """;

        }
    }

}
