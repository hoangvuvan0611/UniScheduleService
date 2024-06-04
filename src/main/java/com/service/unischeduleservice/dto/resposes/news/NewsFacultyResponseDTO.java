package com.service.unischeduleservice.dto.resposes.news;

import com.service.unischeduleservice.model.NewsModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsFacultyResponseDTO {
    private List<NewsModel> departmentNewsList;
    private String departmentNewsLinkAll;
}
