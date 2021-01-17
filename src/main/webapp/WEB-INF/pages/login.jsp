<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

            div footer a, div footer a:hover, div footer a:active, div footer a:visited, div footer a:link {
                text-decoration: none;
                color: #000;
                background: transparent;
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

                        <c:if test="${sessionScope.accountId == null}">
                            <input id="username" name="account-id" value="${sessionScope.accountId}"
                                   type="text" class="form-control" placeholder="Account Id"
                                   aria-describedby="sizing-addon1" required autofocus>
                        </c:if>
                        <c:if test="${sessionScope.accountId != null}">
                            <input id="username" name="account-id" value="${sessionScope.accountId}"
                                   type="text" class="form-control" placeholder="Account Id"
                                   aria-describedby="sizing-addon1" required>
                        </c:if>
                    </div>

                    <!-- 密码 -->
                    <label for="password" style="font-size: x-large; color: black; display: block">密码</label>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
                        </span>

                        <c:if test="${sessionScope.accountId == null}">
                            <input id="password" name="password"
                                   type="password" class="form-control"
                                   placeholder="Password" aria-describedby="sizing-addon1" required>
                        </c:if>
                        <c:if test="${sessionScope.accountId != null}">
                            <input id="password" name="password"
                                   type="password" class="form-control"
                                   placeholder="Password" aria-describedby="sizing-addon1" required autofocus>
                        </c:if>
                    </div>

                    <!-- 验证码 -->
                    <label for="captcha" style="font-size: x-large; color: black; display: block">验证码</label>
                    <div class="input-group input-group-lg">
                    <span class="input-group-addon">
                    <span class="glyphicon glyphicon-console" aria-hidden="true"></span>
                    </span>
                        <input id="captcha" name="verify-code"
                               type="text" class="form-control"
                               placeholder="Verify Code" required aria-describedby="sizing-addon1">
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
            <p style="color: #ff0000;font-size: x-large">${requestScope.message}</p>
            <p></p>
            <div class="text-center">
                <footer>
                    <a href="https://beian.miit.gov.cn/">粤ICP备20032637号</a>
                </footer>
            </div>
        </div>
    </body>

    <%@include file="asserts/script.jsp" %>
    <script>
        function flushVerifyCode() {
            $("#verify-code-img").prop("src", "${pageContext.servletContext.contextPath}/verifyCode?time=" + new Date().getTime());
        }
    </script>
</html>