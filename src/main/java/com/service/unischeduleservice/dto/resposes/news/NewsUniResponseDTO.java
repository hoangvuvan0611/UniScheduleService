package com.service.unischeduleservice.dto.resposes.news;

import com.service.unischeduleservice.model.NewsModel;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NewsUniResponseDTO {
    private String universityNewsLinkAll;
    private List<NewsModel> universityNewsList;
}
