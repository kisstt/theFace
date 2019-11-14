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
 * @date 2019-10-27 22:43:45
 */

@Data
public class UserInfoPo implements Serializable { 
	/**
	*用户唯一id 自增长id
	*/
	private Long userId;

	/**
	*昵称
	*/
	private String nickname;

	/**
	*用户名
	*/
	private String username;

	/**
	*密码(MD5加密)
	*/
	private String password;

	/**
	*邮箱
	*/
	private String email;

	/**
	*生日
	*/
	private String birthday;

	/**
	*创建时间
	*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	*星座
	*/
	private String constellation;

	/**
	*头像路径
	*/
	private String avatarUrl;

	/**
	*手机号码
	*/
	private String tele;

}
