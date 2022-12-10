package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.CateGory;

/**
 * @ClassName GateGoryService
 * @Description TODO
 * @Author YeChao
 * @Date 2022/11/22 14:44
 * @Version 1.0
 */
public interface CateGoryService extends IService<CateGory> {

    void deleteById(Long id);
}
