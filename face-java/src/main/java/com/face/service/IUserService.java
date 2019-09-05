package com.face.service;

import com.face.po.UserLoginPo;
import com.face.vo.UserVo;

public interface IUserService {

    boolean isExistUser(UserVo user);

    UserVo findUserByNickname(String nickname);

    int addUser(UserVo user);

    UserVo qryUser(UserVo user);

    int updateUserInfo(UserVo user);

    int updateUserLogin(UserLoginPo userLoginPo);
}
