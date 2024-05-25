package com.service.unischeduleservice.apis.responses.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private Date timeStamp;
    private int status;
    private String error;
    private String path;
    private String message;
}
