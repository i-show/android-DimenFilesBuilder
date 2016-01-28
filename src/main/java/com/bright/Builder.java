package com.bright;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Bright 于海洋
 */
public class Builder {
    /**
     * 要产生多少组数据
     */
    private static final int TAGGET_COUNT = 500;
    /**
     * 标准分辨率
     */
    private static final float STANDARD_DENSITY = 480f;
    /**
     * 生成文件夹的名字
     */
    private static final String TAGGET_PATH = "./res";
    /**
     * 模版 用来生成数据
     */
    private final static String TEMPLATE = "    <dimen name=\"dp_{0}\">{1}dp</dimen>\n";
    /**
     * 每一个分辨率对应的文件夹
     * 忍不住的的废话：
     * 1. 获取 sw对应的值 Configuration.smallestScreenWidthDp
     * 这个值是一个奇葩的值 例如在N6上是411 ，Sony S36H 输入miui7 居然是 392
     */
    private final static String VALUE_TEMPLATE = "values-sw{0}dp";

    /**
     * 要生成的分辨率
     * 忍不住的的废话： 目前流行的分辨率在400 左右，在400 左右的密度增加的小一点
     * 1. 384 对应着是 N4
     * 2. 392 sony 36h
     * 3. 411 N6
     */
    private static final int[] SUPPORT_DIMESION = new int[]{
            240, 320, 360, 384, 392, 400, 411, 420, 440, 480, 520, 560, 600, 640, 682, 768, 800, 820
    };


    public static void main(String[] args) throws Exception {
        new Builder().generate();
    }


    public Builder() {

        File dir = new File(TAGGET_PATH);
        if (! dir.exists()) {
            dir.mkdirs();
        }
        System.out.println(dir.getAbsoluteFile());

    }

    public void generate() {
        for (int width : SUPPORT_DIMESION) {
            generateXmlFile(width);
        }

    }

    private void generateXmlFile(int nowDensity) {

        StringBuilder target = new StringBuilder();
        target.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        target.append("<resources>\n");
        float scale = nowDensity * 1.00f / STANDARD_DENSITY;

        System.out.println("width : " + nowDensity + ", scale = " + scale);
        for (int i = 1; i <= TAGGET_COUNT; i++) {
            // <dimen name="dp_{0}">{1}dp</dimen>
            DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            String result = decimalFormat.format(i * scale);
            target.append(TEMPLATE
                    .replace("{0}", String.valueOf(i))
                    .replace("{1}", result));
        }
        target.append("</resources>");


        File fileDir = new File(TAGGET_PATH + File.separator + VALUE_TEMPLATE.replace("{0}", nowDensity + ""));
        fileDir.mkdir();

        File targetFile = new File(fileDir.getAbsolutePath(), "dimen.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(targetFile));
            pw.print(target.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
