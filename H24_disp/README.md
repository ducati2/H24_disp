# H24_userageReport文档
- 该项目适合用于H24数据库表类型结构。
- 当前项目下载后解压，使用Java IDE进行修改数据库设置后，即可进行本机IDE环境启动。启动方式：运行APP.java文件后，浏览器localhost：8080/all即可。
- 以下为导出war包详细操作


## 修改pom.xml依赖

### 修改导出格式
原代码：

	<groupId>citrix</groupId>
	<artifactId>H24_disp</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>


修改后代码：

	<groupId>citrix</groupId>
	<artifactId>H24_disp</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

### 分离本地Tomcat
添加：

	<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>

**注：无**

### 注册启动类
在原启动类APP.java的同级目录下创建ServletInitializer.java，继承SpringBootServletInitializer ，覆盖configure()，把启动类App注册进去。外部web应用服务器构建Web Application Context的时候，会将启动类添加进去。如下：

	public class ServletInitializer extends SpringBootServletInitializer {
	    @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(App.class);
	    }
	}

## 导出工程为war包

步骤如下（以下为eclipse导出方式）：
- 右击项目名称，点击“Run As”，点击：“Maven Build”。
- 在“Goals”中键入“package”，勾选“Skip Tests”，亦可修改顶Name（ProjectName），点击“Run”，即可在指定目录得到.war文件。
- 在对应本地或者远程环境将.war文件放入tomcat/Webapps文件夹下，启动服务器。浏览器键入：“服务器ip：8080/ProjectName/all”即可。

