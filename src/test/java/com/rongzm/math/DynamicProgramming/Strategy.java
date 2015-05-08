package com.rongzm.math.DynamicProgramming;

/**
 * Created by rongzhiming on 2015/5/7.
 */
public class Strategy {
    static double percent = 0.1;

    Double init;
    double threshold;
    int time = 0;

    public double run(double amount){
        time++;
        System.out.printf("[time]%s\\t[amount]%s\n",time,amount);
        if(init == null){
            init = amount;
            threshold = 0.1 * init;
        }
        if(amount <= threshold){
            return amount;
        }else if(time == 10){
            return amount;
        }else{
            double nowAmount = 1.1 * amount;
            return percent * nowAmount + run((1-percent) * nowAmount);
        }
    }

    public static void main(String[] args) {
        Strategy s = new Strategy();
        double result = s.run(1000);
        System.out.println(result);
    }
}
