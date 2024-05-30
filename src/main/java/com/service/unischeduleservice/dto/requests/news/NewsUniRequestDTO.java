package com.service.unischeduleservice.dto.requests.news;

import com.service.unischeduleservice.model.NewsModel;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NewsUniRequestDTO {
    private List<NewsModel> universityNewsList;
    private List<NewsModel> departmentNewsList;
    private String universityNewsLinkAll;
    private String departmentNewsLinkAll;
}
