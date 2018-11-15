package com.dikong.lightcontroller.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import com.dikong.lightcontroller.entity.GraphControlHistory;

/**
 * @author huangwenjun
 * @Date 2018年11月13日
 */
public interface GraphControlHistoryDao extends Mapper<GraphControlHistory>, InsertListMapper<GraphControlHistory> {

}
