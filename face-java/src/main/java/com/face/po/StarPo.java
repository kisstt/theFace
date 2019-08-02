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
public class StarPo implements Serializable { 
	/**
	*id
	*/
	private Integer starId;

	/**
	*星座名
	*/
	private String constellation;

	/**
	*星座时间间隔
	*/
	private String time;

	/**
	*属性
	*/
	private String element;

	/**
	*创建时间
	*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdTime;

	/**
	*介绍
	*/
	private String description;

}
