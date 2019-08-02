package com.face.dao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import java.util.List;
import com.face.po.ReplyPo;
import org.springframework.stereotype.Repository;

/**头部内容结束
 *
 * Generator create
 * @author Generator
 * @date 2019-07-30 11:02:01
 */

@Mapper
public interface IReplyDao { 

	int insert(ReplyPo replyPo) throws DataAccessException;

	int update(ReplyPo replyPo) throws DataAccessException;

	List<ReplyPo>  list(ReplyPo replyPo) throws DataAccessException;

	//业务代码请写在下面，防止后续生成被覆盖

}

