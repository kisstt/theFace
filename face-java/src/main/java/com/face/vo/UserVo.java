package com.face.vo;

import com.face.po.UserInfoPo;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo extends UserInfoPo implements Serializable {
    private static final long serialVersionUID = 1L;
    String ip;//来源ip

    String mac;//mac地址

    String device;//来源设备
}
