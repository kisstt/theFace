package com.face.service;

import com.face.po.UserFollowPo;
import com.face.po.UserLoginPo;
import com.face.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {

    boolean isExistUser(UserVo user);

    UserVo findUserByNickname(String nickname);

    int addUser(UserVo user);

    UserVo qryUser(UserVo user);

    int update(UserVo userVo);

    void loginRedisOps(UserVo userVo,String sessionId);

    void afterLogin(UserVo userVo, HttpServletRequest req);

    int updateUserInfo(UserVo user);

    int updateUserLogin(UserLoginPo userLoginPo);

    UserVo qryUserByUserId(UserVo user);


    void addFollow(UserFollowPo userFollowPo);
}
