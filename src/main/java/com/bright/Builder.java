/**
 * Copyright (C) 2015 The yuhaiyang Android Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
     * 要产生多少组数据 1 - 500
     */
    private static final int TAGGET_COUNT = 500;
    /**
     * 标准分辨率 按照360 为最基础的
     * 即：values-sw360dp 下面dimen.xml 是 1 - 500 一直是相等的
     * 其他的文件按照比率（计算）来修改
     */
    private static final float STANDARD_DENSITY = 360f;
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
     * 这个值是一个奇葩的值 例如在N6上是411 ，Sony S36H 刷入miui7 居然是 392， N4 居然是384
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
        //  创建文件夹
        File dir = new File(TAGGET_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        System.out.println(dir.getAbsoluteFile());

    }

    /**
     * 生成文件
     */
    public void generate() {
        /**
         * 循环生成目录
         */
        for (int width : SUPPORT_DIMESION) {
            generateXmlFile(width);
        }

    }

    /**
     * 开始生成XML
     */
    private void generateXmlFile(int nowDensity) {

        StringBuilder target = new StringBuilder();
        target.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        target.append(Configure.COPY_RIGHT);
        target.append("<resources>\n");
        float scale = nowDensity * 1.00f / STANDARD_DENSITY;

        System.out.println("width : " + nowDensity + ", scale = " + scale);
        for (int i = 1; i <= TAGGET_COUNT; i++) {
            // 方法进行保留2位小数来算
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String result = decimalFormat.format(i * scale);
            /**
             *  模版：<dimen name="dp_{0}">{1}dp</dimen>
             *  例子：<dimen name="dp_100">100dp</dimen>
             */
            target.append(TEMPLATE
                    .replace("{0}", String.valueOf(i))
                    .replace("{1}", result));
        }
        target.append("</resources>");

        // 创建values-sw**dp 的文件夹
        File fileDir = new File(TAGGET_PATH + File.separator + VALUE_TEMPLATE.replace("{0}", nowDensity + ""));
        fileDir.mkdir();
        // 生成dimen.xml文件
        File targetFile = new File(fileDir.getAbsolutePath(), "dimen.xml");
        // 写入文件
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(targetFile));
            pw.print(target.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
