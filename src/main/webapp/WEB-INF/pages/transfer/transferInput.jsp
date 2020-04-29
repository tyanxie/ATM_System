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
                <form action="${pageContext.request.contextPath}/account/transfer" method="get">
                    <div>
                        <label for="target-account-id"
                               style="font-weight: normal;font-size: x-large; color: black; ">请输入目标账户</label>
                    </div>
                    <div class="input-group input-group-lg">
                        <input id="target-account-id" name="target-account-id"
                               type="text" class="form-control" placeholder=""
                               aria-describedby="sizing-addon1" required autofocus>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                        </span>
                    </div>
                    <div>
                        <label for="transfer"
                               style="font-weight: normal;font-size: x-large; color: black; ">请输入转账金额</label>
                    </div>
                    <div class="form-group">
                        <div class="input-group input-group-lg">
                            <input id="transfer" name="transfer"
                                   type="number" step="0.01" min="100" class="form-control" placeholder=""
                                   aria-describedby="sizing-addon1" required>
                            <span class="input-group-addon">￥</span>
                        </div>
                    </div>

                    <input style="font-size: x-large; color: black;" type="submit" class="btn btn-default" value="确认"/>

                </form>
                <p></p>
            </div>
        </div>
    </body>
    <%@include file="../asserts/script.jsp" %>

</html>