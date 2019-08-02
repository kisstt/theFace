package com.face.po;

import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 *
 * Generator create
 * @author Generator
 * @date 2019-07-30 11:02:01
 */

@Data
public class ReplyPo implements Serializable { 
	/**
	*唯一id
	*/
	private Integer replyId;

	/**
	*创建时间
	*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdTime;

	/**
	*评论id
	*/
	private Integer commentId;

	/**
	*回复内容
	*/
	private String replyContent;

}
