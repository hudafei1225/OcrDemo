package com.baidu.ocr;

import com.baidu.ai.aip.utils.Base64Util;
import com.baidu.ai.aip.utils.FileUtil;
import com.baidu.ai.aip.utils.HttpUtil;


public class App 
{
    public static void main(String[] args) throws Exception
    {
        /**
         * 重要提示代码中所需工具类
         * FileUtil,Base64Util,HttpUtil,GsonUtils请从
         * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
         * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
         * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
         * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
         * 下载
         */
        String otherHost = "https://aip.baidubce.com/rest/2.0/solution/v1/iocr/recognise";
        String filePath = "path\to\your\image.jpg";
        try {
                byte[] imgData = FileUtil.readFileByBytes(filePath);
                String imgStr = Base64Util.encode(imgData);
                String params = "templateSign=your_template_sign&image=" + URLEncoder.encode(imgStr, "UTF-8");
                String accessToken = "your_access_token";
                String result = HttpUtil.post(otherHost, accessToken, params);
                System.out.println(result);
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
}

