package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName ShoppingCartController
 * @Description TODO 购物车管理
 * @Author YeChao
 * @Date 2022/12/6 14:18
 * @Version 1.0
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 查询购物车信息
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> getList(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 添加到购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("shoppingCart:{}",shoppingCart);
        //获取当前登录的用户id，指定当前购物车属于哪个用户
        shoppingCart.setUserId(BaseContext.getCurrentId());
        //获取当前菜品id
        Long dishId = shoppingCart.getDishId();
        //构造条件构造器
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //判断添加的是菜品还是套餐
        if (dishId != null){
            //添加的是菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            //添加的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        ShoppingCart cartOne = shoppingCartService.getOne(queryWrapper);
        //判断判断购物车中是否已存在该商品
        if (cartOne != null){
            //购物车中存在该商品，商品数量加1，默认值为1
            cartOne.setNumber(cartOne.getNumber()+1);
            shoppingCartService.updateById(cartOne);
        }else {
            //购物车中不存在，直接添加商品
            shoppingCart.setNumber(1);
            //设置添加时间
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartOne = shoppingCart;
        }
        return R.success(cartOne);
    }


    /**
     * 清空购物车
     */
    @DeleteMapping("/clean")
    public R<String> cleanCart(){
        //创建条件构造器
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return R.success("购物车已清空！");
    }

    /**
     * 减少购物车菜品数量
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public R<String> deleteOne(@RequestBody ShoppingCart shoppingCart){
        //获取当前菜品id
        Long dishId = shoppingCart.getDishId();
        //条件构造器
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //判断减少的是菜品还是套餐
        if (null != dishId){
            //减少的是菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            //减少的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart shoppingCartOne = shoppingCartService.getOne(queryWrapper);
        //判断数量是否为1，为1直接删除，大于1，数量减1
        if (shoppingCartOne.getNumber() > 1){
            //数量大于1，直接减1
            shoppingCartOne.setNumber(shoppingCartOne.getNumber() - 1);
            shoppingCartService.updateById(shoppingCartOne);
        }else {
            //数量小于2，删除菜品
            shoppingCartService.remove(queryWrapper);
        }
        return R.success("修改成功！");
    }
}
