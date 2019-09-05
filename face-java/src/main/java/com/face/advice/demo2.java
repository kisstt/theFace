package com.face.advice;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 悲观锁
 */
public class demo2 {

    BigDecimal sumMoney = new BigDecimal(1000);
    BigDecimal remainMoney = new BigDecimal(sumMoney.doubleValue());

    BigDecimal min = new BigDecimal(0.01);
    int sum_people = 20;
    int max_people = sum_people;

    Lock lock = new ReentrantLock();
    int i;

    boolean takeRedPackage() {
        lock.lock();
        i++;
        if (max_people == 0) {
            System.out.println(i + "抢红包失败");
            lock.unlock();
            return false;
        }
        BigDecimal baseMoney = new BigDecimal(remainMoney.doubleValue() / max_people).setScale(2, BigDecimal.ROUND_HALF_UP);
        baseMoney = baseMoney.multiply(new BigDecimal(2));
        double random = Math.random();
        BigDecimal money = baseMoney.multiply(new BigDecimal(random));
        BigDecimal maxMoney = remainMoney.add(new BigDecimal(-0.01 * (max_people - 1)))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        if (max_people == 1) {
            money = remainMoney;
        } else {
            if (money.compareTo(min) <= -1) {
                money = new BigDecimal(0.01);
            }
            if (money.compareTo(maxMoney) >= 1) {
                money = maxMoney;
            }
        }
        remainMoney = remainMoney.add(money.multiply(new BigDecimal(-1)));
        max_people--;
        System.out.println(i + "抢到了" + money.toString().substring(0, money.toString().indexOf(".") + 3) +
                ",还剩" + remainMoney.toString().substring(0, remainMoney.toString().indexOf(".") == -1 ? 1 : remainMoney.toString().indexOf(".") + 3) +
                ",红包还剩" + max_people + "个");
        lock.unlock();
        return true;
    }

    public static void main(String[] args) {
        demo2 demo2 = new demo2();
        for (int i = 0; i < demo2.sum_people; i++) {
            new Thread() {
                @Override
                public void run() {
                    demo2.takeRedPackage();
                }
            }.start();
        }
    }
}
