package com.face.service;

import com.face.po.User;

public interface IUserService {

    boolean isExistUser(User user);

    User findUserByNickname(String nickname);

    int addUser(User user);

    User qryUser(User user);

    int updateUser(User user);
}
