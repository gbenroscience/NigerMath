/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package splashyflashy;

import gui.NaijaCalc;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

/**
 *
 * @author GBENRO
 */
public class FinalSplash extends JWindow implements Runnable {

    private Thread timer;
    private int splashTimer;
    private static FinalSplash splash;
//The canvas that the splash's graphics are drawn on.
    private SplashPanel pan;
    private JProgressBar bar;

    /**
     * The calculating framework.
     */
    private NaijaCalc calc;

    public FinalSplash() {
        setLayout(null);
        pan = new SplashPanel();
        setCalc(new NaijaCalc());
        getCalc().setTitle("MathematicalNaija!!!...YOUR SUPER-DUPER MATHEMATICAL MESSENGER.");
        
        
        setSize(pan.getSize());
        
        bar = new JProgressBar(0, 100);
        bar.setOrientation(JProgressBar.HORIZONTAL);
        bar.setVisible(true);
        bar.setSize(200, 25);
        bar.setLocation(getWidth() - bar.getWidth(), getHeight() - bar.getHeight());
        bar.setForeground(Color.BLACK);
        
        
        
        
        setLocationRelativeTo(null);

        add(bar);
        add(pan);
        timer = new Thread(this);
        timer.start();
    }//end FinalSplash constructor

    /**
     *
     * @return the SplashPanel object to which the calculator framework is
     * attached
     */
    public SplashPanel getPan() {
        return pan;
    }

    /**
     *
     * @param pan sets the SplashPanel object to which the calculator framework
     * is attached
     */
    public void setPan(SplashPanel pan) {
        this.pan = pan;
    }

    /**
     *
     * @return the FinalSplash object that is used to start the application
     */
    public static FinalSplash getSplash() {
        return splash;
    }

    /**
     *
     * @param splash sets the FinalSplash object that is used to start the
     * application.
     */
    public static void setSplash(FinalSplash splash) {
        FinalSplash.splash = splash;
    }

    public NaijaCalc getCalc() {
        return calc;
    }

    public void setCalc(NaijaCalc calc) {
        this.calc = calc;
    }

    @Override
    public void run() {
        Thread me = Thread.currentThread();
        while (timer == me) {
            try {
                Thread.currentThread().sleep(30);
            }//end try
            catch (InterruptedException e) {
            }//end catch
            repaint();
            getContentPane().setBackground(pan.getBackground());
            if (splashTimer < 700) {
                bar.setValue(splashTimer / 7);
                bar.setStringPainted(true);
                bar.setString(" LOADING... " + bar.getValue() + "%");
                splashTimer++;
            }//end if
            else {
                setVisible(false);
                FinalSplash.this.setVisible(false);
                NaijaCalc superCalc = new NaijaCalc();
                superCalc.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
                superCalc.pack();
                setCalc(superCalc);
                superCalc.setVisible(true);
                break;
            }//end else
        }//end while

    }

    public static void main(String[] args) {
        splash = new FinalSplash();
        splash.setVisible(true);

    }//end main

}
