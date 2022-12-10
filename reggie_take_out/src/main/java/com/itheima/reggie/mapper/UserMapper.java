package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author YeChao
 * @Date 2022/12/1 16:33
 * @Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
