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
public class CommentPo implements Serializable {
    /**
     * 唯一id
     */
    private Integer commentId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /**
     * 评论人
     */
    private Integer userId;

    /**
     * 评论文章的id
     */
    private Integer articleId;

}
