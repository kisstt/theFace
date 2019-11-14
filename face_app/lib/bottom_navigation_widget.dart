import 'package:face_app/pages/message_page.dart';
import 'package:flutter/material.dart';
import 'pages/home_screen.dart';
import 'pages/pages_screen.dart';
import 'pages/aboutme_screen.dart';
import 'pages/email_screen.dart';

class ButtomNavigationWidget extends StatefulWidget {
  @override
  _ButtomNavigationWidgetState createState() => _ButtomNavigationWidgetState();
}

class _ButtomNavigationWidgetState extends State<ButtomNavigationWidget> {
  final _ButtomNavigationColor=Colors.red;
  int _currentIndex=2;
  List<Widget> list=List();

  @override
  void initState() {
    list
      ..add(MessageWidget())
      ..add(EmailScreen())
      ..add(PageScreen())
      ..add(AboutmeScreen());
      super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: list[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        items: [
         
            BottomNavigationBarItem(
            icon: Icon(
              Icons.email,
              color: _ButtomNavigationColor,
            ),
            title: Text(
              '消息',
              style: TextStyle(color: _ButtomNavigationColor),
            )
          ),  BottomNavigationBarItem(
            icon: Icon(
              Icons.contact_mail,
              color: _ButtomNavigationColor,
            ),
            title: Text(
              '通讯录',
              style: TextStyle(color: _ButtomNavigationColor),
            )
          ),BottomNavigationBarItem(
            icon: Icon(
              Icons.pages,
              color: _ButtomNavigationColor,
            ),
            title: Text(
              '今日预测',
              style: TextStyle(color: _ButtomNavigationColor),
            )
          ), BottomNavigationBarItem(
            icon: Icon(
              Icons.airplay,
              color: _ButtomNavigationColor,
            ),
            title: Text(
              '我',
               style:TextStyle(color:_ButtomNavigationColor)
            )
          ),
        ],
        currentIndex: _currentIndex,
        onTap: (int index){
          setState(() {
           _currentIndex =index; 
          });
        },
        type: BottomNavigationBarType.fixed,
        ),
    );
  }
}