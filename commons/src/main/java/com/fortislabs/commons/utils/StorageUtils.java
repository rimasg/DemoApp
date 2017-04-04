package com.fortislabs.commons.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Okis on 2017.03.18.
 */

public class StorageUtils {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_AUDIO = 2;
    public static final int MEDIA_TYPE_TEXT = 3;

    /** Checks if external storage is available to at least write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /** Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getOutputMediaFile(Context context, int type, String fileName) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());

        File outputDir = null;
        File outputFile = null;

        if (!isExternalStorageWritable()) {
            Toast.makeText(context, "External storage not mounted. Can't write/read file.", Toast.LENGTH_LONG).show();
            return null;
        } else {
            switch (type) {
                case MEDIA_TYPE_IMAGE:
                    outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    break;
                case MEDIA_TYPE_AUDIO:
                    outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                    break;
                case MEDIA_TYPE_TEXT:
                    outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                    break;
            }
        }

        if (outputDir != null) {
            if (fileName != null) {
                outputFile = new File(outputDir.getPath() + File.separator + fileName);
            } else {
                switch (type) {
                    case MEDIA_TYPE_IMAGE:
                        outputFile = new File(outputDir.getPath() + File.separator +
                                "IMG_" + timeStamp);
                        break;
                    case MEDIA_TYPE_AUDIO:
                        outputFile = new File(outputDir.getPath() + File.separator +
                                "SND_" + timeStamp);
                        break;
                    case MEDIA_TYPE_TEXT:
                        outputFile = new File(outputDir.getPath() + File.separator +
                                "TXT_" + timeStamp);
                        break;
                }
            }
        } else {
            Toast.makeText(context, "Couldn't create Output folder.", Toast.LENGTH_LONG).show();
        }

        return outputFile;
    }
}
