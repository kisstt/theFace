import 'dart:core' as prefix0;
import 'dart:core';

import 'package:face_app/flower_page.dart';
import 'package:face_app/login_page.dart';
import 'package:face_app/predict_page.dart';
import 'package:face_app/recommend_page.dart';
import 'package:face_app/share_page.dart';
import 'package:flutter/material.dart';

import 'mine.dart';

//底部导航创建
class TabNavigator extends StatefulWidget {
  _TabNavigatorState createState() => _TabNavigatorState();
}

class _TabNavigatorState extends State<TabNavigator> with SingleTickerProviderStateMixin {
  final _defaultColor = Colors.grey;
  final _activeColor = Colors.redAccent;
  int _currentIndex = 0;

  // final ContentPageController _contentPageController=ContentPageController();

  List<Widget> _list=new List();
  TabController _tabController;

  @override
  void initState(){
    _tabController=new TabController(vsync: this,length: 4);
  _list..add(RecommendPage())..add(SharePage())
    ..add(PredictPage())..add(MinePage())..add(FlowerPage());
    super.initState();
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      body: _list[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
          currentIndex: _currentIndex,
          onTap: (index) {
            // _contentPageController.jumpToPage(index);
            print('页面跳转');
            print(index);
            setState(() {
              _currentIndex = index;
            });
          },
          type: BottomNavigationBarType.fixed,
          items: [
            _bottomItem("主页", Icons.home, 0),
            _bottomItem("分享", Icons.explore, 1),
            _bottomItem("预测", Icons.pages, 2),
            _bottomItem("我", Icons.airplay, 3),
          ]),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: () {
           setState(() {
              _currentIndex = 2;
            });
        },
        icon: Icon(Icons.add, color: Colors.white),
        backgroundColor: Colors.pinkAccent,
        label: Text('相面'),
      ),
    );
  }

  _bottomItem(String title, IconData icon, int index) {
    return BottomNavigationBarItem(
        icon: Icon(
          icon,
          color: _defaultColor,
        ),
        activeIcon: Icon(
          icon,
          color: _activeColor,
        ),
        title: Text(
          title,
          style: TextStyle(
              color: _currentIndex != index ? _defaultColor : _activeColor),
        ));
  }
}
