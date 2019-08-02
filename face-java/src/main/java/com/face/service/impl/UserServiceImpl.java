package com.face.service.impl;

import com.face.dao.IUserInfoDao;
import com.face.dao.IUserLoginDao;
import com.face.service.IUserService;
import com.face.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author FHX
 */

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserInfoDao userInfoDao;

    @Resource
    private IUserLoginDao userLoginDao;
    @Override
    public boolean isExistUser(UserVo user) {
        UserVo newUser = this.qryUser(user);
        return newUser != null;
    }

    @Override
    public UserVo findUserByNickname(String nickname) {
//        QueryWrapper<U> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("nickname", nickname);
//        User user = userMapper.selectOne(queryWrapper);
        return null;
    }

    @Override
    public UserVo qryUser(UserVo user) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        if (user.getNickname() != null)
//            queryWrapper.eq("nickname", user.getNickname());
//        if (user.getEmail() != null)
//            queryWrapper.or(i -> i.eq("email", user.getEmail()));
//        if (user.getTele() != null)
//            queryWrapper.or(i -> i.eq("tele", user.getTele()));
//        User newUser = userMapper.selectOne(queryWrapper);
//        return newUser;
        return null;
    }

    @Override
    public int addUser(UserVo user) {
//        user.setCreateTime(new Date());
//        return userMapper.insert(user);
        return 0;
    }

    @Override
    public int updateUser(UserVo user) {
        return 0;
    }


}
