import 'package:flutter/material.dart';

class PageScreen extends StatefulWidget {
  @override
  _PageScreenState createState() => _PageScreenState();
}

class _PageScreenState extends State<PageScreen> {
  @override
  Widget build(BuildContext context) {
     return Scaffold(
      appBar: AppBar(title:Text('今日预测',
          style: TextStyle(color: Colors.white),
        ),
      ),
      body: Center(
        child: Text('PAGE'),
      )
     );
  }
}