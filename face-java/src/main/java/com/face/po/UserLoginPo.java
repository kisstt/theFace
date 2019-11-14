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
 * @date 2019-10-23 14:14:56
 */

@Data
public class UserLoginPo implements Serializable {
    /**
     * 主键
     */
    private Integer userLoginId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /**
     * 用户登录ip
     */
    private String userLoginIp;

    /**
     * 用户设备
     */
    private String userLoginDevice;

    /**
     * 用户mac地址
     */
    private String userLoginMac;

    private Long userId;

    private String userLoginLocation;

}
