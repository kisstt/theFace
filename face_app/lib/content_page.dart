
import 'dart:ui';

import 'package:face_app/card_recommend.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'mine_card.dart';


class ContentPage extends StatefulWidget {
final ValueChanged<int> onPageChanged;
final ContentPageController contentPageController;
//构造方法,可选参数
//:初始化列表
  const ContentPage({Key key, this.onPageChanged,this.contentPageController})  :super(key: key);

  @override
  _ContentPageState createState() => _ContentPageState();
}

class _ContentPageState extends State<ContentPage> {
PageController _pageController=PageController(
  viewportFraction:0.9 );

  static List<Color>  _colors=[
    Colors.blue,
    Colors.red,
    Colors.deepOrange,
    Colors.teal,
  ];

  @override
  void initState() { 
    if(widget.contentPageController!=null){
      widget.contentPageController._pageController=_pageController;
    }
    _statusBar();
    super.initState();
  }
  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        //appBar
        // CustomAppBar(),
        Expanded(child: PageView(
          onPageChanged: widget.onPageChanged,
          //高度撑开，否则在Colunm没有高度会报错
          controller: _pageController,
          children: <Widget>[
            // RecommendPage(),
            _wrapItem(CardRecommend()),
            _wrapItem(CardRecommend()),
            _wrapItem(CardMine())         
          ],
          ),)
      ],
    );
  }

  Widget _wrapItem(Widget widget){
    return Padding(
      padding: EdgeInsets.all(5),
      child: widget,
    );
  }

//状态栏样式  沉浸式状态栏
  _statusBar(){
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
}

//内容区域的控制器
class ContentPageController{
  PageController _pageController;
  void jumpToPage(int page){
    //dart  技巧:安全的调用
    _pageController?.jumpToPage(page);
  }
}

