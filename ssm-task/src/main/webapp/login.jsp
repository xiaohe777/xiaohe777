<%--
  Created by IntelliJ IDEA.
  User: HAHA
  Date: 2022/2/16
  Time: 0:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <script src="js/jquery-3.6.0.min.js"></script>
    <script>
        $("#btn").click(function (){
            $.ajax({
                url:"/login",
                type:"post",
                data:'{"userName":${"input[name='userName']"},"passWord":${"input[name='passWord']"}}',
                dataType:"json"
            });
        });
    </script>

</head>
<body>
<h3>用户登录</h3>
<form id="f" action="login" method="post">
    用户名<input type="text" name="userName">
    密码<input type="text" name="passWord">
<%--    <input type="submit" value="提交">--%>
    <button id="btn" value="">点我</button>
</form>


</body>
</html>
