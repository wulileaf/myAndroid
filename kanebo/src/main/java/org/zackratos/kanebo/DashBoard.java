package org.zackratos.kanebo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import butterknife.internal.Utils;


public class DashBoard extends View {
    private static final int ANGLE = 120;//仪表盘 缺口 弧度
//    private static final float LENGTH = Utils.dp2px(100);//指针长度
//    private static final float RADIUS = Utils.dp2px(150);//半径
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿标志
    Path dash = new Path();//刻度
    PathDashPathEffect effect;//刻度间隔

    public DashBoard(Context context) {
        super(context);
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
//        paint.setStyle(Paint.Style.STROKE);//行程
//        paint.setStrokeWidth(Utils.dp2px(2));//弧线宽度
//        //----画弧线----
//        Path arc = new Path();
//        //参数，先确定这个弧线的矩形。 起始点角度。 扫过的角度
//        arc.addArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS,
//                90 + ANGLE / 2, 360 - ANGLE);
//        //----画刻度----
//        dash.addRect(0, 0, Utils.dp2px(2), Utils.dp2px(10), Path.Direction.CW);//每个分割点就是一个矩形,最后一个参数是顺时针
//        //计算总弧形长度 算出平均间隔
//        PathMeasure pathMeasure = new PathMeasure(arc, false);//是否闭合
//        //参数1 刻度本体。 参数2 间隔距离（弧形长度 - 刻度的厚度  然后平均分20分）。参数3 起始第一个距离开头。 参数4 旋转类型
//        effect = new PathDashPathEffect(dash, (pathMeasure.getLength() - Utils.dp2px(2)) / 20, 0, PathDashPathEffect.Style.ROTATE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
