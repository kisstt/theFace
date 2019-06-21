import 'package:flutter/material.dart';


class EmailScreen extends StatefulWidget {
  @override
  _EmailScreenState createState() => _EmailScreenState();
}

class _EmailScreenState extends State<EmailScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title:Text(
          '消息',
          style:TextStyle(color: Colors.white)
        ),
      ),
      body: Center(
        child: Text('EMAIL'),
      )
    );
  }
}