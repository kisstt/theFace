import 'package:flutter/material.dart';


class EmailScreen extends StatefulWidget {
  @override
  _EmailScreenState createState() => _EmailScreenState();
}

class _EmailScreenState extends State<EmailScreen> {

  String name = "null";
  String phone = "null";
  String email = "null";
  String portrait = "null";

  @override
  void initState() {
    super.initState();


  }

   Widget _drawerOption(Icon icon, String name) {
    return new Container(
      padding: const EdgeInsets.only(top: 19.0),
      child: new Row(
        children: <Widget>[
          new Container(
              padding: const EdgeInsets.only(right: 28.0), child: icon),
          new Text(name, textScaleFactor: 1.1)
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    Drawer drawer=new Drawer(
      child: new ListView(
        children: <Widget>[
          new DrawerHeader(
            child: new Column(
              children: <Widget>[
                new GestureDetector(
                  onTap: (){

                  },
                  child: new Row(
                    children: <Widget>[
                      new Container(
                        padding: const EdgeInsets.only(right:12.0),
                        child: new CircleAvatar(
                          backgroundImage: new NetworkImage(""),
                          radius: 22.0,
                        ),
                      ),
                      new Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          new Text(
                            name,
                            textScaleFactor:1.4
                          ),
                          new Text(
                            phone,
                            textScaleFactor:1.1
                          )
                        ],
                      )
                    ]
                    ),
                ),
                
            new GestureDetector(
              onTap: (){

              },
              child: new Container(
                decoration: new BoxDecoration(),
                child: _drawerOption(new Icon(Icons.account_circle), "个人资料"),
                )
                ),
                new GestureDetector(
                  onTap: (){

                  },
                  child: new Container(
                    decoration: new BoxDecoration(),
                    child: _drawerOption(new Icon(Icons.settings),"设置"),
                  ),
                )
              ],
            ),
          )
        ],
      ),
    );
    return Scaffold(
      appBar: AppBar(
        title:Text(
          '消息',
          style:TextStyle(color: Colors.white),
        ),
        centerTitle: true,
        elevation: 0.0,
      ),
      drawer: drawer,
      body: Center(
        child: Text('EMAIL'),
      )
    );
  }
}