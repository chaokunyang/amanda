# amanda
A blogging system written in java8、spring boot. Elegant and beautiful, worth trying. http://amanda.timeyang.com

## Getting Started
### 创建数据库
```sql
CREATE DATABASE amanda DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_unicode_ci';
USE amanda;
grant all privileges on *.* to 'amanda'@'localhost' identified by '123456'; 
grant all privileges on *.* to 'amanda'@'127.0.0.1' identified by '123456'; 
grant all privileges on *.* to 'amanda'@'::1' identified by '123456'; 
flush privileges;
```
### 前端开发
```shell
npm install
npm start
```
npm run-script watch
