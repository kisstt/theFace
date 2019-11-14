package com.face.dao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import java.util.List;
import com.face.po.UserInfoPo;

/**头部内容结束
 *
 * Generator create
 * @author Generator
 * @date 2019-10-27 22:43:45
 */

 @Mapper
public interface IUserInfoDao { 

	int insert(UserInfoPo userInfoPo) throws DataAccessException;

	int update(UserInfoPo userInfoPo) throws DataAccessException;

	List<UserInfoPo>  list(UserInfoPo userInfoPo) throws DataAccessException;

	//业务代码请写在下面，防止后续生成被覆盖

}

