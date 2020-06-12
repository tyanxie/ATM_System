package top.atm.web.servlet;

import top.atm.constant.ErrorCode;
import top.atm.constant.WebConstant;
import top.atm.service.AccountService;
import top.atm.service.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 取款 Servlet, 用于接收用户取款的请求
 * 接受路径为 /account/withdraw 仅接受 post 请求
 *
 * @author taifu
 */

@WebServlet ("/account/withdraw")
public class WithdrawServlet extends HttpServlet {
    private static final AccountService accountService = AccountServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户取款的金额, 以及用户的帐号
        String withdraw = request.getParameter("withdraw");
        String accountId = (String) request.getSession().getAttribute(WebConstant.ACCOUNT_ID);

        ErrorCode message = accountService.withdraw(accountId, withdraw);
        if (message.isError()) {
            request.setAttribute("messages", message.getMessages());
            request.getRequestDispatcher("/withdrawFail").forward(request, response);
            return;
        }
        request.setAttribute("withdraw", withdraw);
        request.getRequestDispatcher("/withdrawEnd").forward(request, response);
    }
}
