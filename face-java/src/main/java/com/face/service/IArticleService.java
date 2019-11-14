package com.face.service;

import com.face.page.Page;
import com.face.po.ArticlePo;
import com.face.po.ArticleTagPo;

import java.util.List;

public interface IArticleService {

    void insertAritle(ArticlePo articlePo);

    void insertAritleTag(ArticleTagPo articleTagPo);

    void updateAritle(ArticlePo articlePo);

    void updateAritleTag(ArticleTagPo articleTagPo);

    Page qryAritle4Page(ArticlePo articlePo, Page page);


    void likeAritle(Long userId, ArticlePo articlePo);

}
