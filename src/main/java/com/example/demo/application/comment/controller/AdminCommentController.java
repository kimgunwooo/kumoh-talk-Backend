package com.example.demo.application.comment.controller;

import com.example.demo.application.comment.api.AdminCommentApi;
import com.example.demo.domain.comment.service.BoardCommentService;
import com.example.demo.domain.comment.service.RecruitmentBoardCommentService;
import com.example.demo.global.base.dto.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.global.base.dto.ResponseUtil.createSuccessResponse;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminCommentController implements AdminCommentApi {
    private final BoardCommentService boardCommentService;
    private final RecruitmentBoardCommentService recruitmentBoardCommentService;

    public AdminCommentController(
            BoardCommentService boardCommentService,
            RecruitmentBoardCommentService recruitmentBoardCommentService) {
        this.boardCommentService = boardCommentService;
        this.recruitmentBoardCommentService = recruitmentBoardCommentService;
    }

    /**
     * [관리자 전용 마크다운 게시물 댓글 삭제] <br>
     * 작성한 댓글 soft 삭제
     *
     * @apiNote 1. 삭제된 댓글도 응답으로 보내야하므로 Comment 엔티티에 SQLRestriction 처리를 해놓지 않았음
     * 2. 필터에서 유저 권한이 ADMIN인 것을 확인하면 따로 서비스 로직에서 유저 인증을 거치지 않도록 isAuthorized 매개변수를 true로 하여 서비스 메서드를 호출
     */
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @DeleteMapping("/board/comments/{commentId}")
    public ResponseEntity<ResponseBody<Void>> deleteBoardComment(@PathVariable Long commentId) {
        boardCommentService.deleteComment(null, commentId, true);
        return ResponseEntity.ok().body(createSuccessResponse());
    }

    /**
     * [관리자 전용 모집 게시물 댓글 삭제] <br>
     * 작성한 댓글 soft 삭제
     *
     * @apiNote 1. 삭제된 댓글도 응답으로 보내야하므로 Comment 엔티티에 SQLRestriction 처리를 해놓지 않았음
     * 2. 필터에서 유저 권한이 ADMIN인 것을 확인하면 따로 서비스 로직에서 유저 인증을 거치지 않도록 isAuthorized 매개변수를 true로 하여 서비스 메서드를 호출
     */
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @DeleteMapping("/recruitment-board/comments/{commentId}")
    public ResponseEntity<ResponseBody<Void>> deleteRecruitmentBoardComment(@PathVariable Long commentId) {
        recruitmentBoardCommentService.deleteComment(null, commentId, true);
        return ResponseEntity.ok().body(createSuccessResponse());
    }
}
