package com.service.unischeduleservice.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NewsModel {
    private String title;
    private String url;
    private String date;
}
