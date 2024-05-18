package com.service.unischeduleservice.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NewsDTO {
    private String title;
    private String url;
    private String date;
}
