package com.coweconomy.service;

import com.coweconomy.api.controller.UserController;
import com.coweconomy.domain.user.dto.UserDto;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.repository.UserArticleMemoRepository;
import com.coweconomy.repository.UserArticleRepository;
import com.coweconomy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

    @Autowired
    UserArticleMemoRepository userArticleMemoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserArticleRepository userArticleRepository;
    
    /**
     * userId에 해당하는 모든 memo 가져오기
     * @param userId 사용자 ID
     * **/
    public List<UserArticleMemo> getUserMemo(Long userId) {
        return userArticleMemoRepository.findAllByUser_UserId(userId);
    }

    /**
     * userId에 해당되는 User 정보 조회
     * @param Long 회원 id(seq)
     * @return User 회원 Entity
     * **/
    public UserDto getUserByUserId(Long userId) {
        Optional<User> user = userRepository.findByUserId(userId);

        // S) user 정보 return
        if (user.isPresent()) {
            // Level에 따라 F ~ S 중 하나로 mapping (ASCII CODE)
            Map<Integer, Integer> levelMap = new HashMap<>();
            levelMap.put(1, 46); // F
            levelMap.put(2, 45); // E
            levelMap.put(3, 44); // D
            levelMap.put(4, 43); // C
            levelMap.put(5, 42); // B
            levelMap.put(6, 41); // A
            levelMap.put(7, 53); // S
            user.get().setUserLevel(levelMap.getOrDefault(user.get().getUserLevel(), 0));

            return new UserDto(user.get());
        }
        // F)
        return null;
    }

    /**
     * userId에 해당되는 6개월 간 읽은 기사 수 조회
     * @param Long 회원 id(seq)
     * @return List<Integer>
     * **/
    public List<Object[]> getReadArticleCount(Long userId) {
        try {
            LocalDateTime sixMonthAgo = LocalDateTime.now().minusMonths(6);
            return userArticleRepository.findByUserUserIdAndRegtimeBefore(userId, sixMonthAgo);
        }
        catch (Exception exception) {
            logger.error(exception.toString());
            return null;
        }
    }

}
