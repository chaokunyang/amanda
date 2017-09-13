# amanda
A blogging system written in java8、spring boot. Elegant and beautiful, worth trying. http://amanda.timeyang.com

## Demo
[网站示例](http://timeyang.com)

- 首页
![Jkes流程图](https://raw.githubusercontent.com/chaokunyang/amanda/master/docs/images/Jkes%20Architecture.png)


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




### Running
first run, add following parameters to setup demo data:
```
--spring.profiles.active=init
```


