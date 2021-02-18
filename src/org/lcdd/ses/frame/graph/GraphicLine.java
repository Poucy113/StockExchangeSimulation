package org.lcdd.ses.frame.graph;

import java.awt.Color;

import org.lcdd.ses.SESMain;

public class GraphicLine {
	
	private int before = 0;
	
	private GraphicNode n1, n2;
	private GraphicLineType type;
	
	public GraphicLine(GraphicNode n1, GraphicNode n2) {
		this.n1 = n1;
		this.n2 = n2;
	}
	
	public void draw() {
		SESMain.getFrame().getGraph().addLine(this);
	}
	
	public GraphicLineType getType() {return type;}
	public void setType(GraphicLineType type) {this.type = type;}
	public GraphicNode getN1() {return n1;}
	public void setN1(GraphicNode n1) {this.n1 = n1;}
	public GraphicNode getN2() {return n2;}
	public void setN2(GraphicNode n2) {this.n2 = n2;}
	public int getBefore() {return before;}
	public void setBefore(int before) {this.before = before;}
	
	@Override
	public String toString() {
		return type+":"+n1+":"+n2+":"+hashCode();
	}
	
	public enum GraphicLineType {
		POSITIV(Color.GREEN),
		NEUTRAL(Color.GRAY),
		NEGATIV(Color.RED);
		private Color color;
		private GraphicLineType(Color color) {this.color = color;}
		public Color getColor() {return color;}
	}

}
