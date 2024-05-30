package com.service.unischeduleservice.dto.resposes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse<T> {
    private int page;
    private int size;
    private long total;
    private T items;
}
