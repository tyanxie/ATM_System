<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Withdraw</title>
        <%@include file="../asserts/style.jsp" %>
    </head>

    <body>
        <%@include file="../asserts/parent.jsp" %>

        <div align="center">
            <div id="parent-container">
                <form>
                    <div style="margin: 20px">
                        <span style="font-size: x-large; color: black; ">取款成功</span>
                    </div>
                    <div style="margin: 20px">
                        <span style="font-size: x-large; color: black; ">你已取出${requestScope.withdraw}￥</span>
                    </div>
                </form>
                <p></p>
                <a href="${pageContext.request.contextPath}/select" class="btn btn-default">
                    <span style="font-size: x-large; color: black; ">点击此处返回主页</span>
                </a>
            </div>
        </div>
    </body>
    <%@include file="../asserts/script.jsp" %>
</html>