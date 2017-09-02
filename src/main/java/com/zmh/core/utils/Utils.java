package com.zmh.core.utils;

import com.zmh.core.LeftMoneyPackage;

import java.util.Random;

/**
 * Created by Administrator on 2017/7/25.
 */
public class Utils {

    /**
     * 获取随机红包金额
     */
    public static double getRandomMoney(LeftMoneyPackage moneyPackage) {
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        int remainSize = moneyPackage.getRemainSize();
        if (remainSize == 1) {
            moneyPackage.setRemainSize( remainSize - 1);
            return (double) Math.round(moneyPackage.getRemainMoney() * 100) / 100;
        }

        if(remainSize == 0){
            return 0;
        }

        double remainMoney = moneyPackage.getRemainMoney();
        Random r = new Random();
        double min   = 0.01;
        double max   = remainMoney / remainSize * 2;
        double money = r.nextDouble() * max;
        money = money <= min ? 0.01: money;
        money = Math.floor(money * 100) / 100;
        moneyPackage.setRemainSize( remainSize - 1);
        moneyPackage.setRemainMoney(remainMoney - money);
        return money;
    }

    public static void main(String[] args){

        LeftMoneyPackage moneyPackage = new LeftMoneyPackage();
        for(int i = 0; i < 10; i++){
            System.out.println(getRandomMoney(moneyPackage));
            System.out.println(moneyPackage.getRemainMoney());
            System.out.println(moneyPackage.getRemainSize());
            System.out.println("-----------------------------------");
        }
    }
}
