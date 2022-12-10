package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.CateGory;
import com.itheima.reggie.service.CateGoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName CateGoryController
 * @Description TODO 菜品分类管理
 * @Author YeChao
 * @Date 2022/11/22 14:54
 * @Version 1.0
 */

@RestController
@Slf4j
@RequestMapping("/category")
public class CateGoryController {
    @Autowired
    private CateGoryService cateGoryService;

    /**
     * 分类管理分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("/page")
    public R<Page> cateGoryList(int page, int pageSize){
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize);

        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<CateGory> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        //queryWrapper.like()
        //添加排序条件
        queryWrapper.orderByAsc(CateGory::getSort);
        //执行查询
        cateGoryService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
//    @RequestMapping("/{id}")
//    public R<CateGory> queryCateGoryById(@PathVariable Long id){
//        log.info("根据id查询分类，回显数据");
//        CateGory cateGory = cateGoryService.getById(id);
//        return R.success(cateGory);
//    }

    /**
     * 根据id修改分类信息
     * @param cateGory
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody CateGory cateGory){
        log.info("修改分类信息：{}", cateGory);

        cateGoryService.updateById(cateGory);

        return R.success("修改成功！");
    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id){
        cateGoryService.deleteById(id);
        return R.success("删除分类成功！");
    }

    /**
     * 添加菜品分类信息
     * @param cateGory
     * @return
     */
    @PostMapping
    public R<String> add(@RequestBody CateGory cateGory){
        log.info("category:{}",cateGory);

        cateGoryService.save(cateGory);

        return R.success("添加分类成功");
    }

    /**
     * 根据条件查询分类数据
     * @param cateGory
     * @return
     */
    @GetMapping("/list")
    public R<List<CateGory>> cateGoryList(CateGory cateGory){
        //创建构造器
        LambdaQueryWrapper<CateGory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //创建条件
        lambdaQueryWrapper.eq(cateGory.getType() != null, CateGory::getType, cateGory.getType());
        //创建排序条件
        lambdaQueryWrapper.orderByAsc(CateGory::getSort).orderByAsc(CateGory::getUpdateTime);
        //执行查询
        List<CateGory> cateGoryList = cateGoryService.list(lambdaQueryWrapper);

        return R.success(cateGoryList);
    }
}
