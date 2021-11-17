package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import butterknife.BindView;
import butterknife.OnClick;

// socket通信
public class SocketView extends BaseActivity {

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_back)
    ImageView titleBack;

    @Override
    protected int initView() {
        return R.layout.atc_socket;
    }

    @Override
    protected void initData() {
        initTitle();
    }

    // 服务端
    protected void TCPServer() {
        try {
            // 创建服务器端 Socket，指定监听端口
            ServerSocket serverSocket = new ServerSocket(8888);
            // 等待客户端连接
            Socket clientSocket = serverSocket.accept();
            // 获取客户端输入流，
            InputStream is = clientSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String data = null;
            // 读取客户端数据
            while ((data = br.readLine()) != null) {
                System.out.println("服务器接收到客户端的数据：" + data);
            }
            // 关闭输入流
            clientSocket.shutdownInput();
            // 获取客户端输出流
            OutputStream os = clientSocket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            // 向客户端发送数据
            pw.print("服务器给客户端回应的数据");
            pw.flush();
            // 关闭输出流
            clientSocket.shutdownOutput();
            // 关闭资源
            pw.checkError();
            os.close();
            br.close();
            isr.close();
            is.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 客户端
    protected void TCPClient() {
        try {
            // 创建客户端Socket，指定服务器的IP地址和端口
            Socket socket = new Socket(InetAddress.getLocalHost(), 8888);
            // 获取输出流，向服务器发送数据
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("客户端给服务器端发送的数据");
            pw.flush();
            // 关闭输出流
            socket.shutdownOutput();

            // 获取输入流，接收服务器发来的数据
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String data = null;
            // 读取客户端数据
            while ((data = br.readLine()) != null) {
                System.out.println("客户端接收到服务器回应的数据：" + data);
            }
            // 关闭输入流
            socket.shutdownInput();

            // 关闭资源
            br.close();
            isr.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTitle() {
        titleContent.setText("socket");
        titleRight.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.title_back, R.id.title_right})
    public void focusClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right:
                showToast("点击了提交");
                break;
            default:
                break;
        }
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
