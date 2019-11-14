import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:face_app/entity/user.dart';
import 'package:face_app/login_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'utils/http.dart';

class MinePage extends StatefulWidget {
  @override
  _MinePageState createState() => _MinePageState();
}

class _MinePageState extends State<MinePage> {
  User user = new User();

  @override
  void initState(){
    // TODO: implement initState
    if(Http.user==null){
      print("-----------请求qryUser--------------");
      Future.wait([_initPage()]);
    }else{
      user=Http.user;
    }
    super.initState();
  }

   Future _initPage() async{
    return _qryUser().then((res) {
      Map<String, dynamic> json = jsonDecode(res.toString());
     this.setState((){
       user.birthday = json['birthday'];
       user.username = json['username'];
       user.nickname = json['nickname'];
       user.email = json['email'];
       user.constellation = json['constellation'];
       user.tele = json['tele'];
       user.avatarUrl=json['avatarUrl'];
     });
     Http.user=user;
    });
  }
  @override
  Widget build(BuildContext context) {
     print(user);
    return Scaffold(
        appBar: AppBar(
          title: Text('UFace 关于你'),
        ),
        body: Container(
          color: Color(0xffe6e7e9),
          child: ListView(
            children: <Widget>[
              Container(
                padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
                child: Image.network(
                  '${user.avatarUrl}',
                  width: 150,
                  height: 150,
                ),
              ),
              buildTextLine('昵称(姓名)', '${user.nickname}(${user.username})'),
              buildTextLine('手机', '${user.tele!=null?user.tele:'暂无'}'),
              buildTextLine('星座', '${user.constellation}'),
              buildTextLine('生日', '${user.birthday}'),
              buildTextLine('邮箱', '${user.email==null||user.email==''?'暂无':user.email}'),
              MaterialButton(
                color: Colors.pinkAccent,
                child: Text(
                  '退出',
                  style: TextStyle(color: Colors.white, fontSize: 20),
                ),
                onPressed: () {
                  Navigator.pushReplacement(context,
                      new MaterialPageRoute(builder: (context) {
                    return LoginPage();
                  }));
                },
              )
            ],
          ),
        ));
  }

  Widget buildTextLine(String key, String value) {
    final double keyFontSize = 18;
    final double valueFontSize = 16;
    return Container(
        color: Colors.white,
        height: 50,
        child: Row(
          children: <Widget>[
            Container(
              width: 100,
              child: Text(
                key,
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Colors.black,
                  fontSize: keyFontSize,
                ),
              ),
            ),
            Text(
              value,
              style: TextStyle(
                color: Colors.black,
                fontSize: valueFontSize,
              ),
            )
          ],
        ));
  }

  _qryUser() async {
    Options options = new Options();
    String _key = Http.userInfoKey;
    print(Http.userInfoKey);
    if (!_key.contains("=")) {
      _key = 'sessionId=' + _key;
    }
    Dio dio = new Dio();
    options.headers['USER_INFO_KEY'] = '$_key';
    Response reponse = await dio
        .post<String>(Http.API_PREFIX + '/api/user/qryUser', options: options);
    return reponse;
  }
}

class UserInfo {
  String nickname;

  String username;

  String email;

  String birthday;

  String constellation;

  String tele;

  String avatarUrl;
}
