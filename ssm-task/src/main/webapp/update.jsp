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
<h2>用户管理-修改</h2>
${success}
<form action="updateUser" method="post">
    请输入需要修改用户的编号<input type="text" name="userId">
    请输入修改后的用户名<input type="text" name="userName">
    请输入修改后的密码<input type="text" name="passWord">
    <input type="submit" value="确认修改">
</form>




</body>
</html>
