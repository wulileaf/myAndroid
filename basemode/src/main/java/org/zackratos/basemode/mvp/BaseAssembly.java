package org.zackratos.basemode.mvp;

import android.content.Context;
//import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;


/**
 * @Date 2017/11/2
 * @author 丁志祥
 * @version 1.0
 * @Note
 */

public class BaseAssembly extends AppCompatEditText implements View.OnTouchListener {

    private Boolean yF = false;// 不给看密码

    //需要实现下面的几个构造函数，不然有可能加载不了这个EditText控件
    public BaseAssembly(Context context) {
        super(context);
        init();
    }

    public BaseAssembly(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public BaseAssembly(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //初始化控件，绑定监听器
    public void init() {
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //如果不是按下操作，就不做处理，如果是按下操作但是没有图片，也不做处理
        if (event.getAction() == MotionEvent.ACTION_UP && this.getCompoundDrawables()[2] != null) {
            //检测点击区域的X坐标是否在图片范围内
            if (event.getX() > this.getWidth()
                    - this.getPaddingRight()
                    - this.getCompoundDrawables()[2].getIntrinsicWidth()) {

                //在此做图片的点击处理
                System.out.println("点击区域");
//                MessageShow.ShowToast(getContext(), "点击了图片");
                if (yF == false) {

                }

            }
            return false;
        }
        return false;
    }
}
