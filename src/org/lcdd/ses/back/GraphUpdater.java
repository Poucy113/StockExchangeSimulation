package org.lcdd.ses.back;

public class GraphUpdater{

    private static int buyPrice = 100;
    private static int sellPrice = 90;
    
    private static Thread th;
    
    public GraphUpdater() {
		th = new Thread(new Runnable() {
	        @Override
	        public void run() {
                while (true) {
                    try {
                        int max = buyPrice + 10;
                        int min = buyPrice - 10;
                        int newPrice = (int) ((max - min) * Math.random()) + min;
                        buyPrice = newPrice;
                        System.out.println(buyPrice);
                        int maxSell = sellPrice + 10;
                        int minSell = sellPrice - 10;
                        int newPriceSell = (int) ((maxSell - minSell) * Math.random()) + minSell;
                        sellPrice = newPriceSell;
                        System.out.println(sellPrice);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
	    });
		th.setName("GraphUpdater");
		th.start();
	}

    public static int getBuyPrice() {
        return buyPrice;
    }
    public static int getSellPrice() {return sellPrice;}

    public static Thread getUpdaterThread() {return th;}

}
