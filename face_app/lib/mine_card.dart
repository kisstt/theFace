import 'package:face_app/base_card.dart';
import 'package:face_app/login_page.dart';
import 'package:flutter/material.dart';

class CardMine extends BaseCard {
  @override
  _CardMineState createState() => _CardMineState();
}

class _CardMineState extends BaseCardState {
  @override
  void initState() {
    subTitleColor = Color(0xffb99444);
    super.initState();
  }

  @override
  bottomContent() {
    // TODO: implement bottomContent
    return Expanded(
      child: Container(
          margin: EdgeInsets.only(top: 20),
          child: Column(
            children: <Widget>[
              ClipRRect(
                  borderRadius: BorderRadius.circular(10),
                  child: MaterialButton(
                    minWidth: double.infinity,
                    child: Text(
                      '退出登录',
                      style: TextStyle(color: Colors.white),
                    ),
                    color: Colors.red,
                    onPressed: () {
                      _showModalBottomSheet(context);
                      // Navigator.push(context,
                      //       new MaterialPageRoute(builder: (context) {
                      //     return LoginPage();
                      //   }));
                    },
                  ))
            ],
          )),
    );
  }
_showModalBottomSheet(BuildContext context) {
    showModalBottomSheet(
      context: context,
      builder: (context) => Container(
            child: ListView(
                children: <Widget>[
                  InkWell(
                  child: Container(alignment: Alignment.center, height: 60.0, child: Text('确认退出',style: TextStyle(color: Colors.red),)),
                  onTap: () {
                   Navigator.pushReplacement(context,
                            new MaterialPageRoute(builder: (context) {
                          return LoginPage();
                        }));
                  }),
                  InkWell(
                  child: Container(alignment: Alignment.center, height: 60.0, child: Text('取消')),
                  onTap: () {
                    Navigator.pop(context);
                  }),
                ],
            ),
            height: 120.0,
          )
    );
  }
  // @override
  // topTitle(String title) {
  //   // TODO: tmplement topTitle
  //   return super.topTitle('用户');
  // }

  // @override
  // Widget subTitle(String title) {
  //   // return super.subTitle('送你一天无限卡，全场书籍免费读 >');
  // }
}
