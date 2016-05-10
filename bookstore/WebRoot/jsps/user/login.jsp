<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>登录</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

    <script type="text/javascript">
        //创建XMLHttpRequest对象的函数
        function createXMLHttpRequest() {
            try {
                return new XMLHttpRequest();
            } catch (e) {
                try {
                    return new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                    try {
                        return new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (e) {
                        throw e;
                    }
                }
            }
        }

        window.onload = function () {
            var pname = document.getElementById("pname");
            pname.onblur = function () {
                var xmlHttp = createXMLHttpRequest();
                //得到username
                var method = "post";
                var url = "<c:url value='/UserServlet?method=login2'/> ";
                xmlHttp.open(method, url);
                xmlHttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
                xmlHttp.send("username=" + pname.value);
                xmlHttp.onreadystatechange = function () {
                    if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
                        var text=xmlHttp.responseText;
                        var p=document.getElementById("p");
                        p.innerHTML=text;

                    }

                };
            };

            var pd = document.getElementById("pd");
            //给密码表单添加监听
            pd.onblur = function () {
                var xmlHttp = createXMLHttpRequest();
                //得到username
                var pname = document.getElementById("pname");
                var method = "post";
                var url = "<c:url value='/UserServlet?method=login2'/> ";
                xmlHttp.open(method, url);
                xmlHttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
                xmlHttp.send("password=" + pd.value+"&username="+pname.value);
                xmlHttp.onreadystatechange = function () {
                    if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
                        var text=xmlHttp.responseText;
                        var p=document.getElementById("p");
                        p.innerHTML=text;

                    }

                };
            };
        };


    </script>
</head>
<!--
        显示错误信息
        回显信息
-->
<body>
<h1>登录</h1>

<p style="color: red; font-weight: 900" id="p">${msg }</p>

<form action="<c:url value='/UserServlet'/>" method="post" target="_top">
    <!--
        隐藏字段
     -->
    <input type="hidden" name="method" value="login"/>
    用户名：<input type="text" name="username" value="${form.username }" id="pname"/>
    <br/>
    密　码：<input type="password" name="password" value="${form.password }" id="pd"/>
    <br/>
    <input type="submit" value="登录"/>
</form>
</body>
</html>
