### 1、用户相关

#### 1.1、登录login

1.1.1、请求参数

```json
{
	"nickname":"伏地魔",
	"password":"123456"
}
```



返回参数

(1) 登录

```json
{
	"nickname":"伏地魔",
	"password":"12346"
}
```

(2) 登录失败

抛出一个异常

```json
{
    "timestamp": "2019-10-23T03:30:04.846+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "用户名不存在",
    "path": "/api/user/login"
}
```









### 2、文章推送

#### 2.1  句子推送/api/sentence/pushSentence

请求

```json
{
	"tagId":5
}
```



返回

```json
{
    "createdTime": "2019-09-11 01:35:05",
    "pushTime": null,
    "sentence": "算命先生说我会在八十岁的时候遇到一生中就重要的女人，她叫孟婆。",
    "tagId": 5,
    "psentenceId": 189
}
```



补充说明：

```json
//每日句子
int DAILY_SENTENCE = 1;

//读书笔记
int STUDY_NOTES = 2;

//名人名言
int CELEBRITY_QUOTES = 3;

//表白的话
int CONFESSION = 4;

//幽默笑话
int HUMOROUS_JOKE = 5;

//个性签名
int SIGNATURE=6;
```

