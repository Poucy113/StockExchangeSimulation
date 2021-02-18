package org.lcdd.ses.frame;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

import org.lcdd.ses.back.GraphUpdater;
import org.lcdd.ses.back.UserManager;
import org.lcdd.ses.frame.SESPopup.PopupType;
import org.lcdd.ses.frame.graph.SESGraph;

public class SESFrame extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private UserManager manager;
	private GraphUpdater updater;
	
	private SESGraph graph;
	private SESMenu menu;
	private List<SESPopup> activePopups = new ArrayList<>();
	
	private JButton buyButton = new JButton();
	private JButton sellButton = new JButton();
	
	public SESFrame() {
		SESPopup login = new SESPopup(this, "SES - Login", "Veuillez entrer votre nom d'utilisateur:", PopupType.INPUT_STRING);
		login.onComplete((answer) -> {
			boolean b = correctUsername(answer);
			if(b)
				login();
			return b;
		});
		activePopups.add(login);
		
		JDesktopPane desk = new JDesktopPane();
		desk.setBounds(super.getBounds());
		desk.setBackground(Color.GRAY);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		super.setTitle("StockExchangeSimulator");
		super.setBounds(0, 0, (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2), (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));
		super.setType(Type.NORMAL);
		super.setEnabled(true);
		super.setVisible(true);
		super.addWindowListener(this);
		
		this.menu = new SESMenu(this);
		this.graph = new SESGraph(this);
		super.setMenuBar(menu);
		desk.add(graph);
		
		desk.setVisible(true);
		super.setContentPane(desk);
	}
	
	private boolean correctUsername(Object answer) {
		if(!(answer instanceof String))
			return false;
		if(((String) answer).length() == 0)
			return false;
		if(((String) answer).equals(" ".repeat(((String) answer).length())))
			return false;
		this.username = ((String) answer)/*.replaceAll(" ", "_")*/;
		return true;
	}

	private void login() {
		updater = new GraphUpdater();
		manager = new UserManager(username);
	}

	public SESGraph getGraph() {return graph;}
	public SESMenu getMenu() {return menu;}
	public JButton getBuyButton() {return buyButton;}
	public JButton getSellButton() {return sellButton;}
	public GraphUpdater getUpdater() {return updater;}
	public UserManager getManager() {return manager;}
	
	public List<SESPopup> getActivePopups() {return activePopups;}

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {
		manager.saveUser();
	}
	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(e.getID());
	}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}

}
