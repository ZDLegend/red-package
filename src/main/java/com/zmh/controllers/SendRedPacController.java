package com.zmh.controllers;

import com.alibaba.fastjson.JSONObject;
import com.zmh.core.*;
import com.zmh.service.CacheManage;
import com.zmh.service.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送红包控制器
 * Created by Administrator on 2017/7/25.
 */
@RestController
@RequestMapping(value = "/send")
public class SendRedPacController {

    @Autowired
    private RedisManager manager;

    @PostMapping
    public JSONObject sendRedPackage(@RequestBody LeftMoneyPackage moneyPackage) {

        /* 从账户中扣钱 */
        //getMoney(moneyPackage.getRemainMoney());

        /* 建立缓存 */
        GettedPackageUserGroup redPackage = CacheManage.greatRedPackage(moneyPackage.getRemainSize());

        /* 存入redis */
        manager.addRedPackage(redPackage.getPackageID(), moneyPackage);

        /* 触发红包事件 */
        //eventHappend(GettedPackageUserGroup.getPackageID())

        return ResponseMessage.ok(redPackage.getPackageID());
    }
}

