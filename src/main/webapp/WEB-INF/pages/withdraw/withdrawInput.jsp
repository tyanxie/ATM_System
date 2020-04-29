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
                <form action="${pageContext.request.contextPath}/account/withdraw" method="post">
                    <div>
                        <label for="withdraw"
                               style="font-weight: normal;font-size: x-large; color: black; ">请输入取款金额</label>
                    </div>

                    <div class="form-group">
                        <div class="input-group input-group-lg">
                            <input id="withdraw" name="withdraw"
                                   type="number" step="100" min="100" class="form-control" placeholder="100.00"
                                   aria-describedby="sizing-addon1" required autofocus>
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