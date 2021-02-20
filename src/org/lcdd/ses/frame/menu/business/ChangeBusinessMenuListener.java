package org.lcdd.ses.frame.menu.business;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.lcdd.ses.back.business.Business;
import org.lcdd.ses.frame.SESFrame;

public class ChangeBusinessMenuListener implements ActionListener {
	
	private SESFrame frame;
	
	private Business business;
	
	public ChangeBusinessMenuListener(SESFrame frame, Business _b) {
		this.business = _b;
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!business.isStarted())
			business.start(frame);
		business.show(frame);
	}
}