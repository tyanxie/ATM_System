package top.atm.web.servlet;

import top.atm.bean.User;
import top.atm.service.AccountService;
import top.atm.service.impl.AccountServiceImpl;
import top.atm.util.StringUtils;

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
    private static final AccountService accountService = AccountServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // 获取用户输入的验证码和 session 中的真实验证码
        String verifyCode = request.getParameter("verify-code");
        String realVerifyCode = (String) session.getAttribute("realVerifyCode");
        // 获取用户输入的账户名和密码
        String accountId = request.getParameter("account-id");
        String password = request.getParameter("password");
        // 无论如何将 accountId 写入 session 域中, 方便系统重用
        session.setAttribute("accountId",accountId);
        // 在获取了 session 中的真正验证码后需要将其删除, 防止重复利用
        session.removeAttribute("realVerifyCode");
        // 对验证码进行校验, 验证时忽略大小写
        if (verifyCode == null||!StringUtils.equalsIgnoreCase(realVerifyCode,verifyCode)){
            // 验证码错误或用户并未填写验证码
            request.setAttribute("message", "验证码错误");
            request.getRequestDispatcher("/login").forward(request, response);
            return;
        }

        // 验证用户登录结果
        User user = accountService.getAccountHostUser(accountId, password);
        if (user == null) {
            // 登陆失败
            request.setAttribute("message", "帐号或密码错误");
            request.getRequestDispatcher("/login").forward(request, response);
            return;
        }
        // 登录成功, 将用户对象 ( 只含有 id 和 name ) 添加进 session 域中, 方便在整个 session 中使用
        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/select");
    }
}
