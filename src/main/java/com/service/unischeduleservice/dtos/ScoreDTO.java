package com.service.unischeduleservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ScoreDTO {
    private String scoreName;
    private String gpa;
    private String score;
    private String credit;
    @JsonIgnore
    private SemesterDTO semester;
}
