package com.service.unischeduleservice.sevice;


import com.service.unischeduleservice.dto.resposes.news.NewsUniResponseDTO;
import com.service.unischeduleservice.dto.resposes.news.NewsFacultyResponseDTO;

public interface NewsService {
    NewsUniResponseDTO scrappingData();
    NewsFacultyResponseDTO getFacultyNews(String facultyName);
}
