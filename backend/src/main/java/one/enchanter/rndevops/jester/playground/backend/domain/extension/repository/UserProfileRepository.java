package one.enchanter.rndevops.jester.playground.backend.domain.extension.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.enchanter.rndevops.jester.playground.backend.domain.extension.mapper.UserProfileMapper;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.UsersEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper.UsersMapper;

@Component
public class UserProfileRepository {

    @Autowired
    private UserProfileMapper userProsMapper;

    @Autowired
    private UsersMapper usersMapper;

    public Optional<UsersEntity> getUserProfileByUid(String uid) {

        Optional<UsersEntity> users = userProsMapper.selectByUid(uid);

        return users;

    }

    public Optional<UsersEntity> getUserProfile(Long userId) {

        Optional<UsersEntity> users = usersMapper.selectByPrimaryKey(userId);

        return users;

    }

}
