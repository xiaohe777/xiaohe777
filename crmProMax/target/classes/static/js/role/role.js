layui.use(['form','table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table,
        form = layui.form;


    /**
     * 角色列表展示
     */
    var tableIns = table.render({
        elem: '#roleList', // 表格绑定的ID
        url: ctx + '/role/list', // 访问数据的地址
        cellMinWidth: 95,
        page: true, // 开启分页
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "roleListTable",
        cols: [[
            {type: "checkbox", fixed:"left", width:50},
            {field: "id", title:'编号',fixed:"true", width:80},
            {field: 'roleName', title: '角色名', minWidth:50, align:"center"},
            {field: 'roleRemark', title: '角色备注', minWidth:100, align:'center'},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'updateDate', title: '更新时间', align:'center',minWidth:150},
            {title: '操作', templet: '#roleListBar', fixed: "right", align: "center", minWidth: 150}
        ]]
    });

    function openAddOrUpdateRoleDialog(roleId) {
        var url = ctx + "/role/addOrUpdateRolePage";
        var title= "角色管理-角色添加";
        if (roleId){
            url = url + "?id=" + roleId;
            title = "角色管理-角色更新";
        }
        //打开页面
        layui.layer.open({
            title:title,
            content:url,
            type: 2,
            area:["600px","280px"],
            maxmin:true
        });
    }

    function openRoleGrantDialog(data) {
        //判断是否没选或者选了多个
        if (data.length == 0){
            layer.msg("请选择数据",{icon:5});
            return;
        }
        if (data.length > 1){
            layer.msg("你选的数据太多了，只能选一个",{icon:5});
            return;
        }
        //弹出框
        layui.layer.open({
            title:"权限管理-角色授权",
            content: ctx + "/role/toAddGrantPage?roleId=" + data[0].id,
            type:2,
            area:["800px","600px"],
            maxmin:true
        });
    }

    //监听头部工具栏
    table.on("toolbar(roles)",function (obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event){
            //如果为添加按钮
            case 'add':
                openAddOrUpdateRoleDialog();
                break;
            case 'grant':
                openRoleGrantDialog(checkStatus.data);
        }
    });

    //监听行内工具栏
    table.on("tool(roles)",function (obj){
        switch (obj.event){
            //如果为编辑按钮
            case 'edit':
                openAddOrUpdateRoleDialog(obj.data.id);
                break;
            case 'del':
                //询问是否删除
                layer.confirm("确认要删除嘛",{
                    btn:["确认","不了"]
                },function (index){
                    //关闭对话框
                    layer.close(index);
                    var roleId = "roleId=" + obj.data.id;
                    //发送请求
                    $.ajax({
                        type:"post",
                        url: ctx + "/role/delete?"+roleId,
                        // data:roleId,
                        // dataType:"json",
                        success:function (msg){
                            if (msg.code == 200){
                                layer.msg("删除成功",{icon: 6});
                                //重新加载表格
                                tableIns.reload();
                            }else {
                                layer.msg(msg.msg,{icon:5});
                            }
                        }
                    });
                });
        }
    });

    //绑定搜索按钮绑定事件
    $(".search_btn").click(function (){
        table.reload("roleListTable",{
            where:{
                roleName:$("input[name='roleName']").val()
            }
            ,page: {
                curr:1  //重新从第一页开始
            }
        });
    });



});