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
 * @date 2019-10-23 19:49:14
 */

@Data
public class UserFacePo implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户面部图片地址
     */
    private String userFaceLocation;

    /**
     * 用户检测结果
     */
    private String userFaceDetectResult;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

}
