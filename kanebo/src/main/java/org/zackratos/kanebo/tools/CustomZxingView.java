package org.zackratos.kanebo.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.hardware.camera2.CameraManager;
import android.util.AttributeSet;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.ViewfinderView;

import java.util.ArrayList;
import java.util.List;

public class CustomZxingView extends ViewfinderView {
    public int laserLinePosition = 0;
    public float[] position = new float[]{0f, 0.5f, 1f};
    public int[] colors = new int[]{0xff0699e6, 0xff0699e6, 0xff0699e6};
    public LinearGradient linearGradient;

    public CustomZxingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 重写draw方法绘制自己的扫描框
     *
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        refreshSizes();
//        || previewFramingRect == null
        if (framingRect == null) {
            return;
        }

        Rect frame = framingRect;
//        Rect previewFrame = previewFramingRect;

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        //绘制4个角

        paint.setColor(Color.parseColor("#139D57"));//定义画笔的颜色

        // 需要提醒的是这个扫描框的左上角就是坐标原点(0,0),水平线是x轴,竖直线是y轴
        // frame.top + 10这个标识横线的高度
        canvas.drawRect(frame.left, frame.top, frame.left + 100, frame.top + 5, paint);// 左上角上边的横线
        canvas.drawRect(frame.left, frame.top, frame.left + 5, frame.top + 100, paint);// 做上角左边的竖线

        canvas.drawRect(frame.right - 100, frame.top, frame.right, frame.top + 5, paint);// 右上角上边的横线
        canvas.drawRect(frame.right - 5, frame.top, frame.right, frame.top + 100, paint);// 右上角右边的竖线

        canvas.drawRect(frame.left, frame.bottom - 5, frame.left + 100, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - 100, frame.left + 5, frame.bottom, paint);

        canvas.drawRect(frame.right - 100, frame.bottom - 5, frame.right, frame.bottom, paint);
        canvas.drawRect(frame.right - 5, frame.bottom - 100, frame.right, frame.bottom, paint);

        // 绘制变暗的外部（即框架矩形外部）
        // 如果没有这段代码所有除扫描区域外的部分将会全部变成透明部分
        // Draw the exterior (i.e. outside the framing rect) darkened
//        paint.setColor(resultBitmap != null ? resultColor : maskColor);
//        canvas.drawRect(0, 0, width, frame.top, paint);
//        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
//        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
//        canvas.drawRect(0, frame.bottom + 1, width, height, paint);


        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);// 设置透明度
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {
            int middle = frame.height() / 2 + frame.top;
            laserLinePosition = laserLinePosition + 5;
            if (laserLinePosition > frame.height()) {
                laserLinePosition = 0;
            }
            linearGradient = new LinearGradient(frame.left + 1, frame.top + laserLinePosition, frame.right - 1, frame.top + 10 + laserLinePosition, colors, position, Shader.TileMode.CLAMP);
            // Draw a red "laser scanner" line through the middle to show decoding is active
            paint.setShader(linearGradient);

            //绘制扫描线
            // frame.left + 50和frame.right - 50是控制扫描线的宽度
            // frame.top + 2 + laserLinePosition是控制扫描线的高度
            canvas.drawRect(frame.left + 50, frame.top + laserLinePosition, frame.right - 50, frame.top + 2 + laserLinePosition, paint);
            paint.setShader(null);

            float scaleX = frame.width() / (float) frame.width();
            float scaleY = frame.height() / (float) frame.height();

            List<ResultPoint> currentPossible = possibleResultPoints;
            List<ResultPoint> currentLast = lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new ArrayList<>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(CURRENT_POINT_OPACITY);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            POINT_SIZE, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                float radius = POINT_SIZE / 2.0f;
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            radius, paint);
                }
            }
            postInvalidateDelayed(16,
                    frame.left,
                    frame.top,
                    frame.right,
                    frame.bottom);
        }
    }

}
