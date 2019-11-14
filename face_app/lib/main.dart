import 'package:face_app/article_page.dart';
import 'package:face_app/face_register_page.dart';
import 'package:face_app/login_first_page.dart';
import 'package:face_app/login_page.dart';
import 'package:face_app/register_page.dart';
import 'package:face_app/tab_navigator.dart';
import 'package:flutter/material.dart';

void main()=>runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '人脸识别App',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(primarySwatch: Colors.red),
      home: LoginPage(),
    );
  }
}








