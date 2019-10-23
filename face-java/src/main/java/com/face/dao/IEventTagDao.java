package com.face.dao;
import com.face.po.EventTagPo;
import org.springframework.dao.DataAccessException;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**头部内容结束
 *
 * Generator create
 * @author Generator
 * @date 2019-09-04 21:36:21
 */

 @Mapper
public interface IEventTagDao { 

	int insert(EventTagPo eventTagPo) throws DataAccessException;

	int update(EventTagPo eventTagPo) throws DataAccessException;

	List<EventTagPo>  list(EventTagPo eventTagPo) throws DataAccessException;

	//业务代码请写在下面，防止后续生成被覆盖

}
