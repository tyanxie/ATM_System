<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>登录</title>
        <%@include file="asserts/style.jsp"%>
    </head>

    <body>
        <div align="center">
            <div id="parent-container">
                <form action="${pageContext.request.contextPath}/account/login" method="post">
                    <!-- 用户名 -->
                    <label for="username" style="font-size: x-large; color: black; display: block">帐号</label>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                        </span>
                        <input id="username" name="account-id" value="${sessionScope.accountId}"
                               type="text" class="form-control" placeholder="Account-Id"
                               aria-describedby="sizing-addon1">
                    </div>

                    <!-- 密码 -->
                    <label for="password" style="font-size: x-large; color: black; display: block">密码</label>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
                        </span>
                        <input id="password" name="password"
                               type="password" class="form-control"
                               placeholder="Password" aria-describedby="sizing-addon1">
                    </div>
                    <p></p>
                    <input style="font-size: x-large; color: black;" type="submit" class="btn btn-default" value="登录">
                </form>
            </div>
            <p style="color: red;font-size: x-large">${requestScope.message}</p>
        </div>
    </body>

    <%@include file="asserts/script.jsp" %>

</html>