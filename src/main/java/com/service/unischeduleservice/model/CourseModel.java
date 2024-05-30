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
public class CourseModel {
    private String courseId;
    private String courseName;
    private String groupCode;
    private String classCode;
    private String credit;
    private String tuitionFee;
    private String testStartDateTime;
    private String testEndDateTime;
    private String testRoom;
    private String lesson;
    @JsonIgnore
    private DataAppResponseDTO user;
    private List<MeetingModel> meetingList;
}
