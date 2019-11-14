import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:face_app/component/toast.dart';
import 'package:face_app/flower_page.dart';
import 'package:face_app/utils/http.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

import 'entity/article.dart';
import 'entity/user.dart';

class RecommendPage extends StatefulWidget {
  @override
  _RecommendPageState createState() => _RecommendPageState();
}

class _RecommendPageState extends State<RecommendPage>
    with SingleTickerProviderStateMixin {
  double width = 200;

  TabController _tabController;
  List<User> followUser = new List();

  @override
  void initState() {
//    if(list.length!=5)
//    for (var i = 1; i <= 5; i++) {
//      Card card = new Card();
//      card.cardId = i;
//      card.imgUrl = 'images/index1.jpeg';
//      card.user = '嘿哈';
//      card.pubTime = '2019-06-20';
//      card.title = '震惊！,某某竟然是某某新的架构图暴露';
//      card.isLike = false;
//      list.add(card);
//    }

    _tabController = new TabController(length: 3, vsync: this);
    super.initState();
    _init();
  }

  @override
  void dispose() {
    _tabController.dispose();
    list = new List();
    super.dispose();
  }


  @override
  Widget build(BuildContext context) {
    width = MediaQuery
        .of(context)
        .size
        .width;
    return new DefaultTabController(
      length: 3,
      child: new Scaffold(
        appBar: new AppBar(
          title: Row(
            children: <Widget>[
              Text('UFace'),
              Container(
                  padding: EdgeInsets.fromLTRB(width / 2 + 50, 0, 0, 0),
                  child: GestureDetector(
                    child: Icon(Icons.add),
                    onTap: () {
                      _showAddFriendDialog("新增朋友", "");
                    },
                  ))
            ],
          ),
          bottom: new TabBar(
            tabs: <Widget>[
              new Tab(
                //icon: new Icon(Icons.directions_bike),
                text: '推荐',
              ),
              new Tab(
                //icon: new Icon(Icons.directions_bus),
                text: '分类',
              ),
              new Tab(
                //icon: new Icon(Icons.directions_boat),
                text: '我的关注',
              ),
            ],
            controller: _tabController,
          ),
        ),
        body: new TabBarView(
          controller: _tabController,
          children: <Widget>[
            new Center(child: buildRecommendTabView()),
            new Center(child: buildCategoryTabView(context)),
            new Center(child: buildMyFollowTabView(context)),
          ],
        ),
      ),
    );

    // floatingActionButton: FloatingActionButton.extended(
    //   icon: Icon(Icons.add,color:  Colors.white,),
    // ),
    // );
  }

  _qryFollow() async {
    Options options = new Options();
    String _key = Http.userInfoKey;
    print(Http.userInfoKey);
    if (!_key.contains("=")) {
      _key = 'sessionId=' + _key;
    }
    Dio dio = new Dio();
    options.headers['USER_INFO_KEY'] = '$_key';
    Response reponse = await dio.post<String>(
        Http.API_PREFIX + '/api/user/qryFollow',
        data: {'tagId': 5},
        options: options);
    return reponse;
  }

  List<Article> artilceDataList = new List();

  _init() {
    List<User> followUserData = new List();
    List<Article> articleList = new List();
    var _this = this;
    _qryFollow().then((res) {
      print('-----------------------------');
      print(res.toString());
      List responseJson = json.decode(res.toString());
      followUserData = responseJson.map((m) => User.fromJson(m)).toList();
      _qryArticleAll().then((res2) {
        print(res2.toString());
        Map<String, dynamic> jsonData = jsonDecode(res2.toString());
        print(jsonData['rows']);
        List list = jsonData['rows'];
        articleList = list.map((m) => Article.fromJson(m)).toList();
        print(articleList);
        _this.setState(() {
          followUser = followUserData;
          artilceDataList = articleList;
        });
      });
    });
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
        data: {"pageNo": 1, "pageSize": 5},
        options: options);
    return reponse;
  }

  Widget buildMyFollowTabView(BuildContext context) {
    List<Widget> wFollowList = new List();
    print('----------------followUser.length---------------------');
    print(followUser.length);
    for (var i = 0; i < followUser.length; i++) {
      print(followUser[i]);
      wFollowList.add(buildListTile(followUser[i].userId,
          followUser[i].nickname, followUser[i].avatarUrl));
    }
    return Container(
      padding: EdgeInsets.fromLTRB(10, 10, 10, 10),
      color: Color(0xffe6e7e9),
      child: ListView(children: wFollowList),
    );
  }

  Widget buildListTile(BigInt userId, String name, String fileUrl) {
    return new Container(
      child: new Container(
        color: Colors.white,
        child: ListTile(
            leading: Image.network(fileUrl, width: 50, height: 50),
            title: Text(
              name,
              style: TextStyle(fontSize: 20),
            ),
            trailing: MaterialButton(
              color: Colors.pink,
              shape: RoundedRectangleBorder(
                  side: BorderSide.none,
                  borderRadius: BorderRadius.all(Radius.circular(50))),
              child: Text(
                '取消关注',
                style: TextStyle(color: Colors.white),
              ),
              onPressed: () {
                var _this = this;
                print('取消关注');
                print(userId);
                _follow(userId).then((res) {
                  print(res.toString());
                  _qryFollow().then((res) {
                    print('-----------------------------');
                    print(res.toString());
                    List responseJson = json.decode(res.toString());
                    List<User> followUserData = new List();
                    followUserData =
                        responseJson.map((m) => User.fromJson(m)).toList();
                    _this.setState(() {
                      followUser = followUserData;
                    });
                  });
                });
              },
            )),
      ),
      padding: EdgeInsets.fromLTRB(0, 0, 0, 5),
    );
  }

  _follow(BigInt userId) async {
    Options options = new Options();
    String _key = Http.userInfoKey;
    print(Http.userInfoKey);
    if (!_key.contains("=")) {
      _key = 'sessionId=' + _key;
    }
    Dio dio = new Dio();
    options.headers['USER_INFO_KEY'] = '$_key';
    Response reponse = await dio.post<String>(
        Http.API_PREFIX + '/api/user/follow',
        data: {"userId": userId.toString()},
        options: options);
    return reponse;
  }

  _followByName() async {
    Options options = new Options();
    String _key = Http.userInfoKey;
    print(Http.userInfoKey);
    if (!_key.contains("=")) {
      _key = 'sessionId=' + _key;
    }
    Dio dio = new Dio();
    options.headers['USER_INFO_KEY'] = '$_key';
    Response reponse = await dio.post<String>(
        Http.API_PREFIX + '/api/user/follow',
        data: {"nickName": _nickName},
        options: options);
    return reponse;
  }

  Widget buildRecommendTabView() {
    print('-------------artilceDataList------------------');
    print(artilceDataList.length);
    List<Widget> wlist = new List();
    for (var i = 0; i < artilceDataList.length; i++) {
      Article article = artilceDataList[i];
      print(article.articleContent);
      wlist.add(buildCard(
          article.articleId,
          article.avatarUrl,
          article.nickname,
          article.createTime,
          article.articleTitle,
          article.like,
          article.articleContent));
    }
    return Container(
      padding: EdgeInsets.fromLTRB(10, 10, 10, 10),
      color: Color(0xffe6e7e9),
      child: ListView(
        children: wlist,
      ),
    );
  }

  Widget buildCategoryTabView(BuildContext context) {
    double _iconWidth = (width - 20) / 3;
    return new Container(
      padding: EdgeInsets.fromLTRB(10, 10, 10, 10),
      child: Column(
        children: <Widget>[
          Row(
            children: <Widget>[
              Text(
                '为您推荐',
                style: TextStyle(color: Colors.black87),
              )
            ],
          ),
          Row(
            children: <Widget>[
              GestureDetector(
                child: Container(
                  decoration: BoxDecoration(
                      border: Border.all(color: Colors.grey, width: 0.5)),
                  width: _iconWidth,
                  height: _iconWidth,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Icon(
                        Icons.language,
                        color: Colors.blue,
                        size: 40,
                      ),
                      Text(
                        '科技专区',
                        style: TextStyle(fontSize: 20),
                      )
                    ],
                  ),
                ),
                onTap: () {
                  _pushSentence().then((res) {
                    Map<String, dynamic> json = jsonDecode(res.toString());
                    _showDialog('科技专区', json['sentence']);
                  });
//                  Toast.toast(context, msg: '前方正在施工');
                },
              ),
              GestureDetector(
                child:
                Container(
                    padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
                    decoration: BoxDecoration(
                        border: Border.all(color: Colors.grey, width: 0.5)),
                    width: _iconWidth,
                    height: _iconWidth,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        Icon(
                          Icons.battery_charging_full,
                          color: Colors.pink,
                          size: 40,
                        ),
                        Text(
                          '读书专区',
                          style: TextStyle(fontSize: 20),
                        )
                      ],
                    )),
                onTap: () {
                  _pushSentence().then((res) {
                    Map<String, dynamic> json = jsonDecode(res.toString());
                    _showDialog('读书专区', json['sentence']);
                  });
//                  Toast.toast(context, msg: '前方正在施工');
                },
              ),
              GestureDetector(
                child: Container(
                    padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
                    decoration: BoxDecoration(
                        border: Border.all(color: Colors.grey, width: 0.5)),
                    width: _iconWidth,
                    height: _iconWidth,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        Icon(
                          Icons.face,
                          color: Colors.green,
                          size: 40,
                        ),
                        Text(
                          '星座运势',
                          style: TextStyle(fontSize: 20),
                        )
                      ],
                    )),
                onTap: () {
                  print(Http.user);
                  _starFind(Http.user.constellation).then((res) {
                    print(res.toString());
                    Map<String, dynamic> json = jsonDecode(res.toString());
                    _showDialog(
                        "星座运势 ${json['constellation']}", json['description']);
                  });
                },
              )
            ],
          ),
          Row(
            children: <Widget>[
              GestureDetector(
                child: Container(
                    decoration: BoxDecoration(
                        border: Border.all(color: Colors.grey, width: 0.5)),
                    width: _iconWidth,
                    height: _iconWidth,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        Icon(
                          Icons.filter_drama,
                          color: Colors.orange,
                          size: 40,
                        ),
                        Text(
                          '每日一句',
                          style: TextStyle(fontSize: 20),
                        )
                      ],
                    )),
                onTap: () {
                  _pushSentence().then((res) {
                    Map<String, dynamic> json = jsonDecode(res.toString());
                    _showDialog('每日一句', json['sentence']);
                  });
                },
              ),
              GestureDetector(
                child: Container(
                    padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
                    decoration: BoxDecoration(
                        border: Border.all(color: Colors.grey, width: 0.5)),
                    width: _iconWidth,
                    height: _iconWidth,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        Icon(
                          Icons.free_breakfast,
                          color: Colors.orangeAccent,
                          size: 40,
                        ),
                        Text(
                          '休闲娱乐',
                          style: TextStyle(fontSize: 20),
                        )
                      ],
                    )),
                onTap: () {
                  _pushSentence().then((res) {
                    Map<String, dynamic> json = jsonDecode(res.toString());
                    _showDialog('休闲娱乐', json['sentence']);
                  });
                },
              ),
              GestureDetector(
                child: Container(
                    padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
                    decoration: BoxDecoration(
                        border: Border.all(color: Colors.grey, width: 0.5)),
                    width: _iconWidth,
                    height: _iconWidth,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        Icon(
                          Icons.local_florist,
                          color: Colors.green,
                          size: 40,
                        ),
                        Text(
                          '绿植养护',
                          style: TextStyle(fontSize: 20),
                        )
                      ],
                    )),
                onTap: () {
                  _pushSentence().then((res) {
                    Map<String, dynamic> json = jsonDecode(res.toString());
                    _showDialog('绿植养护', json['sentence']);
                  });
//                  Navigator.push(context,
//                      new MaterialPageRoute(builder: (context) {
//                        return FlowerPage();
//                      }));
                },
              )
            ],
          )
        ],
      ),
    );
  }

  List<Card> list = new List();

  _pushSentence() async {
    Options options = new Options();
    String _key = Http.userInfoKey;
    print(Http.userInfoKey);
    if (!_key.contains("=")) {
      _key = 'sessionId=' + _key;
    }
    Dio dio = new Dio();
    options.headers['USER_INFO_KEY'] = '$_key';
    Response reponse = await dio.post<String>(
        Http.API_PREFIX + '/api/sentence/pushSentence',
        data: {'tagId': 5},
        options: options);
    return reponse;
  }

  _starFind(String star) async {
    Options options = new Options();
    String _key = Http.userInfoKey;
    print(Http.userInfoKey);
    if (!_key.contains("=")) {
      _key = 'sessionId=' + _key;
    }
    Dio dio = new Dio();
    options.headers['USER_INFO_KEY'] = '$_key';
    Response reponse = await dio.post<String>(
        Http.API_PREFIX + '/api/star/find',
        data: {"constellation": star},
        options: options);
    return reponse;
  }

  Widget buildCard(int cardId, String img, String pubUser, String time,
      String title, bool isLike, String content) {
    if (title.length <= 10) title += '                                 ';
    return Padding(
        padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
        child: Container(
          color: Colors.white,
          child: Column(
            children: <Widget>[
              Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
                  Padding(
                    child: Image.network(
                      img == null
                          ? 'http://134.175.100.101:8012/images/avator/touxiang1.jpg'
                          : img,
                      width: 40.0,
                      height: 40.0,
                    ),
                    padding: EdgeInsets.fromLTRB(10, 5, 10, 10),
                  ),
                  Container(
                      width: width / 2 + 100,
                      height: 90,
                      padding: EdgeInsets.fromLTRB(0, 5, 10, 10),
                      child: Column(
                        children: <Widget>[
                          Text(title,
                              maxLines: 2,
                              overflow: TextOverflow.ellipsis,
                              textAlign: TextAlign.left,
                              style: TextStyle(
                                  fontSize: 18.0, fontFamily: '微软雅黑')),
                          Text(
                            '${pubUser} ${time}                                                                              ',
                            textAlign: TextAlign.left,
                            style:
                            TextStyle(fontSize: 9.0, color: Colors.grey),
                          )
                        ],
                      )),
//                  Container(
//                    width: 80,
//                  ),
//                  Padding(
//                    child: GestureDetector(
//                      child: Icon(
//                        Icons.favorite,
//                        color: isLike ? Colors.red : Colors.grey,
//                      ),
//                      onTap: () async {
//                        print("========进入========");
//                        for (var i = 0; i < list.length; i++) {
//                          if (list[i].cardId == cardId) {
//                            print("======点击了$cardId");
//                            await setState(() {
//                              list[i].isLike = !list[i].isLike;
//                            });
//                            break;
//                          }
//                        }
//                      },
//                    ),
//                    padding: EdgeInsets.fromLTRB(10, 5, 10, 10),
//                  )
                ],
              ),
              RichText(
                text: TextSpan(
                  text: '$content',
                  style: TextStyle(color: Colors.black, fontSize: 15),
                ),
                maxLines: 5,
              )
            ],
          ),
        ));
  }

  void _showDialog(String title, String sentence) {
    showDialog<Null>(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return new AlertDialog(
          title: new Text(title, style: TextStyle(fontSize: 18)),
          content: new SingleChildScrollView(
            child: new ListBody(
              children: <Widget>[
                Text(sentence, style: TextStyle(fontSize: 20)),
              ],
            ),
          ),
          actions: <Widget>[
            new FlatButton(
              child: new Text('确定'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    ).then((val) {
      print(val);
    });
  }

  String _nickName;
  final _formKey = GlobalKey<FormState>();

  void _showAddFriendDialog(String title, String sentence) {
    showDialog<Null>(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return new AlertDialog(
          title: new Text(title, style: TextStyle(fontSize: 18)),
          content: new SingleChildScrollView(
            child: new ListBody(
              children: <Widget>[
                Form(
                  key: _formKey,
                  child: TextFormField(
                    validator: (value) {
                      if (value.isEmpty) {
                        return '请输入用户名';
                      }
                      return null;
                    },
                    onSaved: (val) {
                      _nickName = val;
                    },
                    decoration: InputDecoration(
                        labelText: '用户名',
                        prefixIcon: Icon(Icons.perm_identity)),
                  ),
                ),
              ],
            ),
          ),
          actions: <Widget>[
            new FlatButton(
              child: new Text('确定'),
              onPressed: () {
                _formKey.currentState.save();
                _followByName().then((res) {
                  Navigator.of(context).pop();
                });
              },
            ),
          ],
        );
      },
    ).then((val) {
      _showDialog("添加成功", "");
    });
  }
}

class Card {
  int cardId;
  String imgUrl;
  String user;
  String pubTime;
  String title;
  bool isLike;
}
