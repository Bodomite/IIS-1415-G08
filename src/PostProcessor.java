import java.awt.image.BufferedImage;


public class PostProcessor 
{
	public PostProcessor()
	{
	}
	
	public BufferedImage process(BufferedImage image)
	{
		return image;
	}
	
    
	private short[] thresholdLut(int t)
    {
    	short[] lut = new short[256];
		for(int i = 0; i < (short)lut.length; i++)
		{
			if(i <= t)
				lut[i] = 255;
			else
				lut[i]= 0;
		}
		return lut;
    }
    
    private BufferedImage thresholdAnImage(BufferedImage image)
    {
    	return ImageOp.pixelop(image, thresholdLut(50));
    }
    
    private BufferedImage open(BufferedImage image, int m)
    {
    	// m is mask size.
    	return ImageOp.open(image, 3);
    }
    
    private BufferedImage close(BufferedImage image, int m)
    {
    	//m is mask size.
    	return ImageOp.close(image, 3);
    }
}
