package org.lcdd.ses.frame.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JLabel;

import org.lcdd.ses.back.business.Business;
import org.lcdd.ses.frame.SESFrame;

public class SESGraph extends JDesktopPane {
	private static final long serialVersionUID = 1L;

	private static int interval = 25;
	
    private SESFrame frame;
    private Business business;
    
    private JLabel act = new JLabel("0.0 €");
    
    private List<GraphicLine> lines = new ArrayList<>();
    
    public SESGraph(SESFrame frame, Business business) {
    	super();
        this.frame = frame;
        this.business = business;
        
        super.setBackground(Color.WHITE);
        super.setVisible(true);
        super.setIgnoreRepaint(false);
    }
    
    @Override
    public void paint(Graphics g) {
    	super.paint(g);
    	
    	Graphics2D g2 = (Graphics2D) g;
    	
    	g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.BLACK);
		for(GraphicLine l1 : Arrays.asList(
					new GraphicLine(new GraphicNode(200), new GraphicNode(200)),
					new GraphicLine(new GraphicNode(100), new GraphicNode(100)),
					new GraphicLine(new GraphicNode(0), new GraphicNode(0)),
					new GraphicLine(new GraphicNode(-100), new GraphicNode(-100)),
					new GraphicLine(new GraphicNode(-200), new GraphicNode(-200))
				)) {
			g2.drawLine(
					0, l1.getN1().getY()+(super.getHeight()/2),
					super.getWidth(), l1.getN2().getY()+(super.getHeight()/2)
        	);
			g2.drawString((l1.getN1().getY()/100)+"", 5, ((l1.getN1().getY()+2)*(-1))+(super.getHeight()/2));
		}
			
		for(int i = 0; i <= interval; i++)
			//g2.draw(new Line2D.Double(((double) (super.getWidth() / interval))*i, 10+super.getHeight()/2, ((double) (super.getWidth() / interval))*i, super.getHeight()/2-10));
			g2.drawLine((super.getWidth() / interval)*i, 10+super.getHeight()/2, (super.getWidth() / interval)*i, super.getHeight()/2-10);
	
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
    		g2.draw(
    				new Line2D.Double(
    						(double) (super.getWidth() / interval)*i, (double) (lines.get(0).equals(line) ? line.getN1().getY()+(super.getHeight()/2) : lines.get(i-1).getN2().getY()+(super.getHeight()/2)),
    	        			(double) ((super.getWidth() / interval)*(i+1)), ((double) line.getN2().getY()+(super.getHeight()/2))
    				)
    		);
    		/*g2.drawLine(
					(int) (super.getWidth() / interval)*i, (lines.get(0).equals(line) ? ((int) line.getN1().getY()+(super.getHeight()/2)) : lines.get(i-1).getN2().getY()+(super.getHeight()/2)),
        			(int) ((super.getWidth() / interval)*(i+1)), ((int) line.getN2().getY()+(super.getHeight()/2))
        	);*/
    	}
    }
    
    public SESGraph addLine(GraphicLine line) {
    	if(lines.size() >= 1)
    		line.setN1(new GraphicNode(lines.get(lines.size()-1).getN2().getY(), true));
    	lines.add(line);
    	if(lines.size() > interval)
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

    public int getInterval() {return interval;}
    public void setInterval(int interval) {SESGraph.interval = interval;}
	public SESFrame getFrame() {return frame;}
	public List<GraphicLine> getLines() {return lines;}
	public Business getBusiness() {return business;}
	public void setBusiness(Business business) {this.business = business;}
	public JLabel getAct() {return act;}

}
