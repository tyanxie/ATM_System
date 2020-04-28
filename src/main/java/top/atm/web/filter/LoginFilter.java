package top.atm.web.filter;

import top.atm.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 判断用户是否登录, 如果用户未登录, 则要求其登录
 * 防止未登录用户也能够访问需要登录权限的页面
 *
 * @author taifu
 */

@WebFilter ("/*")
public class LoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestPath = request.getServletPath();
        String[] pathSplit = requestPath.split("\\.");

        // 判断是否是静态文件或者登录页面或登录 servlet 或登录验证码
        if ((pathSplit.length == 2 && StringUtils.join(pathSplit[1], "css", "js", "html", "ttf"))
            || StringUtils.join(requestPath, "/index.jsp", "/index.html", "/index.htm", "/", "/login")
            || "/account/login".equals(requestPath) || "/verifyCode".equals(requestPath)) {
            request.removeAttribute("message");
            chain.doFilter(request, response);
            return;
        }

        // 用户是否登录
        if (request.getSession().getAttribute("user") != null) {
            // 用户已登录
            request.removeAttribute("message");
            chain.doFilter(request, response);
            return;
        }

        // 用户未登录, 需要提示其登录
        request.setAttribute("message", "您还未登录, 请登录");
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
