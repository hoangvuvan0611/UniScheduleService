package com.service.unischeduleservice.dto.resposes;

import com.service.unischeduleservice.model.CourseModel;
import com.service.unischeduleservice.model.SemesterModel;
import lombok.*;

import java.util.List;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataAppResponseDTO {
    private String userId;
    private String userName;
    private String dateOfBirth;
    private String classOfUser;
    private String department;
    private String specialized;
    private String tuitionFee;
    private String paidTuitionFee;
    private String oldSemesterTuitionDebt;
    private String gpa;
    private String totalCredit;
    private String currentSemester;
    private String dateStartSemester;
    private String isStudent;
    private String premium;
    private List<CourseModel> courseModelList;
    private List<SemesterModel> semesterModelList;
}
