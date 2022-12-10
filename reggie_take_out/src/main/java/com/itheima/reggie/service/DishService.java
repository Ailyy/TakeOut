package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    //添加菜品信息，同时存入对应的口味数据
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味
    public DishDto getByIdWithFlavor(Long id);

    //更新菜品信息和对应的口味信息
    public void updateWithFlavor(DishDto dishDto);

    //根据ids批量删除
    public void deleteByIds(List<Long> ids);
}
