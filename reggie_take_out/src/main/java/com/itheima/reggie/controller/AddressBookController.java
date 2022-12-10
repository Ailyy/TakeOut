package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.itheima.reggie.common.FrontContext;
import com.itheima.reggie.common.FrontContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName AddressBookController
 * @Description TODO 地址簿管理
 * @Author YeChao
 * @Date 2022/12/5 14:26
 * @Version 1.0
 */
@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增地址
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(FrontContext.getCurrentId());
        log.info("addressBook:{}",addressBook);
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }

    /**
     * 查询指定用户的全部地址
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> addressList(AddressBook addressBook){
        //获取当前的用户Id，用参数AddressBook接收
        addressBook.setUserId(FrontContext.getCurrentId());
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != addressBook.getUserId(), AddressBook::getUserId,addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getCreateTime);
        return R.success(addressBookService.list(queryWrapper));
    }

    /**
     * 根据id查询单个地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> getOne(@PathVariable Long id){
        return R.success(addressBookService.getById(id));
    }

    /**
     * 修改地址信息
     * @param addressBook
     * @return
     */
    @PutMapping
    public R<String> updateAddress(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return R.success("修改成功！");
    }

    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AddressBook::getUserId,FrontContext.getCurrentId());
        lambdaQueryWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook addressBook = addressBookService.getOne(lambdaQueryWrapper);
        if (addressBook == null){
            return R.error("没有找到该地址！");
        }
        return R.success(addressBook);
    }

    /**
     * 修改默认地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public R<AddressBook> updateDefault(@RequestBody AddressBook addressBook){
        LambdaUpdateWrapper<AddressBook> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,FrontContext.getCurrentId());
        queryWrapper.set(AddressBook::getIsDefault,0);
        addressBookService.update(queryWrapper);
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }
}
