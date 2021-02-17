package org.lcdd.ses.frame;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.lcdd.ses.utils.SESServer;

public class SESFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private SESServer server;
	private SESGraph graph;
	private SESMenu menu;
	
	public SESFrame(SESServer server) {
		this.server = server;
		
		super.setBounds(0, 0, (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2), (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));
		super.setType(Type.NORMAL);
		super.setEnabled(true);
		super.setVisible(true);
		super.getContentPane().setBackground(Color.GRAY);
		
		this.menu = new SESMenu(this);
		this.graph = new SESGraph(server, this);
		
		super.setMenuBar(menu);
		
		super.add(graph);
	}
	
	public SESServer getServer() {return server;}
	public SESGraph getGraph() {return graph;}
	public SESMenu getMenu() {return menu;}

}
