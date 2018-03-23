package com.test.alice;

import android.content.Context;

import java.io.File;

/**
 * Created by alice on 2018/3/10.
 */

public class DataFileUtil {

    public static File getSaveFile(Context context) {
        return new File(context.getFilesDir(), "pic.jpg");
    }
}
