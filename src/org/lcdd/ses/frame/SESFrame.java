package org.lcdd.ses.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lcdd.ses.back.GraphUpdater;
import org.lcdd.ses.back.UserManager;
import org.lcdd.ses.frame.SESPopup.PopupType;
import org.lcdd.ses.frame.graph.GraphicLine.GraphicLineType;
import org.lcdd.ses.frame.graph.SESGraph;

public class SESFrame extends JFrame implements WindowListener, ComponentListener {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private UserManager manager;
	private GraphUpdater updater;
	
	private SESGraph graph;
	private SESMenu menu;
	private List<SESPopup> activePopups = new ArrayList<>();
	
	private JPanel userPanel = new JPanel();
	private JLabel userPanelUserName = new JLabel();
	private JLabel userPanelMoneyCount = new JLabel();
	
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
		super.addComponentListener(this);
		
		this.menu = new SESMenu(this);
		this.graph = new SESGraph(this);
		super.setMenuBar(menu);
		desk.add(graph);
		
		userPanel.setBackground(Color.BLUE);
		userPanel.setVisible(true);
		userPanelUserName.setVisible(true);
		userPanelUserName.setForeground(Color.WHITE);
		userPanel.add(userPanelUserName);
		userPanelMoneyCount.setBackground(GraphicLineType.POSITIV.getColor());
		userPanelMoneyCount.setVisible(true);
		userPanelMoneyCount.setForeground(Color.WHITE);
		userPanel.add(userPanelMoneyCount);
		desk.add(userPanel);
		
		desk.setVisible(true);
		super.setContentPane(desk);
		
		resizeFrame();
	}
	
	private void resizeFrame() {
		this.graph.resizeFrame();
		
		userPanelUserName.setBounds(15, 15, ((super.getContentPane().getWidth() / 10)*2)-15, ((super.getContentPane().getHeight() / 10)*2));
		userPanelUserName.setFont(new Font(userPanelUserName.getFont().getName(), Font.PLAIN, Math.min((int)(userPanelUserName.getFont().getSize() * (double)userPanelUserName.getWidth() / (double)userPanelUserName.getFontMetrics(userPanelUserName.getFont()).stringWidth(userPanelUserName.getText())), userPanel.getHeight()/2 -10)));
		
		userPanelMoneyCount.setForeground(GraphicLineType.getFor((manager != null ? manager.getMoney() : 0)).getColor());
		userPanelMoneyCount.setText((manager != null ? manager.getMoney() : 0)+" €");
		userPanelMoneyCount.setBounds(15, 15+userPanelUserName.getHeight(), ((super.getContentPane().getWidth() / 10)*2)-15, ((super.getContentPane().getHeight() / 10)*2));
		userPanelMoneyCount.setFont(new Font(userPanelMoneyCount.getFont().getName(), Font.PLAIN, Math.min((int)(userPanelMoneyCount.getFont().getSize() * (double)userPanelMoneyCount.getWidth() / (double)userPanelMoneyCount.getFontMetrics(userPanelMoneyCount.getFont()).stringWidth(userPanelMoneyCount.getText())), userPanel.getHeight()/2 -10)));
		
		userPanel.setBounds(10, 10, ((super.getContentPane().getWidth() / 10)*2)-10, ((super.getContentPane().getHeight() / 10)*4)+10);
	}
	
	private boolean correctUsername(Object answer) {
		if(!(answer instanceof String))
			return false;
		if(((String) answer).length() == 0)
			return false;
		if(((String) answer).equals(repeat(" ", ((String) answer).length())))
			return false;
		this.username = ((String) answer);
		return true;
	}

	private String repeat(String string, int length) {
		String s = "";
		for(int i = 0; i < length; i++)
			s += string;
		return s;
	}

	private void login() {
		updater = new GraphUpdater();
		manager = new UserManager(username);
		
		userPanelUserName.setText(username);
		
		resizeFrame();
	}

	public SESGraph getGraph() {return graph;}
	public SESMenu getMenu() {return menu;}
	public JButton getBuyButton() {return buyButton;}
	public JButton getSellButton() {return sellButton;}
	public GraphUpdater getUpdater() {return updater;}
	public UserManager getManager() {return manager;}
	public String getUsername() {return username;}
	public JPanel getUserPanel() {return userPanel;}
	
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
	@Override
	public void componentResized(ComponentEvent e) {
		resizeFrame();
	}
	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
	@Override
	public void componentHidden(ComponentEvent e) {}

}
