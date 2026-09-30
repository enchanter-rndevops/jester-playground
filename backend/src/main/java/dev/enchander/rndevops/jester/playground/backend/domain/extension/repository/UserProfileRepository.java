package dev.enchander.rndevops.jester.playground.backend.domain.extension.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.enchander.rndevops.jester.playground.backend.domain.generated.entity.Users;
import dev.enchander.rndevops.jester.playground.backend.domain.generated.mapper.UsersMapper;

@Component
public class UserProfileRepository {

    @Autowired
    private UsersMapper usersMapper;

    public Users getUserProfile(String sub) {

        Users users = usersMapper.selectByPrimaryKey(sub);

        return users;

    }

}
