package com.service.unischeduleservice.controller;

import com.service.unischeduleservice.configuration.Translator;
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
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Controller")
public class UserController {

    final ScraperService scraperService;
    final UserService userService;

    @Operation(method = "POST", summary = "Get data of app", description = "Send a request via this API to get data for app")
    @PostMapping(path ="/setupData")

    public ResponseEntity<DataAppResponseDTO> setUpData(@Valid @RequestBody DataAppRequestDTO request) {
        DataAppResponseDTO dataAppResponseDTO =  scraperService.scrappingData(request);
        if(dataAppResponseDTO == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(dataAppResponseDTO);
    }

    @Operation(method = "POST", summary = "subscribe new User", description = "Send a request via this API to subscribe new user")
    @PostMapping(path = "/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<?> subscribeUser(@Valid @RequestBody UserRequestDTO request) {
        log.info("Request subscribe user, userId:{} , month: {}", request.getUserId(), request.getMonthsCount());
        try {
            String result = userService.subscribeUser(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), Translator.toLocale("user.sub.success"), result);
        } catch (Exception ex) {
            log.error("ErrorMessage={}", ex.getMessage(), ex.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("user.sub.fail"));
        }
    }

    @Operation(method = "PUT", summary = "Update user", description = "Send a request via this API to update user")
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseData<?> updateUser(@PathVariable @NotBlank(message = "userId must be not blank!") String userId, @RequestBody UserRequestDTO request) {
        log.info("Request update userId={}", request.getUserId());
        try {
            userService.updateUser(request);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), Translator.toLocale("user.upd.success"));
        }catch (Exception ex) {
            log.error("ErrorMessage={}", ex.getMessage(), ex.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), Translator.toLocale("user.upd.fail"));
        }
    }

    @Operation(method = "DELETE", summary = "Delete user permanently", description = "Send a request via this API to delete user permanently")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseData<?> deleteUser(@PathVariable @NotBlank(message = "userId must be not blank!") String userId) {
        log.info("Delete userId={}", userId);
        try {
            userService.deleteUser(userId);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete user successfully!");
        } catch (Exception ex) {
            log.error("ErrorMessage={}", ex.getMessage(), ex.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete user fail!");
        }
    }

    @GetMapping(path = "/semester")
    public List<String> getAllSemester() {
        return scraperService.getSemesterList();
    }
}
