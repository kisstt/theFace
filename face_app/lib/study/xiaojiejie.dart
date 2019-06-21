import 'package:flutter/material.dart';


void main() {
  runApp(MaterialApp(
    title: '页面跳转返回数据',
    home: FirstPage(),
  ));
}

class FirstPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title:Text('找女生要电话'),
      ),
      body:Center(
          child: RouteButton(),
      )
    );
  }
}


class RouteButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return RaisedButton(
      onPressed: (){
        _navigateToXiaoJieJie(context);
      },
      child: Text('去找女生'),
    );
  }

  _navigateToXiaoJieJie(BuildContext context) async{
    final result =await Navigator.push(context,
    MaterialPageRoute(builder: (context)=>XiaoJieJie())
    );

    Scaffold.of(context).showSnackBar(SnackBar(content:Text('$result')));
  }

}


class XiaoJieJie extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('我是女生'),
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            RaisedButton(
              child: Text('小姐姐1'),
              onPressed: (){
                Navigator.pop(context,'小姐姐1');
              },
            ), 
            RaisedButton(
              child: Text('小姐姐2'),
              onPressed: (){
                Navigator.pop(context,'小姐姐1');
              },
            ), 
            RaisedButton(
              child: Text('小姐姐3'),
              onPressed: (){
                Navigator.pop(context,'小姐姐3');
              },
            ),
          ],
          ),
        ),
      );
  }
}