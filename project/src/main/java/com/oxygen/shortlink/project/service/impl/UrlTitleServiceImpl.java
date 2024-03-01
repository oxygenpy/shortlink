package com.oxygen.shortlink.project.service.impl;

import com.oxygen.shortlink.project.service.UrlTitleService;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author LiJinLong
 * @description URL 标题接口实现层
 * @create 2024-03-01 21:29
 * @date 1.0
 */
@Service
public class UrlTitleServiceImpl implements UrlTitleService {

    /**
     * 通过URL获取网站标题
     *
     * @param url 网站地址
     * @return 网站标题
     */
    @SneakyThrows
    @Override
    public String getTitleByUrl(String url) {
        URL targetUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Document document = Jsoup.connect(url).get();
            return document.title();
        }
        return "Error while fetching title.";
    }
}
