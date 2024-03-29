import 'package:flutter/material.dart';

//卡片基类
class BaseCard extends StatefulWidget {
  @override
  BaseCardState createState() => BaseCardState();
}

class BaseCardState extends State<BaseCard> {

  Color subTitleColor=Colors.grey;
  @override
  Widget build(BuildContext context) {
    return PhysicalModel(
      color: Colors.transparent,
      borderRadius: BorderRadius.circular(6),
      clipBehavior: Clip.antiAlias,
      child: Container(
        decoration: BoxDecoration(
          color: Colors.white,
          
        ),
        child: Column(
          children: <Widget>[
            topContent(),
            bottomContent()
          ],
        ),
      ),
    );
  }

  topContent(){
    return Padding(
      padding: EdgeInsets.only(left: 10,top: 20,bottom: 20,right: 10),
      child: Column(
        children: <Widget>[
          Row(
            children: <Widget>[
              topTitle('')
            ],
          ),
          subTitle('')
        ],
      ),
    );
  }

  bottomContent(){
    return Container();
  }

  Widget subTitle(String title){
    return Padding(
      padding: EdgeInsets.only(top: 5),
      child: Text(title,style: TextStyle(fontSize: 11,
      color:subTitleColor ),
      ),
    );
  }

  topTitle(String title){
    return Text(title,style: TextStyle(fontSize: 22));
  }
}