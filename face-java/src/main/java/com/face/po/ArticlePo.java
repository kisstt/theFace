package com.face.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Generator create
 *
 * @author Generator
 * @date 2019-10-27 21:36:58
 */

@Data
public class ArticlePo implements Serializable {
    /**
     * 唯一id
     */
    private Integer articleId;

    /**
     * 发表人的id
     */
    private Long userId;

    /**
     * 名称
     */
    private String articleTitle;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    private String searchSql;
}
