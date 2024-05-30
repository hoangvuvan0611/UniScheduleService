package com.service.unischeduleservice.controller;

import com.service.unischeduleservice.dto.requests.news.NewsUniRequestDTO;
import com.service.unischeduleservice.dto.requests.news.NewsFacultyRequestDTO;
import com.service.unischeduleservice.sevice.NewsService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/news")
public class NewsController {
    final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/gets")
    public ResponseEntity<NewsUniRequestDTO> setUpData() {
        NewsUniRequestDTO newsUniRequestDTO = newsService.scrappingData();
        if (newsUniRequestDTO == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(newsUniRequestDTO);
    }

    @GetMapping("/faculty={facultyName}")
    public ResponseEntity<?> getFacultyNews(@PathVariable @NotBlank(message = "facultyName must be not blank") String facultyName) {
        NewsFacultyRequestDTO newsFacultyRequestDTO = newsService.getFacultyNews(facultyName);
        return ResponseEntity.ok(newsFacultyRequestDTO);
    }
}
