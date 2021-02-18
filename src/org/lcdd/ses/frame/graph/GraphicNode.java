package org.lcdd.ses.frame.graph;

public class GraphicNode {

	private String name;
	private int y;
	
	public GraphicNode(String _n, int _y) {
		this.name = _n;
		this.y = _y-(_y*2);
	}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public int getY() {return y;}
	public void setY(int y) {this.y = y;}
	
}
