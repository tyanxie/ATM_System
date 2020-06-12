package top.atm.web.servlet;

import top.atm.constant.WebConstant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 退出 servlet, 用于已登录用户的退出
 * 接受路径为 /account/logout
 *
 * @author taifu
 */

@WebServlet ("/account/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
            移除用户示例, 用户即完成登出
            注意此处并没有移除 accountId, 方便用户登出错误时重新登录无需输入 accountId
         */
        request.getSession().removeAttribute(WebConstant.USER);
        response.sendRedirect(request.getContextPath() + "/");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
