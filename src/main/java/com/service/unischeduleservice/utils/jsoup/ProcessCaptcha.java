package com.service.unischeduleservice.utils.jsoup;

import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.util.Map;

import static org.jsoup.Jsoup.connect;

public class ProcessCaptcha {
    @SneakyThrows
    public static Map<String, String> process(String urlCaptcha) {
        // Get page contain captcha and form for submit
        Connection.Response initialResponse;
        initialResponse = connect(urlCaptcha)
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();

        // Read captcha
        Document documentCaptcha = initialResponse.parse();
        String captcha = documentCaptcha.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblCapcha").text();

        // Lấy các trường ẩn
        String eventArgument = documentCaptcha.select("input[name=__EVENTARGUMENT]").val();
        String eventTarget = documentCaptcha.select("input[name=__EVENTTARGET]").val();
        String viewState = documentCaptcha.select("input[name=__VIEWSTATE]").attr("value");
        String viewStateGenerator = documentCaptcha.select("input[name=__VIEWSTATEGENERATOR]").attr("value");

        // Bước 2: Gửi mã captcha cùng với form mà không tải lại trang
        Connection.Response formResponse = connect(urlCaptcha)
                .data("ctl00$ContentPlaceHolder1$ctl00$txtCaptcha", captcha) // Tên trường input của captcha, cần thay đổi nếu khác
                .data("__VIEWSTATE", viewState)
                .data("__EVENTTARGET", eventTarget) // Có thể để trống nếu không cần
                .data("__EVENTARGUMENT", eventArgument)
                .data("__VIEWSTATEGENERATOR", viewStateGenerator)
                .data("ctl00$ContentPlaceHolder1$ctl00$btnXacNhan", "Vào website")
                .method(Connection.Method.POST)
                .execute();
        return formResponse.cookies();
    }
}
