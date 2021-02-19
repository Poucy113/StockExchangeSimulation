package org.lcdd.ses.frame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.lcdd.ses.SESMain;
import org.lcdd.ses.back.business.Business;
import org.lcdd.ses.frame.SESFrame;

public class SESMenu extends JMenuBar {
	private static final long serialVersionUID = 1L;

	public static final int DELETE  = -1;
	public static final int CREATE  =  0;
	public static final int CHANGE  =  1;
	
    public SESMenu(SESFrame frame) {
    	super.add(build());
    }
    
	public class ChangeBusinessMenuListener implements ActionListener {
    	private Business business;
    	public ChangeBusinessMenuListener(Business _b) {
			this.business = _b;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!business.isStarted())
				business.start(SESMain.getFrame());
			business.show(SESMain.getFrame());
		}
	}
    
    public class MainBusinessMenuListener implements ActionListener {
		private int button;
		public MainBusinessMenuListener(int create) {
			this.button = create;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (button) {
			case DELETE:
				deleteFrame();
				break;
			case CREATE:
				createFrame();
				break;
			case CHANGE:
				
				break;
			}
		}
		
		public void createFrame() {
			
		}
		
		public void deleteFrame() {
			
		}
	}
    
    public void resizeFrame() {
    	build();
	}
    private JMenu build() {
    	MenuConstructor changeMenu = new MenuConstructor("Changer", false);
    	
    	changeMenu = changeMenu.addItem(SESMain.getFrame().getbManager().getBaseBusiness().getName()).addActionListener(new ChangeBusinessMenuListener(SESMain.getFrame().getbManager().getBaseBusiness())).build().addSeparator();
    	for(Business b : SESMain.getFrame().getbManager().getBusinesses())
    		changeMenu = changeMenu.addItem(b.getName()).addActionListener(new ChangeBusinessMenuListener(b)).build();
    	
    	return 
			new MenuConstructor("Entreprises", false)
				.add(
						changeMenu
						.build()
				)
				.addItem("Créer")
					.addActionListener(new MainBusinessMenuListener(CREATE))
				.build()
				.addItem("Supprimer")
					.addActionListener(new MainBusinessMenuListener(DELETE))
				.build()
			.build();
	}

}
