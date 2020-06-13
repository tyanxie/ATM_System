<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>交易记录</title>
        <%@include file="../asserts/style.jsp" %>

        <style>
            #parent-container {
                width: 700px;
                height: 600px;
            }
        </style>
    </head>

    <body>
        <%@include file="../asserts/parent.jsp" %>

        <div align="center">
            <div id="parent-container">
                <form action="${pageContext.request.contextPath}/account/transfer" method="get">

                    <div class="panel panel-default">
                        <div class="panel-heading">交易记录</div>
                        <table class="table">
                            <tr>
                                <th>序号</th>
                                <th>转账目标</th>
                                <th>金额</th>
                                <th>类型</th>
                                <th>时间</th>
                                <th>备注</th>
                            </tr>

                            <c:forEach items="${requestScope.recordPage.itemList}" var="record" varStatus="status">
                                <tr style="text-align: left">
                                    <td>
                                            ${requestScope.recordPage.startNumber + status.index + 1}
                                    </td>
                                    <td>
                                        <c:if test="${record.type == 2}">
                                            ${record.overlayUserName}, ${record.overlayTargetAccountId}
                                        </c:if>
                                        <%--
                                        <c:if test="${record.type == 3}">
                                            ${record.overlayUserName}, ${record.overlaySourceAccountId}
                                        </c:if>
                                        --%>
                                    </td>
                                    <td>${record.amount}</td>
                                    <td>${record.typeInString}</td>
                                    <td>
                                        <fmt:formatDate value="${record.occurTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>${record.remarks}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>

                    <nav aria-label="Page-navigation">
                        <ul class="pagination">
                            <c:if test="${requestScope.recordPage.currentPage == 1}">
                                <li class="disabled">
                                    <a href="javascript:void(0)" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${requestScope.recordPage.currentPage != 1}">
                                <li>
                                    <a aria-label="Previous"
                                       href="${pageContext.request.contextPath}/record?cp=${requestScope.recordPage.currentPage - 1}">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                            </c:if>

                            <c:forEach var="i" begin="1" end="${requestScope.recordPage.totalPages}">
                                <c:if test="${requestScope.recordPage.currentPage == i}">
                                    <li class="active">
                                        <a href="${pageContext.request.contextPath}/record?cp=${i}">${i}</a>
                                    </li>
                                </c:if>
                                <c:if test="${requestScope.recordPage.currentPage != i}">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/record?cp=${i}">${i}</a>
                                    </li>
                                </c:if>
                            </c:forEach>

                            <c:if test="${requestScope.recordPage.currentPage == requestScope.recordPage.totalPages}">
                                <li class="disabled">
                                    <a href="javascript:void(0)" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${requestScope.recordPage.currentPage != requestScope.recordPage.totalPages}">
                                <li>
                                    <a aria-label="Next"
                                       href="${pageContext.request.contextPath}/record?cp=${requestScope.recordPage.currentPage + 1}">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </form>
                <p></p>
            </div>
        </div>
    </body>

    <%@include file="../asserts/script.jsp" %>
</html>