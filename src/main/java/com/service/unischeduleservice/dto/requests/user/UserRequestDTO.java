package com.service.unischeduleservice.dto.requests.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "userId must be not blank!")
    private String userId;

    @Min(value = 1, message = "monthsCount must be greater than 0")
    private int monthsCount;
}
