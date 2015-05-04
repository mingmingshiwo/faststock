package com.rongzm.controller;

import com.rongzm.entity.OperateLog;
import com.rongzm.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * Created by rongzhiming on 2015/5/4.
 */
@RestController
@RequestMapping("/log")
public class OperateLogController {

    @Autowired
    OperateLogService operateLogService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OperateLog query(@PathVariable("id") int id){
        OperateLog operateLog = operateLogService.query(id);
        return operateLog;
    }
}
