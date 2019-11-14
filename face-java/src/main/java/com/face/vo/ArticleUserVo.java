package com.face.vo;

import com.face.po.ArticlePo;
import lombok.Data;

@Data
public class ArticleUserVo extends ArticlePo {

    private String nickname;

    private String avatarUrl;

    private boolean isLike;
}
