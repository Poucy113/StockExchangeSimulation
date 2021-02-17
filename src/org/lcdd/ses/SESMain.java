package org.lcdd.ses;

import org.lcdd.ses.frame.SESFrame;
import org.lcdd.ses.utils.SESServer;

public class SESMain {

	private static SESFrame frame;
	
	public static void main(String[] args) {
		frame = new SESFrame(new SESServer("https://poucydev.alwaysdata.com/ses.php"));
	}

	public static SESFrame getFrame() {return frame;}

}
