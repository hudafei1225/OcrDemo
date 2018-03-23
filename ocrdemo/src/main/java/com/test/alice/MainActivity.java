package com.test.alice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.idl.util.FileUtil;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.ui.camera.CameraActivity;

public class MainActivity extends AppCompatActivity {

    public static final String ApiKey = "y1KaxDALB455jmRvDOaQ85vd";
    public static final String SerectKey = "E9RIFkcqIkqE8Pw7naREEYENx2Br4x1q";

    private static final int REQUEST_CODE_CAMERA = 102;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化
        initAccessToken();
        // 头像面
        findViewById(R.id.idcard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        DataFileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });
        findViewById(R.id.passport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        DataFileUtil.getSaveFile(getApplication()).getAbsolutePath());
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });
    }

    private void initAccessToken() {

        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                token = result.getAccessToken();
                Log.d("MainActivity", "onResult: " + token);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "初始化认证成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                Log.e("MainActivity", "onError: " + error.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "初始化认证失败,请检查 key", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, getApplicationContext(), ApiKey, SerectKey);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                // 获取调用参数
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                // 通过临时文件获取拍摄的图片
                String filePath = DataFileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                if(!TextUtils.isEmpty(contentType)) {
                    if(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
//                        身份证头像面信息
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("cardtype", IDCardParams.ID_CARD_SIDE_FRONT);
                        intent.putExtra("cardimage",filePath);
                        startActivity(intent);
                    }else if(CameraActivity.CONTENT_TYPE_GENERAL.equals(CameraActivity.CONTENT_TYPE_GENERAL)) {
//                        护照信息
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("cardtype", "passport");
                        intent.putExtra("cardimage",filePath);
                        intent.putExtra("token",token);
                        startActivity(intent);
                    }
                }
            }
        }
    }
}
