import 'package:flutter/material.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

class MessageWidget extends StatefulWidget {
  @override
  _MessageWidgetState createState() => _MessageWidgetState();
}

class _MessageWidgetState extends State<MessageWidget> with SingleTickerProviderStateMixin{
  List _contactGroup = [
    {
      "person": '胖虎',
      "picture": 'images/1.jpeg',
      "lastMessage": '高考加油',
      "lastTime": '9:00'
    },
    {
      "person": '多啦A梦',
      "picture": 'images/42.jpeg',
      "lastMessage": '高考加油',
      "lastTime": '9:00'
    },
    {
      "person": '派大星',
      "picture": 'images/2.jpeg',
      "lastMessage": '高考加油',
      "lastTime": '9:00'
    },
    {
      "person": '王守义',
      "picture": 'images/45.jpeg',
      "lastMessage": '高考加油',
      "lastTime": '9:00'
    }
  ];
RefreshController _refreshController;
  @override
  void initState() {
    super.initState();
    _refreshController = RefreshController(initialRefresh:true);
  }

    void _onRefresh(RefreshController controller, List data) async {
    //monitor fetch data from network
    await Future.delayed(Duration(milliseconds: 1000));

    // if (data.length == 0) {
    //   for (int i = 0; i < 10; i++) {
    //     data.add("Item $i");
    //   }
//      pageIndex++;
    // }
    if(mounted)
    setState(() {
      
    });
    controller.refreshCompleted();

    /*
        if(failed){
         _refreshController.refreshFailed();
        }
      */
  }
  void _onLoading(RefreshController controller, List data) async {
    //monitor fetch data from network
    await Future.delayed(Duration(milliseconds: 2000));
    if(mounted)
    setState(() {
      
    });
    controller.loadComplete();  
  }

  Container buildMessage(data) {
    return new Container(
        padding: const EdgeInsets.only(bottom: 1.0),
        child: new Container(
          child: new ListTile(
            title: new Text(data['person']),
            leading:
                new Image.asset(data['picture'], width: 35.0, height: 35.0),
            subtitle: new Text(data['lastMessage']),
            trailing: new Text(data['lastTime']),
          ),
          color: Colors.white,
        ),
        height: 60.0,
        decoration: new UnderlineTabIndicator(
            borderSide: BorderSide(width: 2.0, color: Colors.grey),
            insets: EdgeInsets.fromLTRB(70, 5, 0, 0)));
  }

  ListView buildList(BuildContext context,List data) {
   return ListView.separated(
     padding: EdgeInsets.only(left: 5,right: 5),
     itemBuilder: (context,index)=>
     buildMessage(data[index]),
     separatorBuilder: (context,index){
       return Container(
         height: 0.5,
         color: Colors.green,
       );
     },
     itemCount: data.length,
   );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: new AppBar(
          title: Text('消息'),
          backgroundColor: Colors.red,
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
        body:
          SmartRefresher(
              child:buildList(context,_contactGroup),
              controller: _refreshController,
              enablePullUp: true,
              onRefresh: () {
                _onRefresh(_refreshController, _contactGroup);
              },
              onLoading: () {
                _onLoading(_refreshController, _contactGroup);
              },

            ),
        // new Container(
        //       child: new ListView(
        //       children: <Widget>[...buildList()],
        //     ),
        //   )
      );
  }
}
