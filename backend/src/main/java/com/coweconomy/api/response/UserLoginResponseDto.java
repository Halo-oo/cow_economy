package com.coweconomy.api.response;

import com.coweconomy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API 요청에 대한 응답값(Response) 정의
 */
@Getter
@Setter
public class UserLoginResponseDto extends BaseResponseBody  {

    @ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
    String token;

    @ApiModelProperty(name="사용자 등록 여부", example="true")
    boolean isRegistered;

    public static UserLoginResponseDto of(Integer statusCode, String message, String token, boolean isRegistered) {
        UserLoginResponseDto response = new UserLoginResponseDto();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setToken(token);
        response.setRegistered(isRegistered);

        return response;
    }
}
