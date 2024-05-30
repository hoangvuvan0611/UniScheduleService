package com.service.unischeduleservice.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsFacultyDTO {
    private List<NewsDTO> departmentNewsList;
    private String departmentNewsLinkAll;
}
