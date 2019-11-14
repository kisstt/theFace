package com.face.dao;
import com.face.page.Page;
import com.face.vo.ArticleUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import java.util.List;
import com.face.po.ArticlePo;
import org.springframework.stereotype.Repository;

/**头部内容结束
 *
 * Generator create
 * @author Generator
 * @date 2019-10-27 21:36:58
 */

 @Mapper
public interface IArticleDao { 

	int insert(ArticlePo articlePo) throws DataAccessException;

	int update(ArticlePo articlePo) throws DataAccessException;

	List<ArticlePo>  list(ArticlePo articlePo) throws DataAccessException;

	//业务代码请写在下面，防止后续生成被覆盖
	List<ArticleUserVo> qryArticleUserVo4Page(ArticlePo articleUserVo);
}

