package com.service.unischeduleservice.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DataAppRequestDTO {
    @NotBlank(message = "userId must be not blank")
    private String userId;
    @NotBlank(message = "semester must be not blank")
    private String semester;
}
