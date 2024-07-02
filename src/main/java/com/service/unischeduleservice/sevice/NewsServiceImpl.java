package com.service.unischeduleservice.sevice;

import com.service.unischeduleservice.dto.resposes.news.NewsUniResponseDTO;
import com.service.unischeduleservice.exception.ResourceNotFoundException;
import com.service.unischeduleservice.model.NewsModel;
import com.service.unischeduleservice.constant.enums.FacultyEnum;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

    @Value("${url.home}")
    private String homeUrl;

    @Value("${url.faculty_of_fisheries}")
    private String facultyOfFisheries;

    @Value("${url.faculty_of_it}")
    private String facultyOfIt;

    @Value("${url.faculty_of_accounting}")
    private String facultyOfAccounting;

    @Value("${url.university_news_link_all}")
    private String universityNewsLinkAll;

    @Override
    public NewsUniResponseDTO scrappingData() {
        return NewsUniResponseDTO.builder()
                .universityNewsList(getUniversityNewsList())
                .universityNewsLinkAll(universityNewsLinkAll)
                .build();
    }

    @Override
    public List<NewsModel> getFacultyNews(String facultyName) {
        System.out.println("'"+facultyName+"'");
        FacultyEnum facultyEnum = FacultyEnum.fromString(facultyName);
        Document document;

        return switch (facultyEnum) {
            case IT -> {
                document = getDocument(facultyOfIt);
                yield getNewsFacultyOfIt(document);
            }
            case FISHERIES -> {
                document = getDocument(facultyOfFisheries);
                yield getNewsFacultyOfFisheries(document);
            }
            case ACCOUNTING -> {
                document = getDocument(facultyOfAccounting);
                yield getNewsFacultyOfAccounting(document);
            }
            case ECONOMICS -> null;
        };
    }

    private Document getDocument(String url) {
        Document document;
        try {
            document = Jsoup.connect(url).timeout(1000*45).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return document;
    }

    private List<NewsModel> getUniversityNewsList() {
        Document document = getDocument(homeUrl);
        if(document.getElementById("ctl00_ContentPlaceHolder1_ctl00_tbViTri2") == null){
            throw new ResourceNotFoundException("Not found data news!");
        }

        List<NewsModel> newsModelList = new ArrayList<>();
        Elements elementsTable = document.getElementById(
                "ctl00_ContentPlaceHolder1_ctl00_tbViTri2").firstElementChild().getAllElements();

        for (Element elementTd: elementsTable) {
            if(elementTd.hasClass("TextTitle")) {
                elementTd.child(0);
                String title = elementTd.child(0).text().trim();
                elementTd.getElementsByClass("NgayTitle");
                String date = elementTd.getElementsByClass("NgayTitle").text();
                elementTd.attr("href");
                String url = elementTd.attr("href");

                NewsModel newsModel = NewsModel.builder()
                        .title(title)
                        .url("https://daotao.vnua.edu.vn/" + url)
                        .date(date)
                        .build();
                newsModelList.add(newsModel);

            }
        }
        return newsModelList;
    }

    private List<NewsModel> getNewsFacultyOfFisheries(Document document) {
        Elements elements = document.getElementsByClass("mh-list-post");
        if(elements == null) {
            throw new ResourceNotFoundException("Not found data fisheries!");
        }

        List<NewsModel> newsModelList = new ArrayList<>();
        NewsModel news;
        for(Element element : elements) {
            Elements listNews = element.getElementsByTag("a");
            for(Element li : listNews) {
                String text = li.text().trim();
                news = NewsModel.builder()
                        .title(text.substring(0, text.lastIndexOf(" ")))
                        .url(li.attr("href"))
                        .date(text.substring(text.lastIndexOf(" ") + 1))
                        .build();
                newsModelList.add(news);
            }
        }
        return newsModelList;
    }

    private List<NewsModel> getNewsFacultyOfIt(Document document) {
        Element element = document.getElementById("content-area");
        if(element == null) {
            throw new ResourceNotFoundException("Not found data IT!");
        }

        List<NewsModel> newsModelList = new ArrayList<>();
        Elements elements = element.firstElementChild().children();
        NewsModel newsModel;
        for(int i=0; i<elements.size(); i++) {
            if(i + 3 >= elements.size()) {
                break;
            }
            newsModel = NewsModel.builder()
                    .title(elements.get(i).getElementsByClass("title").text())
                    .date(elements.get(i+1).getElementsByClass("post-meta").text())
                    .url(elements.get(i+3).getElementsByClass("readmore").attr("href"))
                    .build();
            i+=4;
            newsModelList.add(newsModel);
        }
        return newsModelList;
    }

    private List<NewsModel> getNewsFacultyOfAccounting(Document document) {
        Element elementClass = document.getElementById("dnn_ctr5957_ModuleContent");
        if(elementClass == null) {
            throw new ResourceNotFoundException("Not found data Accounting!");
        }
        Elements elements = elementClass.getElementsByClass("news-cate-other").first().children();
        List<NewsModel> newsModelList = new ArrayList<>();
        NewsModel newsModel;
        for (Element element : elements) {
            newsModel = NewsModel.builder()
                    .title(element.text())
                    .url(Objects.requireNonNull(element.firstElementChild()).attr("href"))
                    .build();
            newsModelList.add(newsModel);
        }
        return newsModelList;
    }
}
