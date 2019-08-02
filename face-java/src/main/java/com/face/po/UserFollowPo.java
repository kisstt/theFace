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
 * @date 2019-07-30 10:55:17
 */

@Data
public class UserFollowPo implements Serializable { 
	/**
	*创建时间
	*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdTime;

	/**
	*用户id
	*/
	private Integer userId;

	/**
	*跟随的id
	*/
	private Integer followId;

}
