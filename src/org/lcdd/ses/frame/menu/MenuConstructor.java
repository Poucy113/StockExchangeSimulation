package org.lcdd.ses.frame.menu;

import java.awt.MenuComponent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuConstructor {
    private JMenu menu;
    public MenuConstructor(String s, boolean def) {
        menu = new JMenu(s, def);
    }
    public MenuConstructor removeNotify() {
        this.menu.removeNotify();
        return this;
    }
    public MenuConstructor removeActionListener(ActionListener l) {
        this.menu.removeActionListener(l);
        return this;
    }
    public MenuConstructor removeAll() {
        this.menu.removeAll();
        return this;
    }
    public MenuConstructor remove(MenuComponent i) {
        this.menu.remove(i);
        return this;
    }
    public MenuConstructor remove(int i) {
        this.menu.remove(i);
        return this;
    }
    public MenuConstructor addNotify(String s) {
        this.menu.add(s);
        return this;
    }
    public MenuConstructor addActionListener(ActionListener l) {
        this.menu.addActionListener(l);
        return this;
    }
    public MenuConstructor addNotify() {
        this.menu.addNotify();
        return this;
    }
    public MenuConstructor addSeparator() {
        this.menu.addSeparator();
        return this;
    }
    public MenuConstructor setName(String s) {
        this.menu.setName(s);
        return this;
    }
    public MenuConstructor add(JMenuItem s) {
        this.menu.add(s);
        return this;
    }
    public MenuItemConstructor addItem(String s) {
    	return new MenuItemConstructor(this, s);
    }
    public JMenu build() {
        return menu;
    }
    
    public class MenuItemConstructor {
    	private JMenuItem item;
    	private MenuConstructor cons;
    	public MenuItemConstructor(MenuConstructor t, String s) {
    		this.item = new JMenuItem(s);
    		this.cons = t;
    	}
    	public MenuItemConstructor removeNotify() {
            this.item.removeNotify();
            return this;
        }
        public MenuItemConstructor removeActionListener(ActionListener l) {
            this.item.removeActionListener(l);
            return this;
        }
        public MenuItemConstructor addActionListener(ActionListener l) {
            this.item.addActionListener(l);
            return this;
        }
        public MenuItemConstructor addNotify() {
            this.item.addNotify();
            return this;
        }
        public MenuItemConstructor setName(String s) {
            this.item.setName(s);
            return this;
        }
    	public MenuConstructor build() {
    		cons.add(item);
    		return cons;
    	}
    }
}