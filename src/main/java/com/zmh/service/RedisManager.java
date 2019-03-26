package com.zmh.service;

import com.zmh.core.LeftMoneyPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * 数据库操作
 * Created by Administrator on 2017/7/25.
 */
@Service
public class RedisManager {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ExecutorService pool;

    @PostConstruct
    public void init(){
       /* 开启事务 */
        stringRedisTemplate.setEnableTransactionSupport(true);
        pool = Executors.newCachedThreadPool();
    }

    @PreDestroy
    public void uninit(){
        pool.shutdown();
    }

    /**
     * 发红包
     */
    public void addRedPackage(String id, LeftMoneyPackage leftMoneyPackage){
        /*
         * 一天过期
         */
        long timeOut = 1;
        stringRedisTemplate.opsForValue().set(id, leftMoneyPackage.toJson(), TimeUnit.DAYS.toDays(timeOut));
    }

    /**
     * 拆红包
     */
    public double modifyPacakage(String id){

        RedPackageTask task = new RedPackageTask();
        task.setId(id);
        boolean isOk = false;

       /* 乐观锁拆红包处理，不断去对比版本号，直到拆成功或者数据库中的红包剩余金钱为0为止 */
        while (!isOk) {

            Future<Object> future = pool.submit(() -> stringRedisTemplate.execute(task));
            try {
                if (future.get() == null) {
                    continue;
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("领取红包出错");
            }

            isOk = true;
        }

        CacheManage.deleteUser(id);
        return task.getMoney();
    }
}
