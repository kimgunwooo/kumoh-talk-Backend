package com.example.demo.application.comment.dto.vo;

public enum CommentTargetBoardType {
    BASIC,
    RECRUITMENT;

    public static final String errorMsg = "게시물 타입은 basic, recruitment 중 하나여야 합니다.";
}
