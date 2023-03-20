package com.coweconomy.service;

import com.coweconomy.api.request.UserRegisterPostReq;
import com.coweconomy.domain.user.dto.UserDto;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *	User 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
//@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired UserRepository userRepository;

    @Override
    public User createUser(UserRegisterPostReq userRegisterInfo) {
        User user = userRepository.findByUserEmail(userRegisterInfo.getUserEmail());
        if(user != null) {
            return null;
        }

        user = new User();
        user.setUserEmail(userRegisterInfo.getUserEmail());
        user.setUserNickname(userRegisterInfo.getUserNickname());
        user.setUserLevel(1);
        user.setUserExperience(0);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserByUserEmail(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail);
        return user;
    }

    @Override
    public UserDto getUserInfoByEmail(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail);
        UserDto userDto = new UserDto(user);
        return userDto;
    }


    /**
     * ID에 해당되는 User 정보 조회(가져오기)
     * @param userId 조회할 회원 ID
     * @return User 회원 Entity
     * **/
}
