package com.service.unischeduleservice.apis.responses.success;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DataListResponse<T> extends BaseResponse {
    private long total;
    private List<T> data;
}
