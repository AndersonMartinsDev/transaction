package com.transaction.infrastructure.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ExceptionModel {
    private String message;
    private String code;
}