package org.lcdd.ses.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lcdd.ses.SESMain;
import org.lcdd.ses.back.GraphUpdater;
import org.lcdd.ses.back.UserManager;
import org.lcdd.ses.back.business.BusinessManager;
import org.lcdd.ses.frame.SESPopup.PopupType;
import org.lcdd.ses.frame.graph.GraphicLine.GraphicLineType;
import org.lcdd.ses.frame.graph.SESGraph;

public class SESFrame extends JFrame implements WindowListener, ComponentListener {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private UserManager manager;
	private GraphUpdater updater;
	private SESGraph graph;
	private BusinessManager bManager;
	
	private SESMenu menu;
	private List<SESPopup> activePopups = new ArrayList<>();
	
	private JPanel userPanel = new JPanel();
	private JLabel userPanelUserName = new JLabel();
	private JLabel userPanelMoneyCount = new JLabel();
	private JLabel userPanelActionsList = new JLabel("Vos actions:");
	
	private JButton buyButton = new JButton();
	private JButton sellButton = new JButton();
	
	public SESFrame(BusinessManager manager) {
		this.bManager = manager;
		
		JDesktopPane desk = new JDesktopPane();
		desk.setBounds(super.getBounds());
		desk.setBackground(Color.GRAY);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		super.setTitle("StockExchangeSimulator");
		super.setType(Type.NORMAL);
		super.setEnabled(true);
		super.addWindowListener(this);
		super.addComponentListener(this);
		
		this.menu = new SESMenu(this);
		super.setMenuBar(menu);
		
		userPanel(desk);
		buttons(desk);
		
		desk.setVisible(true);
		super.setContentPane(desk);
		super.setIconImage(new ImageIcon("src/assets/icon.png").getImage());
		super.setBounds(0, 0, (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2), (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));
		
		SESPopup login = new SESPopup(this, "SES - Login", "Veuillez entrer votre nom d'utilisateur:", PopupType.INPUT_STRING);
		login.setStopOnClose(true);
		login.onComplete((answer) -> {
			boolean b = correctUsername(answer);
			if(b)
				login((String) answer);
			return b;
		});
		
		super.setVisible(true);
	}
	
	private void buttons(JDesktopPane desk) {
		buyButton.setText("Acheter");
		buyButton.setVisible(true);
		buyButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SESMain.getFrame().getManager().buy();
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
		});
		desk.add(buyButton);
		
		if(manager != null)
			sellButton.setText((manager.getActions().size() > 0 ? "Vendre: "+manager.getActions().get(0) : "Vendre"));
		else
			sellButton.setText("Vendre");
		sellButton.setVisible(true);
		sellButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SESMain.getFrame().getManager().sell();
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
		});
		desk.add(sellButton);
	}
	private void userPanel(JDesktopPane desk) {
		userPanel.setBackground(Color.LIGHT_GRAY);
		userPanel.setVisible(true);
		userPanelUserName.setVisible(true);
		userPanelUserName.setForeground(Color.WHITE);
		userPanel.add(userPanelUserName);
		userPanelMoneyCount.setVisible(true);
		userPanelMoneyCount.setForeground(Color.WHITE);
		userPanel.add(userPanelMoneyCount);
		userPanelActionsList.setVisible(true);
		userPanelActionsList.setForeground(Color.WHITE);
		userPanel.add(userPanelActionsList);
		desk.add(userPanel);
	}

	public void resizeFrame() {
		if(graph != null)
			this.graph.resizeFrame();
		
		if(username != null) {
			userPanelUserName.setText(username);
			userPanelUserName.setBounds(10, 15, ((super.getContentPane().getWidth() / 10)*2)-15, ((super.getContentPane().getHeight() / 10)*2));
			userPanelUserName.setFont(new Font(userPanelUserName.getFont().getName(), Font.PLAIN, Math.min((int)(userPanelUserName.getFont().getSize() * (double)userPanelUserName.getWidth() / (double)userPanelUserName.getFontMetrics(userPanelUserName.getFont()).stringWidth(userPanelUserName.getText())), userPanel.getHeight()/2 -10)));
		}
		
		userPanelMoneyCount.setForeground(GraphicLineType.getFor((manager != null ? manager.getMoney() : 0)).getColor());
		userPanelMoneyCount.setText((manager != null ? manager.getMoney() : 0)+" €");
		userPanelMoneyCount.setBounds(10, 15+userPanelUserName.getHeight(), ((super.getContentPane().getWidth() / 10)*2)-15, ((super.getContentPane().getHeight() / 10)*2));
		userPanelMoneyCount.setFont(new Font(userPanelMoneyCount.getFont().getName(), Font.PLAIN, Math.min((int)(userPanelMoneyCount.getFont().getSize() * (double)userPanelMoneyCount.getWidth() / (double)userPanelMoneyCount.getFontMetrics(userPanelMoneyCount.getFont()).stringWidth(userPanelMoneyCount.getText())), userPanel.getHeight()/2 -10)));
		
		userPanelActionsList.setBounds(10, 15+userPanelUserName.getHeight()+userPanelMoneyCount.getHeight(), ((super.getContentPane().getWidth() / 10)*2)-15, userPanel.getHeight()-(userPanelUserName.getHeight()+userPanelMoneyCount.getHeight()));
		if(manager != null)
			userPanelActionsList.setText("<html>Vos actions:<br>"+getActionsText()+"</html>");
		
		userPanel.setBounds(5, 5, ((super.getContentPane().getWidth() / 10)*2)-10, super.getContentPane().getHeight()-5);
		
		if(graph != null) {
			buyButton.setBounds(graph.getX()+(graph.getWidth()/2*0), graph.getHeight(), graph.getWidth()/2, super.getContentPane().getHeight()-graph.getHeight());
			if(manager != null)
				sellButton.setText((manager.getActions().size() > 0 ? "Vendre: "+manager.getActions().get(0) : "Vendre"));
			sellButton.setBounds(graph.getX()+(graph.getWidth()/2*1), graph.getHeight(), graph.getWidth()/2, super.getContentPane().getHeight()-graph.getHeight());
		}
	}
	
	private String getActionsText() {
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for(double d : manager.getActions()) {
			sb.append(i+": "+d+"<br>");
			i++;
		}
		return sb.toString();
	}

	private boolean correctUsername(Object answer) {
		if(!(answer instanceof String))
			return false;
		if(((String) answer).length() == 0)
			return false;
		if(((String) answer).equals(repeat(" ", ((String) answer).length())))
			return false;
		return true;
	}

	private String repeat(String string, int length) {
		String s = "";
		for(int i = 0; i < length; i++)
			s += string;
		return s;
	}

	private void login(String answer) {
		this.username = answer;
		
		bManager.getBaseBusiness().start(this).show(this);
		manager = new UserManager(username);
		
		userPanelUserName.setText(username);
		
		resizeFrame();
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
	public void setGraph(SESGraph graph) {super.remove(graph);this.graph = graph;super.add(graph);}
	public BusinessManager getbManager() {return bManager;}
	public List<SESPopup> getActivePopups() {return activePopups;}

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {
		if(manager != null)
			manager.saveUser();
		if(bManager != null)
			bManager.saveAll();
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
