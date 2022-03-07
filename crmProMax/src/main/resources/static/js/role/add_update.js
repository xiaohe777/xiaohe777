layui.use(['form', 'jquery'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;


    //绑定取消按钮点击事件
    $("#closebtn").click(function (){
        // 先得到当前iframe层的索引
        var index = parent.layer.getFrameIndex(window.name);
        // 再执行关闭
        parent.layer.close(index);
    });



    //监听表单提交
    form.on("submit(addOrUpdateRole)",function (data){
        var index = layer.msg("数据提交中，请稍后",{
            icon:6,//图标
            time:false, // 不关闭
            shade:0.8 // 设置遮罩的透明度
        });
        var url = ctx + "/role/save";
        //判断是否为修改
        if ($("input[name=id]").val()){
            url = ctx + "/role/update";
        }
        //发送请求
        $.post(url,data.field,function (obj){
            //成功
            if (obj.code == 200){
                //提示信息
                layer.msg(obj.msg,{icon: 6});
                //关闭提示页
                layer.closeAll("iframe");
                //重新加载
                parent.location.reload();
            }else {
                layer.msg(obj.msg,{icon: 5});
            }
        });
        return false;
    });

});