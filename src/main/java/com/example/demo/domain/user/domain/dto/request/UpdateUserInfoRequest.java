package com.example.demo.domain.user.domain.dto.request;

import com.example.demo.domain.user.domain.vo.Role;
import com.example.demo.global.aop.valid.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.example.demo.global.regex.S3UrlRegex.S3_PROFILE_FILE_URL;
import static com.example.demo.global.regex.UserRegex.NICKNAME_REGEXP;

public record UpdateUserInfoRequest(
        @Pattern(regexp = NICKNAME_REGEXP, message = "닉네임 정규식을 맞춰주세요.") String nickname,
        @NotBlank(message = "이름은 빈값일 수 없습니다.") String name,
        @Pattern(regexp = S3_PROFILE_FILE_URL) String profileImageUrl,
        @ValidEnum(enumClass = Role.class) Role role
) {
}
