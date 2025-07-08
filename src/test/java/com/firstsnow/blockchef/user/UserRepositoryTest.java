package com.firstsnow.blockchef.user;

import com.firstsnow.blockchef.domain.user.User;
import com.firstsnow.blockchef.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    

    @Test
    @DisplayName("사용자 생성, 저장 완료")
    void createAndSaveUser() {

        //given
        User user = new User();
        user.setName("nicedog02");
        user.setEmail("nicedog02@naver.com");
        user.setPassword("<PASSWOR0221D>");

        //when
        User savedUser = userRepository.save(user);

        //then
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());


    }

}
