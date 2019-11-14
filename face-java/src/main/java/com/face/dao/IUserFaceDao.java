package com.face.dao;

import com.face.po.UserFacePo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * 头部内容结束
 * <p>
 * Generator create
 *
 * @author Generator
 * @date 2019-10-23 19:49:14
 */

@Mapper
public interface IUserFaceDao {

    int insert(UserFacePo userFacePo) throws DataAccessException;

    int update(UserFacePo userFacePo) throws DataAccessException;

    List<UserFacePo> list(UserFacePo userFacePo) throws DataAccessException;

    //业务代码请写在下面，防止后续生成被覆盖

}
