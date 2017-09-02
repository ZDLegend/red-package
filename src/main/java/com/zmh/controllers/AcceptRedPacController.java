package com.zmh.controllers;

import com.alibaba.fastjson.JSONObject;
import com.zmh.core.*;
import com.zmh.service.CacheManage;
import com.zmh.service.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接手红包控制器
 * @apiNote 请红包分两步，抢和拆
 * Created by Administrator on 2017/7/25.
 */
@RestController
@RequestMapping(value = "/accept")
public class AcceptRedPacController {

    @Autowired
    private RedisManager manager;

    @GetMapping(value = "get/{id}")
    public JSONObject getRedPackage(@PathVariable("id") String id){

       /* 加入缓存队列 */
        boolean ok = CacheManage.addUser(id);
        if(!ok){
            return ResponseMessage.error("红包已抢完");
        }
        return ResponseMessage.ok();
    }

    @GetMapping(value = "open/{id}")
    public JSONObject openRedPackage(@PathVariable("id") String id){

        /* 检查缓存队列中是否有该用户 */
        if(!CacheManage.checkUser(id)){
            return ResponseMessage.error("没有抢红包资格");
        }

        /* 拆红包并修改数据库红包，红包抢完后移除缓存中的红包ID */
        double money = manager.modifyPacakage(id);
        if(money == 0.0){
            CacheManage.removeOverdueRedPackage(id);
            ResponseMessage.error("红包已抢完");
        }

        /* 将钱存入账户 */
        //saveMoney(money);

        return ResponseMessage.ok(money);
    }
}
