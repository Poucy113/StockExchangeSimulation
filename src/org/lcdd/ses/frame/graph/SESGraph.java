package org.lcdd.ses.frame.graph;

import javax.swing.JComponent;

import org.lcdd.ses.frame.SESFrame;
import org.lcdd.ses.utils.SESServer;

public class SESGraph extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private SESServer server;
	private SESFrame frame;
	
	public SESGraph(SESServer server, SESFrame frame) {
		this.server = server;
		this.frame = frame;
		
		// http://www.lirmm.fr/~leclere/enseignements/TER/2008/Rapport/18.pdf // 3.3
		
		//Rectangle rect = (Rectangle) frame.getBounds().clone();
		//rect.setRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		super.setBounds(frame.getBounds() /*rect*/);
	}
	
	public SESServer getServer() {return server;}
	public SESFrame getFrame() {return frame;}

}
