package com.honjane.handlerdemo.lib;

/**
 * Created by honjane on 2017/3/12.
 */

public final class Looper {


    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    public MessageQueue mQueue;

    public Looper() {
        mQueue = new MessageQueue();
    }

    /**
     * 实例化一个属于当前线程的looper对象
     */
    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    /**
     * 轮询消息队列
     */
    public static void loop() {
        Looper me = myLooper();
        MessageQueue queue = me.mQueue;
        //轮询
        Message msg;
        for (; ; ) {
            msg = queue.next();
            //获取到发送消息的 msg.target （handler）本身，然后分发消息
            if (msg == null || msg.target == null) {
                continue;
            }

            msg.target.dispatchMessage(msg);

        }
    }
}
