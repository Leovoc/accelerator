# accelerator
#作者：陈同学
HSCF 开发加速器，提供如下工具。


根据表元数据自动生成如下数据：

* 生成JavaBean代码
* 生成为知笔记Markdown文档
* 生成Liquibase Groovy脚本
* 生成Postman Collection脚本 
* 根据表生成Mock数据
* 根据Markdown文档那个生成Mock数据

# 如果在本地运行
项目默认打war包，若要在本地以springboot项目运行，需进行如下修改，运行完推代码前需要回滚以下修改
* 删除 org.utopiavip.ServletInitializer
* 将 pom.jar.xml 拷贝到 pom.xml
		
