package com.service.unischeduleservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.unischeduleservice.dto.resposes.DataAppResponseDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SemesterModel {
    private String semesterName;
    private String gpa;
    private String totalCredit;
    @JsonIgnore
    private DataAppResponseDTO user;
    private List<ScoreModel> scoreModelList;
}
