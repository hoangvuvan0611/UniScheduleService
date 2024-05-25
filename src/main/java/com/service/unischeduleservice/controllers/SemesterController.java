package com.service.unischeduleservice.controllers;

import com.service.unischeduleservice.sevices.ScraperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/semester")
public class SemesterController {

    final ScraperService scraperService;

    public SemesterController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping
    public List<String> getAllSemester() {
        return scraperService.getSemesterList();
    }
}
