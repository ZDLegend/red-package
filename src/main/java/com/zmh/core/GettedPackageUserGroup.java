package com.zmh.core;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * 抢到 {@link packageID} 红包的用户组
 * Created by Administrator on 2017/7/25.
 */
public class GettedPackageUserGroup {

    /**
     * 红包ID
     */
    private String packageID;

    /**
     * 抢到红包的用户列表（客户端IP地址）
     */
    private List<String> list;

    /**
     * 红包数量
     */
    private int size;

    /**
     * 初始化组
     *
     * @param size 红包个数
     */
    public GettedPackageUserGroup(int size){
        packageID = UUID.randomUUID().toString();
        list = new LinkedList<>();
        this.size = size;
    }

    /**
     * 添加用户到列表中,当列表长度大于红包数量时红包已抢完
     */
    public boolean addList(String str){
        if(list.size() < size){
            list.add(str);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 检查该用户是否在队列内，不在无法拆红包
     */
    public boolean check(String ip){
        return list.contains(ip);
    }

    /**
     * 将拆过红包的用户去除，防止可以拆两次
     */
    public void deleteUser(String ip){
        list.remove(ip);
    }

    public String getPackageID() {
        return packageID;
    }
}
