package com.face.service;


import com.chat.netty.chat.ChatInfo;
import com.face.vo.UserVo;

public interface IUserService {

    boolean isExistUser(UserVo user);

    UserVo findUserByNickname(String nickname);

    int addUser(UserVo user);

    UserVo qryUser(UserVo user);

    int updateUser(UserVo user);
}
