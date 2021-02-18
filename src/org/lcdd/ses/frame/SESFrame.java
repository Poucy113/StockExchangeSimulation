package org.lcdd.ses.frame;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.lcdd.ses.frame.graph.SESGraph;

public class SESFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private SESGraph graph;
	private SESMenu menu;
	
	public SESFrame() {
		super.setBounds(0, 0, (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2), (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));
		super.setType(Type.NORMAL);
		super.setEnabled(true);
		super.setVisible(true);
		super.getContentPane().setBackground(Color.GRAY);
		
		this.menu = new SESMenu(this);
		this.graph = new SESGraph(this);
		
		super.setMenuBar(menu);
		
		super.add(graph);
	}
	
	public SESGraph getGraph() {return graph;}
	public SESMenu getMenu() {return menu;}

}
