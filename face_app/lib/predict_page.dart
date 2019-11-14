import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:face_app/utils/http.dart';
import 'package:flutter/material.dart';
import 'dart:io';

import 'package:image_picker/image_picker.dart';

import 'component/toast.dart';

class PredictPage extends StatefulWidget {
  @override
  _PredictPageState createState() => _PredictPageState();
}

class _PredictPageState extends State<PredictPage> {
  bool _isPredict = true;
  final double _imgWidth = 200;
  String eyeSummary;
  String faceSummary;
  double facePoint;
  String faceTitle;
  String faceTips;
  String emutionTips;
  String sentence;
  @override
  void initState() {
    _init();
    super.initState();
  }

  _init(){
    var _this=this;
    _pushSentence().then((res){
      Map<String, dynamic> json = jsonDecode(res.toString());
      String s=json['sentence'];
      _this.setState((){
       sentence=s;
      });
    });
  }

  _takePhoto() async {
    var image = await ImagePicker.pickImage(source: ImageSource.camera);
    setState(() {
      _imgPath = image;
    });
  }
  _takePhoto2() async {
    var image = await ImagePicker.pickImage(source: ImageSource.gallery);
    setState(() {
      _imgPath = image;
    });
  }
  File _imgPath;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('UFace 人脸预测'),
        ),
        body: _isPredict ? buildPrePhoto() : buidAfterPhoto());
  }

  Widget buildPrePhoto() {
    return ListView(children: <Widget>[
      Container(
          // width: _imgWidth - 20,
          // height: _imgWidth - 20,
          child: Image.asset(
        'images/index1.jpeg',
        width: _imgWidth - 40,
        height: _imgWidth - 40,
      )),
      Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
        Container(
          child: MaterialButton(
            color: Colors.pinkAccent,
            child: Row(

              children: <Widget>[
                Icon(
                  Icons.photo_camera,
                  color: Colors.white,
                ),
                Text(
                  '拍照',
                  style: TextStyle(color: Colors.white),
                )
              ],
            ),
            onPressed: () async {
              await _takePhoto();
              if(_imgPath!=null)
                _predictFace();
            },
          ),
        ), Container(
            padding: EdgeInsets.fromLTRB(40, 0, 0, 0),
          child: MaterialButton(
            color: Colors.pinkAccent,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Text(
                  '相册',
                  style: TextStyle(color: Colors.white),
                )
              ],
            ),
            onPressed: () async {
              await _takePhoto2();
              if(_imgPath!=null)
              _predictFace();
            },
          ),
        )
    ],),
      Container(
        child: Column(
          children: <Widget>[
            Text('每日一句',style: TextStyle(fontSize: 15,),textAlign: TextAlign.left,),
            Text(sentence,style: TextStyle(fontSize: 15),textAlign: TextAlign.left,),
          ],
        )
      )

    ]);
  }
  _pushSentence() async {
    Options options = new Options();
    String _key = Http.userInfoKey;
    print(Http.userInfoKey);
    if (!_key.contains("=")) {
      _key = 'sessionId=' + _key;
    }
    Dio dio = new Dio();
    options.headers['USER_INFO_KEY'] = '$_key';
    Response reponse = await dio.post<String>(
        Http.API_PREFIX + '/api/sentence/pushSentence',
        data: {'tagId': 5},
        options: options);
    return reponse;
  }
  _predictFace() {
    var _this = this;
    double _facePoint = 40.0;
    String _faceTitle = '❤小主人你拍照要多笑笑哦,据说笑笑可以提高颜值❤';
    String _eyeSummary = '鼻梁上的眼镜给了你文曲星的样子，你有着温文尔雅的一面';
    String _faceTips = '倔强的我们靠才华吃饭';
    String _faceSummary =
        '您是椭圆脸型哦，椭圆形脸与圆形脸很接近，只是椭圆形脸的命运要更上一些，往往能够找到好的另一半；做事能力较强，有主见与主意。椭圆形的人很活泼，很有才华，但是做事情持久性不足，需要有人尽量支持，在背后鼓励他们，多结交朋友可以遇到生命的贵人。';
    _predictFaceReq().then((res){
//      res='{"statusCode": 200,"msg": "","data": {"result": {"face_num": 1,"face_list": [{"glasses": {"probability": 1,"type": "common"},"face_shape": {"probability": 0.43,"type": "round"},"emotion": {"probability": 0.96,"type": "neutral"},"gender": {"probability": 1,"type": "male"},"beauty": 50.78,"face_type": {"probability": 0.97,"type": "human"},"angle": {"roll": -1.96,"pitch": 8.52,"yaw": -1.09},"face_token": "a236128d850f9d94b87fe070d9687521","location": {"top": 92.39,"left": 57.32,"rotation": 1,"width": 94,"height": 84},"face_probability": 1,"age": 22}]},"log_id": 9965999999949,"error_msg": "SUCCESS","cached": 0,"error_code": 0,"timestamp": 1573581075}}';
      if(res.toString().contains("null")){
        Toast.toast(context,msg: "上传图片不含人脸");
        return;
      }
      print(res.toString());
      Map<String,dynamic> jsonData=jsonDecode(res.toString());
      Map<String, dynamic> result = jsonData['data']['result']['face_list'][0];
      print(result['beauty']);
      _facePoint = result['beauty'];
      int age = result['age'];
      String _faceShape = result['face_shape']['type'];
      String _age = "$age";
      String sex = result['gender']['type'];
      String glassesType = result['glasses']['type'];
      String _emotion = result['emotion']['type'];
      if (_emotion == 'happy') {
        _faceTitle = '❤谢谢你这么开心地看着我❤';
      } else {
        _faceTitle = '❤小主人你拍照要多笑笑哦，据说笑笑可以提高颜值❤';
      }

      if (glassesType == "none") {
        _eyeSummary = "眼睛中有星辰大海";
      } else if (glassesType == "common") {
        if (sex == "male")
          _eyeSummary = "鼻梁上的眼镜给了你文曲星的样子，你有着温文尔雅的一面";
        else {
          _eyeSummary = "动人的双眸下有俏皮的一面;小姐姐擦擦你的眼镜，就能看到身边不一样的人";
        }
      } else if (glassesType == "sun") {
        _eyeSummary = "带着墨镜的你嚣张怪癖，有Rap的风范";
      }

      if (_faceShape == "heart") {
        _faceSummary = "您是心形脸哦，心形脸是最完美的脸型，笑起来会给人很温柔的感觉。心形脸的人一般是开朗大方，热情如火，性格温和善良。";
      } else if (_faceShape == "round") {
        _faceSummary =
        "您是圆形脸哦，又称娃娃脸。圆形脸的人注重生活与享受，做事积极但是有时也不愿意付出劳力，喜欢轻快的工作。圆形脸的人非常聪明反应很快，头脑很灵敏，比较好相处，有较好的口才潜力。";
      } else if (_faceShape == "square") {
        _faceSummary =
        "您是方形脸哦，有领导气质。性格执着而不服输，万事都不怕，能够自强不息，坚持不懈，直到达到自己的目的。长方形脸的人很守法、很正直，同时你也希望人家不要随随便便，相对也给人严肃的印象。但处理好的话，也会给人成熟大气，处事冷静，不卑不亢的好印象，是天生的领导者。";
      } else if (_faceShape == "oval") {
        if (sex == "female") {
          _faceSummary =
          "鹅蛋脸是传统的美人脸，给人气质美人温婉大方的印象。鹅蛋脸一般都是文静内敛的个性，很有贤妻良母的感觉。有自己的主见，活泼有才华，多结交认识身边的人，会遇到生命中的贵人。";
        } else if (sex == "male") {
          _faceSummary =
          "您是椭圆脸型哦，椭圆形脸与圆形脸很接近，只是椭圆形脸的命运要更上一些，往往能够找到好的另一半；做事能力较强，有主见与主意。椭圆形的人很活泼，很有才华，但是做事情持久性不足，需要有人尽量支持，在背后鼓励他们，多结交朋友可以遇到生命的贵人。";
        }
      } else if (sex == "triangle") {
        _faceSummary =
        "您是三角脸型哦，人机敏、善于捕捉对手的细微变化，能够随机应变，做事守时、行事不拖拖拉拉、不足之处是唯己主意偏重，做事坚持力可能不足，多和别人交流，邀请朋友监督会更有利于您的发展。三角形脸的人一般来说容易冲动，平日要反思有没有这样情绪上头的情况，有的话要多反思，冲动是魔鬼。";
      }

      if (_facePoint < 45) {
        _faceTips = '倔强的我们靠才华吃饭';
      } else if (_facePoint < 60) {
        _faceTips = '您颜值很高，可以靠脸吃饭';
      } else if (_facePoint >= 60) {
        _faceTips = '要不要考虑出道，未来的天王巨星';
      }

      _this.setState(() {
        _isPredict = false;
        facePoint = _facePoint;
        eyeSummary = _eyeSummary;
        faceTitle = _faceTitle;
        faceTips = _faceTips;
        faceSummary = _faceSummary;
      });
    });

  }

  _predictFaceReq() async {
    Options options = new Options();
    String _key = Http.userInfoKey;
    print(Http.userInfoKey);
    if (!_key.contains("=")) {
      _key = 'sessionId=' + _key;
    }
    var name = _imgPath.path
        .substring(_imgPath.path.lastIndexOf("/") + 1, _imgPath.path.length);
    print(name);
    FormData fd = new FormData.from(
        {"imgFile": new UploadFileInfo(new File(_imgPath.path), name)});
    Dio dio = new Dio();
    options.headers['USER_INFO_KEY'] = '$_key';
    Response reponse = await dio.post<String>(
        Http.API_PREFIX + '/api/face/detect',
        data: fd,
        options: options);
    return reponse;
  }

  Widget buidAfterPhoto() {
    final double fontSize = 18;
    final double titleSize = 20;
    String facePointStr=facePoint.toString();
    facePointStr=facePointStr.substring(0,5);
    return Container(
      color: Color(0xffe6e7e9),
      child: ListView(
        children: <Widget>[
          Container(
            // width: _imgWidth - 20,
            // height: _imgWidth - 20,
            child: Image.asset(
            _imgPath?.path,
//              'images/index1.jpeg',
              width: _imgWidth - 40,
              height: _imgWidth - 40,
            ),
          ),
          Container(
            padding: EdgeInsets.fromLTRB(0, 10, 0, 0),
            child: Container(
                color: Colors.white,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: <Widget>[
                    Text(
                      faceTitle,
                      textAlign: TextAlign.left,
                      style: TextStyle(
                          fontSize: fontSize - 2, color: Colors.black),
                    ),
                    Row(
                      children: <Widget>[
                        Text(
                          '您的颜值得分为',
                          style: TextStyle(
                              fontSize: fontSize, color: Colors.black),
                        ),
                        Container(
                          color: Colors.deepOrangeAccent,
                          child: Text(
                            '${facePointStr}',
                            style: TextStyle(
                                fontSize: fontSize, color: Colors.white),
                          ),
                        )
                      ],
                    ),
                    Row(
                      children: <Widget>[
                        Text(
                          '您的颜值击败了${facePoint / 80 * 100}%的人',
                          textAlign: TextAlign.left,
                          style: TextStyle(
                              fontSize: fontSize, color: Colors.black),
                        ),
                      ],
                    ),
                    Row(
                      children: <Widget>[
                        Text(
                          '颜值批注:${faceTips}',
                          textAlign: TextAlign.left,
                          style: TextStyle(
                              fontSize: fontSize, color: Colors.black),
                        ),
                      ],
                    )
                  ],
                )),
          ),
          Container(
            padding: EdgeInsets.fromLTRB(0, 10, 0, 0),
            child: Container(
                color: Colors.white,
                child: Column(
                  children: <Widget>[
                    Row(
                      children: <Widget>[
                        Icon(
                          Icons.remove_red_eye,
                          size: titleSize,
                        ),
                        Text(
                          '眼睛类型',
                          style: TextStyle(fontSize: titleSize),
                        )
                      ],
                    ),
                    Container(
                      padding: EdgeInsets.fromLTRB(0, 5, 0, 0),
                      child: Text(
                        eyeSummary,
                        style: TextStyle(fontSize: fontSize),
                      ),
                    )
                  ],
                )),
          ),
          Container(
//              color: Colors.white,
              padding: EdgeInsets.fromLTRB(0, 10, 0, 0),
              child: Container(
                color: Colors.white,
                child: Column(
                  children: <Widget>[
                    Row(
                      children: <Widget>[
                        Icon(
                          Icons.face,
                          size: titleSize,
                        ),
                        Text(
                          '脸型',
                          style: TextStyle(fontSize: titleSize),
                        )
                      ],
                    ),
                    Container(
                      padding: EdgeInsets.fromLTRB(0, 5, 0, 0),
                      child: Text(
                        faceSummary,
                        style: TextStyle(fontSize: fontSize),
                      ),
                    )
                  ],
                ),
              )),
          Container(
//          color: Colors.pinkAccent,
            child: Container(
              padding: EdgeInsets.fromLTRB(0, 10, 0, 50),
              child: Container(
                color: Colors.pinkAccent,
                child: MaterialButton(
                  onPressed: () {
                    _takePhoto();
                  },
                  color: Colors.pinkAccent,
                  child: Text(
                    '重新检测',
                    style: TextStyle(fontSize: fontSize, color: Colors.white),
                  ),
                ),
              ),
            ),
          )
        ],
      ),
    );
  }
}
