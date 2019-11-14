import 'dart:convert';
import 'dart:core' as prefix0;
import 'dart:core';
import 'dart:io';

import 'package:dio/dio.dart';
import 'package:face_app/component/toast.dart';
import 'package:face_app/register_page.dart';
import 'package:face_app/tab_navigator.dart';
import 'package:face_app/utils/http.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

import 'entity/user.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final double _imgWidth = 200;
  bool _showLogo = true;
  File _imgPath;
  bool _showPwd = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: new SingleChildScrollView(
        child: new ConstrainedBox(
          constraints: new BoxConstraints(
            minHeight: 120.0,
          ),
          child: Column(
            children: <Widget>[
              Padding(
                padding: EdgeInsets.only(top: 20),
                child: GestureDetector(
                    onTap: () {
                      this.setState(() {
                        _showLogo = !_showLogo;
                      });
                    },
                    child: Container(
                      // width: _imgWidth - 20,
                      // height: _imgWidth - 20,
                      child: _showLogo
                          ? Image.asset(
                              'images/index1.jpeg',
                              width: _imgWidth - 40,
                              height: _imgWidth - 40,
                            )
                          : GestureDetector(
                              onLongPress: () {
                                _takePhoto();
                              },
                              child: Container(
                                width: _imgWidth - 40,
                                height: _imgWidth - 40,
                                alignment: Alignment.center,
                                child: _imgPath == null
                                    ? Text(
                                        '长按拍照',
                                        textAlign: TextAlign.center,
                                        style: TextStyle(fontSize: 25.0),
                                      )
                                    : Image.asset(
                                        _imgPath.path,
                                        width: _imgWidth - 40,
                                        height: _imgWidth - 40,
                                      ),
                              ),
                            ),
                    )),
              ),
              Text(
                '点击头像人脸验证',
                style: TextStyle(fontSize: 20, color: Colors.black87),
              ),
              SingleChildScrollView(
                child: buildForm(),
              )
            ],
          ),
        ),
      ),
    );
  }

  String _nickName;
  String _password;

  _takePhoto() async {
    var image = await ImagePicker.pickImage(source: ImageSource.camera);
    setState(() {
      _imgPath = image;
    });
  }

  final _formKey = GlobalKey<FormState>();

  Widget buildForm() {
    return Form(
      key: _formKey,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          TextFormField(
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
                labelText: '用户名', prefixIcon: Icon(Icons.perm_identity)),
          ),
          TextFormField(
            validator: (value) {
              if (value.isEmpty) {
                return '请输入密码';
              }
              return null;
            },
            obscureText: !_showPwd,
            onSaved: (val) {
              _password = val;
            },
            decoration: InputDecoration(
                labelText: '密码',
                prefixIcon: IconButton(
                  icon:
                      Icon(_showPwd ? Icons.visibility : Icons.visibility_off),
                  onPressed: () {
                    setState(() {
                      _showPwd = !_showPwd;
                    });
                  },
                )),
          ),
          Container(
              width: double.infinity,
              padding: const EdgeInsets.symmetric(vertical: 16.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  MaterialButton(
                    color: Colors.blue,
                    highlightColor: Colors.blue,
                    textColor: Colors.white,
                    height: 50,
                    onPressed: () {
                      if (_showLogo || _imgPath != null) {
                        //1.根据用户名密码登录
                        if (_showLogo) {
                          if (_formKey.currentState.validate()) {
                            _formKey.currentState.save();
                            _login().then((res) {
                              print(res.toString());
                              Map<String, dynamic> json =
                                  jsonDecode(res.toString());
                              print(json['statusCode']);
                              if (res.toString().contains("200")) {
                                _qryUser().then((res) {
                                  Map<String, dynamic> json =
                                      jsonDecode(res.toString());
                                  User user = new User();
                                  user.birthday = json['birthday'];
                                  user.username = json['username'];
                                  user.nickname = json['nickname'];
                                  user.email = json['email'];
                                  user.constellation = json['constellation'];
                                  user.tele = json['tele'];
                                  user.avatarUrl = json['avatarUrl'];
                                  Http.user = user;
                                  Navigator.pushReplacement(context,
                                      new MaterialPageRoute(builder: (context) {
                                    return TabNavigator();
                                  }));
                                });
                              } else {
                                Toast.toast(context, msg: "用户名不存在或密码错误");
                              }
                            });
                            // print(result.data);
                          }
                          //2.根据Face登录
                        } else if (_imgPath != null) {
                          _loginByFace().then((res) {
                            print(res);
                            Navigator.pushReplacement(context,
                                new MaterialPageRoute(builder: (context) {
                              return TabNavigator();
                            }));
                          });
                        }
                      } else {
                        print("拍照");
                        _takePhoto();
                      }
                    },
                    child: Text(
                      _showLogo ? '登录' : _imgPath != null ? 'Face登录' : '拍照',
                      style: TextStyle(fontSize: 20),
                    ),
                    shape: new RoundedRectangleBorder(
                        borderRadius: new BorderRadius.circular(3.0)),
                  ),
                  Container(
                    margin: EdgeInsets.only(left: 20),
                    width: 100,
                    height: 50,
                    decoration: BoxDecoration(
                      border: Border(
                          top: BorderSide(color: Colors.grey, width: 1.0),
                          left: BorderSide(color: Colors.grey, width: 1.0),
                          right: BorderSide(color: Colors.grey, width: 1.0),
                          bottom: BorderSide(color: Colors.grey, width: 1.0)),
                    ),
                    child: RaisedButton(
                      textColor: Colors.black,
                      color: Colors.white,
                      disabledColor: Colors.white,
                      onPressed: () {
                        Navigator.push(context,
                            new MaterialPageRoute(builder: (context) {
                          return RegisterPage();
                        }));
//                        });
                      },
                      child: Text(
                        '注册',
                        style: TextStyle(fontSize: 20),
                      ),
                    ),
                  )
                ],
              ))
        ],
      ),
    );
  }

  _loginByFace() async {
    Options options = new Options();
    String _key = Http.userInfoKey;
    print(Http.userInfoKey);
    if (!_key.contains("=")) {
      _key = 'sessionId=' + _key;
    }
    var name = _imgPath.path
        .substring(_imgPath.path.lastIndexOf("/") + 1, _imgPath.path.length);
    print(name);
    FormData fd = new FormData.from(
        {"imgFile": new UploadFileInfo(new File(_imgPath.path), name)});
    Dio dio = new Dio();
    options.headers['USER_INFO_KEY'] = '$_key';
    Response reponse = await dio.post<String>(
        Http.API_PREFIX + '/api/face/search',
        data: fd,
        options: options);
    Http.userInfoKey = reponse.headers.value('USER_INFO_KEY');
    return reponse;
  }

  _login() async {
    Response response = await Http.request('/api/user/login',
        method: Http.POST,
        data: {'nickname': _nickName, 'password': _password});
    Http.userInfoKey = response.headers.value('USER_INFO_KEY');
    print(Http.userInfoKey);
    return response;
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
