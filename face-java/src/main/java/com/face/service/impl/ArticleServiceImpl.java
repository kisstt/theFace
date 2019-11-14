package com.face.service.impl;

import com.face.WebConstants;
import com.face.dao.IArticleDao;
import com.face.dao.IArticleTagDao;
import com.face.page.Page;
import com.face.po.ArticlePo;
import com.face.po.ArticleTagPo;
import com.face.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private IArticleDao articleDao;

    @Autowired
    private IArticleTagDao articleTagDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void insertAritle(ArticlePo articlePo) {
        articleDao.insert(articlePo);
    }

    @Override
    public void insertAritleTag(ArticleTagPo articleTagPo) {
        articleTagDao.insert(articleTagPo);
    }

    @Override
    public void updateAritle(ArticlePo articlePo) {
        articleDao.update(articlePo);
    }

    @Override
    public void updateAritleTag(ArticleTagPo articleTagPo) {
        articleTagDao.update(articleTagPo);
    }

    @Override
    public Page qryAritle4Page(ArticlePo articlePo, Page page) {
        List al = articleDao.qryArticleUserVo4Page(articlePo);
        page.setRows(al);
        page.setTotal(al.size());
        return page;
    }


    @Override
    public void likeAritle(Long userId, ArticlePo articlePo) {

        Set<Integer> set=redisTemplate.opsForSet().members(WebConstants.USER_ARTICLE_ID + ":" + userId);
        if(!set.contains(articlePo.getArticleId())){
            redisTemplate.opsForSet().add(
                    WebConstants.USER_ARTICLE_ID + ":" + userId, articlePo.getArticleId());
        }else {
            redisTemplate.opsForSet().remove(
                    WebConstants.USER_ARTICLE_ID + ":" + userId, articlePo.getArticleId());
        }
    }
}
