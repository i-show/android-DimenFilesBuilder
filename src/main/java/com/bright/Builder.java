package com.bright;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

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
     */
    private final static String VALUE_TEMPLATE = "values-w{0}dp";
    /**
     * 要生成的分辨率
     */
    private static final int[] SUPPORT_DIMESION = new int[]{
            240, 320, 360, 400, 480, 540, 600, 640, 682, 768, 800, 820
    };


    public static void main(String[] args) throws Exception {
        new Builder().generate();
    }


    public Builder() {

        File dir = new File(TAGGET_PATH);
        if (!dir.exists()) {
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

        System.out.println("width : " + nowDensity + "," + TAGGET_COUNT + "," + scale);
        for (int i = 1; i < TAGGET_COUNT; i++) {
            // <dimen name="dp_{0}">{1}dp</dimen>
            target.append(TEMPLATE
                    .replace("{0}", String.valueOf(i))
                    .replace("{1}", String.valueOf(i * scale)));
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


}
