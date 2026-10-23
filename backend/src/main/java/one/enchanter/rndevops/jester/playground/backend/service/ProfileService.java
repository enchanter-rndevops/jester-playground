package one.enchanter.rndevops.jester.playground.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.enchanter.rndevops.jester.playground.backend.domain.extension.repository.UserProfileRepository;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.UsersEntity;

/**
 * Profile情報を扱うクラス。
 * 自分自身の情報のみ扱う。
 * 
 * 認可処理でそのような制御を行うようにすること。
 * 
 */
@Service
public class ProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public UsersEntity getProfile(String sub) {

        Optional<UsersEntity> result = userProfileRepository.getUserProfile(sub);

        return result.orElse(null);

    }

}
