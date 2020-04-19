package top.atm.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * public void $methodName$(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
 *
 * @author taifu
 */

public class BaseServlet extends HttpServlet {
    private final static String JSON_MIME_TYPE = "application/json;charset=utf-8";

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String[] uris = request.getRequestURI().split("/");
        String methodName = uris[uris.length - 1];

        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (NoSuchMethodException e) {
            logger.error("获取方法【" + e.getMessage() + "】失败");
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("执行方法【" + methodName + "】失败 --> " + e.getLocalizedMessage());
        }
    }

    protected static void writeJson(HttpServletResponse response, Object target) throws IOException {
        response.setContentType(JSON_MIME_TYPE);
        OBJECT_MAPPER.writeValue(response.getWriter(), target);
    }
}
