package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuth2MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
}
