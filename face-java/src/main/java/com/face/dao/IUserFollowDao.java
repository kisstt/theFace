package com.face.dao;

import com.face.po.UserFollowPo;
import com.face.po.UserInfoPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * 头部内容结束
 * <p>
 * Generator create
 *
 * @author Generator
 * @date 2019-10-27 21:36:58
 */

@Mapper
public interface IUserFollowDao {

    int insert(UserFollowPo userFollowPo) throws DataAccessException;

    int update(UserFollowPo userFollowPo) throws DataAccessException;

    int delete(UserFollowPo userFollowPo) throws DataAccessException;

    List<UserFollowPo> list(UserFollowPo userFollowPo) throws DataAccessException;


    List<UserInfoPo> qryFollow(UserFollowPo userFollowPo) throws DataAccessException;
    //业务代码请写在下面，防止后续生成被覆盖

}

