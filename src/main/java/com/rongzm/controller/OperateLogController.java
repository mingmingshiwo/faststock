package com.rongzm.controller;

import com.rongzm.entity.OperateLog;
import com.rongzm.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rongzhiming on 2015/5/4.
 */
@RestController
@RequestMapping("/log")
public class OperateLogController {

    @Autowired
    OperateLogService operateLogService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public OperateLog query(){
        OperateLog operateLog = operateLogService.query(1);
        return operateLog;
    }
}
