package com.coweconomy.service;

import com.coweconomy.api.request.UserRegisterPostReq;
import com.coweconomy.domain.user.entity.User;

/**
 *	User 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {

    /**
     * 회원가입
     * @param userRegisterInfo 회원가입 할 회원의 정보
     * @return User 회원 Entity
     * **/
    User createUser(UserRegisterPostReq userRegisterInfo);

    /**
     * 회원 Email로 User 객체 가져오기
     * @param userEmail
     * @return
     */
    User getUserByUserEmail(String userEmail);

    /**
     * RefreshToken DB 저장
     * @param userEmail
     * @param token
     * @return
     */
    boolean isTokenSaved(String userEmail, String token);
}
