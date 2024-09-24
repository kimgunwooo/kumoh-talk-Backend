package com.example.demo.domain.seminar_application.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SeminarApplicationRequest(
        @NotBlank(message = "이름은 빈값일 수 없습니다.") String name,
        @NotBlank(message = "학과는 빈값일 수 없습니다.") String department,
        @NotNull(message = "학년은 빈값일 수 없습니다.") int grade,
        @NotBlank(message = "학번은 빈값일 수 없습니다.") String studentId,
        @NotBlank(message = "전화번호는 빈값일 수 없습니다.") String phoneNumber,
        @NotNull(message = "희망 날짜는 빈값일 수 없습니다.") LocalDate preferredDate, // TODO. 날짜의 앞 뒤 한계 설정 필요?
        @NotBlank(message = "발표 주제는 빈값일 수 없습니다.") String presentationTopic,
        @NotBlank(message = "세미나 이름은 빈값일 수 없습니다.") String seminarName,
        @NotBlank(message = "예상 시간은 빈값일 수 없습니다.") String estimatedDuration
) {
}