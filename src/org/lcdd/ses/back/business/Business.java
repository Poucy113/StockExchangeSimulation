package org.lcdd.ses.back.business;

import java.io.File;

import javax.swing.ImageIcon;

import org.lcdd.ses.back.GraphUpdater;
import org.lcdd.ses.frame.SESFrame;
import org.lcdd.ses.frame.graph.SESGraph;

public class Business {
	
	private static final ImageIcon BASE_ICON = new ImageIcon("src/assets/base-business-icon.png");
	
	private String name;
	private ImageIcon icon = BASE_ICON;
	private double max = 0;
	private double min = 0;
	
	private int maxUpdateTime = 1250;
	
	private GraphUpdater graphUpdater;
	private SESGraph graph;
	
	private boolean started = false;
	
	public Business(String _n, int _m1, int _m2, int _m3) {
		this.name = _n;
		this.min = _m1;
		this.max = _m2;
		this.maxUpdateTime = _m3;
		
		getIcon();
	}
	
	public Business start(SESFrame frame) {
		graphUpdater = new GraphUpdater(this);
		if(frame != null)
			graph = new SESGraph(frame, this);
		started = true;
		return this;
	}
	public void show(SESFrame frame) {
		frame.setUpdater(graphUpdater);
		frame.setGraph(this.graph);
		frame.resizeFrame();
	}
	
	private boolean contains(String[] list, String replaceAll) {
		for(String s : list)
			if(s.equals(replaceAll))
				return true;
		return false;
	}
	
	public boolean isStarted() {return started;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public double getMax() {return max;}
	public void setMax(double max) {this.max = max;}
	public double getMin() {return min;}
	public void setMin(double min) {this.min = min;}
	public SESGraph getGraph() {return graph;}
	public void setGraph(SESGraph graph) {this.graph = graph;}
	public GraphUpdater getGraphUpdater() {return graphUpdater;}
	public void setGraphUpdater(GraphUpdater graphUpdater) {this.graphUpdater = graphUpdater;}
	public int getMaxUpdateTime() {return maxUpdateTime;}
	public void setMaxUpdateTime(int maxUpdateTime) {this.maxUpdateTime = maxUpdateTime;}
	public ImageIcon getIcon() {
		if(contains(new File("./src/assets/").list(), (name.toLowerCase().replaceAll(" ", "-")+"-business-icon.png"))) 
			icon = new ImageIcon("./src/assets/"+(name.toLowerCase().replaceAll(" ", "-")+"-business-icon.png"));
		return icon;
	}
	public void setIcon(ImageIcon icon) {this.icon = icon;}
	
}
