package com.service.unischeduleservice.dto.resposes;

import lombok.Getter;

@Getter
public class ResponseData<T> {
    private final int status;
    private final String message;
    private T data;

    /**
     * Response data for the Api to retrieve data successfully. For GET, POST only
     * @param status
     * @param message
     * @param data
     */
    public ResponseData(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * Response data for the Api executes successfully (PUT, PATCH, DELETE) of getting error
     * @param status
     * @param message
     */
    public ResponseData(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
