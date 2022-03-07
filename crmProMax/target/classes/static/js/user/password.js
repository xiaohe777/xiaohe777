layui.use(['form', 'jquery', 'jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    //监听提交
    form.on('submit(saveBtn)', function (data) {
        ///校验
        // if (data.field.old_password == 'undefined' || data.field.old_password.trim() == "") {
        //     layer.msg("请输入原始密码");
        //     return false;
        // }
        // if (data.field.new_password == 'undefined' || data.field.new_password.trim() == "") {
        //     layer.msg("请输入新密码");
        //     return false;
        // }
        // if (data.field.again_password == 'undefined' || data.field.again_password.trim() == "") {
        //     layer.msg("请输入确认密码");
        //     return false;
        // }
        //发送请求
        $.ajax({
            type: "post",
            url: ctx + "/user/updatePwd",
            data: {
                oldPwd: data.field.old_password,
                newPwd: data.field.new_password,
                confirmPwd: data.field.again_password
            },
            dataType: "json",
            success: function (msg) {
                if (msg.code == 200) {
                    layer.msg("修改成功,请重新登录", {icon:6},function () {
                        $.removeCookie("userIdStr", {domain:"localhost",path:"/crm"});
                        $.removeCookie("userName", {domain:"localhost",path:"/crm"});
                        $.removeCookie("trueName", {domain:"localhost",path:"/crm"});
                        //跳转重新登陆(parent解决画中画问题)
                        window.parent.location.href = ctx + "/index";
                    });
                } else {
                    layer.msg(msg.msg,{icon:5});
                }
            }
        });
        return false;
    });
});