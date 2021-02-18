package org.lcdd.ses.frame.graph;

import org.lcdd.ses.frame.SESFrame;

import javax.swing.*;

public class SESGraph extends JComponent {
    private static final long serialVersionUID = 1L;

    private SESFrame frame;

    public SESGraph(SESFrame frame) {
        this.frame = frame;

        // http://www.lirmm.fr/~leclere/enseignements/TER/2008/Rapport/18.pdf // 3.3

        //Rectangle rect = (Rectangle) frame.getBounds().clone();
        //rect.setRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        super.setBounds(frame.getBounds() /*rect*/);
    }

    public SESFrame getFrame() {
        return frame;
    }

}
