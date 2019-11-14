import 'dart:io';

import 'package:face_app/mine.dart';
import 'package:face_app/predict_page.dart';
import 'package:face_app/recommend_page.dart';
import 'package:face_app/share_page.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

class FlowerPage extends StatefulWidget {
  @override
  _FlowerPageState createState() => _FlowerPageState();
}

class _FlowerPageState extends State<FlowerPage>
    with SingleTickerProviderStateMixin {
  bool _isPredict;
  final double _imgWidth = 200;
  File _imgPath;
  List<Widget> _list = new List();
  TabController _tabController;
  int _currentIndex = 4;
  final _defaultColor = Colors.grey;
  final _activeColor = Colors.redAccent;
  @override
  void initState() {
    super.initState();
    _isPredict = false;
    _tabController = new TabController(vsync: this, length: 4);
    _list
      ..add(RecommendPage())
      ..add(SharePage())
      ..add(PredictPage())
      ..add(MinePage());
  }

  _takePhoto() async {
    var image = await ImagePicker.pickImage(source: ImageSource.camera);
    setState(() {
      _imgPath = image;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('UFace 绿植检测'),
      ),
//      bottomNavigationBar: BottomNavigationBar(
//          currentIndex: _currentIndex,
//          onTap: (index) {
//            // _contentPageController.jumpToPage(index);
//            print('页面跳转');
//            print(index);
//            setState(() {
//              _currentIndex = index;
//            });
//          },
//          type: BottomNavigationBarType.fixed,
//          items: [
//            _bottomItem("主页", Icons.home, 0),
//            _bottomItem("分享", Icons.explore, 1),
//            _bottomItem("预测", Icons.pages, 2),
//            _bottomItem("我", Icons.airplay, 3),
//          ]),
//      floatingActionButton: FloatingActionButton.extended(
//        onPressed: () {
//          //  Navigator.pushReplacement(context,
//          //                         new MaterialPageRoute(builder: (context) {
//          //                       return PredictPage();
//          //                         }));
//          setState(() {
//            _currentIndex = 2;
//          });
//        },
//        icon: Icon(Icons.add, color: Colors.white),
//        backgroundColor: Colors.pinkAccent,
//        label: Text('相面'),
//      ),
      body: buildFlower()
    );
  }

  Widget buildFlower(){
    return ListView(children: <Widget>[
        Container(
          // width: _imgWidth - 20,
          // height: _imgWidth - 20,
          child: !_isPredict
              ? Image.asset(
                  'images/index1.jpeg',
                  width: _imgWidth - 40,
                  height: _imgWidth - 40,
                )
              : Image.asset(
                  _imgPath.path,
                  width: _imgWidth - 40,
                  height: _imgWidth - 40,
                ),
        ),
        Container(
          child: MaterialButton(
            color: Colors.pinkAccent,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Icon(
                  Icons.photo_camera,
                  color: Colors.white,
                ),
                Text(
                  '拍照',
                  style: TextStyle(color: Colors.white),
                )
              ],
            ),
            onPressed: () {
              _takePhoto();
            },
          ),
        )
      ]);
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
