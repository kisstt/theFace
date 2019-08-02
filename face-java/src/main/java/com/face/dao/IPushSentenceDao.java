package com.face.dao;

import com.face.po.PushSentencePo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * 头部内容结束
 * <p>
 * Generator create
 *
 * @author Generator
 * @date 2019-07-30 10:55:17
 */
@Mapper
public interface IPushSentenceDao {

    int insert(PushSentencePo pushSentencePo) throws DataAccessException;

    int update(PushSentencePo pushSentencePo) throws DataAccessException;

    List<PushSentencePo> list(PushSentencePo pushSentencePo) throws DataAccessException;

    //业务代码请写在下面，防止后续生成被覆盖

}

