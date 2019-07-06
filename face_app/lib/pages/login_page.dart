import 'package:face_app/bottom_navigation_widget.dart';
import 'package:flutter/material.dart';
import 'package:groovin_material_icons/groovin_material_icons.dart';
import '../utils/http.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  String _nickname, _password;
  bool _isObscure = true;
  Color _eyeColor;
  List _loginMethod = [
    {"title": "github", "icon": GroovinMaterialIcons.github_box},
    {
      "title": "google",
      "icon": GroovinMaterialIcons.google,
    },
    {
      "title": "twitter",
      "icon": GroovinMaterialIcons.twitter,
    },
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: Form(
            key: _formKey,
            child: ListView(children: <Widget>[
              SizedBox(
                height: kToolbarHeight,
              ),
              buildTitle(),
              buildTitleLine(),
              buildNickNameTextField(context),
              buildPassWordTextField(context),
              buildLoginButtn(context)
            ])),
      ),
    );
  }

  Padding buildTitle() {
    return Padding(
      padding: EdgeInsets.all(8.0),
      child: Text(
        'Login',
        style: TextStyle(fontSize: 42.0, color: Colors.redAccent),
      ),
    );
  }

  Padding buildTitleLine() {
    return Padding(
      padding: EdgeInsets.only(left: 12.0, top: 4.0),
      child: Align(
        alignment: Alignment.bottomLeft,
        child: Container(
          color: Colors.black,
          width: 40.0,
          height: 2.0,
        ),
      ),
    );
  }

  TextFormField buildNickNameTextField(BuildContext context) {
    return TextFormField(
      onSaved: (String value) => _nickname = value,
      obscureText: _isObscure,
      validator: (String value) {
        if (value.isEmpty) {
          return '请输入昵称/手机号/邮箱';
        }
      },
      decoration: InputDecoration(labelText: '昵称/手机号/邮箱'),
    );
  }

  TextFormField buildPassWordTextField(BuildContext context) {
    return TextFormField(
      onSaved: (String value) => _password = value,
      obscureText: _isObscure,
      validator: (String value) {
        if (value.isEmpty) {
          return '请输入密码';
        }
      },
      decoration: InputDecoration(
        labelText: '密码',
        suffixIcon: IconButton(
          icon: Icon(
            Icons.remove_red_eye,
            color: _eyeColor,
          ),
          onPressed: () {
            setState(() {
              _isObscure = !_isObscure;
              _eyeColor =
                  _isObscure ? Colors.grey : Theme.of(context).iconTheme.color;
            });
          },
        ),
      ),
    );
  }

  RaisedButton buildLoginButtn(BuildContext context) {
    return RaisedButton(
      onPressed: () => {_login(context)},
      child: Text(
        '登录',
        style: TextStyle(fontSize: 20),
      ),
    );
  }

  _login(BuildContext context) async {
    //  var response =await Http().post(
    //     "/login",
    //     data:
    //     {
    //       'nickname': _nickname,
    //       'password': _password
    //     }
    //   );
    //   print("====================");
    //   print(response);
    Navigator.of(context).pushReplacementNamed('/home');
  }
}
