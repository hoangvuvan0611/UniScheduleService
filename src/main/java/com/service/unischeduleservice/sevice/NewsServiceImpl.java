package com.service.unischeduleservice.sevice;

import com.service.unischeduleservice.dto.resposes.news.NewsUniResponseDTO;
import com.service.unischeduleservice.exception.ResourceNotFoundException;
import com.service.unischeduleservice.model.NewsModel;
import com.service.unischeduleservice.dto.resposes.news.NewsFacultyResponseDTO;
import com.service.unischeduleservice.constant.enums.FacultyEnum;
import com.service.unischeduleservice.model.pool.NewsModelPool;
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

@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

    @Value("${url.home}")
    private String homeUrl;

    @Value("${url.faculty_of_fisheries}")
    private String facultyOfFisheries;

    @Value("${url.faculty_of_it}")
    private String facultyOfIt;

    @Value("${url.university_news_link_all}")
    private String universityNewsLinkAll;

    private final NewsModelPool newsModelPool = NewsModelPool.getInstance();

    @Override
    public NewsUniResponseDTO scrappingData() {
        return NewsUniResponseDTO.builder()
                .universityNewsList(getUniversityNewsList())
                .universityNewsLinkAll(universityNewsLinkAll)
                .build();
    }

    @Override
    public NewsFacultyResponseDTO getFacultyNews(String facultyName) {
        NewsFacultyResponseDTO facultyDTO = new NewsFacultyResponseDTO();
        FacultyEnum facultyEnum = FacultyEnum.fromString(facultyName);
        Document document;

        return switch (facultyEnum) {
            case IT -> {
                document = getDocument(facultyOfIt);
                facultyDTO.setDepartmentNewsList(getNewsFacultyOfIt(document));
                yield facultyDTO;
            }
            case FISHERIES -> {
                document = getDocument(facultyOfFisheries);
                facultyDTO.setDepartmentNewsList(getNewsFacultyOfFisheries(document));
                yield facultyDTO;
            }
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
//                .first()
//                .firstElementChild()
//                .getAllElements();

        System.out.println(elements.size());
        List<NewsModel> newsModelList = new ArrayList<>();
        NewsModel newsModel;
//        for(Element element: elements) {
//            String title = element.child(0).text().trim();
//            String url = element.attr("href");
//            System.out.println(url);
//            newsModel = newsModelPool.getNewsModel(
//                    title.substring(0, title.indexOf("(") -1 ),
//                    url,
//                    title.substring(title.indexOf("(") + 1, title.indexOf(")"))
//            );
//            System.out.println(newsModel.hashCode());
//            newsModelList.add(newsModel);
//        }
        return newsModelList;
    }

    private List<NewsModel> getNewsFacultyOfIt(Document document) {
        if(document.getElementById("col-1943509528") == null){
            log.info("hoang");
            log.info(document.getElementById("col-1943509528").toString());
            return null;
        }

        List<NewsModel> newsModelList = new ArrayList<>();
//        Elements elements = document.getElementsByClass("mh-list-post").first().getAllElements();
//
//        System.out.println(elements);
//        for(Element element: elements) {
//            System.out.println(element.children());
//            String url = element.attr("href");
//            System.out.println(url);
//        }
        return newsModelList;
    }
}
