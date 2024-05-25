package com.service.unischeduleservice.apis.responses.success;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse<T> extends BaseResponse {
    private T data;
}
