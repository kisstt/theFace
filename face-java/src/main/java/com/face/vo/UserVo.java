package com.face.vo;

import com.face.po.User;
import lombok.Data;

@Data
public class UserVo extends User {

    String ip;//来源ip

    String mac;//mac地址

    String device;//来源设备
}
