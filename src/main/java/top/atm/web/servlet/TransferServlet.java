package top.atm.web.servlet;

import top.atm.message.AbstractMessage;
import top.atm.service.AccountService;
import top.atm.service.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 转账 Servlet
 * <p>
 * 接受路径为 /account/transfer
 * get 方法用于确认转账目标和余额是否正确, post 方法为真正的转账方法
 *
 * @author taifu
 */

@WebServlet ("/account/transfer")
public class TransferServlet extends HttpServlet {
    private static final AccountService accountService = AccountServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetAccountId = request.getParameter("target-account-id");
        String transfer = request.getParameter("transfer");
        String sourceAccountId = (String) request.getSession().getAttribute("accountId");
        AbstractMessage message = accountService.transfer(sourceAccountId, targetAccountId, transfer);

        if (message.isError()) {
            request.setAttribute("messages", message.getMessages());
            request.getRequestDispatcher("/transferFail").forward(request, response);
            return;
        }

        request.setAttribute("targetUsername", message.getMessages()[0]);
        request.setAttribute("amount", transfer);
        request.getRequestDispatcher("/transferEnd").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetAccountId = request.getParameter("target-account-id");
        String transfer = request.getParameter("transfer");
        String sourceAccountId = (String) request.getSession().getAttribute("accountId");
        AbstractMessage message = accountService.verifyTransfer(sourceAccountId, targetAccountId, transfer);

        if (message.isError()) {
            // 不可转账
            request.setAttribute("messages", message.getMessages());
            request.getRequestDispatcher("/transferFail").forward(request, response);
            return;
        }
        // 允许转账
        request.setAttribute("targetAccountId", targetAccountId);
        request.setAttribute("transfer", transfer);
        request.setAttribute("targetUsername", message.getMessages()[0]);
        request.getRequestDispatcher("/transferConfirm").forward(request, response);
    }
}
