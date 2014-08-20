部署配置：
	1./WEB-INF/classes/config.properties
		文件中配置数据库连接属性（包括用户和密码）
		可配置系统发送邮箱信息
	2./js/app.js
		修改第13行代码
		SALES.constant('SERVER_URL', 'http://localhost:8080/sales');
		http://localhost:8080/sales -您部署的项目访问url，默认为＝http://localhost:8080/sales