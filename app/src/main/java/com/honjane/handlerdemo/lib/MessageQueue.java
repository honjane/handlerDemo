package com.honjane.handlerdemo.lib;


import android.util.Log;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by honjane on 2017/3/12.
 */

public class MessageQueue {
    private static final String TAG = MessageQueue.class.getName();
    Message[] mItems;
    int mPutIndex;
    //队列中消息数
    private int mCount;
    private int mTakeIndex;
    //锁
    Lock mLock;
    //条件变量
    Condition mNotEmpty;
    Condition mNotFull;

    public MessageQueue() {
        mItems = new Message[50];
        mLock = new ReentrantLock();
        mNotEmpty = mLock.newCondition();
        mNotFull = mLock.newCondition();
    }

    /**
     * 消息队列取消息 出队
     *
     * @return
     */
    Message next() {
        Message msg = null;
        try {
            mLock.lock();
            //检查队列是否空了
            while (mCount <= 0) {
                //阻塞
                mNotEmpty.await();
                Log.i(TAG, "队列空了，阻塞");
            }
            msg = mItems[mTakeIndex];//可能空
            //取了之后置空
            mItems[mTakeIndex] = null;
            mTakeIndex = (++mTakeIndex >= mItems.length) ? 0 : mTakeIndex;
            mCount--;
            //通知生产这生产
            mNotFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mLock.unlock();
        }

        return msg;
    }

    /**
     * 添加消息进队列
     *
     * @param message
     */

    public void enqueueMessage(Message message) {

        try {
            mLock.lock();
            //检查队列是否满了
            while (mCount >= mItems.length) {
                //阻塞
                mNotFull.await();
                Log.i(TAG, "队列满了，阻塞");
            }

            mItems[mPutIndex] = message;
            mPutIndex = (++mPutIndex >= mItems.length) ? 0 : mPutIndex;
            mCount++;
            //通知消费者消费
            mNotEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mLock.unlock();
        }


    }
}
