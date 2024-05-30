package com.service.unischeduleservice.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum FacultyEnum {
    IT("Công nghệ thông tin"),
    FISHERIES("Thủy sản");

    private final String faculty;
    FacultyEnum(String faculty) {
        this.faculty = faculty;
    }

    public static FacultyEnum fromString(String facultyIn) {
        for (FacultyEnum faculty : FacultyEnum.values()) {
            if (faculty.getFaculty().equals(facultyIn)) {
                return faculty;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + facultyIn);
    }
}
