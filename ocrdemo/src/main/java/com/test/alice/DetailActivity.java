package com.test.alice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.google.gson.Gson;
import com.test.alice.utils.Base64Util;
import com.test.alice.utils.FileUtil;
import com.test.alice.utils.HttpUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    private TextView txt_title, content;
    private ImageView cardimage;
    private String card_type, card_image, token;
    public static final String Platform = "11cac48f5ae574004af209226cd101f1";
    public static final String url = "https://aip.baidubce.com/rest/2.0/solution/v1/iocr/recognise";
    public static final String contentType = "application/x-www-form-urlencoded";
    String encoding = "UTF-8";
    private String generalUrl;
    private String params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        card_type = intent.getStringExtra("cardtype");
        card_image = intent.getStringExtra("cardimage");
        token = intent.getStringExtra("token");
        generalUrl = url + "?access_token=" + token;
        initView();
    }

    private void initView() {
        txt_title = (TextView) findViewById(R.id.txt_title);
        cardimage = (ImageView) findViewById(R.id.cardimage);
        content = (TextView) findViewById(R.id.content);

        File file = new File(card_image);

        if (file.exists()) {

            Bitmap bm = BitmapFactory.decodeFile(card_image);

            cardimage.setImageBitmap(bm);

        }

        if (card_type.equals("front")) {


            getIdCard(card_image);
        } else {

            getPassPort();

        }
    }

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */

    private void getPassPort() {

            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... strings) {

                    String result = doPost();
                    Log.d("doInBackground : ==", result);
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    Log.d("onPostExecute : ==", result);
                    Gson gson = new Gson();
                    PassportModel passportModel = gson.fromJson(result, PassportModel.class);
                    PassportModel.DataBean data = passportModel.data;
                    if (passportModel.error_code == 0 && data != null) {
                        if (data.isStructured) {
                            List<PassportModel.DataBean.RetBean> ret = data.ret;
                            StringBuilder builder = new StringBuilder();
                            for (int i = 0; i < ret.size(); i++) {
                                PassportModel.DataBean.RetBean retBean = ret.get(i);
                                builder.append(retBean.word_name + ":"+ retBean.word + "\n\n");
                            }

                            content.setText(builder);
                        }
                    }else {
                        Toast.makeText(DetailActivity.this, "识别失败", Toast.LENGTH_SHORT).show();
                    }
                    //

                }
            }.execute();


    }

    private String doPost() {

        URL url = null;
        String result = "";
        try {
            byte[] imgData = FileUtil.readFileByBytes(card_image);
            String imgStr = Base64Util.encode(imgData);
            params = "templateSign=909343ad9ff3f5fca1d955d1ee0e3cd7&image=" + URLEncoder.encode(imgStr, "UTF-8");
            url = new URL(generalUrl);
            // 打开和URL之间的连接
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            // 设置通用的请求属性
            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 得到请求的输出流对象
            DataOutputStream out = null;
            out = new DataOutputStream(connection.getOutputStream());
            out.write(params.getBytes("UTF-8"));
            out.flush();
            out.close();
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> headers = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headers.keySet()) {
                System.err.println(key + "--->" + headers.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = null;
            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), encoding));
            String getLine;
            while ((getLine = in.readLine()) != null) {
                result += getLine;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void getIdCard(String card_image) {

        IDCardParams params = new IDCardParams();
        params.setImageFile(new File(card_image));
        params.setIdCardSide(card_type);
        params.setDetectDirection(true);// 设置方向检测
        params.setImageQuality(50); // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        OCR.getInstance().recognizeIDCard(params, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult idCardResult) {

                if (idCardResult != null) {
                    String name = "";
                    String sex = "";
                    String nation = "";
                    String num = "";
                    String address = "";
                    if (idCardResult.getName() != null) {
                        name = idCardResult.getName().toString();
                    }
                    if (idCardResult.getGender() != null) {
                        sex = idCardResult.getGender().toString();
                    }
                    if (idCardResult.getEthnic() != null) {
                        nation = idCardResult.getEthnic().toString();
                    }
                    if (idCardResult.getIdNumber() != null) {
                        num = idCardResult.getIdNumber().toString();
                    }
                    if (idCardResult.getAddress() != null) {
                        address = idCardResult.getAddress().toString();
                    }
                    content.setText("姓名: " + name + "\n\n" + "性别: " + sex + "\n\n" +
                            "民族: " + nation + "\n\n" + "身份证号码: " + num + "\n\n" +
                            "住址: " + address + "\n\n");
                }
            }
            @Override
            public void onError(OCRError ocrError) {

                Toast.makeText(DetailActivity.this, "识别出错,请查看log错误代码", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "onError: " + ocrError.getMessage());
            }
        });
    }

}
