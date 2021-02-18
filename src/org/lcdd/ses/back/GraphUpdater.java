package org.lcdd.ses.back;

import java.util.Random;

import org.lcdd.ses.SESMain;
import org.lcdd.ses.frame.SESPopup;
import org.lcdd.ses.frame.SESPopup.PopupType;
import org.lcdd.ses.frame.graph.GraphicLine;
import org.lcdd.ses.frame.graph.GraphicNode;

public class GraphUpdater{

    private static double price = 100;
	
    private static Thread th;
    
    public GraphUpdater() {
		th = new Thread(new Runnable() {
			private Random r = new Random();
	        @Override
	        public void run() {
                while (true) {
                    try {
                    	double oldPrice = price;
                    	price = rand();
                    	double newPrice = price;
                    	
                    	SESMain.getFrame().getGraph().addLine(new GraphicLine(new GraphicNode((int) oldPrice), new GraphicNode((int) newPrice))).update();
                    	
                        Thread.sleep(r.nextInt(1250));
                    } catch (InterruptedException e) {
                    	new SESPopup(null, "SES - Alert", "Une erreur est survenue lors de la generation aléatoire: "+e.getLocalizedMessage(), PopupType.ALERT).onComplete((es) -> System.exit(0));
                    }
                }
            }
	        private double rand() {
	        	double d = price+(r.nextInt((int) Math.round(20 +1))-10) - (Math.random()*0.5);
        		if(d < -350) {
        			d += (d / 35);
        			rand();
        		}else if(d > 350) {
        			d -= (d / 35);
        			rand();
        		}else
        			return d;
				return d;
	        }
	    });
		th.setName("GraphUpdater");
		th.start();
	}

    //public static int getBuyPrice() {return buyPrice;}
    //public static int getSellPrice() {return sellPrice;}
    public static double getPrice() {return price;}
    public static Thread getUpdaterThread() {return th;}

}
