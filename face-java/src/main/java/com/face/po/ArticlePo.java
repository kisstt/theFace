package com.face.po;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * Generator create
 * @author Generator
 * @date 2019-07-29 16:59:49
 */

@Data
public class ArticlePo implements Serializable { 
	/**
	*唯一id
	*/
	private String articleId;

	/**
	*发表人的id
	*/
	private String userId;

	/**
	*名称
	*/
	private String articleTitle;

	/**
	*文章内容
	*/
	private String articleContent;

	/**
	*创建时间
	*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
