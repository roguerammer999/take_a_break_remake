import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class takeabreak extends JFrame
{
    public static void main (String args[])
    {
        new takeabreak();
    }
    
    public takeabreak()
    {
        //The strange numbers for the size are based around
        //an output area of 1280x800 pixels.  The margins of the frame
        //require it to be somewhat larger.  I use 1280x800 to calculate
        //the location of the shapes.
        this.setSize(1298,865);
        this.setLocation(350,190);
        this.setTitle("Take a break on the beach!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.add(new tbBackground());
        
        this.setVisible(true);
    }
    
    
    //This class represents the background.
    private class tbBackground extends JComponent
    {
        public void paint(Graphics inputGr)
        {
            Graphics2D baseGraphics = (Graphics2D)inputGr;
            Shape sky = new Rectangle2D.Float(0,0,1280,350);
            Shape sun = new Ellipse2D.Float(610,320,60,60);
            Shape sunCorona = new Ellipse2D.Float(595,305,90,90);
            Shape water = new Rectangle2D.Float(0,350,1280,270);
            Shape sand = new Rectangle2D.Float(0,620,1280,200);
            
            //These three gradients correspond to the sky, water, and sand
            //respectively.
            GradientPaint skyGrad = (new GradientPaint
                (0,0,new Color(56,150,218),
                0,350,new Color(220,127,51)));
            GradientPaint waterGrad = (new GradientPaint
                (0,350,new Color(6,39,88),
                0,620,new Color(43,137,174)));
            GradientPaint sandGrad = (new GradientPaint
                (0,620,new Color(223,172,102),
                0,800,new Color(162,131,72)));
            
            //Drawing in the sky (it will be covered by the sun)
            baseGraphics.setPaint(skyGrad);
            baseGraphics.fill(sky);
            
            //For the sun and "sunCorona" (a blur around the sun),
            //I arbitrarily decided to define the gradient right when
            //filling the shape.
            baseGraphics.setPaint(new GradientPaint(
                0,305,new Color(184,172,148),
                0,350,new Color(166,153,134)));
            baseGraphics.setComposite(AlphaComposite.getInstance
                (AlphaComposite.SRC_OVER, 0.31F));
            baseGraphics.fill(sunCorona);
            baseGraphics.setComposite(AlphaComposite.getInstance
                (AlphaComposite.SRC_OVER, 1F));
            baseGraphics.setColor(new Color(255,241,178));
            baseGraphics.setPaint(new GradientPaint(
                0,305,new Color(255,247,172),
                0,350,new Color(218,186,137)));
            baseGraphics.fill(sun);
            
            //Drawing in the water and sand over the sun
            baseGraphics.setPaint(waterGrad);
            baseGraphics.fill(water);
            baseGraphics.setPaint(sandGrad);
            baseGraphics.fill(sand);
        }
    }
    
    //This class will represent waves on the water.
    private class waves extends JComponent
    {
        
    }
    
    //This class will represent clouds floating by in the air.
    private class clouds extends JComponent
    {
        
    }
    
    //This class will represent ships on the water.
    private class ships extends JComponent
    {
        
    }
}
