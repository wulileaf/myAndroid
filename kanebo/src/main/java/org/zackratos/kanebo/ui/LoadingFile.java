package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.tools.HorizontalProgressBarWithNumber;

import butterknife.BindView;
import butterknife.OnClick;

// 下载文件
public class LoadingFile extends BaseActivity {

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.stop)
    Button stop;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.progressBar)
    HorizontalProgressBarWithNumber progressBar;
    @BindView(R.id.speed)
    TextView speed;
    @BindView(R.id.size)
    TextView size;

    private String DOWNLOAD_URL = "https://app.acsalpower.com/tommy/tommy117.wgt";
    private long taskId;

    @Override
    protected int initView() {
        return R.layout.atc_loading_file;
    }

    @Override
    protected void initData() {
        initTitle();
        Aria.download(this).register();
    }

    private void initTitle() {
        titleContent.setText("文件下载");
        titleRight.setVisibility(View.VISIBLE);
    }

    // Android api29后废弃了Environment.getExternalStorageDirectory()改为File path= getExternalFilesDir(type);type可以为null或者其他系统设定好的参数
    // 为null时返回的路径为：/storage/emulated/0/Android/data/yourPackageName/files
    @OnClick({R.id.title_back, R.id.title_right, R.id.start, R.id.stop, R.id.cancel})
    public void focusClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right:
                showToast("点击了提交");
                break;
            case R.id.start:
                Log.i("StartJD", "点击了开始---------------");
                taskId = Aria.download(this)
                        .load(DOWNLOAD_URL)
                        .setFilePath(getExternalFilesDir(null) + "/abc.wgt")
                        .create();
                break;
            case R.id.stop:// 暂停
                Aria.download(this).load(taskId).stop();// 停止下载
                break;
            case R.id.cancel:// 删除任务
                Aria.download(this).load(taskId).resume();// 恢复下载
                break;
            default:
                break;
        }
    }

// 2021-11-04 23:44:58.967 9666-9666/org.zackratos.kanebo I/StartJD: 点击了开始---------------
// 2021-11-04 23:45:00.224 9666-9666/org.zackratos.kanebo I/StartJD: ----------------com.arialyy.aria.core.task.DownloadTask@dc0b8c8
// 2021-11-04 23:45:00.226 9666-9666/org.zackratos.kanebo I/StartJD: 33----------------902.17kb
// 2021-11-04 23:45:00.226 9666-9666/org.zackratos.kanebo I/StartJD: 11----------------0
// 2021-11-04 23:45:00.427 9666-9666/org.zackratos.kanebo I/StartJD: 44----------------2.60mb
// 2021-11-04 23:45:00.428 9666-9666/org.zackratos.kanebo I/StartJD: 22----------------0
// 2021-11-04 23:45:00.428 9666-9666/org.zackratos.kanebo I/StartJD: 在这里处理任务完成的状态----------------100
    //在这里处理任务执行中的状态，如进度进度条的刷新
    @Download.onTaskRunning
    protected void running(DownloadTask task) {
        if (task.getKey().equals(DOWNLOAD_URL)) {
//            可以通过url判断是否是指定任务的回调
            Log.i("StartJD", "----------------" + task);
            progressBar.setProgress(task.getPercent());//任务进度百分比
            size.setText(task.getConvertSpeed());
//            int p = task.getPercent();// 任务进度百分比
//            String speed = task.getConvertSpeed();// 转换单位后的下载速度，单位转换需要在配置文件中打开
            long speed1 = task.getSpeed();// 原始byte长度速度
            // task.getConvertCurrentProgress()这个获取的是下载速度
            Log.i("StartJD", "33----------------" + task.getConvertCurrentProgress());// null
            Log.i("StartJD", "11----------------" + speed1);// 0
        }


    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        //在这里处理任务完成的状态
        progressBar.setProgress(task.getPercent());// 任务进度百分比
        size.setText(task.getConvertSpeed());
        Log.i("StartJD", "44----------------" + task.getConvertCurrentProgress());// null
        Log.i("StartJD", "22----------------" + task.getSpeed());// 0
        Log.i("StartJD", "在这里处理任务完成的状态----------------" + task.getPercent());
    }


    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }
}
