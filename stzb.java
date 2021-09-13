/*
 * @Date: 2021-09-06 15:47:22
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-09-07 16:03:38
 * @FilePath: \note\stzb.java
 */

import java.util.Random;

public class stzb {
    public static void main(String[] args) {
        // 战法释放概率
        Double probability = 0.5;
        // 模拟对战次数
        int testTimes = 1000;
        // 战法释放总次数
        int count = 0;
        Double allCount = 0.0;
        for (int i = 0; i < 10; i++) {
            allCount+=test(probability, testTimes, count, new Random());
        }
        Double res=Double.valueOf(allCount/10000);
        System.out.print("场均释放:"+res);
    }

    private static int test(Double probability, int testTimes, int count, Random random) {
        int thisTime = 0;
        for (int i = 0; i < 8; i++) {
            // 如果战法释放了则释放次数加1，并跳过下一回合
            if (random.nextInt(100) < probability * 100) {
                thisTime += 1;
                i++;
            } else {
                // 如果第7回合没有释放战法，直接跳出本次模拟
                if (i == 6) {
                    break;
                }
            }
        }
        testTimes--;
        int nextTimes = 0;
        if (testTimes != 0) {
            nextTimes = test(probability, testTimes, count, random);
        }
        return thisTime + nextTimes;
    }

}
