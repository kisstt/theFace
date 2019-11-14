package com.face.controller;

import com.face.WebConstants;
import com.face.dao.IUserFollowDao;
import com.face.page.Page;
import com.face.po.ArticlePo;
import com.face.po.UserFollowPo;
import com.face.resolver.MultiRequestBody;
import com.face.service.IArticleService;
import com.face.service.IUserService;
import com.face.vo.ArticleUserVo;
import com.face.vo.ReturnData;
import com.face.vo.StatusCode;
import com.face.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;
    @Resource
    private IUserService userService;

    @Resource
    private IUserFollowDao userFollowDao;

    @Resource
    RedisTemplate redisTemplate;

    @RequestMapping("/add")
    @ResponseBody
    public ReturnData add(@RequestBody ArticlePo articlePo, HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute("user");
        articlePo.setUserId(userVo.getUserId());
        articlePo.setCreateTime(new Date());
        articleService.insertAritle(articlePo);
        return new ReturnData(StatusCode.SUCCESS, "添加文章成功", "");
    }

    @RequestMapping("/qryArticle4Page/{type}")
    @ResponseBody
    public Page qryArticle4Page(@PathVariable("type") String type,
                                @MultiRequestBody ArticlePo articlePo,
                                @MultiRequestBody Page page,
                                HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute("user");
        String sql = "`article` .user_id in(";
        UserFollowPo userFollowPo = new UserFollowPo();
        userFollowPo.setUserId(userVo.getUserId());
        List<UserFollowPo> userFollowPoList = userFollowDao.list(userFollowPo);
        if (userFollowPoList.size() == 0) {
            return page;
        }
        for (int i = 0; i < userFollowPoList.size(); i++) {
            sql += userFollowPoList.get(i).getFollowId() + ",";
        }
        if (sql.endsWith(",")) {
            sql = sql.substring(0, sql.length() - 1);
        }
        sql += ")";
        articlePo.setSearchSql(sql);
        Page page1 = articleService.qryAritle4Page(articlePo, page);
        List<ArticleUserVo> al = (List<ArticleUserVo>) page1.getRows();
        Set<Long> set = redisTemplate.opsForSet().members(WebConstants.USER_ARTICLE_ID + ":" + userVo.getUserId());
        for (ArticleUserVo articleUserVo : al) {
            articleUserVo.setLike(set.contains(articleUserVo.getArticleId()));
        }
        page1.setRows(al);
        return page1;
    }

    @RequestMapping("/like")
    @ResponseBody
    public ReturnData like(@MultiRequestBody ArticlePo articlePo, HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute("user");
        userVo = userService.qryUserByUserId(userVo);
        articleService.likeAritle(userVo.getUserId(), articlePo);
        return new ReturnData(StatusCode.SUCCESS, "点赞成功", "");
    }
}
