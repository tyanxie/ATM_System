<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>存款</title>
        <%@include file="../asserts/style.jsp" %>
    </head>

    <body>
        <%@include file="../asserts/parent.jsp" %>

        <div align="center">
            <div id="parent-container">
                <form>
                    <div style="margin: 13px">
                        <span style="font-size: x-large; color: black; ">核对失败</span>
                    </div>
                    <div style="margin: 13px">
                        <span style="font-size: x-large; color: black; ">请将钞票取出</span>
                    </div>
                    <div style="margin: 13px">
                        <span style="font-size: x-large; color: black; ">确认无误后重新放入</span>
                    </div>
                </form>
                <p></p>
                <a class="btn btn-default" href="${pageContext.request.contextPath}/depositInput">
                    <span style="font-size: x-large; color: black; ">点击此处返回上一级</span>
                </a>
            </div>
        </div>
    </body>
    <%@include file="../asserts/script.jsp" %>
</html>