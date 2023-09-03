package com.example.demo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //기본키
    private String name; //유저 이름
    private String password; //유저 비밀번호
    private String email; //유저 구글 이메일
    private String role; //유저 권한 (일반 유저, 관리지ㅏ)
    private String provider; //공급자 (google, facebook ...)
    private String providerId; //공급 아이디

    @Builder
    public Member(String name, String password, String email, String role, String provider, String providerId) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public String getRole() {
        // 여기서 역할 정보를 가져오거나 반환하는 로직을 작성해야 합니다.
        // 예를 들어, "ROLE_USER"를 반환하거나 사용자의 역할 정보를 반환하도록 구현합니다.
        return "ROLE_USER";
    }

}
