package com.service.unischeduleservice.controllers;

import com.service.unischeduleservice.dtos.UserDTO;
import com.service.unischeduleservice.apis.requests.UserDataRequest;
import com.service.unischeduleservice.sevices.ScraperService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    final ScraperService scraperService;

    public UserController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @PostMapping("/setupData")
    public ResponseEntity<UserDTO> setUpData(@Valid @RequestBody UserDataRequest request) {
        UserDTO userDTO =  scraperService.scrappingData(request);
        if(userDTO == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/semester")
    public List<String> getAllSemester() {
        return scraperService.getSemesterList();
    }
}
