import 'package:face_app/model/User.dart';
import 'package:flutter/material.dart';

class AboutmeScreen extends StatefulWidget {
  @override
  _AboutmeScreenState createState() => _AboutmeScreenState();
}

class _AboutmeScreenState extends State<AboutmeScreen> {
  User user=new User();
  @override
  void initState() {
    super.initState();
    user.setNickname("伏海旭");
    user.setUsername("伏海旭");
    user.setPassword("123");
    user.setTele("15189801203");
  }

  Widget buildAvator(User user) {
    return new Container(
        padding: const EdgeInsets.only(top:10.0),
        child: new Container(
          child: new ListTile(
              title: new Text(user.getUsername()+"("+user.getNickname()+")"),
              leading: new Image.asset("resources/QQ.jpg",
                  width: 60.0, height: 60.0),
              subtitle: new Text("联系方式:"+user.getTele()),
              trailing: 
              new Icon(Icons.keyboard_arrow_right)
              ),
            ),
          color: Colors.white,
        height: 100.0
      );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('关于我', style: TextStyle(color: Colors.white)),
        ),
        body: ListView(
          children: <Widget>[
            buildAvator(user),
            new Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Container(
                  child: Icon(Icons.ac_unit),
                  height: 50.0,
                  width: 50.0,
                  decoration:
                    BoxDecoration(
                      border:  Border.all(color: Colors.grey),
                    ),
                )
              ],
            ),
          ]
        ));
  }
}
