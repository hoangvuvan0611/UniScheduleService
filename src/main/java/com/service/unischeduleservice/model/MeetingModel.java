package com.service.unischeduleservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MeetingModel {
    private String roomName;
    private String practiceTeam;
    private List<String> startEndTime;
    private String week;
    private String currentWeek;
    private String lesson;
    @JsonIgnore
    private CourseModel courseModel;
}
