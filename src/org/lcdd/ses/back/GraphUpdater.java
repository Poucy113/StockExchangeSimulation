package org.lcdd.ses.back;

import java.lang.*;

public class GraphUpdater{

    private static int buyPrice = 100;
    private static int sellPrice = 90;

    public static int getBuyPrice() {
        return buyPrice;
    }
    public static int getSellPrice() {return sellPrice;}


    Thread th = new Thread(new Runnable() {
        @Override
        public void run() {
            int max = buyPrice + 10;
            int min = buyPrice - 10;
            int newPrice = (int) ((max-min)*Math.random()) + min;
            buyPrice = newPrice;
            System.out.println(buyPrice);
            int maxSell = sellPrice + 10;
            int minSell = sellPrice - 10;
            int newPriceSell = (int) ((maxSell-minSell)*Math.random()) + minSell;
            sellPrice = newPriceSell;
            System.out.println(sellPrice);
            try {
                th.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });


}
