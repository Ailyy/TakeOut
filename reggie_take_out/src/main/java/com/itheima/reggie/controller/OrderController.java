package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.FrontContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.OrderDetail;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrderDetailService;
import com.itheima.reggie.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName OrderController
 * @Description TODO 订单管理
 * @Author YeChao
 * @Date 2022/12/7 11:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户提交订单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submitOrder(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("下单成功！");
    }

    /**
     * 订单信息分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/orderPage")
    public R<Page> orderPage(int page, int pageSize){
        Long userId = FrontContext.getCurrentId();
        Page<Orders> ordersPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, userId);
        queryWrapper.orderByDesc(Orders::getOrderTime);

        orderService.page(ordersPage,queryWrapper);

        return R.success(ordersPage);
    }

    /**
     * 再来一单
     * @param orders
     * @return
     */
    @PostMapping("/again")
    public R<Orders> again(Orders orders){
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId,orders.getId());
        return R.success(orderService.getById(orders.getId()));
    }
}
