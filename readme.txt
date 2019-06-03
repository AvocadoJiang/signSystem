1.环境要求：java1.8+,mysql10.1.37+
2.开发工具：STS,git


现在mysql中创建好数据库
create database signsystem default charset utf8;
然后用编译器导入项目，根据自己电脑的开发环境修改配置文件application.properties，主要是修改数据库用户名和密码：
spring.datasource.username= 用户名
spring.datasource.password= 密码

直接启动就好，数据库中的表会自动创建的