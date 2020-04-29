<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Transfer</title>
        <%@include file="../asserts/style.jsp" %>
    </head>

    <body>
        <%@include file="../asserts/parent.jsp" %>

        <div align="center">
            <div id="parent-container">
                <form>
                    <div style="margin: 20px">
                        <span style="font-size: x-large; color: black; ">转账成功</span>
                    </div>
                    <div style="margin: 20px">
                        <span style="font-size: x-large; color: black; ">你已向 ${requestScope.targetUsername} 转账 ${requestScope.amount} ￥</span>
                    </div>
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