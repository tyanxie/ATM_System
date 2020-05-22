<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>余额查询</title>

        <%@include file="../asserts/style.jsp" %>
    </head>

    <body>
        <%@include file="../asserts/parent.jsp" %>

        <div align="center">
            <div id="parent-container">
                <form action="${pageContext.request.contextPath}/account/balanceQuery" method="post">
                    <div style="margin: 0px">
                        <span style="font-size: x-large; color: black; ">余额查询</span>
                    </div>
                    <input style="font-size: x-large; color: black;" type="submit" class="btn btn-default" value="点击此处查询余额"/>
                </form>
                <p></p>
                <a class="btn btn-default" href="${pageContext.request.contextPath}/select">
                    <span style="font-size: x-large; color: black; ">点击此处返回主页</span>
                </a>
            </div>
        </div>
    </body>

    <%@include file="../asserts/script.jsp" %>
</html>