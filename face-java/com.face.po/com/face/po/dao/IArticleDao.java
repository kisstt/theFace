package com.face.dao;
import org.springframework.dao.DataAccessException;
import java.util.List;
import com.face.po.po.ArticlePo;
/**头部内容结束
 *
 * Generator create
 * @author Generator
 * @date 2019-07-29 16:36:38
 */

public interface IArticleDao { 

	int insert(ArticlePo articlePo) throws DataAccessException;

	int update(ArticlePo articlePo) throws DataAccessException;

	List<ArticlePo>  list(ArticlePo articlePo) throws DataAccessException;

	//业务代码请写在下面，防止后续生成被覆盖

}
