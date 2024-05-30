package com.service.unischeduleservice.dto.requests.news;

import com.service.unischeduleservice.model.NewsModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsFacultyRequestDTO {
    private List<NewsModel> departmentNewsList;
    private String departmentNewsLinkAll;
}
