import 'package:flutter/material.dart';


class CustomAppBar extends StatefulWidget {
  @override
  _CustomAppBarState createState() => _CustomAppBarState();
}

class _CustomAppBarState extends State<CustomAppBar> {
 String _searchKey="";
  @override
  Widget build(BuildContext context) {
  double paddingTop=MediaQuery.of(context).padding.top;
    return Container(
      margin: EdgeInsets.fromLTRB(20, paddingTop+10, 20, 5),
      padding: EdgeInsets.fromLTRB(20, 7, 20, 7),
      decoration: BoxDecoration(
        borderRadius:BorderRadius.circular(19),color: Colors.white70),
        child: Row(
          children: <Widget>[
            Icon(
              Icons.search,
              color: Colors.grey,
            ),
            Expanded(child: TextField(
              onChanged:(value){
                setState(() {
                 _searchKey=value; 
                });
              },
              decoration: InputDecoration(

              ),
            ),
            ),
            Container(
              width: 1,
              height: 20,
              margin: EdgeInsets.only(right: 13),
              decoration: BoxDecoration(color: Colors.grey),
            ),
            Text(
              '搜索',
              style: TextStyle(fontSize: 13),
            )
          ],),
    );
  }
}