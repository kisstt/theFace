package com.face.dao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import java.util.List;
import com.face.po.UserLoginPo;

/**头部内容结束
 *
 * Generator create
 * @author Generator
 * @date 2019-10-23 14:14:56
 */

 @Mapper
public interface IUserLoginDao { 

	int insert(UserLoginPo userLoginPo) throws DataAccessException;

	int update(UserLoginPo userLoginPo) throws DataAccessException;

	List<UserLoginPo>  list(UserLoginPo userLoginPo) throws DataAccessException;

	//业务代码请写在下面，防止后续生成被覆盖

}

