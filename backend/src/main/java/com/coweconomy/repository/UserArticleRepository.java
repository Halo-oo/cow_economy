package com.coweconomy.repository;

import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserArticleRepository extends JpaRepository<UserArticle, Long> {

    /**
     * 회원이 일주일 이내에 읽은 기사 조회
     * @param userId 조회할 회원 ID
     * @return List<UserArticle> 회원이 읽은 기사 List
     * **/
    @Query("select ua from UserArticle ua where ua.user.userId= :userId and ua.regtime>= :regtime")
    List<UserArticle> findByArticle(@Param("userId") Long userId, @Param("regtime") LocalDateTime regtime);

    /**
     * 사용자 ID와 기사 ID에 해당하는 컬럼 조회
     * @param user 사용자 Entity
     * @param article 기사 Entity
     * @return Optional<UserArticle> 사용자 읽은 기사 컬럼
     * **/
    Optional<UserArticle> findByUserAndArticle(@Param("user") User user, @Param("article") Article article);

}
