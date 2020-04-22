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
                    <div>
                        <span style="font-size: x-large; color: black; ">请输入目标账户</span>
                    </div>
                    <div class="input-group input-group-lg">
                        <input type="text" class="form-control" placeholder="" aria-describedby="sizing-addon1">
                        <span class="input-group-addon">
                    <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                </span>
                    </div>
                    <div>
                        <span style="font-size: x-large; color: black; ">请输入转账金额</span>
                    </div>
                    <div class="input-group input-group-lg">
                        <input type="number" step="100" min="0" class="form-control" placeholder=""
                               aria-describedby="sizing-addon1">
                        <span class="input-group-addon">
                    ￥
                </span>
                    </div>
                </form>
                <p></p>
                <button type="button" class="btn" onclick="javascrtpt:window.location.href='transferEnd.html'">
                    <span style="font-size: x-large; color: black;">确认</span>
                </button>
            </div>
        </div>
    </body>
    <%@include file="../asserts/script.jsp"%>

</html>