package org.lcdd.ses.frame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.lcdd.ses.SESMain;
import org.lcdd.ses.back.business.Business;
import org.lcdd.ses.frame.SESFrame;
import org.lcdd.ses.frame.menu.business.MainBusinessMenuListener;

public class SESMenu extends JMenuBar {
	private static final long serialVersionUID = 1L;

	public static final int DELETE  = -1;
	public static final int CREATE  =  0;
	
	private SESFrame frame;
	private JMenu business;
	
    public SESMenu(SESFrame frame) {
    	this.frame = frame;
    	
    	business = business();
    	super.add(business);
    	
    	//super.add(options());
    }
    
	public class ChangeBusinessMenuListener implements ActionListener {
    	private Business business;
    	public ChangeBusinessMenuListener(Business _b) {
			this.business = _b;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!business.isStarted())
				business.start(frame);
			business.show(frame);
		}
	}
    
    public void resizeFrame() {
    	super.remove(business);
    	business = business();
    	super.add(business);
    	//options();
    	
    	//for(int i = 0; i < super.getMenuCount(); i++)
    	//	revalid(super.getMenu(i));
    	super.revalidate();
    	super.repaint();
	}
    @SuppressWarnings("deprecation")
	public void revalid(JMenu m) {
    	//m.getPopupMenu().pack();
    	m.revalidate();
    	m.repaint();
    	for(int i = 0; i < m.getItemCount(); i++) {
    		if(m.getItem(i) instanceof JMenu) {
    			System.out.println("b: "+m.getItem(i).getName()+" "+m.getItem(i).getLabel());
    			revalid((JMenu) m.getItem(i));
    			m.getItem(i).revalidate();
    			m.getItem(i).repaint();
    		}else if(m.getItem(i) instanceof JMenuItem) {
    			System.out.println("a: "+m.getItem(i).getName()+" "+m.getItem(i).getLabel());
    			m.getItem(i).revalidate();
    			m.getItem(i).repaint();
    		}
    	}
	}
	private JMenu options() {
    	MenuConstructor optionsMenu = new MenuConstructor("Options", false);
    	
    	return optionsMenu.build();
    }
    private JMenu business() {
    	MenuConstructor businessMenu = new MenuConstructor("Entreprises", false);
    	MenuConstructor changeMenu = new MenuConstructor("Changer", false);
    	
    	/*changeMenu = */changeMenu.addItem(SESMain.getFrame().getbManager().getBaseBusiness().getName()).addActionListener(new ChangeBusinessMenuListener(SESMain.getFrame().getbManager().getBaseBusiness())).build().addSeparator();
    	for(Business b : SESMain.getFrame().getbManager().getBusinesses())
    		/*changeMenu = */changeMenu.addItem(b.getName()).addActionListener(new ChangeBusinessMenuListener(b)).build();
    	
    	/*mainMenu = */businessMenu.add(changeMenu.build())
		.addItem("Créer")
			.addActionListener(new MainBusinessMenuListener(frame, CREATE))
		.build()
		.addItem("Supprimer")
			.addActionListener(new MainBusinessMenuListener(frame, DELETE))
		.build();
    	
    	return businessMenu.build();
	}

}
