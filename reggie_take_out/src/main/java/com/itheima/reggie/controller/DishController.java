package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.CateGory;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.service.CateGoryService;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DishController
 * @Description TODO 菜品管理
 * @Author YeChao
 * @Date 2022/11/22 16:35
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CateGoryService cateGoryService;

    @Autowired
    private DishFlavorService flavorService;

    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @RequestMapping("/page")
    public R<Page> dishPage(int page, int pageSize, String name){
        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            CateGory category = cateGoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * 添加菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> add(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("添加菜品成功！");
    }

    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id){
        DishDto dishDto =  dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品信息
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功！");
    }

    /**
     * 批量删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> removeByIds(@RequestParam List<Long> ids){
        dishService.deleteByIds(ids);
        return R.success("删除成功！");
    }


    @PostMapping("/status/{status}")
    public R<String> updateStatus(@RequestParam List<Long> ids, @PathVariable("status") String status){
        List<Dish> dishes = new ArrayList<>();
        for (Long id : ids) {
            Dish dish = dishService.getById(id);
            if (dish.getStatus() == Integer.parseInt(status)){
                if (status.equals("0")){
                    return R.error("所选菜品中存在已停售菜品");
                }else {
                    return R.error("所选菜品中存在已启售菜品");
                }
            }
            dish.setStatus(Integer.parseInt(status));
            dishes.add(dish);
        }
        dishService.updateBatchById(dishes);
        return R.success("修改成功！");
    }

    /**
     * 根据条件查询菜品信息
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(dish.getName()),Dish::getName,dish.getName());
        queryWrapper.eq(null != dish.getCategoryId(),Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（启售）的菜品
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        List<Dish> dishes = dishService.list(queryWrapper);

        List<DishDto> dishDtos = dishes.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            CateGory cateGory = cateGoryService.getById(item.getCategoryId());
            if (cateGory != null){
                dishDto.setCategoryName(cateGory.getName());
            }
            LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DishFlavor::getDishId,item.getId());

            dishDto.setFlavors(flavorService.list(wrapper));
            return dishDto;
        }).collect(Collectors.toList());
        return R.success(dishDtos);
    }
}
