package com.example.demo.domain.newsletter.strategy;

import com.example.demo.domain.recruitment_board.entity.RecruitmentBoardInfo;
import com.example.demo.domain.recruitment_board.entity.vo.RecruitmentBoardType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MentoringNoticeEmailDeliveryStrategy extends BaseEmailDeliveryStrategy {
    private String type;
    private String topicTag;
    private String title;
    private String author;
    private String postUrl;

    @Override
    public String getTemplateName() {
        return "mentoring_notice";
    }

    @Override
    public Map<String, Object> getVariables() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("type", type);
        variables.put("topicTag", topicTag);
        variables.put("title", title);
        variables.put("author", author);
        variables.put("postUrl", postUrl);
        variables.put("youtubeUrl", youtubeUrl);
        variables.put("changeSubscribeUrl", changeSubscribeUrl);
        variables.put("cancelSubscribeUrl", cancelSubscribeUrl);
        return variables;
    }

    @Override
    public String getSubject() {
        return "[야밤의금오톡] '멘토링 공고' 새 글 알림";
    }

    public static MentoringNoticeEmailDeliveryStrategy create(RecruitmentBoardInfo recruitmentBoardInfo) {
        if (!recruitmentBoardInfo.getType().equals(RecruitmentBoardType.MENTORING)) {
            throw new IllegalArgumentException("멘토링에 대한 이메일 알림만 허용합니다.");
        }
        return new MentoringNoticeEmailDeliveryStrategy(
                recruitmentBoardInfo.getType().name(),
                recruitmentBoardInfo.getTag().name(),
                recruitmentBoardInfo.getTitle(),
                recruitmentBoardInfo.getUserNickname(),
                "https://kumoh-talk.com/recruitment-boards/detail?boardType=mentoring&id=" + recruitmentBoardInfo.getBoardId()
        );
    }
}
