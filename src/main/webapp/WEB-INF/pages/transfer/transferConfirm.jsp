<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>转账</title>
        <%@include file="../asserts/style.jsp" %>
    </head>

    <body>
        <%@include file="../asserts/parent.jsp" %>

        <div align="center">
            <div id="parent-container">
                <form action="${pageContext.request.contextPath}/account/transfer" method="post">
                    <div style="margin: 20px">
                        <span style="font-size: x-large; color: black; ">你将要向 ${requestScope.targetUsername}</span>
                    </div>
                    <div style="margin: 20px">
                        <span style="font-size: x-large; color: black; ">转账 ${requestScope.transfer} ￥</span>
                    </div>

                    <input type="hidden" name="transfer" value="${requestScope.transfer}">
                    <input type="hidden" name="target-account-id" value="${requestScope.targetAccountId}">

                    <p></p>
                    <input style="font-size: x-large; color: black;" type="submit" class="btn btn-default" value="确认"/>
                    <a class="btn btn-default" href="${pageContext.servletContext.contextPath}/select">
                        <span style="font-size: x-large; color: black; ">取消</span>
                    </a>
                </form>
            </div>
        </div>
    </body>

    <%@include file="../asserts/script.jsp" %>
</html>