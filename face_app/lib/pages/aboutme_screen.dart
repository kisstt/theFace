import 'package:flutter/material.dart';

class AboutmeScreen extends StatefulWidget {
  @override
  _AboutmeScreenState createState() => _AboutmeScreenState();
}

class _AboutmeScreenState extends State<AboutmeScreen> {
  @override
  Widget build(BuildContext context) {
     return Scaffold(
      appBar: AppBar(
        title:Text(
          '关于我',
          style:TextStyle(color: Colors.white)
        ),
      ),
      body:Center(
        child: Container(
        child: Image.asset('resources/QQ.jpg'),
        width: 300,
        height: 200,
        color: Colors.red
      )
    )   
    );
  }
}