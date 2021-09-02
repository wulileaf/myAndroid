package org.zackratos.basemode.mvp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.xutils.common.util.IOUtil.copy;



/**
 * @Date 2017/10/31
 * @author leaf
 * @version 1.0
 * @Note 图片处理
 */
public class BaseTool {

    /**
     * 对图片进行压缩处理，存储图片
     * 控制压缩大小
     */
    public void compressByQuality(Bitmap bitmap, int maxSize, String file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        while (baos.toByteArray().length / 1024 > maxSize) {
            quality -= 10;
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", String.valueOf(e));
        }
    }

    /**
     * 从文件中读取图片
     * 通过File类型的存储路径查找文件，传入的是图片文件地址
     */
    public Bitmap getDiskBitmap(File pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(String.valueOf(pathString));
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(String.valueOf(pathString));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    /**
     * string类型的图片数据转成bitmap
     * 参数st为图片转换成的字符串
     */
    public Bitmap convertStringToIcon(String st) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将图片剪裁为圆形
     * 按一定比例宽高大小
     * 剪切的位置一旦设定就不可以灵活更改
     */
    public Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     */
    public String bitmap2StrByBase64(Bitmap bit) {
        if (bit == null) {
            return "";
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
     * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ;测试NO
     * B.本地路径:url="file://mnt/sdcard/photo/image.png"; 测试OK
     * C.支持的图片格式 ,png, jpg,bmp,gif等等
     *
     * @param url
     * @return 将网络URL或者本地文件URL读取成Bitmap
     */
    public Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 50 * 50);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 50 * 50);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取了图片的网络地址
     * 请求这个地址获取图片数据
     * Android 4.0 之后不能在主线程中请求HTTP请求
     * 但是在onCreate()方法后添加下面的两个方法
     * StrictMode.setThreadPolicy()
     * StrictMode.setVmPolicy()
     */
    public Bitmap GetNetworkPictures(String path) throws IOException {

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }




}
