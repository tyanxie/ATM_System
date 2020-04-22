package top.atm.web.servlet;

import top.atm.bean.User;
import top.atm.service.AccountService;
import top.atm.service.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 登录 servlet, 用于用户的登录
 * 接受路径为 /account/login 且只接受 post 方法的请求
 *
 * @author taifu
 */

@WebServlet ("/account/login")
public class LoginServlet extends HttpServlet {
    private static final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountId = request.getParameter("account-id");
        String password = request.getParameter("password");

        User user = accountService.getAccountHostUser(accountId, password);
        if (user == null) {
            // 登陆失败
            request.getSession().setAttribute("accountId", accountId);
            request.setAttribute("message", "帐号或密码错误");
            request.getRequestDispatcher("/login").forward(request, response);
            return;
        }
        // 登录成功, 将账户的 id 和用户对象 ( 只含有 id 和 name ) 添加进 session 域中, 方便在整个 session 中使用
        request.getSession().setAttribute("accountId", accountId);
        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/select");
    }
}
