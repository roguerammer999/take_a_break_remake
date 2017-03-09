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
    private static JSpinner cloudControlSp;
    private static JSlider sunControl;
    private static JSpinner sunControlSp;
    private static int cloudCount = 6;
    private static ArrayList allClouds = new ArrayList(25);
    private static int sunHeightRaw = 35;
    private static int sunHeight;
    private static float sunVerticalCenter;
    private static boolean skyIsDark = false;
    tbBackground mainBG;
    
    Shape sky = new Rectangle2D.Float(0,0,1280,390);
    Shape water = new Rectangle2D.Float(0,390,1280,250);
    Shape sand = new Rectangle2D.Float(0,620,1280,180);
    
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
        allClouds = Takeabreak_Clouds.createClouds(cloudCount);
        this.add(mainBG, BorderLayout.CENTER);
        
        this.setVisible(true);
    }
    
    
    //This class represents the background.
    private class tbBackground extends JComponent
    {
        public void paint(Graphics inputGr)
        {
            Graphics2D baseGraphics = (Graphics2D)inputGr;
            baseGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            sunHeight = (int)(3.6 * sunHeightRaw);
            
            //The sun is bigger when higher in the sky.
            float sunDiameter = 60 + sunHeight/6;
            Shape sun = new Ellipse2D.Float(
                    (640-(sunDiameter/2)),
                    390 - (sunDiameter/2) - sunHeight,
                    sunDiameter, sunDiameter);
            sunVerticalCenter = 360 - sunHeight + (sunDiameter/2);
            
            //These three gradients/shapes correspond to the sky, water,
            //and sand respectively.
            GradientPaint skyGrad = (setSkyColors());
            GradientPaint waterGrad = (setWaterColors());
            GradientPaint sandGrad = (setSandColors());
            
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
        
        JLabel sunLabel = new JLabel ("Height of sun");
        sunControl = new JSlider(0,100,50);
        sunControl.setMajorTickSpacing(20);
        sunControl.setMinorTickSpacing(5);
        sunControl.setPaintTicks(true);
        sunControl.setPaintLabels(true);
        sunControl.setSnapToTicks(false);
        sunControl.addChangeListener(e -> sunSlideChange());
        sunControlSp = new JSpinner(new SpinnerNumberModel
                (sunControl.getValue(),0,100,1));
        sunControlSp.addChangeListener(e -> sunSpinChange());
        
        JLabel cloudLabel = new JLabel ("Number of clouds");
        cloudControl = new JSlider(0,25,cloudCount);
        cloudControl.setMajorTickSpacing(5);
        cloudControl.setMinorTickSpacing(1);
        cloudControl.setPaintTicks(true);
        cloudControl.setPaintLabels(true);
        cloudControl.setSnapToTicks(true);
        cloudControl.addChangeListener(e ->cloudSlideChange());
        cloudControlSp = new JSpinner(new SpinnerNumberModel
        (cloudControl.getValue(),0,25,1));
        cloudControlSp.addChangeListener(e -> cloudSpinChange());
        
        tbControls.add(sunLabel);
        tbControls.add(sunControl);
        tbControls.add(sunControlSp);
        
        tbControls.add(cloudLabel);
        tbControls.add(cloudControl);
        tbControls.add(cloudControlSp);
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
        float sunIsDark = 1F;
        if(skyIsDark == true)
            sunIsDark = 0.92F;
        int red1 = (int) (sunIsDark * (62 + (sunHeight/4)));
        int green1 = (int) (sunIsDark * (140 + (sunHeight/6)));
        int blue1 = (int) (sunIsDark * (210 + (sunHeight/10)));
        int red2 = (int) (sunIsDark * (230 - (sunHeight/10)));
        int green2 = (int) (sunIsDark * (136 + (sunHeight/4)));
        int blue2 = (int) (sunIsDark * (51 + (sunHeight/2)));
        return new GradientPaint(0,0,new Color(red1,green1,blue1),
                0,390,new Color(red2,green2,blue2));
    }
    
    //This sets linear color changes for the water color gradient.
    //The "1" colors are the top color of the gradient, "2" are bottom.
    private static GradientPaint setWaterColors ()
    {
        float sunIsDark = 1F;
        if(skyIsDark == true)
            sunIsDark = 0.92F;
        int red1 = (int) (sunIsDark * (15 + (sunHeight/8)));
        int green1 = (int) (sunIsDark * (45 + (sunHeight/4)));
        int blue1 = (int) (sunIsDark * (95 + (sunHeight/4)));
        int red2 = (int) (sunIsDark * (40 + (int)(sunHeight/3.5)));
        int green2 = (int) (sunIsDark * (90 + (sunHeight/3)));
        int blue2 = (int) (sunIsDark * (175 - (sunHeight/10)));
        return new GradientPaint(0,390,new Color(red1,green1,blue1),
                0,640,new Color(red2,green2,blue2));
    }
    
    //This sets linear color changes for the sand color gradient.
    private static GradientPaint setSandColors ()
    {
        float sunIsDark = 1F;
        if(skyIsDark == true)
            sunIsDark = 0.92F;
        int red1 = (int) (sunIsDark * (173 + sunHeight/6));
        int green1 = (int) (sunIsDark * (106 + sunHeight/3));
        int blue1 = (int) (sunIsDark * (45 + sunHeight/3));
        int red2 = (int) (sunIsDark * (89 + sunHeight/3));
        int green2 = (int) (sunIsDark * (58 + (int)(sunHeight/2.5)));
        int blue2 = (int) (sunIsDark * (27 + sunHeight/3));
        return new GradientPaint(0,640,new Color(red1,green1,blue1),
                0,800,new Color(red2,green2,blue2));
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
        boolean skyIsDarkInner = false;
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
            int bottomG = (int) (220 + (yHt/10) - (sunHeight / 30));
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
            if(ovalCloud.contains(640D,(double)sunVerticalCenter))
            {
                cloudDraw.setComposite(
                        AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.6F));
                skyIsDarkInner = true;
            }
            else
                {
                cloudDraw.setComposite(
                        AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 1F));
            }
            cloudDraw.fill(ovalCloud);
        }
        if(skyIsDarkInner == true)
            skyIsDark = true;
        else
            skyIsDark = false;
    }
    
    private void cloudSlideChange ()
    {
        cloudCount = cloudControl.getValue();
        cloudControlSp.setValue(cloudCount);
        allClouds = Takeabreak_Clouds.createClouds(cloudCount);
        mainBG.repaint();
    }
    
    private void sunSlideChange ()
    {
        sunHeightRaw = sunControl.getValue();
        sunControlSp.setValue(sunHeightRaw);
        mainBG.repaint();
    }
    
    private void cloudSpinChange ()
    {
        cloudCount = (int)cloudControlSp.getValue();
        cloudControl.setValue(cloudCount);
        mainBG.repaint();
    }
    
    private void sunSpinChange ()
    {
        sunHeightRaw = (int)sunControlSp.getValue();
        sunControl.setValue(sunHeightRaw);
        mainBG.repaint();
    }
}
