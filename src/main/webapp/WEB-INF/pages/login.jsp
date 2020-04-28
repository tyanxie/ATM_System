<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>登录</title>

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
              crossorigin="anonymous">
        <style>
            #parent-container {
                width: 500px;
                padding: 30px;
                border: 15px solid gray;
                margin: 0;
                height: 450px;
                text-align: center;
            }
        </style>
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
                               type="text" class="form-control" placeholder="Account Id"
                               aria-describedby="sizing-addon1" autofocus>
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

                    <!-- 验证码 -->
                    <label for="captcha" style="font-size: x-large; color: black; display: block">验证码</label>
                    <div class="input-group input-group-lg">
                    <span class="input-group-addon">
                    <span class="glyphicon glyphicon-console" aria-hidden="true"></span>
                    </span>
                        <input id="captcha" name="verify-code"
                               type="text" class="form-control"
                               placeholder="Verify Code" aria-describedby="sizing-addon1">
                    </div>
                    <p></p>
                    <a href="javascript:void(0)" onclick="flushVerifyCode()">
                        <img id="verify-code-img" src="${pageContext.request.contextPath}/verifyCode"
                             alt="验证码" class="img-rounded" style="CURSOR: pointer">
                    </a>

                    <p></p>
                    <input style="font-size: x-large; color: black;" type="submit" class="btn btn-default" value="登录">
                </form>
            </div>
            <p style="color: red;font-size: x-large">${requestScope.message}</p>
        </div>
    </body>

    <%@include file="asserts/script.jsp" %>
    <script>
        function flushVerifyCode() {
            $("#verify-code-img").prop("src", "${pageContext.servletContext.contextPath}/verifyCode?time=" + new Date().getTime());
        }
    </script>
</html>