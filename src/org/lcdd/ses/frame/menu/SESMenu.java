package org.lcdd.ses.frame.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.lcdd.ses.SESMain;
import org.lcdd.ses.back.business.Business;
import org.lcdd.ses.frame.SESFrame;
import org.lcdd.ses.frame.menu.business.ChangeBusinessMenuListener;
import org.lcdd.ses.frame.menu.business.MainBusinessMenuListener;
import org.lcdd.ses.frame.menu.options.MainOptionsMenuListener;

public class SESMenu extends JMenuBar {
	private static final long serialVersionUID     = 1L;

	public static final int BUSINESS_DELETE        = -1;
	public static final int BUSINESS_CREATE        =  0;
	
	public static final int OPTIONS_GRAPH_INTERVAL = 0;
	public static final int OPTIONS_CLEAR_POPUPS   = 1;
	
	private SESFrame frame;
	
	private JMenu business;
	private JMenu options;
	
    public SESMenu(SESFrame frame) {
    	this.frame = frame;
    	
    	resizeFrame();
    }
    
    public void resizeFrame() {
    	super.removeAll();
    	
    	business();
    	options();
    	
    	super.revalidate();
    	super.repaint();
	}
	private void options() {
    	MenuConstructor optionsMenu = new MenuConstructor("Options", false)
    	.addItem("Intervale du graphe")
    		.addActionListener(new MainOptionsMenuListener(frame, OPTIONS_GRAPH_INTERVAL))
    	.build()
    	.addItem("Fermer les popups")
    		.addActionListener(new MainOptionsMenuListener(frame, OPTIONS_CLEAR_POPUPS))
    	.build();
    	
    	options = optionsMenu.build();
    	super.add(options);
    }
    private void business() {
    	MenuConstructor businessMenu = new MenuConstructor("Entreprises", false);
    	MenuConstructor changeMenu = new MenuConstructor("Changer", false);
    	
    	changeMenu.addItem(SESMain.getFrame().getbManager().getBaseBusiness().getName()).addActionListener(new ChangeBusinessMenuListener(frame, SESMain.getFrame().getbManager().getBaseBusiness())).setIcon(SESMain.getFrame().getbManager().getBaseBusiness().getIcon()).build().addSeparator();
    	for(Business b : SESMain.getFrame().getbManager().getBusinesses())
    		changeMenu.addItem(b.getName()).addActionListener(new ChangeBusinessMenuListener(frame, b)).setIcon(b.getIcon()).build();
    	
    	businessMenu.add(changeMenu.build())
		.addItem("Créer")
			.addActionListener(new MainBusinessMenuListener(frame, BUSINESS_CREATE))
		.build()
		.addItem("Supprimer")
			.addActionListener(new MainBusinessMenuListener(frame, BUSINESS_DELETE))
		.build();
    	
    	business = businessMenu.build();
    	super.add(business);
	}

}
