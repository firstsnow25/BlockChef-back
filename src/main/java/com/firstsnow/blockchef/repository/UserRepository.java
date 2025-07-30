package com.firstsnow.blockchef.repository;

import com.firstsnow.blockchef.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // 기본적인 CRUD 작업은 MongoRepository에서 제공됩니다
    // 필요한 경우 커스텀 쿼리 메서드를 여기에 추가할 수 있습니다

    Optional<User> findByEmail(String email);

}
