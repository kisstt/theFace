import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable()
class User{
  
  @JsonKey(name:'nickname')
  String nickname;

  @JsonKey(name:'username')
  String username;

  @JsonKey(name:'password')
  String password;

  @JsonKey(name:'email')
  String email;
  
  @JsonKey(name:'birthday')
  String birthday;

  @JsonKey(name:'constellation')
  String constellation;

  @JsonKey(name:'tele')
  String tele;

  @JsonKey(name:'avatarUrl')
  String avatarUrl;
  User(){

  }
  BigInt userId;

  static User fromJson(m){
    User user=new User();
    user.nickname=m['nickname'];
    user.username=m['username'];
    user.birthday=m['birthday'];
    user.avatarUrl=m['avatarUrl'];
    user.userId=BigInt.parse(m['userId'].toString());
    return user;
  }

  @override
  String toString() {
    return "[$username,$password,$email,$birthday,$constellation,$tele,$avatarUrl]";
  }


}