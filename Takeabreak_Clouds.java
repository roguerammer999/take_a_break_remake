import java.util.ArrayList;

public class Takeabreak_Clouds
{
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
    //cloud colors of existing shapes can be changed without drawing new shapes.
    public static ArrayList createClouds(int cloudCount)
    {
        ArrayList allClouds = new ArrayList(cloudCount);
        allClouds.clear();
        for(int counter = 0; counter < cloudCount; counter++)
        {
            int [] cloudComponent = cloud();
            allClouds.add(cloudComponent);
        }
        return allClouds;
    }
}
