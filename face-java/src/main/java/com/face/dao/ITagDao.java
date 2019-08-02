package com.face.dao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import java.util.List;
import com.face.po.TagPo;

/**头部内容结束
 *
 * Generator create
 * @author Generator
 * @date 2019-07-30 10:55:17
 */
@Mapper
public interface ITagDao { 

	int insert(TagPo tagPo) throws DataAccessException;

	int update(TagPo tagPo) throws DataAccessException;

	List<TagPo>  list(TagPo tagPo) throws DataAccessException;

	//业务代码请写在下面，防止后续生成被覆盖

}

