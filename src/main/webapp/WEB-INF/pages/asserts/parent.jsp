<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">ATM系统</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="${pageContext.request.contextPath}/select">
                        <span style="font-size: medium;">主页</span>
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/depositInput">
                        <span style="font-size: medium;">存款</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/withdrawInput">
                        <span style="font-size: medium;">取款</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/transferInput">
                        <span style="font-size: medium;">转账</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)">
                        <span style="font-size: medium;">余额查询</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)">
                        <span style="font-size: medium;">查询交易记录</span>
                    </a></li>
                <li>
                    <a href="javascript:void(0)">
                        <span style="font-size: medium;">修改账户信息</span>
                    </a>
                </li>
            </ul>
            <div class="nav navbar-nav navbar-right">
                <span class="navbar-text">${sessionScope.user.name}, 欢迎您!</span>
            </div>
        </div>
    </div>
</nav>
