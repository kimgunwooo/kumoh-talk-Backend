package com.example.demo.domain.recruitment_board.repository;

import com.example.demo.domain.recruitment_board.domain.entity.RecruitmentFormQuestion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.example.demo.domain.recruitment_board.domain.entity.QRecruitmentFormQuestion.recruitmentFormQuestion;

@RequiredArgsConstructor
public class QueryDslRecruitmentFormQuestionRepositoryImpl implements QueryDslRecruitmentFormQuestionRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<List<RecruitmentFormQuestion>> findByBoard_IdByFetchingAnswerList(Long recruitmentBoardId) {
        return Optional.of(jpaQueryFactory
                .selectFrom(recruitmentFormQuestion)
                .leftJoin(recruitmentFormQuestion.recruitmentFormChoiceAnswerList).fetchJoin()
                .where(recruitmentFormQuestion.recruitmentBoard.id.eq(recruitmentBoardId))
                .fetch());
    }
}
