<!DOCTYPE html>
<html>
<head>
    <#include "common.ftl">
    <meta charset="utf-8">
    <title>Layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <style>
        .grid-demo{
            background-color: #00a2d4;
        }
    </style>
</head>
<body> <!-- 让IE8/9支持媒体查询，从而兼容栅格 --> <!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<div style="margin: 0 auto; max-width: 1140px;">
    <blockquote class="layui-elem-quote">
        注意：下述演示中的颜色只是做一个区分作用，并非栅格内置。
    </blockquote>

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;"> <legend>列间隔</legend>
    </fieldset>
    <div class="layui-row layui-col-space3">
        <div class="layui-col-md3">
            <div class="grid-demo grid-demo-bg1">
                1/4
                <table class="layui-hide" id="test"></table>
            </div>
        </div>
        <div class="layui-col-md3">
            <div class="grid-demo">
                1/4
                <table class="layui-hide" id="test1"></table>
            </div>
        </div>
        <div class="layui-col-md3">
            <div class="grid-demo grid-demo-bg1">
                1/4
                <table class="layui-hide" id="test2"></table>
            </div>
        </div>
        <div class="layui-col-md3">
            <div class="grid-demo">
                1/4
                <table class="layui-hide" id="test3"></table>
            </div>
        </div>
    </div>
</div>

<script src="/layui/dist/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述 JS 路径需要改成你本地的 -->
<script>
    layui.use('table', function(){
        var table = layui.table;

        table.render({
            elem: '#test'
            ,url:'/demo/table/user/'
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,cols: [[
                {field:'id', width:80, title: 'ID', sort: true}
                ,{field:'username', width:80, title: '用户名'}
                ,{field:'sex', width:80, title: '性别', sort: true}
                ,{field:'city', width:80, title: '城市'}
                ,{field:'sign', title: '签名', width: '30%', minWidth: 100} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
                ,{field:'experience', title: '积分', sort: true}
                ,{field:'score', title: '评分', sort: true}
                ,{field:'classify', title: '职业'}
                ,{field:'wealth', width:137, title: '财富', sort: true}
            ]]
        });


        table.render({
            elem: '#test1'
            ,url:'/demo/table/user/'
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,cols: [[
                {field:'id', width:80, title: 'ID', sort: true}
                ,{field:'username', width:80, title: '用户名'}
                ,{field:'sex', width:80, title: '性别', sort: true}
                ,{field:'city', width:80, title: '城市'}
                ,{field:'sign', title: '签名', width: '30%', minWidth: 100} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
                ,{field:'experience', title: '积分', sort: true}
                ,{field:'score', title: '评分', sort: true}
                ,{field:'classify', title: '职业'}
                ,{field:'wealth', width:137, title: '财富', sort: true}
            ]]
        });


        table.render({
            elem: '#test2'
            ,url:'/demo/table/user/'
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,cols: [[
                {field:'id', width:80, title: 'ID', sort: true}
                ,{field:'username', width:80, title: '用户名'}
                ,{field:'sex', width:80, title: '性别', sort: true}
                ,{field:'city', width:80, title: '城市'}
                ,{field:'sign', title: '签名', width: '30%', minWidth: 100} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
                ,{field:'experience', title: '积分', sort: true}
                ,{field:'score', title: '评分', sort: true}
                ,{field:'classify', title: '职业'}
                ,{field:'wealth', width:137, title: '财富', sort: true}
            ]]
        });


        table.render({
            elem: '#test3'
            ,url:'/demo/table/user/'
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,cols: [[
                {field:'id', width:80, title: 'ID', sort: true}
                ,{field:'username', width:80, title: '用户名'}
                ,{field:'sex', width:80, title: '性别', sort: true}
                ,{field:'city', width:80, title: '城市'}
                ,{field:'sign', title: '签名', width: '30%', minWidth: 100} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
                ,{field:'experience', title: '积分', sort: true}
                ,{field:'score', title: '评分', sort: true}
                ,{field:'classify', title: '职业'}
                ,{field:'wealth', width:137, title: '财富', sort: true}
            ]]
        });
    });

</script>

</body>
</html>