package com.softuni.softunidemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static File bitmapToTempFile(Context context, Bitmap bitmap) {
        FileOutputStream out = null;
        String filename = "tempfile.png";
        File file = new File(context.getCacheDir(), filename);
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
