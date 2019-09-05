package com.face.vo;

import com.face.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public class UserSessionInfo implements Serializable {

    private static final long serialVersionUID = 1945719258633733239L;

    private UserVo userVo;


    private HttpServletRequest req;

    private HttpServletResponse res;


    private static ThreadLocal<UserSessionInfo> sessionThread = new ThreadLocal<UserSessionInfo>() {
        @Override
        protected synchronized UserSessionInfo initialValue() {
            return new UserSessionInfo();
        }
    };


    public static UserSessionInfo getUserSessionInfo() {
        return sessionThread.get();
    }

    /**
     * 私有构造函数，不需外界实例化
     */
    private UserSessionInfo() {

    }

    /**
     * 获得用户唯一id
     *
     * @return
     */
    public Long getUserId() {
        if (Utils.isEmpty(userVo) || Utils.isEmpty(userVo.getUserId())) {
            return 0L;
        }
        return userVo.getUserId();
    }

    /**
     * 获得ip地址
     *
     * @return
     */
    public String getIpAddress() {
        if (Utils.isEmpty(req)) {
            return "";
        }
        String ips = null;
        return "";
    }


    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public HttpServletResponse getRes() {
        return res;
    }

    public void setRes(HttpServletResponse res) {
        this.res = res;
    }

    public UserVo getUserVo() {
        return userVo;
    }

    public void setUserVo(UserVo userVo) {
        this.userVo = userVo;
    }

    public void clean() {
        this.userVo = null;
        this.req = null;
        this.res = null;
    }
}
