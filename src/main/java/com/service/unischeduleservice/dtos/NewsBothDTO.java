package com.service.unischeduleservice.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NewsBothDTO {
    private List<NewsDTO> universityNewsList;
    private List<NewsDTO> departmentNewsList;
    private String universityNewsLinkAll;
    private String departmentNewsLinkAll;
}
