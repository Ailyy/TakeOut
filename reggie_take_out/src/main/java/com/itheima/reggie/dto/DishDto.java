package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DishDto
 * @Description TODO 中间类
 * @Author YeChao
 * @Date 2022/11/22 17:16
 * @Version 1.0
 */
@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;

    private List<Long> ids;
}
