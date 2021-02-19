package org.lcdd.ses.frame.graph;

import java.awt.geom.Line2D;

public class GraphicLine extends Line2D.Float {
	private static final long serialVersionUID = 1L;
	
	private GraphicNode n1, n2;
	private GraphicLineType type;
	
	public GraphicLine(GraphicNode n1, GraphicNode n2) {
		super(0, n1.getY(), 0, n2.getY());
		this.n1 = n1;
		this.n2 = n2;
	}
	
	public GraphicLineType getType() {return type;}
	public void setType(GraphicLineType type) {this.type = type;}
	public GraphicNode getN1() {return n1;}
	public void setN1(GraphicNode n1) {this.n1 = n1;}
	public GraphicNode getN2() {return n2;}
	public void setN2(GraphicNode n2) {this.n2 = n2;}
	
	@Override
	public String toString() {
		return type+":"+n1+":"+n2+":"+hashCode();
	}
	
}
