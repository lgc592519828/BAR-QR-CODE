package cn.gcheng.barcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;
import org.jbarcode.JBarcode;
import org.jbarcode.JBarcodeFactory;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author gcheng.L
 * @create 2019-03-25 16:46
 */
public class BarcodeUtil_Jbarcode {

    private static final int BARCODE_DPI = ImageUtil.DEFAULT_DPI;// 精细度，采用默认
    private static final String FONT_FAMILY = "console";
    private static final int FONT_SIZE = 13;
    private static String TEXT = "";
    private static JBarcode jbc = null;

    public static JBarcode getJbarcode() throws InvalidAtributeException {
        // 注：
        // 1.常量条形码的高度和字体大小设置很重要，若是设置小了会看不到设置的文件
        // 2.生成的条码偶尔扫描不了，是因为条形码密度太厚，故"setXDimension()"很重要，
        // 值越小密度越细，条形码宽度越宽(高度越高，图片宽度越窄)
        if (jbc == null) {
            // 生成code128
            jbc = JBarcodeFactory.getInstance().createCode128();
            jbc.setEncoder(Code128Encoder.getInstance());
            // 获取自定义文本实例
            jbc.setTextPainter(CustomTextPainter.getInstance());
            // 是否展示文本
            jbc.setShowText(true);
            // 设置高度
            jbc.setBarHeight(15);
            // 设置尺寸、大小、密集程度
            jbc.setXDimension(Double.valueOf(0.8).doubleValue());
            jbc.setPainter(WidthCodedPainter.getInstance());
            // 设置宽度比率
            jbc.setWideRatio(Double.valueOf(30).doubleValue());
            // 是否检查数字
            jbc.setCheckDigit(true);
            // 显示检查数字
            jbc.setShowCheckDigit(false);
        }
        return jbc;
    }

    /**
     * 生成条形码文件
     *
     * @param msg
     *            条形码内容
     * @param file
     *            生成文件
     * @param text
     *            前缀
     * @throws Exception
     */
    public static void createBarcode(String msg, File file, String text) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        createBarcode(msg, fos, text);
        fos.close();
    }

    public static void main(String[] args) throws Exception {
        String msg = "20190325191400";
        String path = "E:\\barcode\\Jbarcode16.png";
        createBarcodeForRemark(msg, new File(path), "", "该条码作用于A检测设备,该条码作用于A检测设备,该条码作用于A检测设备,重要的话说三遍!");
    }

    public static void createBarcodeForRemark(String msg, File file, String text, String textMessage) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        createBarcodeForRemark(msg, fos, text ,textMessage);
        fos.close();
    }

    /**
     * 生成条形码并写入指定输出流
     *
     * @param msg
     *            条形码内容
     * @param os
     *            输出流
     * @param text
     *            前缀
     */
    public static void createBarcode(String msg, OutputStream os, String text) {
        try {
            TEXT = text;
            // 创建条形码的BufferedImage图像
            BufferedImage image = getJbarcode().createBarcode(msg);
            ImageUtil.encodeAndWrite(image, ImageUtil.PNG, os, BARCODE_DPI, BARCODE_DPI);
            System.out.println(image.getHeight());
            System.out.println(image.getWidth());
            os.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param msg  条形码内容
     * @param os   输出流
     * @param text 前缀信息
     * @param textMessage 备注信息(需求原因大批量条码为防止混乱,添加备注信息)，提供一种思路
     */
    public static void createBarcodeForRemark(String msg, OutputStream os, String text, String textMessage) {
        try {
            // 前缀
            TEXT = text;
            // 创建条形码的BufferedImage图像
            BufferedImage image = getJbarcode().createBarcode(msg);

            // 条码图像的height
            int height = image.getHeight();
            // 条码图像的width
            int width = image.getWidth();
            // 由于有备注信息，要比image条码高一些
            BufferedImage logoReamarkImage = new BufferedImage(width, height + 33, BufferedImage.TYPE_INT_RGB);
            Graphics gp = logoReamarkImage.getGraphics();
            gp.setColor(Color.WHITE);
            // 限定备注信息不超过两行, 否则这个高度不够
            gp.fillRect(0, 0, width, height + 33);
            gp.dispose();

            // 将生成的条码转化为像素数组，并重新写到logoReamarkImage画布上
            int[] imageNewArray = new int[width * height];
            image.getRGB(0, 0, width, height, imageNewArray, 0, image.getWidth());
            logoReamarkImage.setRGB(0, 0, width, height, imageNewArray, 0, width);

            // 设置备注信息
            // 设置文字大小
            int fontSize = 12;
            Graphics gpText = logoReamarkImage.createGraphics();
            gpText.setColor(Color.black);
            gpText.setFont(new Font(null, Font.PLAIN, fontSize));
            // 备注信息画板偏移量,是否信息过长需要分行展示
            if (StringUtils.isNotBlank(textMessage)) {
                int textLength = textMessage.length();
                if (textLength * fontSize > width) {
                    // 需要分行显示
                    StringBuffer sb = new StringBuffer(textMessage);
                    // 每行展示字符大小  < (width - 10) : 留有空白, 否则会显得很臃肿
                    // 设定备注信息 在条码宽度+文字大小位置
                    gpText.drawString("备注:" + sb.substring(0, (width - 10) / fontSize), 0, height + fontSize);
                    // 多行展示时要注意截取字符串后文字位置向下移动一行
                    gpText.drawString(sb.substring((width - 10) / fontSize, textLength), 30, (height + fontSize * 2));
                } else {
                    // 不需要分行展示
                    gpText.drawString("备注:" + textMessage, 0, 80);
                }
            }
            gpText.dispose();
            ImageUtil.encodeAndWrite(logoReamarkImage, ImageUtil.PNG, os, BARCODE_DPI, BARCODE_DPI);
            os.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * 静态内部类 自定义的TextPainter,允许定义字体大小、文本等
     * 参考底层实现：BaseLineTextPainter.getInstance()
     *
     * @author xx
     *
     */
    protected static class CustomTextPainter implements TextPainter {

        private static CustomTextPainter instance = new CustomTextPainter();

        public static CustomTextPainter getInstance() {
            return instance;
        }

        @Override
        public void paintText(BufferedImage image, String text, int width) {
            // 绘图
            Graphics g2d = image.getGraphics();
            // 创建字体
            Font font = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE * width);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int height = fm.getHeight();
            int center = (image.getWidth() - fm.stringWidth(text)) / 2;
            g2d.fillRect(0, image.getHeight() - (height * 9 / 10), image.getWidth(), height * 9 / 10);
            g2d.setColor(Color.BLACK);
            // 绘制文本
            g2d.drawString(TEXT, 9, image.getHeight() - (height / 10) - 2);
            // 绘制条形码
            g2d.drawString(text, center, image.getHeight() - (height / 10) - 2);
        }

    }



}
