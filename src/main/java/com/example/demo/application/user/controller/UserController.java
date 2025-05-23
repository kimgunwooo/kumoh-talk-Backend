package com.example.demo.application.user.controller;


import com.example.demo.application.token.dto.TokenResponse;
import com.example.demo.application.user.api.UserApi;
import com.example.demo.application.user.dto.request.CompleteRegistrationRequest;
import com.example.demo.application.user.dto.request.UpdateNicknameRequest;
import com.example.demo.application.user.dto.response.UserInfoResponse;
import com.example.demo.application.user.dto.response.UserProfileResponse;
import com.example.demo.domain.token.entity.Token;
import com.example.demo.domain.user.entity.CompleteRegistration;
import com.example.demo.domain.user.entity.UserProfile;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.global.aop.AssignUserId;
import com.example.demo.global.base.dto.ResponseBody;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.global.base.dto.ResponseUtil.createSuccessResponse;
import static com.example.demo.global.regex.UserRegex.NICKNAME_REGEXP;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {

    private final UserService userService;

    /**
     * GUEST 사용자에 한해서 닉네임 중복 여/부를 확인하는 api
     * TODO. 현재는 중복 체크는 GUEST 유저에게만 허용
     */
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_GUEST')")
    @GetMapping("/check-nickname")
    public ResponseEntity<ResponseBody<Void>> checkNicknameDuplicate(
            @RequestParam("nickname") @Pattern(regexp = NICKNAME_REGEXP, message = "닉네임 정규식을 맞춰주세요.") String nickname) {
        userService.checkNicknameDuplicate(nickname);
        return ResponseEntity.ok(createSuccessResponse());
    }

    /**
     * GUEST 사용자에 한해서 초기 추가정보를 입력받는 api
     */
    @AssignUserId
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_GUEST')")
    @PatchMapping("/complete-registration")
    public ResponseEntity<ResponseBody<TokenResponse>> completeRegistration(@RequestBody @Valid CompleteRegistrationRequest request,
                                                                            Long userId) {
        CompleteRegistration completeRegistration = request.toCompleteRegistrationRequest(request);
        Token token = userService.completeRegistration(userId, completeRegistration);
        return ResponseEntity.ok(createSuccessResponse(TokenResponse.create(token.getAccessToken(), token.getRefreshToken())));
    }

    /**
     * 로그아웃 api
     * TODO. blacklist 고민
     */
    @AssignUserId
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @DeleteMapping("/logout")
    public ResponseEntity<ResponseBody<Void>> logout(Long userId) {
        userService.logout(userId);
        return ResponseEntity.ok(createSuccessResponse());
    }

    /**
     * 사용자 닉네임 수정 api
     */
    @AssignUserId
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @PatchMapping("/me/nickname")
    public ResponseEntity<ResponseBody<Void>> updateNickname(@RequestBody @Valid UpdateNicknameRequest request,
                                                             Long userId) {
        userService.updateNickname(userId, request.nickname());
        return ResponseEntity.ok(createSuccessResponse());
    }

    /**
     * 기본 사용자 정보 확인 api
     */
    @AssignUserId
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<ResponseBody<UserInfoResponse>> getUserInfo(Long userId) {
        return ResponseEntity.ok(createSuccessResponse(UserInfoResponse.toUserInfoResponse(userService.getUserInfo(userId))));
    }

    /**
     * 외부로 노출되는 사용자 정보 확인 api
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseBody<UserProfileResponse>> getUserProfile(@PathVariable Long userId) {
        UserProfile userProfile = userService.getUserProfile(userId);
        return ResponseEntity.ok(createSuccessResponse(UserProfileResponse.toUserProfileResponse(userProfile)));
    }
}
