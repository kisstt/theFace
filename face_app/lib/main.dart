
import 'package:face_app/pages/message_page.dart';
import 'package:flutter/material.dart';
import 'bottom_navigation_widget.dart';
import 'dart:io';
void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: '人脸相面App',
        theme: ThemeData(primarySwatch: Colors.red),
        debugShowCheckedModeBanner: false,
        home: ButtomNavigationWidget(),
        routes:<String,WidgetBuilder>{
          '/home':(BuildContext context)=>ButtomNavigationWidget()
        }
      );
  }
}

class KeepAliveDemo extends StatefulWidget {
  @override
  _KeepAliveDemoState createState() => _KeepAliveDemoState();
}

class _KeepAliveDemoState extends State<KeepAliveDemo>
    with SingleTickerProviderStateMixin {
  TabController _controller;

  @override
  void initState() {
    super.initState();
    _controller = TabController(length: 3, vsync: this);
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('KeepLiveDemo'),
        bottom: TabBar(
          controller: _controller,
          tabs: [
            Tab(icon: Icon(Icons.directions_run)),
            Tab(icon: Icon(Icons.directions_bike)),
            Tab(icon: Icon(Icons.directions_bus))
          ],
        ),
      ),
      body: TabBarView(
        controller: _controller,
        children: <Widget>[
          Text('1'),
          Text('2'),
          Text('3'),
        ], 
      )
    );
  }
}
