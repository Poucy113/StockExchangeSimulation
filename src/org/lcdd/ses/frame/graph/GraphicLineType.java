package org.lcdd.ses.frame.graph;

import java.awt.Color;

public enum GraphicLineType {
	
	POSITIV(Color.GREEN),
	NEUTRAL(Color.GRAY),
	NEGATIV(Color.RED);
	
	private Color color;
	
	private GraphicLineType(Color color) {
		this.color = color;
	}
	
	public Color getColor() {return color;}
	
	public static GraphicLineType getFor(double d) {
		if(Double.valueOf(d) < 0)
			return NEGATIV;
		if(Double.valueOf(d) == 0)
			return NEUTRAL;
		if(Double.valueOf(d) > 0)
			return POSITIV;
		return NEUTRAL;
	}
}