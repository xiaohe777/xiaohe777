layui.use(['form', 'jquery', 'jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    //监听提交
    form.on('submit(saveBtn)', function (data) {
        //发送请求
        $.ajax({
            type: "post",
            url: ctx + "/user/updateUser",
            data: {
                userName: data.field.userName,
                phone: data.field.phone,
                email: data.field.email,
                trueName: data.field.trueName,
                id:data.field.id
            },
            dataType: "json",
            success: function (msg) {
                if (msg.code == 200) {
                    layer.msg("修改成功,请重新登录", {icon:6},function () {
                        //跳转
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