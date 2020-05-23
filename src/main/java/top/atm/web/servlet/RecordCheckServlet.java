package top.atm.web.servlet;

import top.atm.bean.Page;
import top.atm.bean.TransactionRecord;
import top.atm.service.TransactionRecordService;
import top.atm.service.impl.TransactionRecordServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author taifu
 */

@WebServlet ("/record")
public class RecordCheckServlet extends HttpServlet {
    private static final TransactionRecordService recordService = TransactionRecordServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // cp 定义为当前要查询的页面
        String currentPage = request.getParameter("cp");
        // ipp 定义为一个页面显示的元素的个数
        String itemPerPage = request.getParameter("ipp");
        // 从 Session 域中获取 accountId
        String accountId = (String) request.getSession().getAttribute("accountId");

        Page<TransactionRecord> recordPage = recordService.getRecord(accountId, currentPage, itemPerPage);
        if (recordPage == null) {
            // 发生错误
            response.getWriter().write("<h1>出错了</h1>");
        }
        request.setAttribute("recordPage", recordPage);
        request.getRequestDispatcher("/recordCheck").forward(request, response);
    }
}
