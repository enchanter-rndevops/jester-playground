
package one.enchanter.rndevops.jester.playground.backend.domain.extension.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.UsersEntity;

@Mapper
public interface UserProfileMapper {

    @SelectProvider(type = UserProfileRepositoryProvider.class)
    public Optional<UsersEntity> selectByUid(@Param("uid") String uid);

    class UserProfileRepositoryProvider implements ProviderMethodResolver {

        public String selectByUid(@Param("uid") String uid) {

            return """
                    select
                        users.id as id,
                        users.uid as uid,
                        users.email as email,
                        users.name as name,
                        users.createdAt as createdAt
                    from users
                    where
                        users.uid = #{uid}
                    """;

        }
    }

}
