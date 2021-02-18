package org.lcdd.ses.frame;

import java.awt.*;
import java.awt.event.ActionListener;

public class SESMenu extends MenuBar {
    private static final long serialVersionUID = 1L;

    public SESMenu(SESFrame frame) {
        super.add(new MenuConstructor("Options", false).build());
    }

    public class MenuConstructor {
        private Menu menu;

        public MenuConstructor(String s, boolean def) {
            menu = new Menu(s, def);
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

        public MenuConstructor setLabel(String s) {
            this.menu.setLabel(s);
            return this;
        }

        public MenuConstructor setName(String s) {
            this.menu.setName(s);
            return this;
        }

        public MenuConstructor add(MenuItem s) {
            this.menu.add(s);
            return this;
        }

        public Menu build() {
            return menu;
        }
    }

}
