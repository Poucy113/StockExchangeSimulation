package org.lcdd.ses;

import org.lcdd.ses.frame.SESFrame;

public class SESMain {

    private static SESFrame frame;

    public static void main(String[] args) {
        frame = new SESFrame();
    }

    public static SESFrame getFrame() {
        return frame;
    }

}
