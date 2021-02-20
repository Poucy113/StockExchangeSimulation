package org.lcdd.ses.frame.menu.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.lcdd.ses.SESMain;
import org.lcdd.ses.frame.SESFrame;
import org.lcdd.ses.frame.SESPopup;
import org.lcdd.ses.frame.SESPopup.PopupType;
import org.lcdd.ses.frame.menu.SESMenu;

public class MainOptionsMenuListener implements ActionListener {

	private SESFrame frame;
	private int option;
	
	public MainOptionsMenuListener(SESFrame frame, int option) {
		this.frame = frame;
		this.option = option;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (option) {
		case SESMenu.OPTIONS_GRAPH_INTERVAL:
			graphInterval();
			break;
		case SESMenu.OPTIONS_CLEAR_POPUPS:
			for(SESPopup p : SESMain.getFrame().getActivePopups())
				if(p.getPopupType() == PopupType.ALERT)
					p.dispose();
			break;
		}
	}

	private void graphInterval() {
		new SESPopup(frame, "Options - Intervale du graphe", "<html>Veuillez entrer l'inverval du graphe voulu<br>Normal: 25<br>Actuel: "+frame.getGraph().getInterval()+"</html>", PopupType.INPUT_NUMBER)
		.setStopOnClose(false)
		.onComplete((e) -> {
			if((int) e <= 0)
				return false;
			frame.getGraph().setInterval((int) e);
			return true;
		});
	}

}
