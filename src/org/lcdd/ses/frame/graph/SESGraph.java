package org.lcdd.ses.frame.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;

import org.lcdd.ses.frame.SESFrame;

public class SESGraph extends JDesktopPane implements ComponentListener {
	// http://www.lirmm.fr/~leclere/enseignements/TER/2008/Rapport/18.pdf // 3.3
    private static final long serialVersionUID = 1L;

    private SESFrame frame;
    
    private List<GraphicLine> lines = new ArrayList<>();
    
    public SESGraph(SESFrame frame) {
    	super();
        this.frame = frame;

        resizeFrame();
        super.setBackground(Color.WHITE);
        super.setVisible(true);
        super.setIgnoreRepaint(false);
        frame.addComponentListener(this);
    }
    
    @Override
    public void paint(Graphics g) {
    	super.paint(g);
    	
    	Graphics2D g2 = (Graphics2D) g;
    	
    	g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLACK);
		g2.drawLine(0, super.getHeight()/2, super.getWidth(), super.getHeight()/2);
		
		for(int i = 0; i <= 25; i++)
			g2.drawLine((super.getWidth() / 25)*i, 10+super.getHeight()/2, (super.getWidth() / 25)*i, super.getHeight()/2-10);
	
    	g2.setStroke(new BasicStroke(3));
    	for(int i = 0; i < lines.size(); i++) {
    		GraphicLine line = lines.get(i);
    		g2.setColor(line.getType().getColor());
    		if(lines.get(0).equals(line))
    			g2.drawLine(
    					(int) (super.getWidth() / 25)*i, ((int) line.getN1().getY()+(super.getHeight()/2)),
            			(int) ((super.getWidth() / 25)*(i+1)), ((int) line.getN2().getY()+(super.getHeight()/2))
            	);
    		else
    			g2.drawLine(
        				(int) (super.getWidth() / 25)*i, ((int) lines.get(i-1).getN2().getY()+(super.getHeight()/2)),
            			(int) (super.getWidth() / 25)*(i+1), ((int) line.getN2().getY()+(super.getHeight()/2))
            	);
    	}
    }
    
    public void addLine(GraphicLine line) {
    	System.out.println("allig");
    	lines.add(line);
    }
    
    private void resizeFrame() {
    	Rectangle rect = (Rectangle) frame.getBounds().clone();
        rect.setBounds(
        		(int) ((rect.getWidth() / 10)*2),
        		0,
        		(int) ((int) rect.getWidth()-(rect.getWidth() / 10)*2),
        		(int) ((int) rect.getHeight()-((rect.getHeight() / 10)*2)));
        super.setBounds(rect);
	}
    public void update() {repaint();}

	public SESFrame getFrame() {return frame;}

	@Override
	public void componentResized(ComponentEvent e) {
		resizeFrame();
	}

	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
	@Override
	public void componentHidden(ComponentEvent e) {}

}
