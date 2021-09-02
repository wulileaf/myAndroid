package org.zackratos.kanebo;

// 接口测试
// 这里就是一个点击事件的实现，可以是个正常的页面，下面的就是一个
public class Test_One {

    public Test callback;

    public Test_One(Test test) {
        this.callback = test;
    }

    // 这里面也具体需要子线程执行的业务
    public void getInfo() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    callback.success("回调成功");
//                    Thread.currentThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
