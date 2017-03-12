package com.honjane.handlerdemo.lib;


/**
 * Created by honjane on 2017/3/12.
 */

public class Message {

    public int what;

    public int arg1;


    public int arg2;

    public Object obj;

    public Handler target;

    @Override
    public String toString() {
        return obj.toString();
    }
}
