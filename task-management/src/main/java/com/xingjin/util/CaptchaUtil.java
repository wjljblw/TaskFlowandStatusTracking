package com.xingjin.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;

/**
 * 验证码工具类
 * 用于生成图形验证码及其对应的Base64编码字符串
 */
public class CaptchaUtil {
    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;

    /**
     * 生成验证码图片和对应的文字
     * @return 包含两个元素的字符串数组：第一个元素是验证码文字，第二个元素是图片的Base64编码（带data URI前缀）
     */
    public static String[] generateCaptcha() {
        // 生成4位随机验证码字符
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            code.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }

        // 创建图像并绘制背景
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setFont(new Font("Arial", Font.BOLD, 28));

        // 绘制验证码字符
        for (int i = 0; i < 4; i++) {
            g.setColor(new Color(random.nextInt(150), random.nextInt(150), random.nextInt(150)));
            g.drawString(String.valueOf(code.charAt(i)), 20 + i * 25, 30);
        }

        // 添加干扰线
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(random.nextInt(200), random.nextInt(200), random.nextInt(200)));
            g.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT),
                      random.nextInt(WIDTH), random.nextInt(HEIGHT));
        }
        g.dispose();

        // 将图像转换为Base64编码
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
            return new String[]{code.toString(), "data:image/png;base64," + base64};
        } catch (Exception e) {
            return new String[]{code.toString(), ""};
        }
    }
}
