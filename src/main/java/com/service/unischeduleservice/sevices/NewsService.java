package com.service.unischeduleservice.sevices;


import com.service.unischeduleservice.dtos.NewsBothDTO;
import com.service.unischeduleservice.dtos.NewsFacultyDTO;

public interface NewsService {
    NewsBothDTO scrappingData();
    NewsFacultyDTO getFacultyNews(String facultyName);
}
