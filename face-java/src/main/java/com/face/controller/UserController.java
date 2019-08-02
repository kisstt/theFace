package com.face.controller;

import com.face.generator.utils.DateUtils;
import com.face.service.IUserService;
import com.face.utils.MD5Utils;
import com.face.vo.ReturnData;
import com.face.vo.StatusCode;
import com.face.vo.UserVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Api(description = "User相关的API", value = "user")
@Controller("/user")
@Slf4j
@ResponseBody
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation(value = "登录", notes = "登录校验", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "昵称"),
            @ApiImplicitParam(name = "tele", value = "电话"),
            @ApiImplicitParam(name = "email", value = "邮箱"),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    @ApiResponse(code = 200, message = "返回值", response = ReturnData.class)
    @RequestMapping("/login")
    public ReturnData login(@RequestBody UserVo user) {
        UserVo realUser = userService.qryUser(user);
        realUser.setPassword(MD5Utils.convert(user.getPassword()));
        if (realUser == null) {
            return new ReturnData(StatusCode.FAILURE, "用户名不存在", "");
        } else {
            if (realUser.getPassword().equals(user.getPassword())) {
                return new ReturnData(StatusCode.SUCCESS, "登录成功", "");
            } else {
                return new ReturnData(StatusCode.FAILURE, "用户密码错误", "");
            }
        }
    }


    @ApiOperation(value = "注册", notes = "数据库新增用户", httpMethod = "POST")
    @RequestMapping("/register")
    public ReturnData register(@RequestBody UserVo user) {
        //获得星座
        if (user.getBirthday() != null) {
            user.setConstellation(DateUtils.getConstellation(
                    DateUtils.string2Date(user.getBirthday(),"yyyy-MM-dd")));
        }
        user.setPassword(MD5Utils.convert(user.getPassword()));
        if (userService.addUser(user) >= 0) {
            return new ReturnData(StatusCode.SUCCESS, "添加新用户", "");
        }
        return new ReturnData(StatusCode.FAILURE,"失败","");
    }
}
