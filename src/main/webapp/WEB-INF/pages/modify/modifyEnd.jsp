<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

            .button {
                background-color: #FF9966;
                border: none;
                border-radius: 8px;
                color: white;
                padding: 15px 32px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                cursor: pointer;
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
        <!-- 一旦来到这个页面, 就说明用户成功修改了账户信息, 需要重新登录 -->
        <% request.getSession().removeAttribute("user"); %>

        <%@include file="../asserts/parent.jsp" %>
        <div align="center">
            <div align="center" style="
            width:500px;
            padding:0;
            border:15px double pink;
            margin:0;
            height: 300px;">
                <div id="grad1">
                </div>
                <div style="width: 460px">
                    <form>
                        <div style="margin: 20px">
                            <span style="font-size: x-large; color: black; ">
                                修改成功
                            </span>
                        </div>
                        <div style="margin: 20px">
                            <span style="font-size: x-large; color: black; ">
                                您的账户将会退出
                            </span>
                        </div>
                        <div style="margin: 20px">
                            <span style="font-size: x-large; color: black; ">
                                请重新登录
                            </span>
                        </div>
                    </form>
                    <p></p>
                    <a class="btn btn-default" href="${pageContext.request.contextPath}/account/logout">
                        <span style="font-size: x-large; color: black; ">退出</span>
                    </a>
                </div>
            </div>
        </div>
    </body>
    <%@include file="../asserts/script.jsp" %>
</html>
