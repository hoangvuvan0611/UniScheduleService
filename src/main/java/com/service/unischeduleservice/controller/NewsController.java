package com.service.unischeduleservice.controller;

import com.service.unischeduleservice.dto.resposes.ResponseDataList;
import com.service.unischeduleservice.dto.resposes.ResponseError;
import com.service.unischeduleservice.dto.resposes.news.NewsUniResponseDTO;
import com.service.unischeduleservice.dto.resposes.ResponseData;
import com.service.unischeduleservice.exception.ResourceNotFoundException;
import com.service.unischeduleservice.model.NewsModel;
import com.service.unischeduleservice.sevice.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
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

    @Operation(method = "GET", summary = "Get data news of university", description = "Send a request via this API to get list data news of university")
    @GetMapping
    public ResponseData<?> setUpData() {
        try {
            NewsUniResponseDTO newsUniResponseDTO = newsService.scrappingData();
            return new ResponseData<>(HttpStatus.OK.value(), "Data news university", newsUniResponseDTO);
        } catch (ResourceNotFoundException ex) {
            return new ResponseError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        }
    }

    @GetMapping("/{facultyName}")
    public ResponseData<?> getFacultyNews(@PathVariable @NotBlank(message = "facultyName must be not blank") String facultyName) {
        try {
            ResponseDataList<NewsModel> responseDataList = new ResponseDataList<>(newsService.getFacultyNews(facultyName));
            return new ResponseData<>(HttpStatus.OK.value(), facultyName, responseDataList);
        } catch (ResourceNotFoundException ex) {
            return new ResponseError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        }
    }
}
