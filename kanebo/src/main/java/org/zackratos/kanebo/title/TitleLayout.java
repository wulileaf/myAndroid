package org.zackratos.kanebo.title;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.zackratos.kanebo.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class TitleLayout extends LinearLayout {
    @BindView(R.id.title_back)
    ImageView title_back;
    @BindView(R.id.title_right)
    TextView title_right;
    @BindView(R.id.title_content)
    TextView title_contentl;

    // 构建两个参数的构造函数
    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.titlber, this);
    }

    @OnClick({R.id.title_back, R.id.title_content, R.id.title_right})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                ((Activity) getContext()).finish();
                break;
            case R.id.title_content:
                break;
            case R.id.title_right:
                break;
            default:
                break;
        }
    }
}
