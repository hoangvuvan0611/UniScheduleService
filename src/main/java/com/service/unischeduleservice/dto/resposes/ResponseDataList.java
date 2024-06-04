package com.service.unischeduleservice.dto.resposes;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class ResponseDataList<T> {
    int total;
    List<T> dataList;

    public ResponseDataList(List<T> dataList) {
        this.dataList = dataList;
        total = dataList.size();
    }
}
