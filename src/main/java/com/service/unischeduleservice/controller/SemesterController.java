package com.service.unischeduleservice.controller;

import com.service.unischeduleservice.dto.resposes.ResponseData;
import com.service.unischeduleservice.dto.resposes.ResponseDataList;
import com.service.unischeduleservice.dto.resposes.ResponseError;
import com.service.unischeduleservice.exception.ResourceNotFoundException;
import com.service.unischeduleservice.sevice.ScraperService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/semester")
@RequiredArgsConstructor
public class SemesterController {

    final ScraperService scraperService;

    @Operation(method = "GET", summary = "Get list data semester", description = "Send a request via this API to get list data semester")
    @GetMapping
    public ResponseData<?> getAllSemester() {
        log.info("Get list semester");
        try {
            ResponseDataList<String> responseDataList = new ResponseDataList<>(scraperService.getSemesterList());
            return new ResponseData<>(HttpStatus.OK.value(), "List data semester", responseDataList);
        } catch (ResourceNotFoundException ex) {
            return new ResponseError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        }
    }
}
