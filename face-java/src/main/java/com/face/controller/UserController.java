package com.face.controller;

import com.face.exception.BaseAppException;
import com.face.generator.utils.DateUtils;
import com.face.service.IUserService;
import com.face.utils.MD5Utils;
import com.face.vo.ReturnData;
import com.face.vo.StatusCode;
import com.face.vo.UserSessionInfo;
import com.face.vo.UserVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Api(description = "User相关的API", value = "user")
@Controller
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Resource
    private IUserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public ReturnData login(@RequestBody UserVo user, HttpServletRequest request, HttpServletResponse response) throws BaseAppException {
        user.setPassword(MD5Utils.convert(user.getPassword()));
        UserVo realUser = userService.qryUser(user);
        if (realUser == null) {
            throw new BaseAppException("用户名不存在");
        } else {
            if (realUser.getPassword().equals(user.getPassword())) {
                HttpSession session = request.getSession();
                String sessionId = session.getId();
                userService.loginRedisOps(realUser, sessionId);
                realUser.setMac(user.getMac());
                realUser.setIp(user.getIp());
                realUser.setDevice(user.getDevice());
                userService.afterLogin(realUser,request);
                response.addCookie(new Cookie("sessionId", session.getId()));
                return new ReturnData(StatusCode.SUCCESS, "登录成功", "");
            } else {
                throw new BaseAppException("用户密码错误");
            }
        }
    }


    @RequestMapping("/register")
    @ResponseBody
    public ReturnData register(@RequestBody UserVo user, HttpServletRequest request) {
        //获得星座
        if (user.getBirthday() != null) {
            user.setConstellation(DateUtils.getConstellation(
                    DateUtils.string2Date(user.getBirthday(), "yyyy-MM-dd")));
        }
        user.setPassword(MD5Utils.convert(user.getPassword()));
        user.setIp(request.getRemoteAddr());
        user.setCreateTime(new Date());
        if (userService.addUser(user) > 0) {
            return new ReturnData(StatusCode.SUCCESS, "添加新用户", "");
        }
        return new ReturnData(StatusCode.FAILURE, "失败", "");
    }

    @RequestMapping("/qryUser")
    @ResponseBody
    public UserVo qryUser() {
        return userService.qryUser(UserSessionInfo.getUserSessionInfo().getUserVo());
    }
}
