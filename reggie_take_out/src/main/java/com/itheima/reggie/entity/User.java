package com.itheima.reggie.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
/**
 * 用户信息
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //姓名
    private String name;


    //手机号
    private String phone;


    //性别 0 女 1 男
    private String sex;


    //身份证号
    private String idNumber;


    //头像
    private String avatar;


    //状态 0:禁用，1:正常
    private Integer status;

    protected void fun(){
        System.out.println("User protected!");
    }

    //内部类，可以用protected修饰，也可以不需要修饰符，也可以用private修饰
    protected class Student{
        protected void use(){

        }
        public void read(){

        }
        private void play(){

        }
        void teach()
        {

        }
    }

    class Teacher{
        public void read(){

        }
        private void play(){

        }
        protected void use(){

        }
        void teach()
        {

        }
    }

    private class Player{
        public void read(){

        }
        protected void use(){

        }
        private void play(){

        }
        void teach()
        {

        }
    }

    public static void work(){
        System.out.println("=============这是分割线============");
        System.out.println("我是帅哥！我爱工作！");
    }

}
