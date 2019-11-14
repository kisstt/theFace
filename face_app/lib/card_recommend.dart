

import 'package:face_app/base_card.dart';
import 'package:flutter/material.dart';

//本周推荐
class CardRecommend extends BaseCard{

  @override
  _CardRecommendState createState() =>_CardRecommendState();
}

class _CardRecommendState extends BaseCardState{

  @override
  void initState() {
    subTitleColor=Color(0xffb99444); 
    super.initState();
  }

  @override
  bottomContent() {
    // TODO: implement bottomContent
    return Expanded(child: Container(
      margin: EdgeInsets.only(top: 20),
      child:
      ListView(
        children: <Widget>[
           Card(child: ListTile(title: Text('One-line ListTile'))),
    Card(
      child: ListTile(
        leading: FlutterLogo(),
        title: Text('One-line with leading widget'),
      ),
    ),
    Card(
      child: ListTile(
        title: Text('One-line with trailing widget'),
        trailing: Icon(Icons.more_vert),
      ),
    ),
    Card(
      child: ListTile(
        leading: FlutterLogo(),
        title: Text('One-line with both widgets'),
        trailing: Icon(Icons.more_vert),
      ),
    ),
    Card(
      child: ListTile(
        title: Text('One-line dense ListTile'),
        dense: true,
      ),
    ),
    Card(
      child: ListTile(
        leading: FlutterLogo(size: 56.0),
        title: Text('Two-line ListTile'),
        subtitle: Text('Here is a second line'),
        trailing: Icon(Icons.more_vert),
      ),
    ),
    Card(
      child: ListTile(
        leading: FlutterLogo(size: 72.0),
        title: Text('Three-line ListTile'),
        subtitle: Text(
          'A sufficiently long subtitle warrants three lines.'
        ),
        trailing: Icon(Icons.more_vert),
        isThreeLine: true,
      ),
    ),
  ],
      )
    ),);
  }

  @override
  topTitle(String title) {
    // TODO: tmplement topTitle
    return super.topTitle('本周推荐');
  }

  @override
  Widget subTitle(String title) {
    return super.subTitle('送你一天无限卡，全场书籍免费读 >');
  }
}