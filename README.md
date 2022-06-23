# WMagicBotRP

## WMagicBotR
之前写过一个[WMagicBotR](https://github.com/WhiteMagic2014/WMagicBotR) ,整合了springboot+mybatis 等web后端开发人员常用的框架,在此基础上又整合了sqlite,写了一套权限指令的注解加载来便于开发。

我个人对他的定位是一个学习写着玩的项目,便捷开发+方便部署(jar包一键启动)

## WMagicBotRP
WMagicBotRP 作为mirai-console 的插件项目,初期目标是把 WMagicBotR中部分功能移植过来,一开始是打算依然用java写的,因为没怎么接触过kotlin,看起来真的很不习惯。不过看到mirai文档上的这句话 **"使用 Mirai 是一个不错的学习 Kotlin 机会"** 一时间竟然有些感触...

这次就把本项目当做学习新语言的项目吧(反正kotlin写不来的东西还能用java填坑...)


## 更新记录
### 0.0.1
-- 无spring环境整合了sqlite+mybatis (数据存储还得是数据库)

### 0.0.2
-- mybatis 执行insert、update和delete操作时自动提交
-- 从WMagicBotR 迁移 DBVersion 部分功能

## 计划任务
- 移植部分WMagicBotR功能
- 学习kotlin
- 注意！！ 目前本项目没有任何实际功能，仅完成框架搭建....


ps: 本身有工作，仅在空余时间更新，项目有停更/夭折的可能...


## 感谢
- [Mirai](https://github.com/mamoe/mirai) 开发组所有成员

## 注意
### 本项目基于 Mirai
- 本项目使用与Mirai相同协议 (AGPLv3 with Mamoe Exceptions) 开源
- 本项目的所有衍生项目 必须使用相同协议 (AGPLv3 with Mamoe Exceptions) 开源
- 本软件禁止用于一切商业活动
- 本软件禁止收费传递, 或在传递时不提供源代码
