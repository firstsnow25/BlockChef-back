package com.firstsnow.blockchef.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// mongodb 에서 해당 클래스가 어떤 컬렉션에 매핑되는지를 지정
// spring이 User 객체를 처음 저장할 때 자동으로 users 컬렉션을 생성
@Document(collection = "users")
@Getter @Setter
public class User {
    @Id
    private String id; // 자동 생성되는 MongoDB ObjectId
    private String name;
    private String email;
    private String password;
}
