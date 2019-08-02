package com.face.spider.star;

import com.face.po.StarPo;
import com.face.service.impl.StarServiceImpl;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"com.face"}, exclude = {DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource(value = {"classpath:application.properties"})
@MapperScan(basePackages = "com.face.mapper")
public class SpiderApplication {
    public static final String URL = "http://www.xzw.com";
    public static Map<String, String> headers;

    static {
        headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext ac = SpringApplication.run(SpiderApplication.class, args);
        HashMap<String, String> hashMap = get12StarsMap();
        StarServiceImpl starService = ac.getBean(StarServiceImpl.class);
        for (String key : hashMap.keySet()) {
            String starUrl = URL + hashMap.get(key);
            StarPo starPo = getStarPo(starUrl);
            starPo.setCreatedTime(new Date());
            starService.insert(starPo);
        }
    }

    private static HashMap<String, String> get12StarsMap() {
        Connection conn = Jsoup.connect(URL).headers(headers);
        HashMap<String, String> map = new HashMap<>();
        try {
            Connection.Response response = conn.execute();
            Document doc = Jsoup.parse(response.body());
            Element nav = doc.selectFirst("#menu .a-nav");
            Elements links = nav.select("a");
            for (Element e : links) {
                map.put(e.attr("title"), e.attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    private static StarPo getStarPo(String url) throws IOException {
        Connection connection = Jsoup.connect(url).headers(headers);
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
        return starPo;
    }


}
