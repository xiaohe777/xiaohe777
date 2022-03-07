layui.use(['form', 'jquery', 'jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    //监听提交
    form.on('submit(login)', function (data) {
        //校验用户名
        if (data.field.username == 'undefined' || data.field.username.trim() == "") {
            layer.msg("用户名不能为空",{icon:5});
            return false;
        }
        //校验密码
        if (data.field.password == 'undefined' || data.field.password.trim() == "") {
            layer.msg("密码不能为空",{icon:5});
            return false;
        }
        //发送请求
        $.ajax({
            type: "post",
            url: ctx + "/user/login",
            data: {
                //携带表单中的数据
                userName: data.field.username,
                userPwd: data.field.password
            },
            dataType: "json",
            success: function (msg) {
                if (msg.code == 200) {
                    layer.msg("登陆成功", {icon:6},function () {
                        //存入cookie
                        $.cookie("userIdStr", msg.result.userIdStr);
                        $.cookie("userName", msg.result.userName);
                        $.cookie("trueName", msg.result.trueName);
                        //判断是否勾选记住我
                        if ($("#rememberMe").prop("checked")){
                            //设置cookie为7天
                            $.cookie("userIdStr", msg.result.userIdStr,{expires:7});
                            $.cookie("userName", msg.result.userName,{expires:7});
                            $.cookie("trueName", msg.result.trueName,{expires:7});
                        }
                        //跳转页面
                        window.location.href = ctx + "/main";
                    });
                } else {
                    layer.msg(msg.msg,{icon:5});
                }
            }
        });
        return false;
    });
});