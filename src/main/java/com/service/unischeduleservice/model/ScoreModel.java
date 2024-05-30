package com.service.unischeduleservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ScoreModel {
    private String scoreName;
    private String gpa;
    private String score;
    private String credit;
    @JsonIgnore
    private SemesterModel semesterModel;
}
