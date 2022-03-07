layui.use(['form','table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table,
        form = layui.form;


    /**
     * 用户列表展示
     */
    var tableIns = table.render({
        elem: '#userList', // 表格绑定的ID
        url: ctx + '/user/list', // 访问数据的地址
        cellMinWidth: 95,
        page: true, // 开启分页
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "userListTable",
        cols: [[
            {type: "checkbox", fixed:"left", width:50},
            {field: "id", title:'编号',fixed:"true", width:80},
            {field: 'userName', title: '用户名', minWidth:50, align:"center"},
            {field: 'email', title: '用户邮箱', minWidth:100, align:'center'},
            {field: 'phone', title: '用户电话', minWidth:100, align:'center'},
            {field: 'trueName', title: '真实姓名', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'updateDate', title: '更新时间', align:'center',minWidth:150},
            {title: '操作', templet: '#userListBar', fixed: "right", align: "center", minWidth: 150}
        ]]
    });


    //绑定搜索按钮点击事件
    $(".search_btn").click(function (){
        table.reload("userListTable",{
            where:{
                userName:$("input[name='userName']").val(),
                phone:$("input[name='phone']").val(),
                email:$("input[name='email']").val()
            }
            ,page: {
                curr:1  // 重新从第 1 页开始
            }
        });
    });

    function openAddOrUpdateUserDialog(userId) {
        var title = "<h2>用户管理 - 用户添加</h2>";
        var url = ctx + "/user/addOrUpdateUserPage";
        if (userId){
            title = "<h2>用户管理 - 用户更新</h2>";
            var url = url + "?id=" + userId;
        }
        //发送请求
        layui.layer.open({
            title:title,
            type: 2,//iframe
            content:url,
            area:["500px","620px"],
            maxmin:true
        });
    }

    function deleteSaleChance(data) {
        //判断是否选中数据
        if (data.length == 0){
            layer.msg("请选择数据",{icon:5});
            return ;
        }
        //询问是否删除
        layer.confirm("真的要删除嘛",{
            btn:["真的","算了"]
        },function (index){
            //关闭对话框
            layer.close(index);
            var ids = "ids=";
            for (var i = 0; i <data.length;i++){
                if (i<data.length-1){
                    ids = ids + data[i].id + "&ids=";
                }else {
                    ids = ids + data[i].id;
                }
            }
            //发送请求
            $.ajax({
                type:"post",
                url: ctx + "/user/delete?" + ids,
                success:function (msg){
                    if (msg.code == 200){
                        layer.msg("删除成功",{icon:6});
                        //重新刷新表格
                        tableIns.reload();
                    }else {
                        layer.msg(msg.msg,{icon:5});
                    }
                }
            });
        });
    }

    //监听头部工具栏
    table.on("toolbar(users)",function (obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event){
            //如果为添加按钮，执行函数
            case "add" :
                openAddOrUpdateUserDialog();
                break;
            case "del" :
                deleteSaleChance(checkStatus.data);
                break;
        }
    });


    //监听行内工具栏
    table.on("tool(users)",function (obj){
        var userId = obj.data.id;
        var layEvent = obj.event;
        if (layEvent === 'edit'){
            openAddOrUpdateUserDialog(userId);
        }
        if (layEvent === 'del'){
            //询问是否删除
            layer.confirm("真的要删除嘛",{
                btn:["真的","算了"]
            },function (index){
                //关闭对话框
                layer.close(index);
                //发送请求
                $.ajax({
                    type:"post",
                    url: ctx + "/user/delete?ids=" + userId,
                    success:function (msg){
                        if (msg.code == 200){
                            layer.msg("删除成功",{icon:6});
                            //重新刷新表格
                            tableIns.reload();
                        }else {
                            layer.msg(msg.msg,{icon:5});
                        }
                    }
                });
            });
        }
    });


});