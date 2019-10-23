package com.face.spider.sentence;


import com.face.SpringUtils;
import com.face.dao.IPushSentenceDao;
import com.face.po.PushSentencePo;
import com.face.vo.TagCode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.Date;

/**
 * 幽默笑话
 */
@SpringBootApplication(scanBasePackages = {"com.face"}, exclude = {DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource(value = {"classpath:application.properties"})
@MapperScan(basePackages = "com.face.mapper")
public class SentenceHumorDemo {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(SentenceHumorDemo.class);
        Connection connection = Jsoup.connect("https://www.duanwenxue.com/yulu/gaoxiao/")
                .method(Connection.Method.POST);
        Connection.Response response = connection.execute();
        Document document = Jsoup.parse(response.body());
        Element element = document.selectFirst("body > div.row.inner-row > div.row-left > div.list-short-article");
        Elements ps = element.select("p");
        IPushSentenceDao pushSentenceDao = SpringUtils.getBean(IPushSentenceDao.class);
        for (Element p : ps) {
            System.out.println(p.text());
            PushSentencePo sentencePo = new PushSentencePo();
            sentencePo.setCreatedTime(new Date());
            sentencePo.setSentence(p.text());
            sentencePo.setTagId(TagCode.HUMOROUS_JOKE);
            pushSentenceDao.insert(sentencePo);
        }
        System.out.println(ps.size());

    }
}
