package com.service.unischeduleservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private Date timeStamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
