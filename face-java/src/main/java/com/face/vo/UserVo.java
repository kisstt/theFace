package com.face.vo;

import com.face.po.UserInfoPo;
import lombok.Data;
@Data
public class UserVo extends UserInfoPo {

    String ip;//来源ip

    String mac;//mac地址

    String device;//来源设备
}
