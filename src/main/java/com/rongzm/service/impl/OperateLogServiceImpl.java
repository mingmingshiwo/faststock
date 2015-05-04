package com.rongzm.service.impl;

import com.rongzm.entity.OperateLog;
import com.rongzm.mapper.OperateLogMapper;
import com.rongzm.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rongzhiming on 2015/5/4.
 */
@Service
public class OperateLogServiceImpl implements OperateLogService{

    @Autowired
    OperateLogMapper mapper;

    @Override
    public int add(OperateLog operateLog) {
        return mapper.insertSelective(operateLog);
    }

    @Override
    public OperateLog query(long id) {
        return mapper.selectByPrimaryKey(id);
    }
}
