package com.yjxxt.utils;

import com.yjxxt.exceptions.ParamException;

public class AssertUtils {

    public static void isTrue(boolean flag,String msg){

        if(flag) {
            throw new ParamException(msg);
        }
    }
}
