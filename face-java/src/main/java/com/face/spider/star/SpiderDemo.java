package com.face.spider.star;

import com.face.po.StarPo;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SpiderDemo {

    public static void main(String[] args) throws IOException {
        String url = "http://www.xzw.com/astro/virgo/";
        Connection connection = Jsoup.connect(url);
        Connection.Response response = connection.execute();
        Document doc = Jsoup.parse(response.body());
        StarPo starPo = new StarPo();
        String time = doc.selectFirst("#wraper > div.main.mT10 > div.info_box > div.info > dl > dt > h1 > small").text();
        String starName = doc.selectFirst("#wraper > div.main.mT10 > div.info_box > div.info > dl > dt > h1 > strong").text();
        String description = doc.selectFirst("#wraper > div.main.mT10 > div.info_box > div.info > dl > dd > p").text();
        String ele = doc.selectFirst("#wraper > div.main.mT10 > div.info_box > div.info > dl > dt > h1 > em").text();
        starPo.setElement(ele);
        starPo.setTime(time);
        starPo.setConstellation(starName);
        starPo.setDescription(description);
    }
}
