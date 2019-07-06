package com.spider.star;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.print.Doc;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(scanBasePackages = "com.face")
public class SpiderApplication {
    public static final String URL="http://www.xzw.com";
    public static Map<String,String> headers;
    {
        headers=new HashMap<>();
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
    }
    public static void main(String[] args) throws IOException {
        ApplicationContext ac=SpringApplication.run(SpiderApplication.class,args);
        HashMap<String,String> hashMap=get12StarsMap();
        for(String key:hashMap.keySet()){
            String starUrl=hashMap.get(key);
            Connection con=Jsoup.connect(URL+starUrl).headers(headers);
            Connection.Response res=con.execute();
            Document doc=Jsoup.parse(res.body());


        }
    }

    private static HashMap<String,String> get12StarsMap() {
        Connection conn= Jsoup.connect(URL).headers(headers);
        HashMap<String,String> map=new HashMap<>();
        try {
            Connection.Response response=conn.execute();
            Document doc=Jsoup.parse(response.body());
            Element nav=doc.selectFirst("#menu .a-nav");
            Elements links=nav.select("a");
            for (Element e:links){
                map.put(e.attr("title"),e.attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }



}
