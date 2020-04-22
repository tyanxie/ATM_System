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
                    <div style="margin: 40px">
                        <span style="font-size: x-large; color: black; ">你将要存款的金额为：</span>
                    </div>
                </form>
                <p></p>
                <input style="font-size: x-large; color: black;"  type="submit" class="btn" value="确认"/>
                <button type="button" class="btn"
                        onclick="window.location.href='${pageContext.servletContext.contextPath}/select'">
                    <span style="font-size: x-large; color: black; ">取消</span>
                </button>
            </div>
        </div>
    </body>

    <%@include file="../asserts/script.jsp" %>
</html>