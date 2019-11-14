

import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable()
class Article{

  int articleId;

  String userId;

  String articleTitle;

  String articleContent;

  String createTime;

  String nickname;

  String avatarUrl;


  bool like;

  static Article fromJson(m){
    print(m);
    Article article=new Article();
    article.articleId=m['articleId'];
    article.articleTitle=m['articleTitle'];
    article.articleContent=m['articleContent'];
    article.createTime=m['createTime'].toString().split(" ")[0];
    article.nickname=m['nickname'];
    article.avatarUrl=m['avatarUrl'];
    article.like=m['like'];
    return article;
  }

}