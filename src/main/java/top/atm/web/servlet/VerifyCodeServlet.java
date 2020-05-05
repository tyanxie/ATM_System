package top.atm.web.servlet;

import org.apache.commons.lang3.RandomUtils;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * @author taifu
 */

@WebServlet ("/verifyCode")
public class VerifyCodeServlet extends HttpServlet {
    private static final Integer WIDTH = 80; // 验证码的长
    private static final Integer HEIGHT = 30;   // 验证码的宽
    /**
     * 验证码使用的字符集
     */
    private static final String CHARACTER = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
    /**
     * 验证码的字体
     */
    private static final Font FONT = new Font("JetBrains Mono", Font.BOLD, 21);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 获取画笔
        Graphics graphics = image.getGraphics();

        // 填充内部颜色
        graphics.setColor(Color.gray);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        // 绘制边框
        graphics.setColor(Color.black);
        graphics.drawRect(1, 1, WIDTH - 1, HEIGHT - 1);
        // 绘制文字 ( 验证码主体 )
        graphics.setColor(Color.yellow);
        graphics.setFont(FONT);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = RandomUtils.nextInt(0, CHARACTER.length());
            char code = CHARACTER.charAt(index);
            sb.append(code);
            graphics.drawString("" + code, WIDTH / 5 * i + 10, (int) (HEIGHT / 1.25));
        }
        // 绘制干扰线
        graphics.setColor(Color.green);
        int x1, x2, y1, y2;
        for (int i = 0; i < 10; i++) {
            x1 = RandomUtils.nextInt(1, WIDTH);
            x2 = RandomUtils.nextInt(1, WIDTH);
            y1 = RandomUtils.nextInt(1, HEIGHT);
            y2 = RandomUtils.nextInt(1, HEIGHT);
            graphics.drawLine(x1, y1, x2, y2);
        }

        // 输出到页面上
        // 使用 BufferedOutputStream 加快输出速度
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        ImageIO.write(image, "jpg", bos);
        // 强制刷新缓冲区, 防止不输出
        bos.flush();

        // 放置到 Session 中
        request.getSession().setAttribute("realVerifyCode", sb.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
