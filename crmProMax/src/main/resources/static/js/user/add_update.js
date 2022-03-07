layui.use(['form', 'jquery','formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    // 引入 formSelects 模块
    formSelects = layui.formSelects;

    //加载下拉框
    formSelects.config('selectId',{
        type:"post",
        searchUrl: ctx + "/role/allRoles?userId=" + $("input[name=id]").val(),
        //自定义返回数据中name的key, 默认 name
        keyName: 'roleName',
        //自定义返回数据中value的key, 默认 value
        keyVal: 'id'
    },true);


    //取消按钮绑定事件
    $("#closebtn").click(function (){
        // 先得到当前iframe层的索引
        var index = parent.layer.getFrameIndex(window.name);
        // 再执行关闭
        parent.layer.close(index);
    });

    //监听表单提交
    form.on("submit(addOrUpdateUser)",function (data){
        var index = layer.msg("数据提交中，请稍后",{
            icon:6,//图标
            time:false, // 不关闭
            shade:0.8 // 设置遮罩的透明度
        });
        var url = ctx + "/user/save";
        //判断是否为修改
        if ($("input[name='id']").val()){
            url = ctx + "/user/update";
        }
        //发送请求
        $.post(url,data.field,function (obj){
            //成功
            if (obj.code == 200){
                //提示消息
                layer.msg(obj.msg,{icon: 6});
                //关闭提示层
                layer.close(index);
                //关闭页面
                layer.closeAll("iframe");
                //重新加载
                parent.location.reload();
            }else {
                //失败
                layer.msg(obj.msg,{icon: 5});
            }
        });
        return false;
    });


});