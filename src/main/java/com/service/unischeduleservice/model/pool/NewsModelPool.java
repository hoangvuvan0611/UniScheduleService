package com.service.unischeduleservice.model.pool;

import com.service.unischeduleservice.model.NewsModel;

import java.util.LinkedList;
import java.util.Queue;

public class NewsModelPool {
    private static final int MAX_POOL_SIZE = 10;
    private static NewsModelPool instance;
    private final Queue<NewsModel> newsModelPool;

    private NewsModelPool(){
        newsModelPool = new LinkedList<>();
    }

    public static NewsModelPool getInstance(){
        if(instance == null){
            instance = new NewsModelPool();
        }
        return instance;
    }

    public NewsModel getNewsModel(String title, String url, String date){
        if(newsModelPool.isEmpty()) {
            return new NewsModel(title,url,date);
        } else {
            NewsModel newsModel = newsModelPool.poll();
            newsModel.setTitle(title);
            newsModel.setUrl(url);
            newsModel.setDate(date);
            return newsModel;
        }
    }

    public void releaseNews(NewsModel newsModel){
        if(newsModelPool.size() < MAX_POOL_SIZE) {
            newsModel.setTitle(null);
            newsModel.setUrl(null);
            newsModel.setDate(null);
            newsModelPool.offer(newsModel);
        }
    }
}
