package com.service.unischeduleservice.controller;

import com.service.unischeduleservice.dto.requests.user.UserRequestDTO;
import com.service.unischeduleservice.dto.resposes.ResponseData;
import com.service.unischeduleservice.dto.resposes.ResponseError;
import com.service.unischeduleservice.dto.resposes.DataAppResponseDTO;
import com.service.unischeduleservice.dto.requests.DataAppRequestDTO;
import com.service.unischeduleservice.sevice.ScraperService;
import com.service.unischeduleservice.sevice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Controller")
public class UserController {

    final ScraperService scraperService;
    final UserService userService;

    @PostMapping("/setupData")
    public ResponseEntity<DataAppResponseDTO> setUpData(@Valid @RequestBody DataAppRequestDTO request) {
        DataAppResponseDTO dataAppResponseDTO =  scraperService.scrappingData(request);
        if(dataAppResponseDTO == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(dataAppResponseDTO);
    }

    @Operation(method = "POST", summary = "subscribe new User", description = "Send a request via this API to subscribe new user")
    @PostMapping("/subscribe")
    public ResponseData<?> subscribeUser(@Valid @RequestBody UserRequestDTO request) {
        log.info("Request subscribe user, userId:{} , month: {}", request.getUserId(), request.getMonthsCount());
        try {
            String result = userService.subscribeUser(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Subscribe user success!", result);
        } catch (Exception e) {
            log.info("ErrorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(false, HttpStatus.BAD_REQUEST.value(), "Subscribe user fail!");
        }
    }

    @GetMapping("/semester")
    public List<String> getAllSemester() {
        return scraperService.getSemesterList();
    }
}
