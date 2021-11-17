package org.zackratos.kanebo.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CusView extends View {

    // 它提供基本信息之外的所有风格信息，例如颜色、线条粗细、阴影等
    // 比如图形的颜色、空心实心这些，你不管是画圆还是画方都有可能用到的，这些信息则是统一放在 paint 参数里的
    // 这个是普通模式
    private Paint paint = new Paint();
    private Path path = new Path();

    // 1 在绘制的时候，往往需要开启抗锯齿来让图形和文字的边缘更加平滑。开启抗锯齿很简单，只要在 new Paint() 的时候加上一个 ANTI_ALIAS_FLAG 参数就行
    // 2 也可以使用 Paint.setAntiAlias(boolean aa) 来动态开关抗锯齿
//    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CusView(Context context) {
        super(context);
    }

    public CusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //Paint.setStyle(Style style) 设置绘制模式
//Paint.setColor(int color) 设置颜色
//Paint.setStrokeWidth(float width) 设置线条宽度
//Paint.setTextSize(float textSize) 设置文字大小
//Paint.setAntiAlias(boolean aa) 设置抗锯齿开关
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 这类颜色填充方法一般用于在绘制之前设置底色，或者在绘制之后为界面设置半透明蒙版
        // 会把整个画布染色，覆盖掉原有内容
//        Canvas.drawColor(@ColorInt int color) 颜色填充,在整个绘制区域统一涂上指定的颜色
        // ps:顺序1-2是可以显示背景黄色+黑色圆形
        // ps:顺序2-1那就只能显示黄色了，黑色圆形被覆盖住了
        // 1
//        canvas.drawColor(Color.YELLOW);
        // 2  前两个参数 centerX centerY 是圆心的坐标，第三个参数 radius 是圆的半径
        // 独有信息都是直接作为参数写进 drawXXX() 方法里的（比如 drawCircle(centerX, centerY, radius, paint) 的前三个参数）。

//        paint.setColor(Color.RED);// 将画笔设置成红色，用来设置绘制内容的颜色
        // 4 绘画样式
        // setStyle(Style style) 这个方法设置的是绘制的 Style 。
        // Style 具体来说有三种： FILL, STROKE 和 FILL_AND_STROKE 。FILL 是填充模式，STROKE 是画线模式（即勾边模式），FILL_AND_STROKE 是两种模式一并使用：既画线又填充。它的默认值是 FILL，填充模式
//        paint.setStyle(Paint.Style.STROKE); // Style 修改为画线模式，// 将绘制模式改为画线模式
        // 在 STROKE 和 FILL_AND_STROKE 下，还可以使用 paint.setStrokeWidth(float width) 来设置线条的宽度
//        paint.setStrokeWidth(20); // 线条宽度为 20 像素
//        canvas.drawCircle(300, 300, 200, paint);
        // 3
//        canvas.drawColor(Color.parseColor("#88880000")); // 半透明红色
//        canvas.drawRGB(100, 200, 100);
//        canvas.drawARGB(100, 100, 200, 100);

        //  drawRect(float left, float top, float right, float bottom, Paint paint) 画矩形
        // left, top, right, bottom 是矩形四条边的坐标
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(100, 100, 500, 500, paint);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(700, 100, 1100, 500, paint);

        // x 和 y 是点的坐标
        // drawPoint(float x, float y, Paint paint) 画点
//        paint.setStrokeWidth(20);
//        paint.setStrokeCap(Paint.Cap.ROUND);
//        paint.setColor(Color.RED);
//        canvas.drawPoint(50, 50, paint);

        // 批量画点
//        drawPoints(float[] pts, int offset, int count, Paint paint) / drawPoints(float[] pts, Paint paint) 画点（批量）
        // pts 这个数组是点的坐标，每两个成一对；offset 表示跳过数组的前几个数再开始记坐标；count 表示一共要绘制几个点
//        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
//// 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)
//        canvas.drawPoints(points, 2 /* 跳过两个数，即前两个 0 */,
//                8 /* 一共绘制 8 个数（4 个点）*/, paint);

//        drawOval(float left, float top, float right, float bottom, Paint paint) 画椭圆
//        left, top, right, bottom 是这个椭圆的左、上、右、下四个边界点的坐标
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawOval(400, 50, 700, 200, paint);

        // Api
//        drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint) 画圆角矩形

        // Api
        // startX, startY, stopX, stopY 分别是线的起点和终点坐标
//        drawLine(float startX, float startY, float stopX, float stopY, Paint paint) 画线
//        drawLines(float[] pts, int offset, int count, Paint paint) / drawLines(float[] pts, Paint paint) 画线（批量）
//        float&#91;] points = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
//canvas.drawLines(points, paint);
        // Api
        // drawArc() 是使用一个椭圆来描述弧形的。left, top, right, bottom 描述的是这个弧形所在的椭圆；startAngle 是弧形的起始角度（x 轴的正向，即正右的方向，是 0 度的位置；顺时针为正角度，逆时针为负角度），sweepAngle 是弧形划过的角度；useCenter 表示是否连接到圆心，如果不连接到圆心，就是弧形，如果连接到圆心，就是扇形
//        drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter, Paint paint) 绘制弧形或扇形
//        paint.setStyle(Paint.Style.FILL); // 填充模式
//        canvas.drawArc(0, 0, 800, 500, -90, 90, true, paint); // 绘制扇形
//        canvas.drawArc(200, 100, 800, 500, 20, 140, false, paint); // 绘制弧形
//        paint.setStyle(Paint.Style.STROKE); // 画线模式
//        canvas.drawArc(200, 100, 800, 500, 180, 60, false, paint); // 绘制不封口的弧形

//        canvas.drawPath();
        // RectF(float left, float top, float right, float bottom)
//        RectF rectF = new RectF(1,2,3,4);


//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(0, 0, 100, paint);// 画圆
//        canvas.drawOval(0, 0, 100, 50, paint);// 画椭圆
//        canvas.drawPoint(50, 50, paint);// 画点
//        float[] points = {0, 0, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
//        // 2为跳过两个数，即前两个0
//        // 一共绘制8个数，即从第2个0后面数8个数
//        canvas.drawPoints(points, 2, 8, paint);
//        canvas.drawPoints(points, paint);// 批量画点
//        canvas.drawArc(0, 0, 800, 500, -90, 90, true, paint);// 画弧线
//        canvas.drawLine(0, 0, 0, 100, paint);// 画线
//        canvas.drawRect(0, 0, 200, 100, paint);// 画矩形
//        canvas.drawRoundRect(0, 0, 200, 100, 20, 20, paint);// 画圆角的矩形


    }
}
