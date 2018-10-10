#这是一个基于android工程的IM项目

##app 模块：
· app界面模块，项目通过mvp结构划分，包含所有界面。

##netty 模块：
· 长链接模块，一个新进程的service来建立与服务端的长链接，序列化方式使用protobuf

##sms 模块：
· 短信发送模块，通过SMSManager单例类来提供发送短信功能

##support 模块：
· 基础模块，包含最常用的基础类，包括http、db、toast和常用ui等等