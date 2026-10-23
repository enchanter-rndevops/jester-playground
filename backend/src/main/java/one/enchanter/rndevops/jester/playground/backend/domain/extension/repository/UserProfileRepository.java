package one.enchanter.rndevops.jester.playground.backend.domain.extension.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.UsersEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper.UsersMapper;

@Component
public class UserProfileRepository {

    @Autowired
    private UsersMapper usersMapper;

    public Optional<UsersEntity> getUserProfile(String sub) {

        Optional<UsersEntity> users = usersMapper.selectByPrimaryKey(sub);

        return users;

    }

}
