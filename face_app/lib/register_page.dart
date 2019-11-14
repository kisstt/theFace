import 'package:dio/dio.dart';
import 'package:face_app/component/toast.dart';
import 'package:face_app/entity/user.dart';
import 'package:face_app/face_register_page.dart';
import 'package:face_app/login_first_page.dart';
import 'package:face_app/login_page.dart';
import 'package:face_app/utils/http.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class RegisterPage extends StatefulWidget {
  @override
  _RegisterPageState createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  //生日时间
  var _birthDay;
  User _user = User();
  String _confirmPwd;
  bool _showPwd = false;

  @override
  void initState() {
    _statusBar();
    super.initState();
  }

  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blue,
        title: Text(
          '注册',
          style: TextStyle(color: Colors.white),
        ),
      ),
      body: new SingleChildScrollView(
        child: new ConstrainedBox(
          constraints: new BoxConstraints(
            minHeight: 120.0,
          ),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Form(
                key: _formKey,
                child: Column(
                  children: <Widget>[
                    //用户名Input
                    TextFormField(
                      validator: (value) {
                        if (value.isEmpty) {
                          return '请输入用户名';
                        }
                        return null;
                      },
                      onSaved: (value) {
                        setState(() {
                          _user.nickname = value;
                          _user.username = value;
                        });
                      },
                      decoration: InputDecoration(
                          labelText: '用户名',
                          prefixIcon: Icon(Icons.perm_identity)),
                    ),
                    TextFormField(
                      validator: (value) {
                        if (value.isEmpty) {
                          return '请输入密码';
                        }
                        return null;
                      },
                      onSaved: (value) {
                        setState(() {
                          _user.password = value;
                        });
                      },
                      obscureText: !_showPwd,
                      decoration: InputDecoration(
                          labelText: '密码',
                          prefixIcon: IconButton(
                              icon: _showPwd
                                  ? Icon(Icons.visibility_off)
                                  : Icon(Icons.visibility),
                              onPressed: () {
                                setState(() {
                                  _showPwd = !_showPwd;
                                });
                              })),
                    ),
                    TextFormField(
                      validator: (value) {
                        if (value.isEmpty) {
                          return '确认密码';
                        }
                        return null;
                      },
                      obscureText: !_showPwd,
                      onSaved: (value) {
                        setState(() {
                          _confirmPwd = value;
                        });
                      },
                      decoration: InputDecoration(labelText: '确认密码'),
                    ),
                    TextFormField(
                      validator: (value) {
                        if (value.isEmpty) {
                          return '确认手机号';
                        }
                        return null;
                      },
                      onSaved: (value) {
                        setState(() {
                          _user.tele = value;
                        });
                      },
                      decoration: InputDecoration(
                          labelText: '手机号', prefixIcon: Icon(Icons.phone)),
                    ),
                    Row(
                      children: <Widget>[
                        Icon(Icons.access_alarm),
                        RaisedButton(
                          color: Colors.blue,
                          onPressed: () {
                            _showDataPicker();
                          },
                          child: Text(
                            _birthDay == null ? '选择生日' : _birthDay,
                            style: TextStyle(color: Colors.white),
                          ),
                        ),
                      ],
                    )
                  ],
                ),
              ),
              MaterialButton(
                height: 50.0,
                onPressed: () {
                  if (_formKey.currentState.validate()) {
                    _formKey.currentState.save();
                    if (_user.password == _confirmPwd) {
                      _user.birthday = _birthDay;
                      _register().then((res) {
                        print(res);
                        if (res.toString().contains("添加新用户")) {
                          Navigator.push(
                            context,
                            new MaterialPageRoute(
                              builder: (context) {
                                return LoginFirstPage();
                              },
                            ),
                          );
                        }
                      });
                    } else {
                      Toast.toast(context, msg: "两次输入密码不一致");
                    }
                  }
                },
                color: Colors.blue,
                minWidth: double.infinity,
                child: Text(
                  '注册',
                  style: TextStyle(color: Colors.white, fontSize: 25),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  _statusBar() {
    //黑色沉浸式状态栏
    SystemUiOverlayStyle uiOverlayStyle = SystemUiOverlayStyle(
      systemNavigationBarColor: Color(0xFF000000),
      systemNavigationBarDividerColor: null,
      statusBarColor: Colors.transparent,
      systemNavigationBarIconBrightness: Brightness.light,
      statusBarIconBrightness: Brightness.dark,
      statusBarBrightness: Brightness.light,
    );
    SystemChrome.setSystemUIOverlayStyle(uiOverlayStyle);
  }

  _showDataPicker() async {
    Locale myLocale = Localizations.localeOf(context);
    print(DateTime.now());
    var picker = await showDatePicker(
        context: context,
        initialDate: DateTime.now(),
        firstDate: DateTime(1900),
        lastDate: DateTime.now(),
        locale: myLocale);
    setState(() {
      _birthDay = picker.toString().split(" ")[0];
    });
  }

  Widget buildTextInput(String name, Icon icon) {
    return TextFormField(
      validator: (value) {
        if (value.isEmpty) {
          return '请输入$name';
        }
        return null;
      },
      onSaved: (value) {},
      decoration: InputDecoration(labelText: '$name', prefixIcon: icon),
    );
  }

  _register() async {
    Response response =
        await Http.request('/api/user/register', method: Http.POST, data: {
      'nickname': _user.nickname,
      'username': _user.username,
      'password': _user.password,
      'email': _user.email,
      'tele': _user.tele,
      'birthday': _user.birthday,
      'avatarUrl': "http://localhost:8012/images/avator/touxiang1.jpg"
    });
    return response;
  }
}
