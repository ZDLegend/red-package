package com.zmh.core;


import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2017/7/25.
 */
public class LeftMoneyPackage {

    private int remainSize ;
    private double remainMoney;

    public LeftMoneyPackage(){
        remainSize = 10;
        remainMoney = 200;
    }

    public LeftMoneyPackage(int size, double money){
        remainMoney = money;
        remainSize = size;
    }

    public int getRemainSize() {
        return remainSize;
    }

    public synchronized void setRemainSize(int remainSize) {
        this.remainSize = remainSize;
    }

    public double getRemainMoney() {
        return remainMoney;
    }

    public synchronized void setRemainMoney(double remainMoney) {
        this.remainMoney = remainMoney;
    }

    public String toJson(){
        return JSON.toJSONString(this);
    }
}
