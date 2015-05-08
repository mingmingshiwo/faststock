package com.rongzm.controller;

import com.rongzm.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by rongzhiming on 2015/5/8.
 */
@RestController
@RequestMapping("/stock")
public class StockController {
    @Resource(name="StockServiceCacheImpl")
    private StockService stockService;

    @RequestMapping(value="/amount/{itemId}",method={RequestMethod.GET})
    public Integer amount(@PathVariable("itemId") Integer itemId){
        return stockService.queryAmount(itemId);
    }

    @RequestMapping(value="/buy/{itemId}/{num}", method={RequestMethod.POST,RequestMethod.GET})
    public Boolean buy(@PathVariable("itemId") Integer itemId, @PathVariable("num") Integer num){
        return stockService.decreaseAmount(itemId,num);
    }
}
