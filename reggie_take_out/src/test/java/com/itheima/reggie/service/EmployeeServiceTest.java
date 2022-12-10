package com.itheima.reggie.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.entity.Employee;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName EmployeeService
 * @Description TODO
 * @Author YeChao
 * @Date 2022/11/22 9:45
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeServiceTest {
    @Resource
    private EmployeeService employeeService;

    @Test
    public void findList(){

    }
}
