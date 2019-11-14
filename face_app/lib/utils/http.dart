import 'package:dio/dio.dart';
import 'package:face_app/entity/user.dart';

class Http{
  static Dio dio;

  /// default options
  static const String API_PREFIX = 'http://134.175.100.101:8012';
  static const int CONNECT_TIMEOUT = 10000;
  static const int RECEIVE_TIMEOUT = 3000;

  
  /// http request methods
  static const String GET = 'get';
  static const String POST = 'post';
  static const String PUT = 'put';
  static const String PATCH = 'patch';
  static const String DELETE = 'delete';

  static User user;
  static String userInfoKey;
  /// 创建 dio 实例对象
  static Dio createInstance() {
    if (dio == null) {
      /// 全局属性：请求前缀、连接超时时间、响应超时时间
      var options = BaseOptions(
        connectTimeout: CONNECT_TIMEOUT,
        receiveTimeout: RECEIVE_TIMEOUT,
        responseType: ResponseType.plain,
        validateStatus: (status) {
          // 不使用http状态码判断状态，使用AdapterInterceptor来处理（适用于标准REST风格）
          return true;
        },
        baseUrl: API_PREFIX,
      );

      dio = new Dio(options);
    }

    return dio;
  }

  static clear() {
    dio = null;
  }

  /// request method
  static request (
    String url, 
    { data, method }) async {

    data = data ?? {};
    method = method ?? 'GET';

    /// restful 请求处理   
    /// /gysw/search/hist/:user_id        user_id=27
    /// 最终生成 url 为     /gysw/search/hist/27
    // data.forEach((key, value) {
    //   if (url.indexOf(key) != -1) {
    //     url = url.replaceAll(':$key', value.toString());
    //   }
    // });
    /// 打印请求相关信息：请求地址、请求方式、请求参数
    print('请求地址：【' + method + '  ' + url + '】');
    print('请求参数：' + data.toString());
    Dio dio = createInstance();
    var result;
    Response response;
    print(userInfoKey);
    try {
      response= await dio.request(url, data: data, options: new Options(method: method,
      headers: {
        'USER_INFO_KEY':userInfoKey!=null?'sessionId=$userInfoKey':''
      }));
      // result = response.data;
      // print(result);
      /// 打印响应相关信息
    } on DioError catch (e) {
      /// 打印请求失败相关信息
      print('请求出错：' + e.toString());
    }
    return response;
  }
}