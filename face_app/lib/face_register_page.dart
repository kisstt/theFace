import 'dart:convert';
import 'dart:io';

import 'package:dio/dio.dart';
import 'package:face_app/component/toast.dart';
import 'package:face_app/entity/user.dart';
import 'package:face_app/tab_navigator.dart';
import 'package:face_app/utils/http.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:image_picker/image_picker.dart';

class FaceRegisterPage extends StatefulWidget {
  @override
  _FaceRegisterPageState createState() => _FaceRegisterPageState();
}

class _FaceRegisterPageState extends State<FaceRegisterPage> {
  @override
  void initState() {
    _statusBar();
    super.initState();
  }

  File _imgPath;
  User _user;

  @override
  Widget build(BuildContext context) {
    double btnWidth = MediaQuery.of(context).size.width - 20;
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blue,
        title: Text('人脸注册'),
      ),
      body: ListView(
        children: <Widget>[
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Container(
                  margin: EdgeInsets.only(top: 20),
                  width: 200.0,
                  height: 200.0,
                  child: Container(
                    width: 150.0,
                    height: 150.0,
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(75.0)),
                    child: _imgPath != null
                        ? Image.asset(
                            _imgPath.path,
                            width: 150.0,
                            height: 150.0,
                          )
                        : null,
                  ),
                  decoration: BoxDecoration(
                    // borderRadius: BorderRadius.circular(100),
                    border: Border(
                        top: BorderSide(color: Colors.grey, width: 10.0),
                        left: BorderSide(color: Colors.grey, width: 10.0),
                        right: BorderSide(color: Colors.grey, width: 10.0),
                        bottom: BorderSide(color: Colors.grey, width: 10.0)),
                  )),
            ],
          ),
          Container(
              margin: EdgeInsets.only(top: 20),
              child: MaterialButton(
                  color: Colors.blue,
                  minWidth: btnWidth,
                  onPressed: () {
                    _takePhoto();
                  },
                  height: 40,
                  child: Column(
                    children: <Widget>[
                      Row(
                        crossAxisAlignment: CrossAxisAlignment.center,
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: <Widget>[
                          Icon(Icons.photo_camera,
                              color: Colors.white, size: 20),
                          Text('拍照',
                              style:
                                  TextStyle(color: Colors.white, fontSize: 20))
                        ],
                      ),
                    ],
                  ))),
          RaisedButton(
            disabledColor: Colors.red,
            child:
                Text('注册', style: TextStyle(color: Colors.white, fontSize: 20)),
            onPressed: () {
              _addFace().then((res) {
                print(
                    "---------------------addFace register----------------------");
                print(res);
                Map<String,dynamic> map=jsonDecode(res.toString());
                if(map['statusCode']==200){
                  if(res.toString().contains("error_msg")){
                    Toast.toast(context,msg:"图片不是人脸",showTime: 3000);
                  }else{
                    Toast.toast(context,msg:"人脸注册成功",showTime: 3000);
                  }
                }
              });
            },
          ),
        ],
      ),
    );
  }

  /**
   *  if (res.toString().contains("200")) {
      if (res.toString().contains("SUCCESS")) {
      Navigator.pushReplacement(context,
      new MaterialPageRoute(builder: (context) {
      return TabNavigator();
      }
      ))
      } else {
      Toast.toast(context, msg: "照片无人脸")
      }
      } else {
      Toast.toast(context, msg: "上传图片错误")
      }
      });
   */

  _takePhoto() async {
    var image = await ImagePicker.pickImage(source: ImageSource.camera);
    setState(() {
      _imgPath = image;
    });
  }

  _statusBar() {
    //黑色沉浸式状态栏
    SystemUiOverlayStyle uiOverlayStyle = SystemUiOverlayStyle(
      systemNavigationBarColor: Color(0xFF000000),
      systemNavigationBarDividerColor: null,
      statusBarColor: Colors.transparent,
      systemNavigationBarIconBrightness: Brightness.light,
      statusBarIconBrightness: Brightness.dark,
      statusBarBrightness: Brightness.light,
    );
    SystemChrome.setSystemUIOverlayStyle(uiOverlayStyle);
  }

  _addFace() async {
    var name = _imgPath.path
        .substring(_imgPath.path.lastIndexOf("/") + 1, _imgPath.path.length);
    print(name);
    FormData fd = new FormData.from(
        {"imgFile": new UploadFileInfo(new File(_imgPath.path), name)});
    Dio dio = new Dio();
    Options options = new Options();
    String _key = Http.userInfoKey;
    print(Http.userInfoKey);
    if(!_key.contains("=")){
      _key='sessionId='+_key;
    }
    options.headers['USER_INFO_KEY'] = '$_key';
    print(options.headers['USER_INFO_KEY']);
    options.contentType=ContentType.binary;
    // options.headers['Content-Type'] = 'application/json;charset=UTF-8';
    Response reponse = await dio.post<String>(Http.API_PREFIX + '/api/face/add',
        data: fd, options: options);
    return reponse;
  }
}
