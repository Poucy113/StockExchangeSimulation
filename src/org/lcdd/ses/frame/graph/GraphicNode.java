package org.lcdd.ses.frame.graph;

public class GraphicNode {

	private int y;
	
	public GraphicNode(int _y) {
		this.y = _y-(_y*2);
	}
	public GraphicNode(int _y, boolean no) {
		this.y = _y;
	}
	
	public int getY() {return y;}
	public void setY(int y) {this.y = y;}
	
	@Override
	public String toString() {
		return y+":"+hashCode();
	}
	
}
