# DimenFilesBuilder
##工具的由来：
###屏幕适配的一个很拙劣的工具，看过鸿洋的适配的屏幕博客
- 1. [XML适配方法](http://blog.csdn.net/lmj623565791/article/details/45460089)
- 2.[ Android AutoLayout全新的适配方式 堪称适配终结者](http://blog.csdn.net/lmj623565791/article/details/49990941)

### 感觉都不太适合我自己的屏幕适配的思想，偶然间发现 values-sw820dp 这个文件夹，查阅了一些资料 sw指的是 small width，那么就可以通过这个进行适配！貌似Google也是这么推荐的

## 工具的使用：
###1. 工具是一个Java工程，直接运行就好。
###2. 相关配置直接列举代码

    /**
     * 要产生多少组数据 1 - 500
     */
    private static final int TAGGET_COUNT = 500;
    /**
     * 标准分辨率 按照360 为最基础的 
	 *    即：values-sw360dp 下面dimen.xml 是 1 - 500 一直是相等的
	 *		  其他的文件按照比率（计算）来修改
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



License
=======

    Copyright 2015 Haiyang Yu
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.