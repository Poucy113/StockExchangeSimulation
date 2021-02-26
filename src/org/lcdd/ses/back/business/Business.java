package org.lcdd.ses.back.business;

import java.io.File;
import java.util.Arrays;

import javax.swing.ImageIcon;

import org.lcdd.ses.back.GraphUpdater;
import org.lcdd.ses.frame.SESFrame;
import org.lcdd.ses.frame.graph.SESGraph;

public class Business {
	
	private static final ImageIcon BASE_ICON = new ImageIcon("assets/base-business-icon.png");
	
	private String name;
	private ImageIcon icon;
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
	
	private String contains(File file, String replaceAll) {
		for(String s : file.list()) {
			String name2 = replaceAll.toLowerCase().replaceAll(" ", "-")+"-business-icon";
			for(String r : Arrays.asList(".png", ".gif", ".jpg")) {
				if(s.equals(name2+r))
					return name2+r;
			}
		}
		return null;
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
		if(contains(new File("./assets/"), name) != null)
			icon = new ImageIcon("./assets/"+contains(new File("./assets/"), name));
		else
			icon = BASE_ICON;
		return icon;
	}
	public void setIcon(ImageIcon icon) {this.icon = icon;}
	
	@Override
	public String toString() {
		return this.getClass().getName()+":"+getName();
	}
	
}
