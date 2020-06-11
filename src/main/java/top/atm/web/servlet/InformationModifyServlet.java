package top.atm.web.servlet;

import top.atm.bean.User;
import top.atm.message.AbstractMessage;
import top.atm.service.UserService;
import top.atm.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 更改用户的信息 Servlet
 * api 接口为 /user/modify, 仅接收 POST 请求
 *
 * @author taifu
 */

@WebServlet ("/user/modify")
public class InformationModifyServlet extends HttpServlet {
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phone-number");

        User user = (User) request.getSession().getAttribute("user");

        AbstractMessage message = userService.modifyInformation(user.getId(), name, address, phoneNumber);

        if (message.isError()) {
            // 修改失败
            request.setAttribute("messages", message.getMessages());
            request.getRequestDispatcher("/modifyFail").forward(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/modifyEnd");
    }
}
