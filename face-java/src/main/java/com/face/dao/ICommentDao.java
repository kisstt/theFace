package com.face.dao;
import org.springframework.dao.DataAccessException;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.face.po.CommentPo;
/**头部内容结束
 *
 * Generator create
 * @author Generator
 * @date 2019-10-27 21:36:58
 */

 @Mapper
public interface ICommentDao { 

	int insert(CommentPo commentPo) throws DataAccessException;

	int update(CommentPo commentPo) throws DataAccessException;

	List<CommentPo>  list(CommentPo commentPo) throws DataAccessException;

	//业务代码请写在下面，防止后续生成被覆盖

}
