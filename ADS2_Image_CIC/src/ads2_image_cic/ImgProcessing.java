package ads2_image_cic;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImgProcessing {
    
    /*Get RGB value at location x and y of an image*/
    public static int[] GetRGB (BufferedImage img, int x, int y){
        int[] rgb = {-1,-1,-1};
        
        //get image size
        int H = img.getHeight();
        int W = img.getWidth();

        if (x>=W || y>=H)
            System.err.println("The index is out of range."+
                               " The size of the image: Width="+W+" Height="+H);
        else  {
            int p = img.getRGB(x,y);
            // get red
            rgb[0] = (p>>16) & 0xff; 
            // get green
            rgb[1] = (p>>8) & 0xff; 
            // get blue
            rgb[2] = p & 0xff;
        }
        
        //Return -1 as error flag
        //Return 3 element array as r,g,b 
        return rgb;
    }
    
    /*scale one image by using a scale factor. The factor is larger than 0. The 
     *image will scale down if the factor is smaller than 1 and scale up if the 
     *factor is larger than 1*/
    public static BufferedImage Scale(BufferedImage sbi, double scale) 
    {
        BufferedImage dbi = null;
        if(sbi != null) {
            int dWidth = (int)(scale*sbi.getWidth());
            int dHeight = (int)(scale*sbi.getHeight());
            dbi = new BufferedImage(dWidth, dHeight, sbi.getType());
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
            g.drawRenderedImage(sbi, at);
        }
        return dbi;
    }
    
    /*Get HSB image clolour histogram*/
    public static float[] Histogram(BufferedImage img){
        //Number of bins in the histogram
        final int bin_1 = 360;
        final int bin_2 = 20;
        final int bin_3 = 20;
        float[] hist = new float [bin_1+bin_2+bin_3];
        if (img!=null){
            int W = img.getWidth();
            int H = img.getHeight();
            int[] rgb;
            float[] hsb;
            //8-bit image has 256 colour in each channel
            for (int i=0; i<H; i++)
                for (int j=0; j<W; j++)
                {
                    rgb = GetRGB(img,j,i);
                    hsb = Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null);
                    hist[(int)(hsb[0]*(bin_1-1))]++;
                    hist[(int)(hsb[1]*(bin_2-1))+bin_1]++;
                    hist[(int)(hsb[2]*(bin_3-1))+bin_1+bin_2]++;            
                }
        }
        return hist;
    }
    
    /*Histogram compare as image distance*/
    public static float Compare(BufferedImage img1, BufferedImage img2){
        float[] hist1 = ImgProcessing.Histogram(img1);
        float[] hist2 = ImgProcessing.Histogram(img2);
        return ImgDistance.minkowskiDist(hist1, hist2);
    }
    
}
