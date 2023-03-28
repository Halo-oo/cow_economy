package com.coweconomy.api.response;

import com.coweconomy.domain.user.dto.UserArticleMemoDto;
import com.coweconomy.domain.user.dto.UserDto;
import com.coweconomy.domain.user.entity.User;
import lombok.Data;

import java.util.List;

/**
 * 유저 마이페이지 API 요청에 대한 응답값(Response) 정의
 */
@Data
public class UserInfoResponseDto {

    // 회원 정보
    UserDto user;

    // 회원이 작성한 memo
    List<UserArticleMemoDto> memoDtoList;

    // 6개월 간 읽은 기사 수 정보
    List<Object[]> articleCntList;

    public UserInfoResponseDto(UserDto user, List<UserArticleMemoDto> memoDtoList, List<Object[]> articleCntList) {
        this.user = user;
        this.memoDtoList = memoDtoList;
        this.articleCntList = articleCntList;
    }
}
