package com.test.alice.utils;

import com.baidu.ocr.sdk.model.BaseImageParams;
import com.baidu.ocr.sdk.model.RequestParams;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alice on 2018/3/12.
 */

public class BaseParams implements RequestParams {

    private Map<String, String> params = new HashMap();

    public BaseParams() {

    }

    @Override
    public Map<String, File> getFileParams() {
        return null;
    }

    public Map<String, String> getStringParams() {
        return this.params;
    }

    public void putParam(String key, String value) {
        if (value != null) {
            this.params.put(key, value);
        } else {
            this.params.remove(key);
        }

    }

    public void putParam(String key, boolean value) {
        if (value) {
            this.putParam(key, "true");
        } else {
            this.putParam(key, "false");
        }

    }


}
