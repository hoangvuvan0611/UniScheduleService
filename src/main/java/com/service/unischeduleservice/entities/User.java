package com.service.unischeduleservice.entities;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String userName;
    private String classOfUser;
    private String department;
    private String specialized;
    private int tuitionFee;
    private int paidTuitionFee;
    private int outstandingTuitionFee;
    private int previousOutstandingTuitionFee;
    private double gpa;
    private int totalCredit;
    private String currentSemester;
    private String dateStartSemester;
    private String dateEndSemester;
}
