import 'package:flutter/material.dart';

class EmailScreen extends StatefulWidget {
  @override
  _EmailScreenState createState() => _EmailScreenState();
}

class _EmailScreenState extends State<EmailScreen> {
  List _contactGroup = [
    {"person": '胖虎', "picture": 'images/1.jpeg'},
    {"person": '多啦A梦', "picture": 'images/42.jpeg'},
    {"person": '派大星', "picture": 'images/2.jpeg'},
    {"person": '王守义', "picture": 'images/45.jpeg'}
  ];

  @override
  void initState() {
    super.initState();
  }

  Container buildMessage(data) {
    return new Container(
        padding: const EdgeInsets.only(bottom: 1.0),
        child: new Container(
          child: new ListTile(
              title: new Text(data['person']),
              leading:
                  new Image.asset(data['picture'], width: 35.0, height: 35.0)),
          color: Colors.white,
        ),
        height: 60.0,
        decoration: new UnderlineTabIndicator(
            borderSide: BorderSide(width: 2.0, color: Colors.grey),
            insets: EdgeInsets.fromLTRB(70, 5, 0, 0)));
  }

  List<Widget> buildList() {
    var list = List<Widget>();
    for (var data in _contactGroup) {
      list.add(buildMessage(data));
    }
    return list;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text(
            '通讯录',
            style: TextStyle(color: Colors.white),
          ),
          actions: <Widget>[
            IconButton(
              icon: Icon(Icons.search, color: Colors.white),
              onPressed: () {},
            ),
            IconButton(
              icon: Icon(Icons.add, color: Colors.white),
              onPressed: () {},
            )
          ],
        ),
        body: new ListView(children: <Widget>[
          new Container(
              padding: const EdgeInsets.only(top: 10.0),
              child: new Container(
                child: new ListTile(
                  title: new Text("新的朋友"),
                  leading: new Container(
                    child: new Icon(Icons.group_add, color: Colors.white),
                    color: Colors.orange,
                    height: 35.0,
                    width: 35.0
                  ),
                  onTap: () {
                    
                  },
                ),
                height: 50.0,
              ),
              decoration: new UnderlineTabIndicator(
                  borderSide: BorderSide(width: 0.5, color: Colors.grey),
                  insets: EdgeInsets.fromLTRB(70, 0, 0, 0))),
          new Container(
              child: new ListTile(
                title: new Text("群聊"),
                leading: new Container(
                  child: new Icon(Icons.group, color: Colors.white),
                  color: Colors.green,
                  height: 35.0,
                  width: 35.0,
                ),
                onTap: () {},
              ),
              height: 50.0,
              decoration: new UnderlineTabIndicator(
                  borderSide: BorderSide(width: 1.0, color: Colors.grey),
                  insets: EdgeInsets.fromLTRB(70, 0, 0, 0))),
          new Container(
            padding: const EdgeInsets.only(bottom: 20.0),
            child: new Container(
                child: new ListTile(
                  title: new Text("标签"),
                  leading: new Container(
                    child: new Icon(Icons.label, color: Colors.white),
                    color: Colors.blue,
                    height: 35.0,
                    width: 35.0,
                  ),
                  onTap: () {
                    print('标签');
                  },
                ),
                height: 50.0,
                decoration: new UnderlineTabIndicator(
                    borderSide: BorderSide(width: 1.0, color: Colors.grey),
                    insets: EdgeInsets.fromLTRB(70, 0, 0, 0))),
          ),
          new Padding(
            padding: const EdgeInsets.only(bottom: 10.0, left: 20.0),
            child: new Text(
              "我的好友",
              style: new TextStyle(fontFamily: "微软雅黑", color: Colors.grey),
            ),
          ),
          ...buildList(),
        ]));
  }
}
