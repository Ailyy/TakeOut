package com.itheima.reggie;

import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.entity.User;
import org.apache.commons.lang.math.NumberUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName Test
 * @Description TODO
 * @Author YeChao
 * @Date 2022/11/29 15:22
 * @Version 1.0
 */

public class Test extends User{

    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void main(String[] args) {
        Employee e1 = new Employee("叶超", 60000, 1987, 4, 17);
        Employee e2 = e1;
        Employee e3 = new Employee("叶超", 60000, 1987, 4, 17);
        Employee bob = new Employee("Bob", 50000, 1988, 8, 18);

        System.out.println(e1 == e2);
        System.out.println(e1 == e3);
        System.out.println(e1.equals(e3));
        System.out.println(e1.equals(bob));
        System.out.println(bob);

        Manager m1 = new Manager("Carl", 80000, 1987, 12, 2);
        Manager boos = new Manager("Carl", 80000, 1987, 12, 2);
        boos.setBonus(5000);
        System.out.println(boos);
        System.out.println(m1.equals(boos));
        System.out.println(e1.hashCode());
        System.out.println(e3.hashCode());
        System.out.println(bob.hashCode());
        System.out.println(m1.hashCode());
        System.out.println("=============这是分割线============");
        System.out.println("当前系统时间戳：" + System.currentTimeMillis());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
        System.out.println(timestamp.getTime());

        System.out.println("=============这是分割线============");

        Test test = new Test();
        test.function();
        User.work();

    }



    public void function(){
        super.fun();
    }

    static class Employee
    {
        public Employee(String name, double salary, int year, int month, int day) {
            this.name = name;
            this.salary = salary;
            GregorianCalendar calendar = new GregorianCalendar(year,month-1,day);
            this.hireDay = calendar.getTime();
        }

        public String getName() {
            return name;
        }

        public double getSalary() {
            return salary;
        }

        public Date getHireDay() {
            return hireDay;
        }

        public void raiseSalary(double byPercent){
            double raise = salary * byPercent / 100;
            salary += raise;
        }

        public boolean equals(Object otherObject){
            if (this == otherObject) return true;
            if (otherObject == null) return false;
            if (getClass() != otherObject.getClass()) return false;
            Employee other = (Employee) otherObject;
            return name.equals(other.name)
                    && salary == other.salary
                    && hireDay.equals(other.hireDay);
        }

        public int hashCode(){
            return 7 * name.hashCode() + 11 * new Double(salary).hashCode() + 13 * hireDay.hashCode();
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", salary=" + salary +
                    ", hireDay=" + hireDay +
                    '}';
        }

        private String name;
        private double salary;
        private Date hireDay;
    }

    static class Manager extends Employee{
        public Manager(String name, double salary, int year, int month, int day){
            super(  name, salary, year, month, day);
            bonus = 0;
        }

        public double getSalary(){
            double baseSalary = super.getSalary();
            return baseSalary + bonus;
        }

        public void setBonus(double bonus){
            this.bonus = bonus;
        }

        public boolean equals(Object otherObject){
            if (!super.equals(otherObject)) return false;
            Manager other = (Manager) otherObject;
            return bonus == other.bonus;
        }

        public int hashCode(){
            return super.hashCode() + 17 * new Double(bonus).hashCode();
        }

        public String  toString(){
            return super.toString() + "[bonus=" + bonus + "]";
        }

        private double bonus;
    }

    public String toChineseNum(int n){
        char [] cNum = new char[]{'零','一','二','三','四','五','六','七','八','九'};
        StringBuffer stringBuffer = new StringBuffer();
        String result ="";
        if (n < 0){
            result = "负";
            n = -n;
        }
        while (n > 0){
            stringBuffer.append(cNum[n % 10]);
            n /= 10;
        }
        stringBuffer.append(result);
        System.out.println(stringBuffer.indexOf("五"));
        return stringBuffer.reverse().toString();
    }
}
