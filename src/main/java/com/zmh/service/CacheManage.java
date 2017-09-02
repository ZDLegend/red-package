package com.zmh.service;

import com.zmh.core.GettedPackageUserGroup;
import com.zmh.core.utils.WebUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存管理
 * Created by Administrator on 2017/7/25.
 */
public class CacheManage {

    /**
     * 红包ID - 抢红包列表
     */
    private static ConcurrentHashMap<String, GettedPackageUserGroup> datas;

    public static GettedPackageUserGroup greatRedPackage(int size){
        GettedPackageUserGroup redPackage = new GettedPackageUserGroup(size);
        datas.put(redPackage.getPackageID(), redPackage);
        return redPackage;
    }

    public static void removeOverdueRedPackage(String redPackId){
        if(datas.containsKey(redPackId)){
            datas.remove(redPackId);
        }
    }

    public static boolean checkUser(String packageId){
        String ip = WebUtil.getIpAddr();
        GettedPackageUserGroup redPackage = datas.get(packageId);
        return redPackage.check(ip);
    }

    public static boolean addUser(String packageId){
        String ip = WebUtil.getIpAddr();
        GettedPackageUserGroup redPackage = datas.get(packageId);
        return redPackage.addList(ip);
    }

    public static void deleteUser(String packageId){
        String ip = WebUtil.getIpAddr();
        GettedPackageUserGroup redPackage = datas.get(packageId);
        redPackage.deleteUser(ip);
    }
}
