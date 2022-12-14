package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);

    /**
     * 修改套餐，同时修改菜品的关联关系
     * @param setmealDto
     */
    public void updateWithSetmealDish(SetmealDto setmealDto);

    /**
     * 根据id查询套餐和对应的菜品信息
     * @param id
     * @return
     */
    public SetmealDto getByIdWithDish(Long id);
}
