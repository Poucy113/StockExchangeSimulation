package org.lcdd.ses.frame.graph;

import java.awt.Color;

import org.lcdd.ses.SESMain;

public class GraphicLine {
	
	private long time;
	private GraphicNode n1, n2;
	private GraphicLineType type;
	
	public GraphicLine(GraphicNode n1, GraphicNode n2, GraphicLineType type) {
		this.type = type;
		this.n1 = n1;
		this.n2 = n2;
		this.time = System.currentTimeMillis();
	}
	
	public void draw() {
		SESMain.getFrame().getGraph().addLine(this);
	}
	
	public GraphicLineType getType() {return type;}
	public void setType(GraphicLineType type) {this.type = type;}
	public long getTime() {return time;}
	public GraphicNode getN1() {return n1;}
	public GraphicNode getN2() {return n2;}
	
	@Override
	public String toString() {
		return time+":"+type+":"+n1+":"+n2;
	}
	
	public enum GraphicLineType {
		POSITIV(Color.GREEN),
		NEUTRAL(Color.BLUE),
		NEGATIV(Color.RED);
		private Color color;
		private GraphicLineType(Color color) {this.color = color;}
		public Color getColor() {return color;}
	}

}
