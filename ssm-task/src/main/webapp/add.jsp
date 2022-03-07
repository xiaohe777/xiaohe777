<%--
  Created by IntelliJ IDEA.
  User: HAHA
  Date: 2022/2/16
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>

</head>
<body>
<h3>${msg}</h3>
<h2>用户管理-添加</h2>
${success}
<form action="addUser" method="post">
    请输入姓名<input type="text" name="userName">
    请输入密码<input type="text" name="passWord">
    <input type="submit" value="确认添加">
</form>




</body>
</html>
