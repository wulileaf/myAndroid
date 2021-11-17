package com.example.h5.hybridTool;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileTool {
    public static final String APP_PATH = Environment.getExternalStorageDirectory() + "/h5/";
    //	 public static final String HTML_PATH = "/sdcard/system/";
    public static final String ZIP_NAME = "html.zip";
    //	public static final String BASE_URL =  Environment.getDataDirectory() + "/html/index.html";
//	public static final String BASE_URL1 = Environment.getDataDirectory()
//			+ "/html/index.html";
    public static final String DB_NAME = APP_PATH + "h5.db";

    // 获取应用文件目录
    public static String getHtmlPath(Context context) {
        return context.getFilesDir() + "/";
    }

    // 解压
    public static boolean UnZip(Context context, String fileName) {
        try {
            CopyZIp(context, fileName);
            ZIP.UnZipFolder(getHtmlPath(context) + ZIP_NAME, getHtmlPath(context));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean UnNewZip(Context context, String fileName) {
        try {
            ZIP.UnZipFolder(getHtmlPath(context) + ZIP_NAME, getHtmlPath(context));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void CopyZIp(Context context, String fileName)
            throws Exception {

        CreatFile(getHtmlPath(context));
        String str[] = context.getAssets().list(fileName);
        if (str.length > 0) {// 如果是目录
            // File file = new File("/data/" + uiFileName);
            // file.mkdirs();
            // for (String string : str) {.
            // path = path + "/" + string;
            // System.out.println("zhoulc:\t" + path);
            // // textView.setText(textView.getText()+"\t"+path+"\t");
            // deepFile(ctxDealFile, path);
            // path = path.substring(0, path.lastIndexOf('/'));
            // }
        } else {// 如果是文件
            InputStream is = context.getAssets().open(fileName);
            FileOutputStream fos = new FileOutputStream(new File(context.getFilesDir() + "/"
                    + fileName));
            byte[] buffer = new byte[1024];
            // int count = 0;
            while (true) {
                // count++;
                int len = is.read(buffer);
                if (len == -1) {
                    break;
                }
                fos.write(buffer, 0, len);
            }
            is.close();
            fos.close();
        }
    }

    private static void CreatFile(String Path) throws Exception {
        File myFile = new File(Path);
        if (!myFile.exists()) {
            myFile.mkdirs();
        }
    }

    public static boolean isExists(String Path) {
        boolean isexits = false;
        try {

            File myFile = new File(Path);
            if (!myFile.exists()) {
                isexits = false;
            } else {
                isexits = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            isexits = false;
        }

        return isexits;
    }

    // try
    // {
    //
    // File file = new File("/sdcard/onesfa//test.txt");
    //
    // File parent = file.getParentFile();
    // if (parent != null && !parent.exists()) {
    // parent.mkdirs();
    // }
    // if (!file.exists()) {
    // file.createNewFile();
    // }
    // FileOutputStream fout = new FileOutputStream(file);
    // fout.write(dataResult.toString().getBytes());
    // fout.close();
    // } catch (Exception e) {
    // // TODO: handle exception
    // String sssString = e.getMessage();
    // }

}
