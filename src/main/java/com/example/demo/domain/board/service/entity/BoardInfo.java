package com.example.demo.domain.board.service.entity;

import java.time.LocalDateTime;

import com.example.demo.domain.user.domain.UserTarget;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardInfo {
	private final Long boardId;
	private final BoardContent boardContent;
	private final Long viewCount;
	private final Long LikeCount;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;
	private final UserTarget userTarget;
	private BoardCategoryNames boardCategoryNames;

	@Builder
	private BoardInfo(Long boardId, BoardContent boardContent, Long viewCount, Long likeCount, LocalDateTime createdAt, LocalDateTime updatedAt, UserTarget userTarget,
		BoardCategoryNames boardCategoryNames) {
		this.boardId = boardId;
		this.boardContent = boardContent;
		this.viewCount = viewCount;
		this.LikeCount = likeCount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userTarget = userTarget;
		this.boardCategoryNames = boardCategoryNames;
	}

	public void setBoardCategoryNames(BoardCategoryNames boardCategoryNames) {
		this.boardCategoryNames = boardCategoryNames;
	}
}
