package com.rongzm.mapper;

import com.rongzm.entity.OperateLog;

public interface OperateLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OperateLog record);

    int insertSelective(OperateLog record);

    OperateLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OperateLog record);

    int updateByPrimaryKey(OperateLog record);
}