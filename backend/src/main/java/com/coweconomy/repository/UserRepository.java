package com.coweconomy.repository;

import com.coweconomy.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User 모델 관련 DB 쿼리 생성을 위한 JPA Query Method 인터페이스 정의
 * */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 사용자 정보 User 테이블에 추가
     * @param user User : 테이블 정보
     */
    User save(User user);

    /**
     * ID에 해당되는 User 조회
     * @param userId 조회할 회원 ID
     * @return User 회원 Entity
     * **/
    Optional<User> findByUserEmail(String userEmail);
}
