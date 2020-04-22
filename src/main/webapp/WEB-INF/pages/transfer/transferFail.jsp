<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Transfer</title>
        <%@include file="../asserts/style.jsp"%>
    </head>

    <body>
        <%@include file="../asserts/parent.jsp" %>

        <div align="center">
            <div id="parent-container">
                <form>
                    <div style="margin: 13px">
                        <span style="font-size: x-large; color: black; ">账户余额不足</span>
                    </div>
                    <div style="margin: 13px">
                        <span style="font-size: x-large; color: black; ">转账失败</span>
                    </div>
                </form>
                <p></p>
                <button type="button" class="btn" onclick="window.location.href='../select.jsp'">
                    <span style="font-size: x-large; color: black; ">点击此处返回主页</span>
                </button>
            </div>
        </div>
    </body>

    <%@include file="../asserts/script.jsp" %>


</html>