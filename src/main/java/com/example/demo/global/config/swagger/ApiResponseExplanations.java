package com.example.demo.global.config.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponseExplanations {

	ApiSuccessResponseExplanation success() default @ApiSuccessResponseExplanation();

	ApiErrorResponseExplanation[] errors() default {};
}

