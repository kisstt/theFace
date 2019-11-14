import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:face_app/utils/http.dart';
import 'package:flutter/material.dart';

import 'entity/article.dart';

class SharePage extends StatefulWidget {
  @override
  _SharePageState createState() => _SharePageState();
}

double width;

class _SharePageState extends State<SharePage> {
  List<Card> list = new List();

  List<Article> artilceDataList = new List();

  @override
  void initState() {
    // TODO: implement initState
    list = new List();
    super.initState();
    _init();
  }



  void _init() {
    var _this = this;
    _qryArticleAll().then((res2) {
      print(res2.toString());
      List<Article> articleList = new List();
      Map<String, dynamic> jsonData = jsonDecode(res2.toString());
      print(jsonData['rows']);
      List list = jsonData['rows'];
      articleList = list.map((m) => Article.fromJson(m)).toList();
      print('-----------------articleList[0].like------------------');
      print(articleList.length);
      print(articleList[0].like);
      _this.setState(() {
        artilceDataList = articleList;
      });
    });
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
    list = new List();
  }

  @override
  Widget build(BuildContext context) {
    width = MediaQuery.of(context).size.width;
//    for (var i = 1; i <= artilceDataList.length; i++) {
//      Card card = new Card();
//      card.cardId = i;
//      card.imgUrl = 'images/index1.jpeg';
//      card.user = '嘿哈';
//      card.pubTime = '2019-06-20';
//      card.title = '震惊！,某某竟然是某某新的架构图暴露';
//      card.isLike = false;
//      list.add(card);
//    }
    List<Widget> wlist = new List();
    print('-------------------------artilceDataList.length---------------------------');
    print(artilceDataList.length);

    for (var i = 0; i <  artilceDataList.length; i++) {
      Article article = artilceDataList[i];
      wlist.add(buildCard(article.articleId, article.avatarUrl, article.nickname, article.createTime,
          article.articleTitle,
          article.articleContent, article.like));
    }
    print("个数");
    print(wlist.length);
    return Scaffold(
        appBar: AppBar(
          title: Text('UFace 每日推荐'),
        ),
        body: Container(
          color: Color(0xffe6e7e9),
          child: ListView(children: wlist
//              buildCard(
//                ,
//                '嘿哈',
//                ,
//                ,
//                true,
//              ),
//              buildCard(
//                'images/index1.jpeg',
//                '123',
//                '2019-06-20',
//                'Ngnix代理',
//                false,
//              ),
//              buildCard(
//                'images/index1.jpeg',
//                '嘿哈',
//                '2019-06-20',
//                'SpringBoot最核心的27个注解',
//                true,
//              ),
//              buildCard(
//                'images/index1.jpeg',
//                '嘿哈',
//                '2019-06-20',
//                'SpringBoot最核心的27个注解',
//                true,
//              ),
//              buildCard(
//                'images/index1.jpeg',
//                '嘿哈',
//                '2019-06-20',
//                'SpringBoot最核心的27个注解',
//                true,
//              )
//            ],
              ),
        ));
  }

  _qryArticleAll() async {
    Options options = new Options();
    String _key = Http.userInfoKey;
    print(Http.userInfoKey);
    if (!_key.contains("=")) {
      _key = 'sessionId=' + _key;
    }
    Dio dio = new Dio();
    options.headers['USER_INFO_KEY'] = '$_key';
    Response reponse = await dio.post<String>(
        Http.API_PREFIX + '/api/article/qryArticle4Page/all',
        data: {"pageNo": 1, "pageSize": 100},
        options: options);
    return reponse;
  }

  Widget buildCard(int cardId, String img, String pubUser, String time,
      String title, String content,bool isLike) {
    return Padding(
        padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
        child: Container(
          color: Colors.white,
          child: Column(
            children: <Widget>[
              Row(
                children: <Widget>[
                  Padding(
                    child:img!=null? Image.network(img,
                      width: 40.0,
                      height: 40.0,
                    ):Image.asset(
        'images/index1.jpeg',
          width: 40.0,
          height: 40.0,
        ),
                    padding: EdgeInsets.fromLTRB(10, 5, 10, 10),
                  ),
                  Container(
                      width: width / 2+40,
                      padding: EdgeInsets.fromLTRB(10, 5, 10, 10),
                      child: Column(
                        children: <Widget>[
                          Text(title,
                              maxLines: 1,
                              overflow: TextOverflow.ellipsis,
                              textAlign: TextAlign.left,
                              style: TextStyle(fontSize: 16.0)),
                          Text(
                            '$pubUser $time',
                            style:
                                TextStyle(fontSize: 14.0, color: Colors.grey),
                          )
                        ],
                      )),
//                  Container(
//                    width: 40,
//                  ),
                  Padding(
                    child: GestureDetector(
                      child: Icon(
                        Icons.favorite,
                        color: isLike ? Colors.red : Colors.grey,
                      ),
                      onTap: () {
//                        for (var i = 0; i < artilceDataList.length; i++) {
//                          if (artilceDataList[i].articleId == cardId) {
//                            setState(() {
//                              artilceDataList[i].like = !artilceDataList[i].like;
//                            });
//                            break;
//                          }
//                        }
                      _like(cardId).then((res){
                        _init();
                      });
                      },
                    ),
                    padding: EdgeInsets.fromLTRB(10, 5, 10, 10),
                  )
                ],
              ),
              RichText(
                text: TextSpan(
                  text:content,
//                      '本文作者叫Srinath，是一位科学家，软件架构师，也是一名在分布式系统上工作的程序员。 他是Apache Axis2项目的联合创始人，也是Apache Software基金会的成员。 他是WSO2流处理器（wso2.com/analytics）的联席架构师。 Srinath撰写了两本关于MapReduce和许多技术文章的书。 他获得了博士学位。 来自美国印第安纳大学。Srinath通过不懈的努力最终总结出了30条架构原则，他主张架构师的角色应该由开发团队本身去扮演，而不是专门有个架构师团队或部门。Srinath认为架构师应该扮演的角色是一个引导者，讨论发起者，花草修建者，而不是定义者和构建者。Srinath为了解决团队内部的架构纷争和抉择，制定了以下30条原则，这些原则被成员们广泛认可，也成为了新手架构师的学习途径。',
                  style: TextStyle(color: Colors.black, fontSize: 15),
                ),
                maxLines: 5,
              )
            ],
          ),
        ));
  }



  _like(int articleId) async{
    Options options = new Options();
    String _key = Http.userInfoKey;
    print(Http.userInfoKey);
    if (!_key.contains("=")) {
      _key = 'sessionId=' + _key;
    }
    Dio dio = new Dio();
    options.headers['USER_INFO_KEY'] = '$_key';
    Response reponse = await dio.post<String>(
        Http.API_PREFIX + '/api/article/like',
        data: {
          "articleId":articleId
        },
        options: options);
    return reponse;
  }
}

/***
 *  'images/index1.jpeg',
    '123',
    '2019-06-20',
    'Ngnix代理',
    false,
 */

class Card {
  int cardId;
  String imgUrl;
  String user;
  String pubTime;
  String title;
  bool isLike;
}
