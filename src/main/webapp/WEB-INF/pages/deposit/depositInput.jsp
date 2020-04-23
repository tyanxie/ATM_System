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
                <form action="${pageContext.request.contextPath}/deposit" method="get">
                    <div>
                        <label for="deposit"
                               style="font-weight: normal;font-size: x-large; color: black; ">请输入存款金额</label>
                    </div>

                    <div class="form-group">
                        <div class="input-group input-group-lg">
                            <input id="deposit" name="deposit"
                                   type="number" step="100" min="0" class="form-control"
                                   placeholder="100.00"
                                   aria-describedby="sizing-addon1">
                            <span class="input-group-addon">￥</span>
                        </div>
                    </div>

                    <input style="font-size: x-large; color: black;" type="submit" class="btn btn-default" value="确认"/>
                </form>

            </div>
        </div>
    </body>

    <%@include file="../asserts/script.jsp" %>
</html>