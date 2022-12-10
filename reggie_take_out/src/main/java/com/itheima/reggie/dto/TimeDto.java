package com.itheima.reggie.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName TimeDto
 * @Description TODO 接收订单明细查询条件的起始时间
 * @Author YeChao
 * @Date 2022/12/7 15:37
 * @Version 1.0
 */
@Data
public class TimeDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
