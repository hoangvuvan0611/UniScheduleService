package com.service.unischeduleservice.constant.enums;

import lombok.Getter;

@Getter
public enum FacultyEnum {
    IT("Công nghệ thông tin"),
    FISHERIES("Thủy sản"),
    ACCOUNTING("Kế toán & Quản trị KD"),
    ECONOMICS("Kinh tế & PTNT"),
    VETERINARY_MEDICINE("Thú y"),
    NATURAL_RESOURCES_ENVIRONMENT("Tài nguyên và Môi trường"),
    TOURISM_FOREIGN_LANGUAGES("Du lịch và Ngoại ngữ"),
    FOOD_TECHNOLOGY("Công nghệ thực phẩm"),
    MECHANICAL_ELECTRICAL_ENGINEERING("Cơ - Điện"),
    SOCIAL_SCIENCES("Khoa học xã hội"),
    AGRONOMY("Nông học"),
    BIOTECHNOLOGY("Công nghệ sinh học");

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
