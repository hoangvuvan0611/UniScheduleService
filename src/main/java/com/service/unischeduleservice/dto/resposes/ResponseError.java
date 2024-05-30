package com.service.unischeduleservice.dto.resposes;

public class ResponseError extends ResponseData{
    public ResponseError(boolean success, int status, String message) {
        super(success, status, message);
    }
}
