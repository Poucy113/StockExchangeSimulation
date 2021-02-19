package org.lcdd.ses.back;

import java.util.Random;

import org.lcdd.ses.SESMain;
import org.lcdd.ses.back.business.Business;
import org.lcdd.ses.frame.SESPopup;
import org.lcdd.ses.frame.SESPopup.PopupType;
import org.lcdd.ses.frame.graph.GraphicLine;
import org.lcdd.ses.frame.graph.GraphicNode;

public class GraphUpdater {

    private double price = 100;
	
    private Thread th;
    
    private Business business;
    
    public GraphUpdater(Business en) {
    	this.business = en;
		th = new Thread(new Runnable() {
			private Random r = new Random();
			private double d;
	        @Override
	        public void run() {
                while (true) {
                    try {
                    	double oldPrice = price;
                    	price = rand();
                    	double newPrice = price;
                    	
                    	if(business.getGraph() != null)
                    		business.getGraph().addLine(new GraphicLine(new GraphicNode((int) oldPrice), new GraphicNode((int) newPrice))).update();
                    	
                        Thread.sleep(r.nextInt(en.getMaxUpdateTime()));
                    } catch (InterruptedException e) {
                    	new SESPopup(SESMain.getFrame(), "SES - Alert", "Une erreur est survenue lors de la generation aléatoire: "+e.getLocalizedMessage(), PopupType.ALERT).onComplete((es) -> {System.exit(0);return true;});
                    }
                }
            }
	        private double rand() {
	        	d = price+((r.nextInt(en.getMin()*(-1) + en.getMax() +1))-(en.getMax() /(r.nextInt(2) +1)));
	        	if(d < -200) {
	        		d += r.nextInt(35);
	        		rand();
        		}else if(d > 200) {
        			d -= r.nextInt(35);
        			rand();
        		}
				return d;
	        }
	    });
		th.setName("GraphUpdater-["+en.getName()+"]");
		th.start();
	}

    public double getPrice() {return UserManager.round(price, 2);}
    public Thread getUpdaterThread() {return th;}
    public Business getBusiness() {return business;}

}
