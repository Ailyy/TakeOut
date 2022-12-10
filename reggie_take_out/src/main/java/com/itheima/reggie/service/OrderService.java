package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Orders;

/**
 * @ClassName OrderService
 * @Description TODO
 * @Author YeChao
 * @Date 2022/12/5 14:19
 * @Version 1.0
 */
public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders);
}
