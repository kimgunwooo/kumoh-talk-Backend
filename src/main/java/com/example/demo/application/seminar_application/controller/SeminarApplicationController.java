package com.example.demo.application.seminar_application.controller;

import com.example.demo.application.seminar_application.api.SeminarApplicationApi;
import com.example.demo.application.seminar_application.dto.request.SeminarApplicationRequest;
import com.example.demo.application.seminar_application.dto.request.SeminarApplicationUpdateRequest;
import com.example.demo.application.seminar_application.dto.response.SeminarApplicationResponse;
import com.example.demo.application.token.dto.TokenResponse;
import com.example.demo.domain.seminar_application.service.SeminarApplicationService;
import com.example.demo.global.aop.AssignUserId;
import com.example.demo.global.base.dto.ResponseBody;
import com.example.demo.global.base.dto.page.GlobalPageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.global.base.dto.ResponseUtil.createSuccessResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/seminar-applications")
public class SeminarApplicationController implements SeminarApplicationApi {

    private final SeminarApplicationService seminarApplicationService;

    /**
     * 세미나 신청서 작성
     */
    @AssignUserId
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ACTIVE_USER')")
    @PostMapping
    public ResponseEntity<ResponseBody<TokenResponse>> applyForSeminar(Long userId,
                                                                       @RequestBody @Valid SeminarApplicationRequest request) {
        return seminarApplicationService.applyForSeminar(userId, request.toDomain())
                .map(token -> ResponseEntity.ok(createSuccessResponse(
                        TokenResponse.create(token.getAccessToken(), token.getRefreshToken()))
                )) // 200 OK
                .orElseGet(() -> ResponseEntity.noContent().build()); // 204 No Content
    }

    /**
     * 내가 쓴 모든 세미나 신청서 목록 조회
     */
    @AssignUserId
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ACTIVE_USER')")
    @GetMapping
    public ResponseEntity<ResponseBody<GlobalPageResponse<SeminarApplicationResponse>>> getSeminarApplicationByUserId(
            Long userId,
            @PageableDefault(page=0, size=10,sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(createSuccessResponse(GlobalPageResponse.create(seminarApplicationService.getSeminarApplicationByUserId(userId, pageable)
                .map(SeminarApplicationResponse::from))));
    }

    /**
     * 내가 쓴 신청서 내용 수정
     */
    @AssignUserId
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ACTIVE_USER')")
    @PutMapping("/{seminarApplicationId}")
    public ResponseEntity<ResponseBody<Void>> updateSeminarApplication(Long userId,
                                                                       @PathVariable Long seminarApplicationId,
                                                                       @RequestBody @Valid SeminarApplicationUpdateRequest request) {
        seminarApplicationService.updateSeminarApplication(userId, seminarApplicationId, request.toDomain());
        return ResponseEntity.ok(createSuccessResponse());
    }

    /**
     * 내가 쓴 신청서 삭제
     */
    @AssignUserId
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ACTIVE_USER')")
    @DeleteMapping("/{seminarApplicationId}")
    public ResponseEntity<ResponseBody<Void>> deleteSeminarApplication(Long userId,
                                                                       @PathVariable Long seminarApplicationId) {
        seminarApplicationService.deleteSeminarApplication(userId, seminarApplicationId);
        return ResponseEntity.ok(createSuccessResponse());
    }
}
