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
	        @Override
	        public void run() {
                while (true) {
                    try {
                    	double oldPrice = price;
                    	price = rand();
                    	double newPrice = price;
                    	
                    	if(business.getGraph() != null)
                    		business.getGraph().addLine(new GraphicLine(new GraphicNode((int) oldPrice), new GraphicNode((int) newPrice))).update();
                    	
                    	if(SESMain.getFrame() != null)
                    		if(SESMain.getFrame().getGraph() != null)
                        		if(SESMain.getFrame().getGraph().getBusiness().equals(business)) {
                            		SESMain.getFrame().updateBusinessPanel();
                            		SESMain.getFrame().updateButtons();
                        		}
                    	
                        Thread.sleep(r.nextInt(en.getMaxUpdateTime()));
                    } catch (InterruptedException e) {
                    	new SESPopup(SESMain.getFrame(), "SES - Alert", "Une erreur est survenue lors de la generation aléatoire: "+e.getLocalizedMessage(), PopupType.ALERT).onComplete((es) -> {System.exit(0);return true;});
                    }
                }
            }
	    });
		th.setName("GraphUpdater-["+en.getName()+"]");
		th.start();
	}
    
    private Random r = new Random();
    private double d;
    public double rand() {
    	d = price+((business.getMin()*(-1)+business.getMax() +1)) *r.nextDouble() -(business.getMin()*(-1));
    	if(d < -200) {
    		d += r.nextInt(35);
    		rand();
		}else if(d > 200) {
			d -= r.nextInt(35);
			rand();
		}
		return d;
    }

    public void setPrice(double price) {this.price = price;}
    public double getPrice() {return UserManager.round(price, 2);}
    public Thread getUpdaterThread() {return th;}
    public Business getBusiness() {return business;}

}
