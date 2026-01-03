/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.text.Caret;

/**
 *
 * @author victoria
 */
public class Clock extends JPanel{
    int tick; 
    Timer timer; 
    double time; 
    int seconds;
    Font font = new Font(Font.SERIF, Font.PLAIN, 60);
    String text; 
    
    BufferedImage timer_display = new BufferedImage(400, 140, TYPE_INT_ARGB_PRE); //w, h, imageType
    
    
    public Clock (){
        text = "00:00:00";
        
        repaint();
        setFocusable(true);
        
        this.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                text = "";
                repaint();
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
        

        
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char letter = e.getKeyChar();
                if(letter == '\n'){
                    if(text.equalsIgnoreCase("quit")){
                        SwingUtilities.getWindowAncestor(Clock.this).dispose();
                    } else if(text.contains(":")){
                        int[] colon_counter = {-1,-1};
                        int index = -1;
                        double time; 
                        
                        for(int i =0; i< text.length(); i++){
                            if(text.substring(i,i+1).equals(":")){
                                index++; //it will be 1 at greatest
                                colon_counter[index] = i; //fills colon_counter w/pos of colon
                            }
                        }
                        
                        if(index == 0){
                            double minutes = Double.parseDouble(text.substring(0,colon_counter[0]));
                            double seconds = Double.parseDouble(text.substring(colon_counter[0]+1, text.length()));
                            time = minutes + seconds/60; 
                        } else { //if index equals 1
                            double hours = Double.parseDouble(text.substring(0,colon_counter[0]));
                            double minutes = Double.parseDouble(text.substring(colon_counter[0]+1, colon_counter[1]));
                            double seconds = Double.parseDouble(text.substring(colon_counter[1]+1, text.length()));
                            time = hours*60 + minutes + seconds/60; 
                            
                        }
                        
                        setTime(time); //in minutes

                    } else { //going to be in minutes
                        setTime(Double.parseDouble(text));
                    }
                } else if (letter == '\b'){
                    text = text.substring(0, text.length()-1);
                    repaint();
                }else {
                    String input = String.valueOf(letter); 
                    text += input;
                    repaint();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
            
        });
    }

    
    public void setTime(double t){
        time = t; 
        seconds = (int) (t*60); 
        startAnimating();
    }
    
    private Graphics2D getGraphics(Graphics g){

        
        Graphics2D pen = timer_display.createGraphics();
        pen.setColor(new Color(1f, 1f, 1f, 1f)); 
        pen.setFont(font);
        pen.setBackground(new Color(0, true));
        return pen; 
        
        
    }
    
    private void blur(Graphics2D pen, int blurPixels){
        //one way horizontal
        float[] data = new float[blurPixels*blurPixels];
        for(int i = 0; i < data.length; i++){
            data[i] = (float) (1.0/data.length);
        }
        
        //rgba(37, 150, 190)
        Kernel kernel = new Kernel(blurPixels, blurPixels, data);
        ConvolveOp blurFilter = new ConvolveOp(kernel);
        timer_display = blurFilter.filter(timer_display, null);
  
        
        pen.drawString(text, 40, 80);

    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g); //makes sure we have a background
        Graphics2D pen = getGraphics(g);
        pen.clearRect(0, 0, timer_display.getWidth(), timer_display.getHeight());
        
        //rgba(255, 221, 189)
        
        pen.setColor(new Color(255,221,189)); //orange color 
        pen.drawString(text, 40,80); 
        
        //blur(pen, 2);
        g.drawImage(timer_display, 0, 0, this);
        
        for(int i = 11; i < 12; i*=2){
            blur(pen, i);
            g.drawImage(timer_display, 0, 0, this);
            
        }
        
        pen = getGraphics(g);
        pen.drawString(text, 40, 80); //gotta do this first so that there is something in timer_display
        g.drawImage(timer_display, 0,0, this);
        
        for(int i = 2; i < 10; i*=2){
            blur(pen, i);
            g.drawImage(timer_display, 0, 0, this);
            
        }
        
        pen.dispose(); 
    }
    
    private void startAnimating(){
        tick = 0; 
        timer = new Timer(1000, new TimerListener()); //updates every second
        timer.start(); 
        
    }
    
    public void createStars(){
        JFrame frame = new JFrame(){ //gets rid of extra stuff 
            {
                getRootPane().putClientProperty("Window.shadow", Boolean.FALSE);
                setAlwaysOnTop(true);
                setUndecorated(true);
                setBackground(new Color(0, true));                        
            }
        }; 
        Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds();

        int screen_x = screen.width; //2500; //2880
        int screen_y = screen.height;//1500; //1864
        frame.setBounds(0, 0, screen_x, screen_y);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        int num_of_stars = 110; 

        int[] delays = new int[num_of_stars]; 
        int[] diameters = new int[num_of_stars]; 
        int[] x = new int[num_of_stars]; 
        int[] y = new int[num_of_stars]; 
        Star s1;
        for(int i = 0; i < num_of_stars; i++){
            delays[i] = i; 
            diameters[i] = (int) (20+ Math.random()*55); 
            //times diameter by 3 bc of lines
            x[i] = (int) (Math.random()*(screen_x-(5*diameters[i])) +2.5*diameters[i]); 
            y[i] = (int) (Math.random()*(screen_y-(5*diameters[i])) +2.5*diameters[i]); 

        }

        s1 = new Star(delays, diameters, x, y);
        s1.setBackground(new Color(0, true)); 
        frame.setContentPane(s1);
        frame.setVisible(true);
    }
    
    
    public class TimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.print(seconds);
            if(seconds == 0){
                createStars(); 
            }
            seconds--; 
           
            
            String output = "";
            int hours = (int) (seconds/3600);
            int minutes = (int) ((seconds - hours*3600)/60); 
            int sec = (int) (seconds - (minutes*60 + hours*3600)); 
            
            
            if (hours > 0){ //if hours are there             
                output = "" + hours + ":"; 
                
                if(minutes <=9){
                    output += "0" + minutes + ":";
                } else {
                    output += minutes + ":";
                }
                if(sec <=9){
                    output += "0" + sec; 
                } else {
                    output += sec;
                }
                
            } else if (minutes >0){
                //sec = (int) (seconds - (minutes*60));
                output = "   " + minutes + ":";
                
                if(sec <=9){
                    output += "0" + sec; 
                } else {
                    output += "" + sec;
                }
            } else if (sec >0){
                sec = (int) (seconds); 
                if(sec <=9){
                    output = "     0:0" + sec; 
                } else {
                    output = "     0:" + sec;
                }
            } else{ //if(seconds <= 0)
                if(seconds%2 == 0){
                    output = "   00:00";
                } else {
                    output = "     :  ";
                }
            } 
            
            if(Star.dispose){ //quits the program 
                timer.stop();
                SwingUtilities.getWindowAncestor(Clock.this).dispose();
            }
            
            text = output;
            repaint();
        }
        
    }
    
    
    public static void main (String...args){ 
        JFrame frame = new JFrame(){
                    {
                        getRootPane().putClientProperty("Window.shadow", Boolean.FALSE);
                        setAlwaysOnTop(true);
                        setUndecorated(true);
                        setBackground(new Color(255, true));  
                        setLayout(null); 
                    }
                }; 
        Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        
        int screen_x = screen.width/5; 
        int screen_y = screen.height/5;
        int start_x = (int) (screen_x*4); 
        int start_y = (int) (screen_y*4); 
        
        frame.setBounds(start_x-100, start_y+40, screen_x+70, screen_y-50);
        
        //frame.setBounds(0, 0, screen.width, screen.height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        
        Clock clock = new Clock();
        
        clock.setBackground(new Color(0, true));
        frame.setContentPane(clock); //adding clock to the frame
        //frame.pack();
        frame.setVisible(true);
        
      
    }
    
}