package top.atm.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atm.message.AbstractMessage;
import top.atm.service.AccountService;
import top.atm.service.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 用于存款的 Servlet
 *
 * 接受路径为 /account/deposit
 * @author taifu
 */

@WebServlet ("/account/deposit")
public class DepositServlet extends HttpServlet {
    private static final AccountService accountService = new AccountServiceImpl();

    static final Logger logger = LoggerFactory.getLogger(DepositServlet.class);

    /**
     * 使用 post 方法处理真正的存款逻辑
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 客户端传递来的存款金额
        String deposit = request.getParameter("deposit");
        // 从 Session 中获取账户 id
        String accountId = (String) request.getSession().getAttribute("accountId");

        // 调用 service 层的方法进行存款
        AbstractMessage message = accountService.deposit(accountId, deposit);
        if (message.getStatus() != 0) {
            // 存款失败
            request.setAttribute("messages", message.getMessages());
            request.getRequestDispatcher("/depositFail").forward(request, response);
            logger.error(message.debugStatus());
            return;
        }

        // 将存款金额放入 request 域中以便 jsp 页面获取
        request.setAttribute("amount", deposit);
        request.getRequestDispatcher("/depositEnd").forward(request, response);
    }

    /**
     * 通过 get 方法获取用户输入的要存款的数额并进行第一次规范性判断
     * 返回给 depositConfirm 或是 depositFail 页面
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deposit = request.getParameter("deposit");
        String accountId = (String) request.getSession().getAttribute("accountId");
        AbstractMessage message = accountService.verifyDeposit(accountId, deposit);
        if (message.getStatus() != 0) {
            // 校验失败, 转发到失败页面
            request.setAttribute("messages", message.getMessages());
            request.getRequestDispatcher("/depositFail").forward(request, response);
            return;
        }
        // 校验成功, 回传用户所要转账的金额, 并转发到用户确认页面
        request.setAttribute("deposit", deposit);
        request.getRequestDispatcher("/depositConfirm").forward(request, response);
    }
}
