package com.service.unischeduleservice.sevice;


import com.service.unischeduleservice.dto.requests.news.NewsUniRequestDTO;
import com.service.unischeduleservice.dto.requests.news.NewsFacultyRequestDTO;

public interface NewsService {
    NewsUniRequestDTO scrappingData();
    NewsFacultyRequestDTO getFacultyNews(String facultyName);
}
