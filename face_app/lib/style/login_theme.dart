import 'dart:ui';

import 'package:flutter/cupertino.dart';

class Theme {

  /**
   * 登录界面，定义渐变的颜色
   */
  static const Color loginGradientStart = Color.fromRGBO(255, 255,0, 0.8);
  static const Color loginGradientEnd =  Color.fromRGBO(255, 0,0, 0.8);

  static const LinearGradient primaryGradient = const LinearGradient(
    colors: const [loginGradientStart, loginGradientEnd],
    stops: const [0.0, 1.0],
    begin: Alignment.topCenter,
    end: Alignment.bottomCenter,
  );
}