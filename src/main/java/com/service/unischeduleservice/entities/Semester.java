package com.service.unischeduleservice.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class Semester {
    private String semesterName;
    private double gpa;
    private int totalCredit;
}
