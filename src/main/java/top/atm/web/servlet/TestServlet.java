package top.atm.web.servlet;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import top.atm.bean.Account;
import top.atm.util.CloseUtils;
import top.atm.util.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * @author taifu
 */

@WebServlet ("/account/*")
public class TestServlet extends BaseServlet {
    public void list(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            QueryRunner runner = new QueryRunner();
            List<Account> accountList = runner.query(
                connection,
                "select" +
                    "   id," +
                    "   user_id userId," +
                    "   bank_id bankId," +
                    "   password," +
                    "   balance," +
                    "   is_freeze freeze " +
                    "from account",
                new BeanListHandler<>(Account.class)
            );
            writeJson(response, accountList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.close(connection);
        }
    }

    public void hello(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/pages/hello.jsp").forward(request,response);
    }
}
