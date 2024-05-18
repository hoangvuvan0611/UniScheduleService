package com.service.unischeduleservice.controllers;

import com.service.unischeduleservice.dtos.NewsBothDTO;
import com.service.unischeduleservice.sevices.NewsService;
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
    public ResponseEntity<NewsBothDTO> setUpData() {
        NewsBothDTO newsBothDTO =  newsService.scrappingData();
        if(newsBothDTO == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(newsBothDTO);
    }
}
