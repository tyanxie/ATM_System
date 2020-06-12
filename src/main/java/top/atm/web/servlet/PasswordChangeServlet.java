package top.atm.web.servlet;

import top.atm.constant.ErrorCode;
import top.atm.constant.WebConstant;
import top.atm.service.AccountService;
import top.atm.service.impl.AccountServiceImpl;
import top.atm.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author taifu
 */

@WebServlet ("/account/password")
public class PasswordChangeServlet extends HttpServlet {
    private static final AccountService accountService = AccountServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oldPassword = request.getParameter("old");
        String newPassword = request.getParameter("new");
        String confirmNewPassword = request.getParameter("confirm");

        if (!StringUtils.equalsNonNull(newPassword, confirmNewPassword)) {
            // 新密码和确认新密码的值不相同
            request.setAttribute("message", "两次填写的新密码不相同");
            request.getRequestDispatcher("/passwordChange").forward(request, response);
            return;
        } else if (StringUtils.equalsNonNull(oldPassword, newPassword)) {
            // 新密码和旧密码相同
            request.setAttribute("message", "新密码和旧密码相同");
            request.getRequestDispatcher("/passwordChange").forward(request, response);
            return;
        }

        // 从 session 中获取 accountId
        String accountId = (String) request.getSession().getAttribute(WebConstant.ACCOUNT_ID);
        ErrorCode code = accountService.changePassword(accountId, oldPassword, newPassword);
        if (code.isError()) {
            // 修改失败
            request.setAttribute("messages", code.getMessages());
            request.getRequestDispatcher("/modifyFail").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/modifyEnd");
    }
}
