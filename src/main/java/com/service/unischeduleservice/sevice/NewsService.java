package com.service.unischeduleservice.sevice;


import com.service.unischeduleservice.dto.resposes.news.NewsUniResponseDTO;
import com.service.unischeduleservice.model.NewsModel;

import java.util.List;

public interface NewsService {
    NewsUniResponseDTO scrappingData();
    List<NewsModel> getFacultyNews(String facultyName);
}
