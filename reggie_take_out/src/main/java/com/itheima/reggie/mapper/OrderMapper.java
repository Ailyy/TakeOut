package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName OrderMapper
 * @Description TODO
 * @Author YeChao
 * @Date 2022/12/5 14:14
 * @Version 1.0
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
