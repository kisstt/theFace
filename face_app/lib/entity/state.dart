import 'dart:collection';
import 'dart:core';
class State{

 static Map<String,String> data=new HashMap<String,String>();

  static String get(String name){
    return data[name];
  }

  static put(String key,String value){
    data[key]=value;
  }

}

final state=State();