package com.rongzm.service;

import com.rongzm.entity.OperateLog;

/**
 * Created by rongzhiming on 2015/5/4.
 */
public interface OperateLogService {
    int add(OperateLog operateLog);

    OperateLog query(long id);
}
