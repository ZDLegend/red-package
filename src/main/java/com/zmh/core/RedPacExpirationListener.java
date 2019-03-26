package com.zmh.core;

import com.zmh.service.CacheManage;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 数据库红包过期处理
 * Created by Administrator on 2017/7/26.
 */
@Service
public class RedPacExpirationListener extends KeyExpirationEventMessageListener {

    public RedPacExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    protected void doHandleMessage(Message message){
        String id = Arrays.toString(message.getBody()).trim();
        CacheManage.removeOverdueRedPackage(id);
    }

}
