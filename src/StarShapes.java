
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author victoria
 */
public class StarShapes {

    Shape circle; 
    RadialGradientPaint circle_blur;
    Stroke stroke; 
    RadialGradientPaint line_blur;
    Rectangle rhombus; 
    
    int diameter; 
    int center_x;  
    int center_y;
    int line_length;
    
    //int final_diameter; 
    
    public StarShapes(int x, int y, int d){
         //public Object[] createStar(int x, int y, int d){
        diameter = d; 
        center_x = x+(diameter/2); 
        center_y = y+(diameter/2); 
        
//        int start_x = center_x - (diameter/2); 
//        int start_y = center_y - (diameter/2); 
//        circle = new Ellipse2D.Double(start_x, start_y, diameter, diameter); 
        
        circle = new Ellipse2D.Double(x, y, diameter, diameter);
        //creating circle blur
        Point2D center = new Point2D.Float(center_x,center_y); 
        float radius = diameter/2; 
        float[] dist = {0f,0.6f}; 
        //Color[] color = {Color.WHITE, new Color(0,0,0,0)}; 
        Color[] color = {Color.WHITE, new Color(255,251,0,2)}; 
        circle_blur = new RadialGradientPaint(center, radius, dist, color); 
        
        stroke = new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1); 
        line_length = (int) (diameter * 0.75); 
        line_blur = new RadialGradientPaint(center, line_length, dist, color); 
       
        int width = (int) (radius/2);
        rhombus = new Rectangle((int) (center_x - 0.25*radius), (int) (center_y - 0.25*radius), width, width);
        
    }
    
    public void update(int new_size){
        diameter = new_size; 
        int start_x = center_x - (new_size/2); 
        int start_y = center_y - (new_size/2); 
        circle = new Ellipse2D.Double(start_x, start_y, diameter, diameter);
        
        
    }
    
    public void fillStarShapes(Graphics2D pen){
        pen.setPaint(circle_blur); 
        pen.fill(circle);
        pen.setStroke(stroke);
        pen.setPaint(line_blur); 
        pen.drawLine(center_x, center_y+line_length, center_x, center_y-line_length);
        pen.drawLine(center_x + line_length, center_y, center_x - line_length, center_y);
        pen.rotate(Math.PI/4, center_x, center_y);
        pen.fill(rhombus); 
        pen.rotate(-Math.PI/4, center_x, center_y);
        
    }
    
    public StarShapes resize(int size){
        int start_x = center_x - (size/2); 
        int start_y = center_y - (size/2); 
        StarShapes newStar = new StarShapes(start_x, start_y, size);
        return newStar; 
    }
    
}
