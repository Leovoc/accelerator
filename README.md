# accelerator
HSCF 开发加速器，提供如下工具。

根据表元数据自动生成如下数据：

* 生成JavaBean代码
* 生成为知笔记Markdown文档
* 生成Liquibase Groovy脚本
* 生成Postman Collection脚本 
* 生成Mock数据

# 如果在本地运行
项目默认打war包，若要在本地以springboot项目运行，需进行如下修改，运行完推代码前需要回滚以下修改
* 删除 org.utopiavip.ServletInitializer
* 在pom中将<packaging>war</packaging>改成jar
* 去除以下依赖中的 <exclusions>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
			<exclusions>
				<exclusion>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-tomcat</artifactId>
				</exclusion>
			</exclusions>
		</dependency>

* 去掉build中的如下插件
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-war-plugin</artifactId>
				<version>2.4</version>
				<configuration>
					<failOnMissingWebXml>false</failOnMissingWebXml>
				</configuration>
			</plugin>	
	
		