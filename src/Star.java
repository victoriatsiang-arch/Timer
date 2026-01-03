
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author victoria
 */

public class Star extends JPanel{
    int ticks[]; //int tick; 
    Timer timer; 
    StarShapes[] starShapes; //StarShapes starShape; 
    int[] final_diameters; //int final_diameter; 
    int[] delays; //every start starts at a different time
    boolean[] on_tracker; 
    public static boolean dispose = false; 
    
    
    public Star(int[] delay, int[] diameters, int[] x, int[] y){
        final_diameters = diameters; 
        delays = delay; 
        on_tracker = new boolean[diameters.length];
        Sound sound = new Sound(); 
        
        for(int i =0; i < diameters.length; i++){
            on_tracker[i] = true; 
        }
        
        startAnimating(x, y, diameters); 
        
        this.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();
                sound.stopSound(); 
                SwingUtilities.getWindowAncestor(Star.this).dispose();
                dispose = true; 
                //SwingUtilities.getWindowAncestor(Clock.this).dispose(); 
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
        });
    }
    
    
   
    
    private Graphics2D getGraphics(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(Color.WHITE); 
        return g2D; 
    }
    
           
    public void paintComponent(Graphics g){
        super.paintComponent(g); 
        Graphics2D pen = getGraphics(g);
        //starShape.fillStarShapes(pen);
        for(int i =0; i < starShapes.length; i++){
            starShapes[i].fillStarShapes(pen); 
        }
    }
    
    
     private void startAnimating(int[] x, int[] y, int[] diameters){
        //starShape = new StarShapes(x,y,diameters);
        starShapes = new StarShapes[x.length]; 
        ticks = new int[x.length];
        for(int i = 0; i < x.length; i++){
            starShapes[i] = new StarShapes(x[i], y[i], diameters[i]); 
            ticks[i] = 0; 
        }
        
        TimerListener timerListener = new TimerListener();
        timerListener.actionPerformed(null);
        timer = new Timer(30, timerListener);
        timer.start(); 
       
    }
    
   
    //theres something wrong going on here
    public class TimerListener implements ActionListener{
            
        @Override
        public void actionPerformed(ActionEvent e) {  
            int tracker = 0; 
            
            for(int i = 0; i < final_diameters.length; i++){
              
                if(delays[i] > ticks[i]){ //draw nothing 
                    starShapes[i] = starShapes[i].resize(2); 
                } else {
                    int time = ticks[i]-delays[i]; //calcs individual time
                    int amp = (final_diameters[i])/2; 
                    int size = (int) (amp*Math.sin((time-5*Math.PI)/10)) + amp+2; 
                    starShapes[i] = starShapes[i].resize(size);     
                }
                
                for(int j = 0; j< on_tracker.length; j++){
                    if(on_tracker[j] == true){
                        tracker--; 
                    }
                }  
                
                if(tracker == 0 && timer != null){
                    timer.stop(); 
                    SwingUtilities.getWindowAncestor(Star.this).dispose();
                }
                
                ticks[i]++;
            }
            
            repaint(); 
            
        }
    
    }
   
    
}


