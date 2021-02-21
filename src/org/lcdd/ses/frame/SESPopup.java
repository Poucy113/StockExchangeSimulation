package org.lcdd.ses.frame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class SESPopup extends JFrame implements MouseListener, WindowListener {
	
	private static final long serialVersionUID = 1L;
	
	private boolean cancelled;
	
	private SESFrame supe;
	private String name;
	private String msg;
	private PopupType type;
	
	private JLabel label;
	private JComponent input;
	private List<JButton> buttons = new ArrayList<>();
	
	private boolean stopOnClose = false;
	
	private Object answer = null;
	private PopupCompleteActionConsumer<Object> onComplete;
	
	public SESPopup(SESFrame supe, String title, String msg, PopupType type) {
		super(title);
		this.supe = supe;
		this.name = title;
		this.msg = msg;
		this.type = type;
		
		supe.getActivePopups().add(this);
		
		super.setType(Type.POPUP);
		super.setSize(325, 200);
		super.setLocation(supe.getWidth()/2 -super.getWidth()/2, supe.getHeight()/2 -super.getHeight()/2);
		
		super.setEnabled(true);
		super.setAlwaysOnTop(true);
		super.setResizable(false);
		
		JDesktopPane desk = new JDesktopPane();
		desk.setBounds(supe.getWidth()/2 -super.getWidth()/2, supe.getHeight()/2 -super.getHeight()/2, 325, 200);
		super.setContentPane(desk);
		super.getContentPane().setBackground(Color.GRAY);
		super.setBounds(super.getContentPane().getBounds());
		super.setIconImage(supe.getIconImage());
		super.addWindowListener(this);
		
		super.setVisible(true);
		
		setup();
	}
	
	private void setup() {
		label = new JLabel(msg);
		label.setForeground(Color.WHITE);
		if(type == PopupType.BOOLEAN)
			label.setBounds(0, (super.getContentPane().getHeight() / 2)*0, super.getContentPane().getWidth(), (super.getContentPane().getHeight() / 2));
		else
			label.setBounds(0, (super.getContentPane().getHeight() / 3)*0, super.getContentPane().getWidth(), (super.getContentPane().getHeight() / 3) +15);
		label.setVisible(true);
		super.getContentPane().add(label);
		
		if(type == PopupType.INPUT_STRING) {
			input = new JTextField();
			input.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		}
		else if(type == PopupType.INPUT_NUMBER) {
			input = new JSpinner();
			input.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		}
		else if(type == PopupType.CHOICE)
			input = new JComboBox<Object>();
		if(type != PopupType.BOOLEAN && type != PopupType.ALERT) {
			input.setBounds(0, (super.getContentPane().getHeight() / 3)*1 +15, super.getContentPane().getWidth(), (super.getContentPane().getHeight() / 3) -15);
			input.setVisible(true);
			super.getContentPane().add(input);
		}
		
		if(type == PopupType.BOOLEAN) {
			JButton button1 = new JButton("Oui"), button2 = new JButton("Non");
			button1.setBounds((super.getContentPane().getWidth() / 2)*0, (super.getContentPane().getHeight() / 2)*1, (super.getContentPane().getWidth() / 2)*1, (super.getContentPane().getHeight() / 2));
			button2.setBounds((super.getContentPane().getWidth() / 2)*1, (super.getContentPane().getHeight() / 2)*1, (super.getContentPane().getWidth() / 2)*1, (super.getContentPane().getHeight() / 2));
			button1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			button2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			buttons.add(button1);
			buttons.add(button2);
		}else if(type == PopupType.ALERT) {
			JButton button1 = new JButton("Ok");
			button1.setBounds(0, (super.getContentPane().getHeight() / 2)*1, super.getContentPane().getWidth(), (super.getContentPane().getHeight() / 3));
			button1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			buttons.add(button1);
		}else {
			JButton button1 = new JButton("Valider");
			button1.setBounds(0, (super.getContentPane().getHeight() / 3)*2, super.getContentPane().getWidth(), (super.getContentPane().getHeight() / 3));
			button1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			buttons.add(button1);
		}
		for(JButton b : buttons) {
			b.addMouseListener(this);
			b.setVisible(true);
			super.getContentPane().add(b);
		}
	}
	
	public SESPopup setBase(Object obj) {
		if(type == PopupType.INPUT_NUMBER)
			((JSpinner) input).setValue((int) obj);
		if(type == PopupType.INPUT_STRING)
			((JLabel) input).setText((String) obj);
		return this;
	}
	@SuppressWarnings("unchecked")
	public <T> SESPopup setItems(List<T> objects, PopupComboBoxTranslateConsumer<T> cons) {
		if(type != PopupType.CHOICE)
			return this;
		for(T t : objects)
			((JComboBox<Object>) input).addItem(cons.<Object>accept(t));
		return this;
	}
	public SESPopup onComplete(PopupCompleteActionConsumer<Object> r) {this.onComplete = r;return this;}
	private boolean complete() {
		if(onComplete == null)
			return true;
		return onComplete.accept(answer);
	}
	
	public void cancel() {cancelled = true;super.dispose();}
	
	public Object getAnswer() {return answer;}
	public JFrame getFrame() {return supe;}
	public String getMsg() {return msg;}
	public String getName() {return name;}
	public PopupType getPopupType() {return type;}
	public JLabel getLabel() {return label;}
	public JComponent getInput() {return input;}
	public List<JButton> getButtons() {return buttons;}
	public PopupCompleteActionConsumer<Object> getOnComplete() {return onComplete;}
	public boolean isCancelled() {return cancelled;}
	public boolean isStopOnClose() {return stopOnClose;}
	public SESPopup setStopOnClose(boolean stopOnClose) {this.stopOnClose = stopOnClose;return this;}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(isCancelled())
			return;
		for(JButton b : buttons) {
			if(e.getComponent().equals(b)) {
				if(type == PopupType.BOOLEAN)
					answer = (b.getText() == "Oui" ? true : false);
				if(type == PopupType.INPUT_NUMBER)
					answer = ((JSpinner) input).getValue();
				if(type == PopupType.INPUT_STRING)
					answer = ((JTextField) input).getText();
				if(type == PopupType.ALERT)
					answer = true;
			}
		}
		if(complete()) {
			supe.getActivePopups().remove(this);
			supe.setEnabled(true);
			super.dispose();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {
		if(isStopOnClose()) {
			supe.dispose();
			System.exit(0);
		}
	}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	
	public static enum PopupType {
		INPUT_STRING(),
		INPUT_NUMBER(),
		BOOLEAN(),
		ALERT(),
		CHOICE();
	}
	
	@FunctionalInterface
	public interface PopupCompleteActionConsumer<T> {
		boolean accept(Object answer);
	}
	@FunctionalInterface
	public interface PopupComboBoxTranslateConsumer<T> {
		<R> R accept(T answer);
	}

}
