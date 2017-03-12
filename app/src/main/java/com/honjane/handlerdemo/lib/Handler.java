package com.honjane.handlerdemo.lib;

/**
 * Created by honjane on 2017/3/12.
 */

public class Handler {
    private Looper mLooper;
    private MessageQueue mQueue;

    public Handler() {
        mLooper = Looper.myLooper();
        mQueue = mLooper.mQueue;
    }

    public void sendMessage(Message message) {
        message.target = this;
        mQueue.enqueueMessage(message);
    }

    /**
     * 子类处理消息
     *
     * @param message
     */
    public void handleMessage(Message message) {

    }

    /**
     * 分发消息
     *
     * @param message
     */
    public void dispatchMessage(Message message) {
        handleMessage(message);
    }
}
