package com.service.unischeduleservice.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsModel {
    private String title;
    private String url;
    private String date;
}
