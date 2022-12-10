package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.FrontContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.TimeDto;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrderDetailService;
import com.itheima.reggie.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.Date;

/**
 * @ClassName OrderDetailController
 * @Description TODO 订单明细管理
 * @Author YeChao
 * @Date 2022/12/7 14:13
 * @Version 1.0
 */
@RestController
@RequestMapping("/orderDetail")
@Slf4j
public class OrderDetailController {
    @Autowired
    private OrderService orderService;

    /**
     * 管理端订单明细分页查询
     * @param page
     * @param pageSize
     * @param number
     * @param timeDto
     * @return
     */
    @GetMapping("/page")
        public R<Page> page(int page, int pageSize, String number, TimeDto timeDto){
        //创建分页构造器
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        //创建条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //设置按订单号查询条件
        queryWrapper.like(StringUtils.isNotEmpty(number),Orders::getNumber,number);
        //设置按订单时间查询条件
        queryWrapper.between(null != timeDto.getBeginTime() || null != timeDto.getEndTime(), Orders::getOrderTime,timeDto.getBeginTime(),timeDto.getEndTime());
        //设置按订单时间降序排序
        queryWrapper.orderByDesc(Orders::getOrderTime);
        //执行查询
        orderService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 修改订单状态
     * @param orders
     * @return
     */
    @PutMapping
    public R<String> updateStatus(@RequestBody Orders orders){
        orderService.updateById(orders);
        return R.success("修改订单状态成功！");
    }
}
