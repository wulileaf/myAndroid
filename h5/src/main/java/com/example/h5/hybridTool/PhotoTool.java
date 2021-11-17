package com.example.h5.hybridTool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import androidx.core.content.FileProvider;

public class PhotoTool {

    public static final String PHOTOPATH = "/sdcard/h5/photo";
    public static final String PHOTOWHOLEPATH = PHOTOPATH + "//test.JPEG";

    private static void createPhotoFile() {
        File file = new File(PHOTOPATH);// 设置照片存储的路径
        if (!file.exists()) {
            try {
                // 按照指定的路径创建文件夹
                file.mkdirs();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public static Bitmap getBitmap(byte[] bytes) {
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
                    bytes.length);
            return bitmap;
        } catch (Exception ex) {
            // String sssString = ex.getMessage();
        }
        return null;
    }

    public final static Bitmap getUriImage(Context context, String url) {
        // Bitmap imageBitmap=null;
        // Drawable drawable = null;
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            // Bitmap bitmap =null;
            bitmap = BitmapFactory.decodeFile(url, options); // 此时返回 bm
            // 为空

            options.inJustDecodeBounds = false;
            int heightRatio = (int) Math.ceil(options.outHeight
                    / Tool.dip2px(context, 480));
            int widthRatio = (int) Math.ceil(options.outWidth
                    / Tool.dip2px(context, 300));
            if (heightRatio > 1 && widthRatio > 1)
            // if (widthRatio > 1)
            {
                if (heightRatio > widthRatio) {
                    options.inSampleSize = heightRatio;
                } else {
                    options.inSampleSize = widthRatio;
                }
            } else
                options.inSampleSize = 1;
            bitmap = BitmapFactory.decodeFile(url, options);
            // drawable = bitmap2Drawable(context, bitmap);
        } catch (Exception ex) {
        }
        return bitmap;
    }

    // public static Intent getCamraIntent(Context context) {
    // Intent intent = new Intent();
    // try {
    // createPhotoFile();
    //
    // final Intent intent_camera = context.getPackageManager()
    // .getLaunchIntentForPackage("com.android.camera");
    // if (intent_camera != null) {
    // intent.setPackage("com.android.camera");
    // }
    // // intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
    // intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
    // // intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//
    // // "android.media.action.IMAGE_CAPTURE"
    // intent.putExtra(MediaStore.EXTRA_OUTPUT,
    // Uri.fromFile(new File(PHOTOWHOLEPATH)));
    // // startActivityForResult(intent, type);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return intent;
    // }

