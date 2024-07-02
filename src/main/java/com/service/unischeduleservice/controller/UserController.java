package com.service.unischeduleservice.controller;

import com.service.unischeduleservice.dto.resposes.ResponseData;
import com.service.unischeduleservice.dto.resposes.ResponseError;
import com.service.unischeduleservice.dto.resposes.DataAppResponseDTO;
import com.service.unischeduleservice.dto.requests.DataAppRequestDTO;
import com.service.unischeduleservice.exception.ResourceNotFoundException;
import com.service.unischeduleservice.sevice.ScraperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Controller")
public class UserController {

    final ScraperService scraperService;

    @Operation(method = "POST", summary = "Get data of app", description = "Send a request via this API to get data for app")
    @PostMapping
    public ResponseData<?> setUpData(@Valid @RequestBody DataAppRequestDTO request) {
        log.info("Get data app by userId={}", request.getUserId());
        System.out.println(request.getFCMToken());
        try {
            DataAppResponseDTO dataAppResponseDTO =  scraperService.scrappingData(request);
            return new ResponseData<>(HttpStatus.OK.value(), "Data app by userId = " + request.getUserId(), dataAppResponseDTO);
        }catch (ResourceNotFoundException ex) {
            return new ResponseError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
