layui.use(['form', 'jquery'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;



    //监听表单提交
    form.on("submit(addOrUpdateSaleChance)",function (data){
        var index = layer.msg("数据提交中，请稍后",{
            icon:6,//图标
            time:false, // 不关闭
            shade:0.8 // 设置遮罩的透明度
        });
        var url = ctx + "/sale_chance/save";
        //判断是否为修改
        if ($("input[name='id']").val()){
            url = ctx + "/sale_chance/update";
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

    //取消按钮绑定事件
    $("#closebtn").click(function (){
        // 先得到当前iframe层的索引
        var index = parent.layer.getFrameIndex(window.name);
        // 再执行关闭
        parent.layer.close(index);
    });

    //绑定指派人下拉框
    var assignMan = $("input[name='assignMan']").val();
    $.ajax({
        type:"post",
        url: ctx + "/sale_chance/sales",
        success:function (data){
            for (x in data){
                if (data[x].id == assignMan){
                    $("#assignMan").append(
                        '<option selected value="'+data[x].id+'">'+data[x].user_name+'</option>'
                    );
                }else {
                    $("#assignMan").append(
                        '<option value="'+data[x].id+'">'+data[x].user_name+'</option>'
                    );
                }
            }
            //重新渲染下拉框
            layui.form.render("select");
        }
    });

});