<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <meta charset="utf-8">
        <title>
            修改信息
        </title>
        <link crossorigin="anonymous" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" rel="stylesheet">
        <link href="https://unpkg.com/bootstrap-table@1.16.0/dist/bootstrap-table.min.css" rel="stylesheet">
        <style>
            #parent-container {
                width: 700px;
                padding: 30px;
                border: 15px solid gray;
                margin: 0;
                height: 600px;
                text-align: center;
            }

            @media (max-width: 767px) {
                #welcome-span {
                    position: absolute;
                    top: 0;
                    right: 72px;
                }
            }

            #grad1 {
                height: 20px;
                background-color: pink;
                background-image: linear-gradient(pink, pink);
            }

            div footer a, div footer a:hover, div footer a:active, div footer a:visited, div footer a:link {
                text-decoration: none;
                color: #000;
                background: transparent;
            }

            input[type=text] {
                width: 200px;
                -webkit-transition: width 0.4s ease-in-out;
                transition: width 0.4s ease-in-out;
                padding: 12px 20px;
                margin: 8px 0;
                box-sizing: border-box;
                border: 1px solid #555;
                outline: none;
            }

            input[type=text]:focus {
                background-color: lightyellow;
                width: 80%;
            }
        </style>
    </head>
    <body>
        <%@include file="../asserts/parent.jsp" %>
        <div align="center">
            <div align="center" style="
            width:647px;
            padding:0;
            border:15px double pink;
            margin:0;
            height: 400px;">
                <div id="grad1">
                </div>
                <div style="width: 460px">
                    <div align="center">
                        <span style="font-size: x-large; color: black; ">
                            修 改 个 人 信 息
                        </span>
                    </div>
                    <form class="form-horizontal" action="${pageContext.request.contextPath}/user/modify" method="post">
                        <div style="color: green">
                            <p>
                                您的卡号为：${sessionScope.accountId}，请修改您的个人信息
                            </p>
                        </div>
                        <div>
                            <label for="name">
                                姓名：
                            </label>
                            <div>
                                <input class="form-control" id="name" placeholder="请输入姓名" type="text" required
                                       value="${sessionScope.user.name}" name="name">
                            </div>
                        </div>
                        <div>
                            <label for="tele">
                                联系电话：
                            </label>
                            <div>
                                <input class="form-control" id="tele" placeholder="请输入电话号码" type="text" required
                                       name="phone-number" maxlength="11" min="11"
                                       value="${sessionScope.user.phoneNumber}">
                            </div>
                        </div>
                        <div>
                            <label for="address">
                                地址：
                            </label>
                            <div>
                                <input class="form-control" id="address" placeholder="请输入地址" type="text" required
                                       value="${sessionScope.user.address}" name="address">
                            </div>
                        </div>
                        <input class="btn btn-warning" type="submit" style="font-size: x-large; color: black;"
                               value="确认">
                    </form>
                </div>
            </div>
        </div>
    </body>
    <%@include file="../asserts/script.jsp" %>
</html>
