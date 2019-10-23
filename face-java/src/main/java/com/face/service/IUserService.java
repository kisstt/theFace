package com.face.service;

import com.face.po.UserLoginPo;
import com.face.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {

    boolean isExistUser(UserVo user);

    UserVo findUserByNickname(String nickname);

    int addUser(UserVo user);

    UserVo qryUser(UserVo user);

    void loginRedisOps(UserVo userVo,String sessionId);

    void afterLogin(UserVo userVo, HttpServletRequest req);

    int updateUserInfo(UserVo user);

    int updateUserLogin(UserLoginPo userLoginPo);
}
