package com.example.demo.domain.recruitment_board.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "모집 게시물 요청")
public class RecruitmentBoardInfoAndFormRequest {
    @Schema(description = "모집 게시물 정보")
    @NotNull(message = "게시물을 등록해야합니다.")
    @Valid
    private RecruitmentBoardInfoRequest board;

    @Schema(description = "모집 게시물 신청폼")
    @NotNull(message = "질문이 널이면 안됩니다.")
    @Size(min = 1, message = "질문을 1개 이상 등록해야합니다.")
    @Valid
    private List<RecruitmentFormQuestionRequest> form = new ArrayList<>();
}
