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
<h2>用户管理-删除</h2>
${success}
<form action="delUser" method="post">
    请输入要删除记录的姓名<input type="text" name="userName">
    <input type="submit" value="确认删除">
</form>




</body>
</html>
