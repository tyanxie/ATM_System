package top.atm.web.servlet;

import top.atm.service.AccountService;
import top.atm.service.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 用于存款的 Servlet
 *
 * @author taifu
 */

@WebServlet ("/deposit")
public class DepositServlet extends HttpServlet {
    private static final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 客户端传递来的存款金额
        String deposit = request.getParameter("deposit");
        // 从 Session 中获取账户 id
        String accountId = (String) request.getSession().getAttribute("accountId");

        // 调用 service 层的方法进行存款
        boolean result = accountService.deposit(accountId, deposit);
        if (!result) {
            // 存款失败
            response.sendRedirect(request.getContextPath() + "/depositFail");
            return;
        }

        // 将存款金额放入 request 域中以便 jsp 页面获取
        request.setAttribute("amount", deposit);
        request.getRequestDispatcher("/depositEnd").forward(request, response);
    }
}
