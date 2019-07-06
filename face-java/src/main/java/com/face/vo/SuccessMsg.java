package com.face.vo;

import com.face.utils.Constants;
import lombok.Data;

@Data
public class SuccessMsg extends BaseMsg{
    public SuccessMsg(String msg){
        this.status= Constants.SUCCESS;
        this.msg=msg;
    }
}
