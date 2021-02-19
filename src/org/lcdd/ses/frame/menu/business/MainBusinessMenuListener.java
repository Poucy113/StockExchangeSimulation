package org.lcdd.ses.frame.menu.business;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.lcdd.ses.SESMain;
import org.lcdd.ses.back.business.Business;
import org.lcdd.ses.frame.SESFrame;
import org.lcdd.ses.frame.SESPopup;
import org.lcdd.ses.frame.SESPopup.PopupType;
import org.lcdd.ses.frame.menu.SESMenu;

public class MainBusinessMenuListener implements ActionListener {

	private SESFrame frame;
	private int button;
	
	public MainBusinessMenuListener(SESFrame frame, int create) {
		this.frame = frame;
		this.button = create;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (button) {
		case SESMenu.DELETE:
			deleteFrame(null, 0);
			break;
		case SESMenu.CREATE:
			createFrame(null, 0);
			break;
		}
		SESMain.getFrame().getMenu().resizeFrame();
	}
	
	public void deleteFrame(Business b, int state) {
		switch (state) {
		case 0:
			new SESPopup(frame, "Supprimer une entreprise - "+(state+1), "Veuillez entrer le nom de l'entreprise:", PopupType.INPUT_STRING)
			.onComplete((e) -> {
				if(getBusiness((String) e) != null)
					deleteFrame(getBusiness((String) e), 1);
				else
					new SESPopup(frame, "Supprimer une entreprise - Erreur", "L'entreprise: "+e+" n'existe pas", PopupType.ALERT)
					.setStopOnClose(false);
				return true;
			})
			.setStopOnClose(false);
			break;
		case 1:
			new SESPopup(frame, "Supprimer une entreprise - "+(state+1), "<html>Êtes-vous sur de vouloir supprimer cette entreprise ?<br>Toutes les actions que vous avez achetés de cette entreprise seront supprimées<html>", PopupType.BOOLEAN)
			.onComplete((e) -> {
				if((boolean) e) {
					frame.getbManager().getBusinesses().remove(b);
					if(frame.getGraph().getBusiness().equals(b))
						frame.getbManager().getBaseBusiness().show(frame);
				}
				frame.getMenu().resizeFrame();
				return true;
			})
			.setStopOnClose(false);
			break;
		}
	}
	private Business getBusiness(String e) {
		for(Business b : frame.getbManager().getBusinesses())
			if(b.getName().equals(e))
				return b;
		return null;
	}
	/**
	 * States:
	 * 0 -> name
	 * 1 -> min
	 * 2 -> max
	 * 3 -> time
	 * 4 -> valid
	 */
	public void createFrame(Business b, int state) {
		switch(state) {
		case 0:
			Business b0 = new Business("New-Business", 0, 0, 0);
			new SESPopup(frame, "Créer une entreprise - "+(state+1), "Veuillez entrer le nom de l'entreprise:", PopupType.INPUT_STRING)
			.onComplete((e) -> {
				createFrame(b0, 1);
				b0.setName((String) e);
				return true;
			})
			.setStopOnClose(false);
			break;
		case 1:
			Business b1 = b;
			new SESPopup(frame, "Créer une entreprise - "+(state+1), "Veuillez entrer le maximum chute:", PopupType.INPUT_NUMBER)
			.onComplete((e) -> {
				if((int) e > 0)
					return false;
				b1.setMin((int) e);
				createFrame(b1, 2);
				return true;
			})
			.setStopOnClose(false);
			break;
		case 2:
			Business b2 = b;
			new SESPopup(frame, "Créer une entreprise - "+(state+1), "Veuillez entrer le maximum d'élévation:", PopupType.INPUT_NUMBER)
			.onComplete((e) -> {
				if((int) e < 0)
					return false;
				b2.setMax((int) e);
				createFrame(b2, 3);
				return true;
			})
			.setStopOnClose(false);
			break;
		case 3:
			Business b3 = b;
			new SESPopup(frame, "Créer une entreprise - "+(state+1), "<html>Veuillez entrer le temps moyen de rafraîchissement:<br>(millisecondes)</html>", PopupType.INPUT_NUMBER)
			.setBase(1000)
			.onComplete((e) -> {
				if((int) e < 0)
					return false;
				b3.setMaxUpdateTime((int) e);
				createFrame(b3, 4);
				return true;
			})
			.setStopOnClose(false);
			break;
		case 4:
			new SESPopup(frame, "Créer une entreprise - "+(state+1), "<html>Veuillez confirmer la création de l'entreprise avec le nom: "+b.getName()+"<br>Valeurs de changements: "+b.getMin()+", "+b.getMax()+"<br>Temps de rafraîchissement moyen: "+b.getMaxUpdateTime()+"</html>", PopupType.BOOLEAN)
			.setBase(1000)
			.onComplete((e) -> {
				if((boolean) e)
					frame.getbManager().getBusinesses().add(b);
				frame.getMenu().resizeFrame();
				return true;
			})
			.setStopOnClose(false);
			break;
		}
	}
}