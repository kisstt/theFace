package com.face.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.face.mapper.UserMapper;
import com.face.po.User;
import com.face.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author FHX
 */

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean isExistUser(User user) {
        User newUser = this.qryUser(user);
        return newUser != null;
    }

    @Override
    public User findUserByNickname(String nickname) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nickname", nickname);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public User qryUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (user.getNickname() != null)
            queryWrapper.eq("nickname", user.getNickname());
        if (user.getEmail() != null)
            queryWrapper.or(i -> i.eq("email", user.getEmail()));
        if (user.getTele() != null)
            queryWrapper.or(i -> i.eq("tele", user.getTele()));
        User newUser = userMapper.selectOne(queryWrapper);
        return newUser;
    }

    @Override
    public int addUser(User user) {
        user.setCreateTime(new Date());
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }


}
