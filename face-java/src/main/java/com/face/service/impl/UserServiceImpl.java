package com.face.service.impl;

import com.alibaba.fastjson.JSON;
import com.face.WebConstants;
import com.face.dao.IUserFollowDao;
import com.face.dao.IUserInfoDao;
import com.face.dao.IUserLoginDao;
import com.face.po.UserFollowPo;
import com.face.po.UserInfoPo;
import com.face.po.UserLoginPo;
import com.face.service.IUserService;
import com.face.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private IUserFollowDao userFollowDao;

    @Override
    public boolean isExistUser(UserVo user) {
        UserVo newUser = this.qryUser(user);
        return newUser != null;
    }

    @Override
    public UserVo findUserByNickname(String nickname) {
        UserInfoPo userInfoPo = new UserInfoPo();
        userInfoPo.setNickname(nickname);
        UserInfoPo userInfoPo1=
                userInfoDao.list(userInfoPo).get(0);
        UserVo userVo=new UserVo();
        userVo.setUserId(userInfoPo1.getUserId());
        userVo.setNickname(userInfoPo1.getNickname());
        userVo.setBirthday(userInfoPo1.getBirthday());
        userVo.setConstellation(userInfoPo1.getConstellation());
        userVo.setCreateTime(userInfoPo1.getCreateTime());
        userVo.setPassword(userInfoPo1.getPassword());
        return userVo;
    }

    @Override
    public UserVo qryUser(UserVo user) {
        UserInfoPo con = new UserInfoPo();
        con.setNickname(user.getNickname());
        List<UserInfoPo> list = userInfoDao.list(con);
        UserVo userVo = new UserVo();
        if (list.size() != 0) {
            UserInfoPo userInfoPo = list.get(0);
            userVo.setCreateTime(userInfoPo.getCreateTime());
            userVo.setPassword(userInfoPo.getPassword());
            userVo.setNickname(userInfoPo.getNickname());
            userVo.setBirthday(userInfoPo.getBirthday());
            userVo.setEmail(userInfoPo.getEmail());
            userVo.setTele(user.getTele());
            userVo.setConstellation(userInfoPo.getConstellation());
            userVo.setUsername(userInfoPo.getUsername());
            userVo.setUserId(userInfoPo.getUserId());
            userVo.setAvatarUrl(userInfoPo.getAvatarUrl());
            return userVo;
        } else {
            return null;
        }
    }

    @Override
    public int update(UserVo userVo) {
        return userInfoDao.update(userVo);
    }

    @Override
    public UserVo qryUserByUserId(UserVo user) {
        UserInfoPo con = new UserInfoPo();
        con.setUserId(user.getUserId());
        List<UserInfoPo> list = userInfoDao.list(con);
        UserVo userVo = new UserVo();
        if (list.size() != 0) {
            UserInfoPo userInfoPo = list.get(0);
            userVo.setPassword(userInfoPo.getPassword());
            userVo.setCreateTime(userInfoPo.getCreateTime());
            userVo.setNickname(userInfoPo.getNickname());
            userVo.setBirthday(userInfoPo.getBirthday());
            userVo.setEmail(userInfoPo.getEmail());
            userVo.setConstellation(userInfoPo.getConstellation());
            userVo.setUsername(userInfoPo.getUsername());
            userVo.setUserId(userInfoPo.getUserId());
            userVo.setAvatarUrl(userInfoPo.getAvatarUrl());
            userVo.setTele(userInfoPo.getTele());
            return userVo;
        } else {
            return null;
        }
    }

    @Override
    public void addFollow(UserFollowPo userFollowPo) {
        List<UserFollowPo> list = userFollowDao.list(userFollowPo);
        if (list.size() == 0) {
            userFollowPo.setCreatedTime(new Date());
            userFollowDao.insert(userFollowPo);
        } else {
            delFollow(userFollowPo);
        }
    }


    public void delFollow(UserFollowPo userFollowPo) {
        userFollowDao.delete(userFollowPo);
    }

    @Override
    public int addUser(UserVo userVo) {
        try {
            int randomNum=(int)(Math.random()*10)%4+1;
            userVo.setAvatarUrl("http://134.175.100.101:8012/images/avator/touxiang"+randomNum+".jpg");
            userInfoDao.insert(userVo);
            UserVo con = new UserVo();
            con.setNickname(userVo.getNickname());
            Long userId = userInfoDao.list(con).get(0).getUserId();
            UserLoginPo userLoginPo = new UserLoginPo();
            userLoginPo.setUserId(userId);
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


    @Override
    public void loginRedisOps(UserVo userVo, String sessionId) {
        String value = JSON.toJSONString(userVo);
        redisTemplate.opsForHash().put(WebConstants.USER_REDIS_KEY, sessionId, value);
    }

    @Override
    public void afterLogin(UserVo userVo, HttpServletRequest req) {
        UserLoginPo userLoginPo = new UserLoginPo();
        userLoginPo.setUserId(userVo.getUserId());
        userLoginPo.setUserLoginMac(userVo.getMac());
        userLoginPo.setUserLoginDevice(userVo.getDevice());
        userLoginPo.setUserLoginIp(req.getRemoteAddr());
        userLoginPo.setCreatedTime(new Date());
        userLoginDao.insert(userLoginPo);
    }
}
