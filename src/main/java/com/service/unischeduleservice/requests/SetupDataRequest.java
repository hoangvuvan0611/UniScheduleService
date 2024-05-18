package com.service.unischeduleservice.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SetupDataRequest {
    private String userId;
    private String semester;
}
