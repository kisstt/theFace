import 'package:flutter/material.dart';
import 'package:face_app/pages/sign_up_page.dart';
import 'package:face_app/style/login_theme.dart' as theme;
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:face_app/pages/sigin_in_page.dart';

class LoginPage extends StatefulWidget {
  LoginPage({Key key}) : super(key: key);

  @override
  _LoginPageState createState() => new _LoginPageState();
}

class _LoginPageState extends State<LoginPage> with TickerProviderStateMixin {
  PageController _pageController;
  PageView _pageView;
  int _currentPage = 0;

  @override
  void initState() {
    super.initState();
    _pageController = new PageController();
    _pageView = new PageView(
      controller: _pageController,
      children: <Widget>[
        new SignInPage(),
        new SignUpPage(),
      ],
      onPageChanged: (index) {
        setState(() {
          _currentPage = index;
        });
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        body: new SafeArea(
      child: new SingleChildScrollView(
          child: new Container(
              height: MediaQuery.of(context).size.height,
              width: MediaQuery.of(context).size.width,
              //设置渐变的背景
              decoration: new BoxDecoration(
                gradient: theme.Theme.primaryGradient,
              ),
              child: new Column(
                mainAxisSize: MainAxisSize.max,
                children: <Widget>[
                  new SizedBox(
                    height: 75,
                  ),
                  //
                      // 可以用SizeBox这种写法代替Padding：在Row或者Column中单独设置一个方向的间距的时候
                       //
        //  new Padding(padding: EdgeInsets.only(top: 75)),

                  //顶部图片
                  new Image(
                      width: 250,
                      height: 191,
                      image: new AssetImage("resources/login_logo.png")),
                  new SizedBox(
                    height: 20,
                  ),

                  //中间的Indicator指示器
                  new Container(
                    width: 300,
                    height: 50,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.all(Radius.circular(25)),
                      color: Color(0x552B2B2B),
                    ),
                    child: new Row(
                      children: <Widget>[
                        Expanded(
                            child: new Container(
                        //TODO:暂时不会用Paint去自定义indicator，
                        //所以暂时只能这样实现了
                          decoration: _currentPage == 0
                              ? BoxDecoration(
                                  borderRadius: BorderRadius.all(
                                    Radius.circular(25),
                                  ),
                                  color: Colors.white,
                                )
                              : null,
                          child: new Center(
                            child: new FlatButton(
                              onPressed: () {
                                _pageController.animateToPage(0,
                                    duration: Duration(milliseconds: 500),
                                    curve: Curves.decelerate);
                              },
                              child: new Text(
                                "已有账号",
                                style: TextStyle(fontSize: 16),
                              ),
                            ),
                          ),
                        )),
                        Expanded(
                            child: new Container(
                          decoration: _currentPage == 1? BoxDecoration(
                                  borderRadius: BorderRadius.all(
                                    Radius.circular(25),
                                  ),
                                  color: Colors.white,
                                )
                              : null,
                          child: new Center(
                            child: new FlatButton(
                              onPressed: () {
                                _pageController.animateToPage(1,
                                    duration: Duration(milliseconds: 500),
                                    curve: Curves.decelerate);
                              },
                              child: new Text(
                                "新用户",
                                style: TextStyle(fontSize: 16),
                              ),
                            ),
                          ),
                        )),
                      ],
                    ),
                  ),
                    //  new SignInPage(),
                    //  new SignUpPage(),
                  new Expanded(child: _pageView),
                ],
              ))),
    ));
  }
}
