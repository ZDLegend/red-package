package com.zmh.service;

import com.alibaba.fastjson.JSON;
import com.zmh.core.LeftMoneyPackage;
import com.zmh.core.utils.Utils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;

/**
 * 拆红包任务
 * Created by Administrator on 2017/7/26.
 */
public class RedPackageTask implements SessionCallback<List<String>> {

    /**
     * 拆开红包的钱
     */
    private double money;

    /**
     * 红包ID
     */
    private String id;

    @Override
    public List<String> execute(RedisOperations operations) throws DataAccessException {

        /* 监控该 key - value */
        operations.watch(id);

        /* 获取红包对象 */
        String origin = (String) operations.opsForValue().get(id);
        LeftMoneyPackage moneyPackage = JSON.parseObject(origin, LeftMoneyPackage.class);

        /* 拆红包 */
        money = Utils.getRandomMoney(moneyPackage);

        /* 修改数据库红包 */
        operations.multi();
        operations.opsForValue().set(id, moneyPackage.toJson());

        /* 执行事务并返回 */
        return operations.exec();
    }

    public double getMoney() {
        return money;
    }

    public void setId(String id) {
        this.id = id;
    }
}
