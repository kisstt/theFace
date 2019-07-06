package com.face.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName(value = "user_info")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    @TableField(value = "user_id")
    private Long userId;

    private String nickname;

    private String username;

    private String password;

    private String email;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @TableField(value = "avatar_url")
    private String avatarUrl;

    private String tele;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time")
    private Date createTime;

    private String constellation;
}
