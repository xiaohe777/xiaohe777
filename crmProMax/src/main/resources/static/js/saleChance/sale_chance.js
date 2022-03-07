layui.use(['form','table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table,
        form = layui.form;


    /**
     * 营销机会列表展示
     */
    var tableIns = table.render({
        elem: '#saleChanceList', // 表格绑定的ID
        url: ctx + '/sale_chance/list', // 访问数据的地址
        cellMinWidth: 95,
        page: true, // 开启分页
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "saleChanceListTable",
        cols: [[
            {type: "checkbox", fixed: "center"},
            {field: "id", title: '编号', fixed: "true"},
            {field: 'chanceSource', title: '机会来源', align: "center"},
            {field: 'customerName', title: '客户名称', align: 'center'},
            {field: 'cgjl', title: '成功几率', align: 'center'},
            {field: 'overview', title: '概要', align: 'center'},
            {field: 'linkMan', title: '联系人', align: 'center'},
            {field: 'linkPhone', title: '联系电话', align: 'center'},
            {field: 'description', title: '描述', align: 'center'},
            {field: 'createMan', title: '创建人', align: 'center'},
            {field: 'createDate', title: '创建时间', align: 'center'},
            {field: 'uName', title: '指派人', align: 'center'},
            {field: 'assignTime', title: '分配时间', align: 'center'},
            {field: 'state', title: '分配状态', align: 'center', templet: function (d) {return formatterState(d.state);}},
            {field: 'devResult', title: '开发状态', align: 'center', templet: function (d) {return formatterDevResult(d.devResult);}},
            {title: '操作', templet: '#saleChanceListBar', fixed: "right", align: "center", minWidth: 150}
        ]]
    });



    //监听头部工具栏
    table.on("toolbar(saleChances)",function (obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event){
            //如果为添加按钮，执行函数
            case "add" :
                openAddOrUpdateSaleChanceDialog();
                break;
            case "del" :
                deleteSaleChance(checkStatus.data);
                break;
        }
    });

    //批删营销机会
    function deleteSaleChance(data) {
        //判断是否选中
        if (data.length == 0){
            layer.msg("请选择数据",{icon:5});
            return ;
        }
        //询问是否删除
        layer.confirm("确认要删除嘛",{
            btn:["确认","不了"]
        },function (index){
            //关闭对话框
            layer.close(index);
            var ids = "ids=";
            //遍历
            for (var i = 0;i < data.length;i++){
                //如果是最后一个，则不拼“ids=”，如果不是则拼上“ids=”
                if (i < data.length - 1){
                    ids = ids + data[i].id + "&ids=";
                }else {
                    ids = ids + data[i].id;
                }
            }
            //发送请求
            $.ajax({
                type:"post",
                url: ctx + "/sale_chance/delete?"+ids,
                // data:ids,
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


    //行内工具栏监听
    table.on("tool(saleChances)",function (data){
        var layEvent = data.event;
        var saleChanceId = data.data.id;
        if (layEvent === 'edit'){
            openAddOrUpdateSaleChanceDialog(saleChanceId);
        }
        if (layEvent === 'del'){
            //询问是否删除
            layer.confirm("确认要删除嘛",{
                btn:["确认","不了"]
            },function (index){
                //关闭对话框
                layer.close(index);
                var ids = "ids=" + saleChanceId;
                //发送请求
                $.ajax({
                    type:"post",
                    url: ctx + "/sale_chance/delete?"+ids,
                    // data:ids,
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


    function openAddOrUpdateSaleChanceDialog(userId) {
        var title = "<h2>营销机会管理 - 机会添加</h2>";
        var url = ctx + "/sale_chance/addOrUpdateSaleChancePage";
        if (userId){
            title = "<h2>营销机会管理 - 机会更新</h2>";
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

    //搜索按钮绑定事件
    $(".search_btn").click(function (){
        table.reload("saleChanceListTable", {
            where:{
                customerName:$("input[name='customerName']").val(),
                createMan:$("input[name='createMan']").val(),
                state:$("#state").val()
            }
            ,page: {
                curr: 1 // 重新从第 1 页开始
            }
        });
    });


    /**
     * 格式化分配状态
     * 0 - 未分配
     * 1 - 已分配
     * 其他 - 未知
     * @param state
     * @returns {string}
     */
    function formatterState(state) {
        if (state == 0) {
            return "<div style='color: #ff8400'>未分配</div>";
        } else if (state == 1) {
            return "<div style='color: green'>已分配</div>";
        } else {
            return "<div style='color: red'>未知</div>";
        }
    }

    /**
     * 格式化开发状态
     * 0 - 未开发
     * 1 - 开发中
     * 2 - 开发成功
     * 3 - 开发失败
     * @param value
     * @returns {string}
     */
    function formatterDevResult(value) {
        if (value == 0) {
            return "<div style='color: #ff8400'>未开发</div>";
        } else if (value == 1) {
            return "<div style='color: #00ff00;'>开发中</div>";
        } else if (value == 2) {
            return "<div style='color: #00B83F'>开发成功</div>";
        } else if (value == 3) {
            return "<div style='color: red'>开发失败</div>";
        } else {
            return "<div style='color: #af0000'>未知</div>"
        }
    }
});