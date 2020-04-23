<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <c:forEach items="${requestScope.messages}" var="message">
                        <div style="margin: 13px">
                            <span style="font-size: x-large; color: black; ">${message}</span>
                        </div>
                    </c:forEach>
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