package com.service.unischeduleservice.model;

import lombok.*;

@Builder
@Getter
@Setter
public class NewsModel {
    private String title;
    private String url;
    private String date;
    private NewsModel(){};
    public static NewsModel createNews(String title, String url, String date){
        return null;
    }
}
