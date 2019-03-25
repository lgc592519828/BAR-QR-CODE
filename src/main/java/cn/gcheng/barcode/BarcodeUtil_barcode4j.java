package cn.gcheng.barcode;

import org.apache.commons.lang.StringUtils;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author gcheng.L
 * @create 2019-03-23 18:46
 */
public class BarcodeUtil_barcode4j {


    /**
     * 已生成code128条形码为例
     * @param msg           要生成的文本
     * @param hideText      隐藏可读文本
     * @param ous
     */
    public static void generateBarCode(String msg, boolean hideText, OutputStream ous) {
        try {
            if (StringUtils.isEmpty(msg) || ous == null) {
                return;
            }

            // 如果想要其他类型的条码(CODE 39, EAN-8...)直接获取相关对象Code39Bean...等等
            Code128Bean bean = new Code128Bean();
            // 分辨率
            int dpi = 150;
            // 设置两侧是否留白
            bean.doQuietZone(true);
            // 设置条码每一条的宽度
            // UnitConv 是barcode4j 提供的单位转换的实体类，用于毫米mm,像素px,英寸in,点pt之间的转换
            bean.setModuleWidth(UnitConv.in2mm(3.0f / dpi));

            // 设置文本位置（包括是否显示）
            if (hideText) {
                bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
            }
            // 设置图片类型
            String format = "image/png";

            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);

            // 生产条形码
            bean.generateBarcode(canvas, msg);

            // 结束
            canvas.finish();
            ous.close();
        } catch (IOException ie) {
            ie.getStackTrace();
        }
    }


    /**
     * 生成条码文件
     * @param msg
     * @param hideText
     * @param path
     * @return
     */
    public static File generateFile(String msg, boolean hideText,String path) {
        File file = new File(path);
        try {
            generateBarCode(msg, hideText, new FileOutputStream(file));
        } catch (FileNotFoundException fe) {
            throw new RuntimeException(fe);
        }
        return file;
    }

    /**
     *  生成条码字节
     * @param msg
     * @param hideText
     * @return
     */
    public static byte[] generateByte(String msg, boolean hideText) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generateBarCode(msg, hideText, ous);
        return ous.toByteArray();
    }

}
