import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class takeabreak extends JFrame
{
    public static void main (String args[])
    {
        new takeabreak();
    }
    
    private static JSlider cloudControl;
    private static JSlider sunControl;
    private static int cloudCount;
    private static int sunHeight;
    private static GradientPaint skyGrad;
    tbBackground mainBG = new tbBackground();
    
    
    public takeabreak()
    {
        //The strange numbers for the size are based around
        //an output area of 1280x800 pixels.  The margins of the frame
        //require it to be somewhat larger.  I use 1280x800 to calculate
        //the location of the shapes.
        this.setSize(1298,945);        
        this.setLocation(370,120);
        this.setTitle("Take a break on the beach!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainBG, BorderLayout.CENTER);
        
        
        
        this.add(controls(), BorderLayout.SOUTH);
        
        this.setVisible(true);
    }
    
    
    //This class represents the background.
    private class tbBackground extends JComponent
    {
        public void paint(Graphics inputGr)
        {
            sunHeight = (int) (3.6 * (sunControl.getValue()));
            cloudCount = cloudControl.getValue();
            Graphics2D baseGraphics = (Graphics2D)inputGr;
            Shape sky = new Rectangle2D.Float(0,0,1280,390);
            Shape sun = new Ellipse2D.Float(610,360 - sunHeight,60,60);
            Shape sunCorona = new Ellipse2D.Float(595,345 - sunHeight,90,90);
            Shape water = new Rectangle2D.Float(0,390,1280,250);
            Shape sand = new Rectangle2D.Float(0,620,1280,180);
            
            //These three gradients correspond to the sky, water, and sand
            //respectively.
            GradientPaint skyGrad = (setSkyColors(sunHeight));
            GradientPaint waterGrad = (setWaterColors(sunHeight));
            GradientPaint sandGrad = (setSandColors(sunHeight));
            
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
                (AlphaComposite.SRC_OVER, 0.37F));
            baseGraphics.fill(sunCorona);
            baseGraphics.setComposite(AlphaComposite.getInstance
                (AlphaComposite.SRC_OVER, 1F));
            baseGraphics.setPaint(new GradientPaint(
                0,360,new Color(255,244,228),
                0,390,new Color(227,215,176)));
            baseGraphics.fill(sun);
            
            //Drawing in the water and sand over the sun
            baseGraphics.setPaint(waterGrad);
            baseGraphics.fill(water);
            baseGraphics.setPaint(sandGrad);
            baseGraphics.fill(sand);
            
            
            drawClouds(baseGraphics);
        }
    }
    
    //The "controls" panel currently has two sliders, one to control
    //the number of clouds and the other to set the height of the sun
    //in the sky.  The sun height controller represents percents, with "100"
    //meaning the sun is at the top and "0" meaning it is setting.
    private JPanel controls ()
    {
        JPanel tbControls = new JPanel();
        
        JLabel cloudLabel = new JLabel ("Number of clouds");
        cloudControl = new JSlider(0,25,7);
        cloudControl.setMajorTickSpacing(5);
        cloudControl.setMinorTickSpacing(1);
        cloudControl.setPaintTicks(true);
        cloudControl.setPaintLabels(true);
        cloudControl.setSnapToTicks(true);
        cloudControl.addChangeListener(e ->cloudSlideChange());
        
        JLabel sunLabel = new JLabel ("Height of sun");
        sunControl = new JSlider(0,100,50);
        sunControl.setMajorTickSpacing(20);
        sunControl.setMinorTickSpacing(5);
        sunControl.setPaintTicks(true);
        sunControl.setPaintLabels(true);
        sunControl.setSnapToTicks(true);
        sunControl.addChangeListener(e -> sunSlideChange());
        
        tbControls.add(sunLabel);
        tbControls.add(sunControl);
        
        tbControls.add(cloudLabel);
        tbControls.add(cloudControl);
        return tbControls;
    }
    
    //This class will represent waves on the water.
    private class wave extends JComponent
    {
        
    }
    
    //This method sets linear color changes for the gradient colors of the sky.
    private static GradientPaint setSkyColors (int sunHeight)
    {
        int red1 = 62 + (sunHeight/4);
        int green1 = 140 + (sunHeight/6);
        int blue1 = 210 + (sunHeight/10);
        int red2 = 210 - (sunHeight/10);
        int green2 = 132 + (sunHeight/4);
        int blue2 = 51 + (sunHeight/2);
        return new GradientPaint(0,0,new Color(red1,green1,blue1),
                0,390,new Color(red2,green2,blue2));
    }
    
    //This sets linear color changes for the water color gradient.
    private static GradientPaint setWaterColors (int sunHeight)
    {
        int red1 = 15 + (sunHeight/8);
        int green1 = 45 + (sunHeight/4);
        int blue1 = 95 + (sunHeight/4);
        int red2 = 40 + (int)(sunHeight/3.5);
        int green2 = 90 + (sunHeight/3);
        int blue2 = 175 - (sunHeight/10);
        return new GradientPaint(0,390,new Color(red1,green1,blue1),
                0,640,new Color(red2,green2,blue2));
    }
    
    //This sets linear color changes for the sand color gradient.
    private static GradientPaint setSandColors (int sunHeight)
    {
        int red1 = 180 + sunHeight/6;
        int green1 = 111 + sunHeight/3;
        int blue1 = 56 + sunHeight/3;
        int red2 = 100 + sunHeight/3;
        int green2 = 65 + (int)(sunHeight/2.5);
        int blue2 = 35 + sunHeight/3;
        return new GradientPaint(0,640,new Color(red1,green1,blue1),
                0,800,new Color(red2,green2,blue2));
    }
    
    //This class represent simple oval clouds in the air by providing
    //height, width, upper left corner x/y-coordinates, and color shade.
    //It randomizes "xLRCorner" and "yLRCorner" as x- and y-coordinates
    //for the lower right corner of the cloud, then randomizes width
    //and height for 175 and 40 max, respectively.
    private static int [] cloud ()
    {
        int xLRCorner = (int)(Math.random()*1280);
        int yLRCorner = (int)(Math.random()*330);
        int width = 55 + (int)(Math.random()*175);
        
        //This block ensures that the program does not attempt to draw
        //anything outside of the boundaries (e.g. negative coordinates)
        if(width > xLRCorner)
            width = xLRCorner;
        int height = 20 + (int)(Math.random()*40);
        if(height > yLRCorner)
            height = yLRCorner;
        if(height<10)
            height = 10;
        if((float)width/(float)height < 3)
            width = (int)(2.5 * height);
        if((float)width/(float)height > 9)
            width = 8 * height;
        
        //With the size boundaries and lower right corners set,
        //The upper left corner's coordinates are calculated.
        int xULCorner = xLRCorner - width;
        int yULCorner = yLRCorner - height;
        
        //Color shade is determined on height.  A cloud higher in the sky
        //will be darker.
        int cloudDarkness = 250 - (int)(yULCorner/6);
        return new int [] {xULCorner, yULCorner, width, height,
        cloudDarkness};
    }
    
    //This class will represent ships on the water.
    private class ships extends JComponent
    {
        
    }
    
    //This takes a cloud object (an int array) and creates an ellipse
    //using the height/width/coordinate parameters, then fills it
    //with the color but slightly blue/red tint.
    private static void drawClouds(Graphics inputGr)
    {
        Graphics2D cloudDraw = (Graphics2D) inputGr;
        cloudCount = cloudControl.getValue();
        for(int counter = 0; counter < cloudCount; counter++)
        {
            int [] newCloud = cloud();
            Shape ovalCloud = new Ellipse2D.Float(newCloud[0],
                    newCloud[1],newCloud[2],newCloud[3]);
            cloudDraw.setColor(
                    new Color(newCloud[4]-4, newCloud[4]-10, newCloud[4]));
            cloudDraw.fill(ovalCloud);
        }
    }
    
    private void cloudSlideChange ()
    {
        mainBG.repaint();
    }
    
    private void sunSlideChange ()
    {
        sunHeight = sunControl.getValue();
        mainBG.repaint();
    }
}
