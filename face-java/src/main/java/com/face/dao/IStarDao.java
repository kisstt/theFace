package com.face.dao;
import com.face.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import java.util.List;
import com.face.po.StarPo;
import org.springframework.stereotype.Repository;

/**头部内容结束
 *
 * Generator create
 * @author Generator
 * @date 2019-07-30 11:02:01
 */

@Mapper
public interface IStarDao { 

	int insert(StarPo starPo) throws DataAccessException;

	int update(StarPo starPo) throws DataAccessException;

	List<StarPo>  list(StarPo starPo) throws DataAccessException;

	//业务代码请写在下面，防止后续生成被覆盖

	List<StarPo> qryStarByPage(StarPo starPo)throws DataAccessException;
}

