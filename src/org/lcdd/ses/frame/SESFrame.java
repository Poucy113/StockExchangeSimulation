package org.lcdd.ses.frame;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.lcdd.ses.frame.SESPopup.PopupType;
import org.lcdd.ses.frame.graph.SESGraph;

public class SESFrame extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	
	private SESGraph graph;
	private SESMenu menu;
	private List<SESPopup> popups = new ArrayList<>();
	
	public SESFrame() {
		SESPopup login = new SESPopup(this, "SES - Login", "Veuillez entrer votre nul d'utilisateur:", PopupType.INPUT_STRING);
		popups.add(login);
		
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		super.setBounds(0, 0, (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2), (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));
		super.setType(Type.NORMAL);
		super.setEnabled(true);
		super.setVisible(true);
		super.getContentPane().setBackground(Color.GRAY);
		super.addWindowListener(this);
		
		this.menu = new SESMenu(this);
		this.graph = new SESGraph(this);
		
		super.setMenuBar(menu);
		
		super.add(graph);
	}
	
	public SESGraph getGraph() {return graph;}
	public SESMenu getMenu() {return menu;}
	
	public List<SESPopup> getPopups() {return popups;}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(e.getID());
	}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

}
