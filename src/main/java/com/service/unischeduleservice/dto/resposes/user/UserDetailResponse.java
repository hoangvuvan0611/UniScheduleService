package com.service.unischeduleservice.dto.resposes.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDetailResponse {
    private Date subscribeDate;
    private int monthsCount;
}
