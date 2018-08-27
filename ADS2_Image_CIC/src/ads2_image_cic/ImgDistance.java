package ads2_image_cic;

public class ImgDistance {
    public static float minkowskiDist(float[] hist1,float[] hist2)
    {
        float minkDist = 0;                
        for (int i=0;i<hist1.length-1;i++)
            minkDist+=Math.pow((hist1[i]-hist2[i]),2);
        return (float) Math.sqrt(minkDist);   
    }
    
    
    public static float histogramIntersectionDist(float[] hist1,float[] hist2)
    {
        float HIDist = 0;
        for (int i=0;i<hist1.length-1;i++)
            HIDist+=Math.min(hist1[i],hist2[i]);
        return HIDist;   
    }
    
    
    public static float relativeDeviationDist(float[] hist1,float[] hist2)
    {
        float RDDist = 0;
        float Numerator=0;
        float Denom1=0;
        float Denom2=0;

        for (int i=0;i<hist1.length-1;i++)
        {
            Numerator+= Math.sqrt(Math.pow((hist1[i]-hist2[i]),2));
            Denom1+=Math.sqrt(Math.pow(hist1[i],2));
            Denom2+=Math.sqrt(Math.pow(hist2[i],2));
        }
        
        RDDist=2*Numerator/(Denom1+Denom2);
        return RDDist;   
    }
}
