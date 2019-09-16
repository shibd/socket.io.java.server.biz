# socket.io.java.server.biz

[![Build Status](https://travis-ci.org/DFocusGroup/socket.io.java.server.biz.png?branch=master)](https://travis-ci.org/DFocusGroup/socket.io.java.server.biz)
[![Codecov](https://codecov.io/gh/DFocusGroup/socket.io.java.server.biz/branch/master/graph/badge.svg)](https://codecov.io/gh/DFocusGroup/socket.io.java.server.biz/branch/master)
![][license-url]

基于netty-socketio实现的推送中心，屏蔽使用细节，使用socketio协议实现项目隔离，多播，
单播。
- 支持多项目隔离
- 支持JWT鉴权
- 支持灵活订阅多播，单播
- 支持高可用
- 支持横向扩容

配套[socket.io.client.biz](https://github.com/DFocusFE/socket.io.client.biz)客户端和[socket.io.java.client.biz](https://github.com/DFocusFE/socket.io.java.client.biz)客户端使用

相关文档参见: [wiki](https://github.com/DFocusFE/socket.io.java.server.biz/wiki)

## 快速启动
1. 配置好Maven, 确保可以使用`mvn`命令，安装包到本地
``` shell
git clone https://github.com/shibd/mint-scaffold.git
cd mint-scaffold
mvn clean install
```
2. `git clone https://github.com/DFocusFE/socket.io.java.server.biz.git`
3. `sh socket.io.java.server.biz/tools/build-restart`
4. 打开测试页面`http://127.0.0.1:8080/msg-center`
5. 连接服务端,发送消息
![](./doc/ui.jpg)



## LICENSE

[MIT License](https://raw.githubusercontent.com/DFocusGroup/socket.io.java.server.biz/master/LICENSE)

[license-url]: https://img.shields.io/github/license/DFocusGroup/socket.io.java.server.biz
