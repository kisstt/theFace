package com.face.service.impl;

import com.face.dao.IUserInfoDao;
import com.face.dao.IUserLoginDao;
import com.face.po.UserInfoPo;
import com.face.po.UserLoginPo;
import com.face.service.IUserService;
import com.face.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author FHX
 */

@Slf4j
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
        UserInfoPo userInfoPo = new UserInfoPo();
        userInfoPo.setNickname(nickname);
        return (UserVo) userInfoDao.list(userInfoPo).get(0);
    }

    @Override
    public UserVo qryUser(UserVo user) {
        return (UserVo) userInfoDao.list(user).get(0);
    }

    @Override
    public int addUser(UserVo userVo) {
        try {
            userInfoDao.insert(userVo);
            Long userId = userInfoDao.list(userVo).get(0).getUserId();
            UserLoginPo userLoginPo = new UserLoginPo();
            userLoginPo.setUserLoginId(userId);
            userLoginPo.setCreatedTime(new Date());
            userLoginPo.setUserLoginIp(userVo.getIp());
            userLoginPo.setUserLoginDevice(userVo.getDevice());
            userLoginPo.setUserLoginMac(userVo.getMac());
            userLoginDao.insert(userLoginPo);
        } catch (Exception e) {
            log.error("user register error, user is" + userVo.toString());
            return 0;
        }
        return 1;
    }

    @Override
    public int updateUserInfo(UserVo user) {
        UserInfoPo userInfoPo = (UserInfoPo) user;
        return userInfoDao.update(userInfoPo);
    }


    @Override
    public int updateUserLogin(UserLoginPo userLoginPo) {
        return userLoginDao.update(userLoginPo);
    }

}