    // 进行拍照的初始化
    public static Intent getCamraIntent(Context context) {
//        Android11以下
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 开启相机
//        if (PhoneTool.Brand().equals("Meitu")) {
//            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);//
//        }
//        try {
//
//            createPhotoFile();
//            // CToast.showMsg(context, "发起");
//            intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                    Uri.fromFile(new File(PHOTOWHOLEPATH)));
//            // startActivityForResult(intent, type);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return intent;

//        Android11
        Intent intent = null;
        createPhotoFile();
        Intent infoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> infos = context.getPackageManager()
                .queryIntentActivities(infoIntent, 0);
        if (infos != null && infos.size() > 0) {
            for (ResolveInfo info : infos) {
                int flags = info.activityInfo.applicationInfo.flags;
                if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) { // 系统相机

                    String packageName = info.activityInfo.packageName;
                    String className = info.activityInfo.name;
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    ComponentName cn = new ComponentName(packageName, className);
                    intent.setComponent(cn);
                    intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    File file=new File(PHOTOWHOLEPATH);
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= 24) {
                        uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        uri = Uri.fromFile(file);
                    }
                    // CToast.showMsg(context, "发起");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    break;
                }
            }
        }

        return intent;
    }

    @SuppressWarnings("deprecation")
    public static Bitmap lessenUriImage(Context context) {

        // ExifInterface exif = new ExifInterface(PHOTOWHOLEPATH);
        // String direction =exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        // //获取图片方向
        // Log.d("照片方向", direction);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(PHOTOWHOLEPATH, options); // 此时返回bm为空
        options.inJustDecodeBounds = false;
        int heightRatio = (int) Math.ceil(options.outHeight
                / (float) Tool.dip2px(context, 800)); // 之前1024  本人的800
        int widthRatio = (int) Math.ceil(options.outWidth
                / (float) Tool.dip2px(context, 480));// 之前768  本人的480
        if (heightRatio > 1 && widthRatio > 1) {
            if (heightRatio > widthRatio) {
                options.inSampleSize = heightRatio;
            } else {
                options.inSampleSize = widthRatio;
            }
        } else
            options.inSampleSize = 1;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;// 该模式是默认的,可不设
//		options.inPreferredConfig = Config.RGB_565;
        options.inPurgeable = true;
        bitmap = BitmapFactory.decodeFile(PHOTOWHOLEPATH, options);

        int angle = getExifOrientation(PHOTOWHOLEPATH);
        if (angle != 0) { // 如果照片出现了 旋转 那么 就更改旋转度数
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    private static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {

        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    public static byte[] Bitmap2Bytes(Bitmap bm, Context context, int size) {
        // ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        // return baos.toByteArray();
        int options = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, options, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        // if(baos.toByteArray().length>1024*1024)
        // options = 100;

        int lenth = baos.toByteArray().length;
        while (lenth / 1024 > size) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩   //之前size 现在改为100
            baos.reset();// 重置baos即清空baos
            if (lenth / 1024 > 500)
                options -= 10;
            else {
                options -= 5;
            }
            if (options > 5) {
                bm.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                lenth = baos.toByteArray().length;
            } else {
                options = 5;
                bm.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                lenth = baos.toByteArray().length;
                break;
            }
        }
        return baos.toByteArray();
    }

    //
    // public static Bitmap generatorContactCountIcon(Bitmap icon, JSONObject
    // fields, Context context) {
    // // 初始化画布
    // // int
    // //
    // iconSize=(int)getResources().getDimension(android.R.dimen.app_icon_size);
    // // Bitmap icon = getBimap(context, resId);
    // Bitmap contactIcon = Bitmap.createBitmap(icon.getWidth(),
    // icon.getHeight(), Config.ARGB_8888);
    // Canvas canvas = new Canvas(contactIcon);
    //
    // int width = icon.getWidth();
    // int height = icon.getHeight();
    //
    // // 拷贝图片
    // Paint iconPaint = new Paint();
    // iconPaint.setDither(true);// 防抖动
    // iconPaint.setFilterBitmap(true);//
    // 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
    // Rect src = new Rect(0, 0, width, height);
    // Rect dst = new Rect(0, 0, width, height);
    // canvas.drawBitmap(icon, src, dst, iconPaint);
    //
    // // canvas.drawOval(new RectF(16,0,36,48),iconPaint);
    // // 启用抗锯齿和使用设备的文本字距
    // Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG
    // | Paint.DEV_KERN_TEXT_FLAG);
    // countPaint.setColor(Color.WHITE);
    // // countPaint.setTextSize(12); // 18
    // countPaint.setTextSize(Tool.dip2px(context, 10)); // 18
    // // canvas.drawText(time, Tool.dip2px(context, 10), height -
    // // Tool.dip2px(context, 35), countPaint);
    //
    // try {
    // canvas.drawText(fields.getString("address"),
    // Tool.dip2px(context, 5), height-Tool.dip2px(context, 20),
    // countPaint);
    // canvas.drawText(fields.getString("baifangdian"),
    // Tool.dip2px(context, 5), height-Tool.dip2px(context, 35),
    // countPaint);
    // canvas.drawText(fields.getString("jizhundian"),
    // Tool.dip2px(context, 5), height-Tool.dip2px(context, 50),
    // countPaint);
    // canvas.drawText(fields.getString("distance"),
    // Tool.dip2px(context, 5), height-Tool.dip2px(context, 65),
    // countPaint);
    // canvas.drawText(fields.getString("shoptitle"),
    // Tool.dip2px(context, 5), height-Tool.dip2px(context, 80),
    // countPaint);
    // canvas.drawText(fields.getString("shopcode"),
    // Tool.dip2px(context, 5), height-Tool.dip2px(context, 95),
    // countPaint);
    // canvas.drawText(fields.getString("username"),
    // Tool.dip2px(context, 5), height-Tool.dip2px(context, 110),
    // countPaint);
    // canvas.drawText(fields.getString("shotdate"),
    // Tool.dip2px(context, 5), height-Tool.dip2px(context, 125),
    // countPaint);
    //
    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    // return contactIcon;
    // }

    public static Bitmap generatorContactCountIcon(Bitmap icon,
                                                   JSONObject fields, Context context) {
        Bitmap contactIcon = null;

        int whiteheight = 0;
        int imgW = icon.getWidth();
        int width = Tool.dip2px(context, 350);// 之前400  本人的350
        if (imgW > width)
            width = imgW;// icon.getWidth();
        int height = icon.getHeight();

        int iconH = height + Tool.dip2px(context, whiteheight);
        contactIcon = Bitmap.createBitmap(width, iconH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(contactIcon);
        canvas.drawColor(Color.WHITE);
        // 拷贝图片
        Paint iconPaint = new Paint();
        iconPaint.setDither(true);// 防抖动
        iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
        Rect src = new Rect(0, 0, imgW, height);
        Rect dst = new Rect((width - imgW) / 2, Tool.dip2px(context,
                whiteheight), (width - imgW) / 2 + imgW, iconH);

        canvas.drawBitmap(icon, src, dst, iconPaint);

        // 启用抗锯齿和使用设备的文本字距
        Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.DEV_KERN_TEXT_FLAG);
        countPaint.setColor(Color.WHITE);
        countPaint.setTextSize(Tool.dip2px(context, 8)); // 8

        try {

            // Iterator<String> sIterator = fields.keys();
            // int index = 0, row = 0, col = 0;
            // while (sIterator.hasNext()) {
            // // 获得key
            // String key = sIterator.next();
            // if (!key.equals("photo")) {
            // if (index % 2 > 0) {
            // col = 1;
            // } else {
            // col = 0;
            // }
            // row = index / 2;
            // canvas.drawText(
            // fields.getString(key),
            // Tool.dip2px(context, 5) + col * width / 2,
            // Tool.dip2px(context, 20) + row
            // * Tool.dip2px(context, 20), countPaint);
            // index++;
            // }
            // }

            Iterator<String> sIterator = fields.keys();
            int index = 0;
            while (sIterator.hasNext()) {
                // 获得key
                String key = sIterator.next();
                if (!key.equals("photo")) {
                    if (!fields.getString(key).equals("")) {
                        canvas.drawText(fields.getString(key),
                                Tool.dip2px(context, 10),
                                height
                                        - (Tool.dip2px(context, 20) + index
                                        * Tool.dip2px(context, 20)),
                                countPaint);
                        index++;
                    }
                }
            }

            // canvas.drawText("门店编号:" + fields.getString("shopCode"),
            // Tool.dip2px(context, 5), Tool.dip2px(context, 40),
            // countPaint);
            // canvas.drawText("拍摄区域:" + fields.getString("photoQuyu"), width /
            // 2
            // + Tool.dip2px(context, 5), Tool.dip2px(context, 40),
            // countPaint);
            //
            // canvas.drawText("经度:" + fields.getString("lon"),
            // Tool.dip2px(context, 5), Tool.dip2px(context, 55),
            // countPaint);
            // canvas.drawText("纬度:" + fields.getString("lat"),
            // width / 2 + Tool.dip2px(context, 5),
            // Tool.dip2px(context, 55), countPaint);
            //
            // canvas.drawText("距离:" + fields.getString("juli"),
            // Tool.dip2px(context, 5), Tool.dip2px(context, 70),
            // countPaint);
            // canvas.drawText("GPS是否合规:" + fields.getString("hegui"), width / 2
            // + Tool.dip2px(context, 5), Tool.dip2px(context, 70),
            // countPaint);
            //
            // canvas.drawText("拍摄时间:" + fields.getString("shotDate"),
            // Tool.dip2px(context, 5), Tool.dip2px(context, 85),
            // countPaint);
            // canvas.drawText("人员名称:" + fields.getString("username"), width / 2
            // + Tool.dip2px(context, 5), Tool.dip2px(context, 85),
            // countPaint);
            //
            // canvas.drawText("" + fields.getString("timu"),
            // Tool.dip2px(context, 5), Tool.dip2px(context, 100),
            // countPaint);
            // canvas.drawText("" + fields.getString("daan"),
            // width / 2 + Tool.dip2px(context, 5),
            // Tool.dip2px(context, 100), countPaint);

        } catch (Exception e) {
            // TODO: handle exception
        }

        return contactIcon;
    }

    public static Bitmap generatorContactCountIcon1(Bitmap icon,
                                                    JSONObject fields, Context context) {
        Bitmap contactIcon = null;

        int imgW = icon.getWidth();
        int width = Tool.dip2px(context, 250);
        if (imgW > width)
            width = imgW;// icon.getWidth();
        int height = icon.getHeight();

        int iconH = height + Tool.dip2px(context, 70);
        contactIcon = Bitmap.createBitmap(width, iconH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(contactIcon);
        canvas.drawColor(Color.WHITE);
        // 拷贝图片
        Paint iconPaint = new Paint();
        iconPaint.setDither(true);// 防抖动
        iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
        Rect src = new Rect(0, 0, imgW, height);
        Rect dst = new Rect((width - imgW) / 2, Tool.dip2px(context, 70),
                (width - imgW) / 2 + imgW, iconH);

        canvas.drawBitmap(icon, src, dst, iconPaint);

        // 启用抗锯齿和使用设备的文本字距
        Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.DEV_KERN_TEXT_FLAG);
        countPaint.setColor(Color.BLACK);
        countPaint.setTextSize(Tool.dip2px(context, 10)); // 18

        try {
            canvas.drawText("门店名称:" + fields.getString("shopTitle"),
                    Tool.dip2px(context, 5), Tool.dip2px(context, 25),
                    countPaint);
            canvas.drawText("门店编号:" + fields.getString("shopCode"),
                    Tool.dip2px(context, 5), Tool.dip2px(context, 40),
                    countPaint);
            canvas.drawText("拍摄时间:" + fields.getString("shotDate"), width / 2
                            + Tool.dip2px(context, 5), Tool.dip2px(context, 40),
                    countPaint);
            canvas.drawText("DPL名称:" + fields.getString("username"),
                    Tool.dip2px(context, 5), Tool.dip2px(context, 55),
                    countPaint);
            canvas.drawText("DPL报月销量照片", width / 2 + Tool.dip2px(context, 5),
                    Tool.dip2px(context, 55), countPaint);

        } catch (Exception e) {
            // TODO: handle exception
        }

        return contactIcon;
    }
}
