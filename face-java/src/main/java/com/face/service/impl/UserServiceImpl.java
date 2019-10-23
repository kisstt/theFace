package com.face.service.impl;

import com.face.WebConstants;
import com.face.dao.IUserInfoDao;
import com.face.dao.IUserLoginDao;
import com.face.po.UserInfoPo;
import com.face.po.UserLoginPo;
import com.face.service.IUserService;
import com.face.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
        UserInfoPo con=new UserInfoPo();
        con.setNickname(user.getNickname());
        List<UserInfoPo> list=userInfoDao.list(con);
        UserVo userVo=new UserVo();
        if(list.size()!=0){
            UserInfoPo userInfoPo=list.get(0);
            userVo.setPassword(userInfoPo.getPassword());
            userVo.setCreateTime(userInfoPo.getCreateTime());
            userVo.setNickname(userInfoPo.getNickname());
            userVo.setBirthday(userInfoPo.getBirthday());
            userVo.setEmail(userInfoPo.getEmail());
            userVo.setConstellation(userInfoPo.getConstellation());
            userVo.setUsername(userInfoPo.getUsername());
            userVo.setUserId(userInfoPo.getUserId());
            return userVo;
        }else {
            return null;
        }
    }

    @Override
    public int addUser(UserVo userVo) {
        try {
            userInfoDao.insert(userVo);
            Long userId = userInfoDao.list(userVo).get(0).getUserId();
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
        redisTemplate.opsForHash().put(WebConstants.USER_REDIS_KEY,sessionId,userVo);
    }

    @Override
    public void afterLogin(UserVo userVo, HttpServletRequest req) {
        UserLoginPo userLoginPo=new UserLoginPo();
        userLoginPo.setUserId(userVo.getUserId());
        userLoginPo.setUserLoginMac(userVo.getMac());
        userLoginPo.setUserLoginDevice(userVo.getDevice());
        userLoginPo.setUserLoginIp(req.getRemoteAddr());
        userLoginPo.setCreatedTime(new Date());
        userLoginDao.insert(userLoginPo);
    }
}
