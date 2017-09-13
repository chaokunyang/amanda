# Amanda
Amanda is a blogging system written in java8、spring boot. Elegant and beautiful, worth trying. http://timeyang.com

## Demo
[网站示例](http://timeyang.com)：http://timeyang.com

*预览*：
- 首页
![首页](https://raw.githubusercontent.com/chaokunyang/amanda/master/docs/images/Home.png)

- 文章
![文章](https://raw.githubusercontent.com/chaokunyang/amanda/master/docs/images/Article.png)

- 简介
![简介](https://raw.githubusercontent.com/chaokunyang/amanda/master/docs/images/Profile.png)

- 登录
![登录](https://raw.githubusercontent.com/chaokunyang/amanda/master/docs/images/Login.png)

- 后台首页
![后台首页](https://raw.githubusercontent.com/chaokunyang/amanda/master/docs/images/Admin_Home.png)

- 后台文章列表
![后台文章列表](https://raw.githubusercontent.com/chaokunyang/amanda/master/docs/images/Admin_Article.png)

- 文章编辑
![文章编辑](https://raw.githubusercontent.com/chaokunyang/amanda/master/docs/images/Article_Edit.png)

- 文件
![文件](https://raw.githubusercontent.com/chaokunyang/amanda/master/docs/images/Admin_File.png)

- 简介
![简介](https://raw.githubusercontent.com/chaokunyang/amanda/master/docs/images/Admin_Profile.png)

- 监控
![监控](https://raw.githubusercontent.com/chaokunyang/amanda/master/docs/images/Admin_Monitor.png)

- 设置
![文件](https://raw.githubusercontent.com/chaokunyang/amanda/master/docs/images/Admin_Setting.png)

## Getting Started
### create database
```sql
CREATE DATABASE amanda DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_unicode_ci';
USE amanda;
grant all privileges on *.* to 'amanda'@'localhost' identified by '123456'; 
grant all privileges on *.* to 'amanda'@'127.0.0.1' identified by '123456'; 
grant all privileges on *.* to 'amanda'@'::1' identified by '123456'; 
flush privileges;
```
### Setup
```shell
./setup.sh
```
### Start
```shell
./start.sh
```

## Development
To build a development version you'll need a recent version of Kafka. You can build jkes with Maven using the standard lifecycle phases.

## Contribute
- Source Code: https://github.com/chaokunyang/jkes
- Issue Tracker: https://github.com/chaokunyang/jkes/issues

## LICENSE
This project is licensed under Apache License 2.0.


