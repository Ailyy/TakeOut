package com.itheima.reggie.common;

/**
 * @ClassName BaseContext
 * @Description TODO 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 * @Author YeChao
 * @Date 2022/11/22 13:52
 * @Version 1.0
 */

public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }
}
