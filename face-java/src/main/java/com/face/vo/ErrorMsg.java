package com.face.vo;

import com.face.utils.Constants;
import lombok.Data;

@Data
public class ErrorMsg extends BaseMsg{
    public ErrorMsg(String msg){
        this.status= Constants.FAILURE;
        this.msg=msg;
    }
}
