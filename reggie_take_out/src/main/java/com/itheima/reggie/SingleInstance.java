package com.itheima.reggie;

/**
 * @ClassName SingleInstance
 * @Description TODO 恶汉单例设计模式
 * @Author YeChao
 * @Date 2022/12/10 11:40
 * @Version 1.0
 */

//定义一个单例类
public class SingleInstance {
    //定义一个静态的类对象
    public static SingleInstance instance = new SingleInstance();
    //单例模式，构造器必须私有
//    private SingleInstance(){
//        System.out.println("创建了一个对象！");
//    }
    public SingleInstance(){
        System.out.println("创建了一个对象！");
    }

    public static void main(String[] args) {
        instance.fun();
    }

    public void fun(){
        System.out.println("这是一个方法！");
    }
}
