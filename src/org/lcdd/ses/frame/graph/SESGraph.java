package org.lcdd.ses.frame.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;

import org.lcdd.ses.frame.SESFrame;
import org.lcdd.ses.frame.graph.GraphicLine.GraphicLineType;

public class SESGraph extends JDesktopPane {
	private static final long serialVersionUID = 1L;

    private SESFrame frame;
    
    private List<GraphicLine> lines = new ArrayList<>();
    
    public SESGraph(SESFrame frame) {
    	super();
        this.frame = frame;

        super.setBackground(Color.WHITE);
        super.setVisible(true);
        super.setIgnoreRepaint(false);
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
    		
    		if(line.getN1().getY() > line.getN2().getY())
    			line.setType(GraphicLineType.POSITIV);
    		if(line.getN1().getY() == line.getN2().getY())
    			line.setType(GraphicLineType.NEUTRAL);
    		if(line.getN1().getY() < line.getN2().getY())
    			line.setType(GraphicLineType.NEGATIV);
    		
    		g2.setColor(line.getType().getColor());
    		g2.drawLine(
					(int) (super.getWidth() / 25)*i, (lines.get(0).equals(line) ? ((int) line.getN1().getY()+(super.getHeight()/2)) : lines.get(i-1).getN2().getY()+(super.getHeight()/2)),
        			(int) ((super.getWidth() / 25)*(i+1)), ((int) line.getN2().getY()+(super.getHeight()/2))
        	);
    	}
    }
    
    public SESGraph addLine(GraphicLine line) {
    	if(lines.size() >= 1)
    		line.setN1(new GraphicNode(lines.get(lines.size()-1).getN2().getY(), true));
    	lines.add(line);
    	if(lines.size() > 25)
    		lines.remove(0);
    	return this;
    }
    
    public void resizeFrame() {
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
	public List<GraphicLine> getLines() {return lines;}

}
