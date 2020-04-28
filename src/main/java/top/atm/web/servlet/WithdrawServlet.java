package top.atm.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atm.service.AccountService;
import top.atm.service.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 取款 Servlet, 用于接收用户取款的请求
 * 接受路径为 /withdraw 仅接受 post 请求
 *
 * @author taifu
 */

@WebServlet ("/withdraw")
public class WithdrawServlet extends HttpServlet {
    private static final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户取款的金额, 以及用户的帐号
        String withdraw = request.getParameter("withdraw");
        String accountId = (String) request.getSession().getAttribute("accountId");

        boolean result = accountService.withdraw(accountId, withdraw);
        if (!result) {
            // 存款失败
            response.sendRedirect(request.getContextPath() + "/withdrawFail");
            return;
        }
        request.setAttribute("withdraw", withdraw);
        request.getRequestDispatcher("/withdrawEnd").forward(request, response);
    }
}
