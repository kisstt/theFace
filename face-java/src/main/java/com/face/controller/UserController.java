package com.face.controller;

import com.face.WebConstants;
import com.face.dao.IUserFollowDao;
import com.face.exception.BaseAppException;
import com.face.generator.utils.DateUtils;
import com.face.po.UserFollowPo;
import com.face.po.UserInfoPo;
import com.face.service.IUserService;
import com.face.utils.MD5Utils;
import com.face.vo.ReturnData;
import com.face.vo.StatusCode;
import com.face.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Resource
    private IUserService userService;


    @Resource
    private IUserFollowDao userFollowDao;

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
                userService.afterLogin(realUser, request);
                response.addHeader(WebConstants.USER_INFO_KEY, "sessionId=" + session.getId());
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
    public UserVo qryUser(HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute("user");
        return userService.qryUser(userVo);
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public UserVo updateUser(@RequestBody UserVo userVo, HttpSession session) {
        UserVo vo = (UserVo) session.getAttribute("user");
        userVo.setUserId(vo.getUserId());
        userService.update(userVo);
        return userService.qryUserByUserId(userVo);
    }

    @RequestMapping("/search")
    @ResponseBody
    public UserVo search(HttpSession session, @RequestBody UserVo userVo) {
        return userService.qryUser(userVo);
    }

    @RequestMapping("/follow")
    @ResponseBody
    public  List<UserInfoPo> follow(@RequestBody UserVo userVo, HttpSession session) throws BaseAppException {
        UserVo vo = (UserVo) session.getAttribute("user");
        UserFollowPo userFollowPo = new UserFollowPo();
        if(userVo.getUserId()==null){
            userVo=userService.findUserByNickname(userVo.getNickname());
            if(userVo==null){
                throw new BaseAppException("不存在用户");
            }
        }
        userFollowPo.setUserId(vo.getUserId());
        userFollowPo.setFollowId(userVo.getUserId());
        userService.addFollow(userFollowPo);
        return userFollowDao.qryFollow(userFollowPo);
    }

    @RequestMapping("/qryFollow")
    @ResponseBody
    public List<UserInfoPo> qryFollow(@RequestBody UserVo userVo, HttpSession session) {
        UserVo vo = (UserVo) session.getAttribute("user");
        UserFollowPo userFollowPo = new UserFollowPo();
        userFollowPo.setUserId(vo.getUserId());
        return userFollowDao.qryFollow(userFollowPo);
    }

}
