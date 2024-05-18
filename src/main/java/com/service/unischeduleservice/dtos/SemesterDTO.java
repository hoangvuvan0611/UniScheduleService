package com.service.unischeduleservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SemesterDTO {
    private String semesterName;
    private String gpa;
    private String totalCredit;
    @JsonIgnore
    private UserDTO user;
    private List<ScoreDTO> scoreList;
}
