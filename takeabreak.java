import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class takeabreak extends JFrame
{
    public static void main (String args[])
    {
        new takeabreak();
    }
    
    private static JSlider cloudControl;
    private static JSlider sunControl;
    private static int cloudCount = 6;
    private static ArrayList allClouds = new ArrayList(25);
    private static int sunHeight;
    tbBackground mainBG;
    
    
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
        
        mainBG = new tbBackground();
        
        this.add(controls(), BorderLayout.SOUTH);
        createClouds();
        this.add(mainBG, BorderLayout.CENTER);
        
        this.setVisible(true);
    }
    
    
    //This class represents the background.
    private class tbBackground extends JComponent
    {
        public void paint(Graphics inputGr)
        {
            sunHeight = (int) (3.6 * (sunControl.getValue()));
            Graphics2D baseGraphics = (Graphics2D)inputGr;
                        
            
            //The sun is bigger when higher in the sky.
            //The part of the sun diameter formula that multiplies by 2
            //and divides by 12 is to ensure the diameter is always
            //an even integer.
            int sunDiameter = 60 + ( 2 * (int)(sunHeight)/12 );
            Shape sun = new Ellipse2D.Float(
                    (640-(sunDiameter/2)),
                    390 - (sunDiameter/2) - sunHeight,
                    sunDiameter, sunDiameter);
            
            //These three gradients/shapes correspond to the sky, water,
            //and sand respectively.
            GradientPaint skyGrad = (setSkyColors());
            Shape sky = new Rectangle2D.Float(0,0,1280,390);
            GradientPaint waterGrad = (setWaterColors());
            Shape water = new Rectangle2D.Float(0,390,1280,250);
            GradientPaint sandGrad = (setSandColors());
            Shape sand = new Rectangle2D.Float(0,620,1280,180);
            
            //Drawing the sky (it will be covered by the sun)
            baseGraphics.setPaint(skyGrad);
            baseGraphics.fill(sky);
            
            drawCorona(baseGraphics);
            
            //Drawing the sun
            baseGraphics.setComposite(AlphaComposite.getInstance
                (AlphaComposite.SRC_OVER, 1F));
            baseGraphics.setColor(Color.WHITE);
            baseGraphics.fill(sun);
            
            //Drawing the water and sand over the sun/corona
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
        cloudControl = new JSlider(0,25,cloudCount);
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
    
    //This class will represent ships on the water.
    private class ship extends JComponent
    {
        
    }
    
    
    
    //This method sets linear color changes for the gradient colors of the sky.
    //The "1" colors are the top color of the gradient, "2" are bottom.
    private static GradientPaint setSkyColors ()
    {
        int red1 = 62 + (sunHeight/4);
        int green1 = 140 + (sunHeight/6);
        int blue1 = 210 + (sunHeight/10);
        int red2 = 230 - (sunHeight/10);
        int green2 = 136 + (sunHeight/4);
        int blue2 = 51 + (sunHeight/2);
        return new GradientPaint(0,0,new Color(red1,green1,blue1),
                0,390,new Color(red2,green2,blue2));
    }
    
    //This sets linear color changes for the water color gradient.
    //The "1" colors are the top color of the gradient, "2" are bottom.
    private static GradientPaint setWaterColors ()
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
    private static GradientPaint setSandColors ()
    {
        int red1 = 173 + sunHeight/6;
        int green1 = 106 + sunHeight/3;
        int blue1 = 45 + sunHeight/3;
        int red2 = 89 + sunHeight/3;
        int green2 = 58 + (int)(sunHeight/2.5);
        int blue2 = 27 + sunHeight/3;
        return new GradientPaint(0,640,new Color(red1,green1,blue1),
                0,800,new Color(red2,green2,blue2));
    }
    
    
    
    //This class represents simple oval clouds in the air.  It defines
    //dimensions and location, as well as two sets of RGB values for a gradient,
    //one set for the top part of the cloud and one for the bottom.
    private static int [] cloud ()
    {
        //Location/dimensions are semi-randomized.  The width is 175-230,
        //and height is 2.7 to 6.7 times less.  Clouds near the horizon
        //can have less width and can be flatter.
        int xLRCorner = 185 + (int) (Math.random()*1095);
        int yLRCorner = 30 + (int) (Math.random()*320);
        int width = 70 + (int)
                (Math.random() *
                (185-(yLRCorner/5))
                );
        int height = (int)
                (width /
                (2.7 + (Math.random()*4) + (yLRCorner/150))
                );

        //With the size boundaries and lower right corners set,
        //the upper left corner's coordinates are calculated.
        int xULCorner = xLRCorner - width;
        int yULCorner = yLRCorner - height;
        
        return new int [] {xULCorner, yULCorner, width, height};
    }
    
    //The createClouds method redraws cloud locations.  If it is not used,
    //cloud colors of existing shapes can be changed without draiwng new shapes.
    private static void createClouds()
    {
        allClouds.clear();
        for(int counter = 0; counter < cloudCount; counter++)
        {
            int [] cloudComponent = cloud();
            allClouds.add(cloudComponent);
        }
    }
    
    
    //This draws the sun's corona based on the height of the sun.  A higher sun
    //results in a larger, brighter corona.  At sunset, the corona diminishes
    //dramatically.
    private static void drawCorona(Graphics inputGr)
    {
        Graphics2D coroGraphics = (Graphics2D) inputGr;
        //The part of the corona diameter formula that multiplies by 2
        //and divides by 12 is to ensure the diameter is always an even integer.
        int coronaDiam = 100 + ( 2 * (int)(sunHeight-30)/12 );
        //The "if" condition causes the corona to shrink dramatically
        //as the sun becomes hidden by the horizon.
        if(sunHeight<=30)
            coronaDiam = 60 + (int)(sunHeight/3 * 4);
        int coronaX = 640 - (coronaDiam/2);
        int coronaY = 390 - (coronaDiam/2);
        Shape sunCorona = new Ellipse2D.Float(coronaX,coronaY - sunHeight,
                coronaDiam,coronaDiam);
        Color coronaColor = new Color(
                243 + (sunHeight/30),
                237 + (sunHeight/20),
                255 -   (int)(180 / ( (sunHeight/20) + 1)));
        
        coroGraphics.setColor (coronaColor);
        coroGraphics.setComposite(AlphaComposite.getInstance
                (AlphaComposite.SRC_OVER, 0.9F));
            coroGraphics.fill(sunCorona);
    }
    
    
    //This takes a cloud object (an int array stored in ArrayList cloudCount)
    //and creates an ellipse using the height/width/coordinate parameters.
    private static void drawClouds(Graphics inputGr)
    {
        Graphics2D cloudDraw = (Graphics2D) inputGr;
        for(int counter = 0; counter < cloudCount; counter++)
        {
            int [] newCloud = (int []) allClouds.get(counter);
            Shape ovalCloud = new Ellipse2D.Float(newCloud[0],
                    newCloud[1],newCloud[2],newCloud[3]);
            
            //These formulas change the cloud colors based on height over
            //horizon of cloud ("yHT") and sun (sunHeight).  "top" variables
            //are RGB values of the top half of the cloud, with "bottom"
            //as the bottom half.  These form a gradient.
            //Maximum yHt is 340, max sunHeight is 360
            int yHt = newCloud[1];
            int topR = (int) (115 - (yHt/10) + (sunHeight * 130/360));
            int topG = (int) (100 - (yHt/10) + (sunHeight * 150/360));
            int topB = (int) (120 - (yHt/12) + (sunHeight * 130/360));
            int bottomR = (int) (225 + (yHt/12) - (sunHeight / 30));
            int bottomG = (int) (210 + (yHt/10) - (sunHeight / 30));
            int bottomB = (int) (145 + (yHt/12) + (sunHeight / 5));


            //This draws the gradient.  When the sun is high in the sky,
            //the gradient starts at the top of the cloud.  A lower sun
            //has the gradient start lower than the top, with a maximum of
            //40% down.  At sunset, clouds are top color dominant.
            cloudDraw.setPaint(new GradientPaint(
                newCloud[0],
                    newCloud[1] + (float)
                            //formula for cloud darkening
                            ( (0.4F * newCloud[3]) * ((360F-sunHeight))/360F),
                    new Color(topR,topG,topB),
                newCloud[0],
                    newCloud[1] + newCloud[3],
                    new Color(bottomR,bottomG,bottomB)));
            cloudDraw.fill(ovalCloud);
        }
    }
    
    private void cloudSlideChange ()
    {
        cloudCount = cloudControl.getValue();
        createClouds();
        mainBG.repaint();
    }
    
    private void sunSlideChange ()
    {
        sunHeight = sunControl.getValue();
        mainBG.repaint();
    }
}
