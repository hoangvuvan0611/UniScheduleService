package com.service.unischeduleservice.entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Course {
    private String courseId;
    private String courseName;
    private String group;
    private int credit;
    private int tuitionFee;
    private String testStartDateTime;
    private String testEndDateTime;
    private String testAddress;
}
