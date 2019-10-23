package com.face.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class LoginResult {
    /**
     *昵称
     */
    private String nickname;

    /**
     *用户名
     */
    private String username;


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
