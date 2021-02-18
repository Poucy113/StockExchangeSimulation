package org.lcdd.ses.frame;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class SESPopup extends JFrame implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	private boolean cancelled;
	
	private JFrame supe;
	private String name;
	private String msg;
	private PopupType type;
	
	private JLabel label;
	private JComponent input;
	private List<JButton> buttons = new ArrayList<>();
	
	private Object answer = null;
	private Consumer<Object> onComplete;
	
	public SESPopup(JFrame supe, String title, String msg, PopupType type) {
		super(title);
		this.supe = supe;
		this.name = title;
		this.msg = msg;
		this.type = type;
		
		supe.setEnabled(false);
		super.setType(Type.POPUP);
		super.setBounds(supe.getX(), supe.getY(), 325, 200);
		super.setEnabled(true);
		super.setAlwaysOnTop(true);
		super.setResizable(false);
		
		JDesktopPane desk = new JDesktopPane();
		desk.setBounds(0, 0, 325, 200);
		super.setContentPane(desk);
		super.getContentPane().setBackground(Color.GRAY);
		super.setBounds(super.getContentPane().getBounds());
		
		super.setVisible(true);
		
		setup();
	}
	
	private void setup() {
		label = new JLabel(msg);
		if(type == PopupType.BOOLEAN)
			label.setBounds(0, (super.getContentPane().getHeight() / 2)*0, super.getContentPane().getWidth(), (super.getContentPane().getHeight() / 2));
		else
			label.setBounds(0, (super.getContentPane().getHeight() / 3)*0, super.getContentPane().getWidth(), (super.getContentPane().getHeight() / 3));
		label.setVisible(true);
		super.getContentPane().add(label);
		
		if(type == PopupType.INPUT_STRING)
			input = new JTextField();
		else if(type == PopupType.INPUT_NUMBER)
			input = new JSpinner();
		if(type != PopupType.BOOLEAN) {
			input.setBounds(0, (super.getContentPane().getHeight() / 3)*1, super.getContentPane().getWidth(), (super.getContentPane().getHeight() / 3));
			input.setVisible(true);
			super.getContentPane().add(input);
		}
		
		if(type == PopupType.BOOLEAN) {
			JButton button1 = new JButton("Oui"), button2 = new JButton("Non");
			button1.setBounds((super.getContentPane().getWidth() / 2)*0, (super.getContentPane().getHeight() / 2)*1, (super.getContentPane().getWidth() / 2)*1, (super.getContentPane().getHeight() / 2));
			button2.setBounds((super.getContentPane().getWidth() / 2)*1, (super.getContentPane().getHeight() / 2)*1, (super.getContentPane().getWidth() / 2)*1, (super.getContentPane().getHeight() / 2));
			buttons.add(button1);
			buttons.add(button2);
		}else {
			JButton button1 = new JButton("Valider");
			button1.setBounds(0, (super.getContentPane().getHeight() / 3)*2, super.getContentPane().getWidth(), (super.getContentPane().getHeight() / 3));
			buttons.add(button1);
		}
		for(JButton b : buttons) {
			b.addMouseListener(this);
			b.setVisible(true);
			super.getContentPane().add(b);
		}
	}
	
	public SESPopup onComplete(Consumer<Object> r) {this.onComplete = r;return this;}
	private void complete() {onComplete.accept(answer);}
	
	public void cancel() {cancelled = true;super.dispose();}
	
	public Object getAnswer() {return answer;}
	public JFrame getFrame() {return supe;}
	public String getMsg() {return msg;}
	public String getName() {return name;}
	public PopupType getPopupType() {return type;}
	public JLabel getLabel() {return label;}
	public JComponent getInput() {return input;}
	public List<JButton> getButtons() {return buttons;}
	public Consumer<Object> getOnComplete() {return onComplete;}
	public boolean isCancelled() {return cancelled;}
	
	public static enum PopupType {
		INPUT_STRING(),
		INPUT_NUMBER(),
		BOOLEAN();
	}

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
			}
		}
		if(answer != null) {
			complete();
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

}
