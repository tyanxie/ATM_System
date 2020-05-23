package top.atm.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atm.message.AbstractMessage;
import top.atm.service.AccountService;
import top.atm.service.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author BlessingChimes
 */

@WebServlet ("/account/balanceQuery")
public class BalanceQueryServlet extends HttpServlet {
    private static final AccountService accountService = AccountServiceImpl.getInstance();

    static final Logger logger = LoggerFactory.getLogger(DepositServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountId = (String) request.getSession().getAttribute("accountId");

        AbstractMessage message = accountService.balanceQuery(accountId);
        if (message.getStatus() != 0) {
            // 默认0为成功状态
            request.setAttribute("messages", message.getMessages());
            request.getRequestDispatcher("/balanceQueryFail").forward(request, response);
            logger.error(message.debugStatus());
            return;
        }

        // 将余额放入 request 域中以便 jsp 页面获取
        request.setAttribute("balance", message.getMessages());
        request.getRequestDispatcher("/balanceQueryEnd").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
