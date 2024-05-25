package com.service.unischeduleservice.sevices;

import com.service.unischeduleservice.dtos.NewsBothDTO;
import com.service.unischeduleservice.dtos.NewsDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Value("${url.home}")
    private String homeUrl;

    @Value("${url.faculty_of_fisheries}")
    private String facultyOfFisheries;

    @Value("${url.university_news_link_all}")
    private String universityNewsLinkAll;

    @Override
    public NewsBothDTO scrappingData() {
        return NewsBothDTO.builder()
                .universityNewsList(getUniversityNewsList())
                .universityNewsLinkAll(universityNewsLinkAll)
                .build();
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

    private List<NewsDTO> getUniversityNewsList() {
        Document document = getDocument(homeUrl);
        if(document.getElementById("ctl00_ContentPlaceHolder1_ctl00_tbViTri2") == null){
            return null;
        }

        List<NewsDTO> newsDTOList = new ArrayList<>();
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
                NewsDTO newsDTO = NewsDTO.builder()
                    .title(title)
                    .url("https://daotao.vnua.edu.vn/" + url)
                    .date(date)
                    .build();
                newsDTOList.add(newsDTO);

            }
        }
        return newsDTOList;
    }

    private List<NewsDTO> getNewsFacultyOfFisheries() {
        Document document = getDocument(facultyOfFisheries);
        if(document.getElementById("col-1943509528") == null){
            System.out.println(document.getElementById("col-1943509528"));
            return null;
        }

        List<NewsDTO> newsDTOList = new ArrayList<>();
        Elements elements = document.getElementsByClass("mh-list-post").first().getAllElements();

        System.out.println(elements);
        for(Element element: elements) {
            System.out.println(element.children());
            String url = element.attr("href");
            System.out.println(url);
        }
        return newsDTOList;
    }
}
