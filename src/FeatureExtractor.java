import java.awt.image.BufferedImage;
import java.awt.image.Raster;


public class FeatureExtractor {
	
	public FeatureExtractor()
	{
	}
	
	public TrainingVector extract(TrainingImage image)
	{
		// Get the vector.
		double[] vector = extract(image.getImage());
		
		// Wrap the vector along with whether it is positive or not.
		return new TrainingVector(image.isPositive(), vector);
	}
	
	public double[] extract(BufferedImage image)
	{
		// Get the empty vector.
		double[] vector = new double[5];
		
		double[] positionOfCentorid = position(image);
		
		// Get the features.
		vector[0] = getArea(image);
		vector[1] = getPerimeter(image);
		vector[2] = compactness(image);
		vector[3] = positionOfCentorid[0];
		vector[4] = positionOfCentorid[1];
		
		return vector;
	}
	
	private double getArea(BufferedImage image)
	{
		return Math.round(moment(image, 0, 0));
	}
	
	private double moment(BufferedImage image, int k, int l)
	{
		// Get the raster of the image.
		Raster r = image.getRaster();
		
		// Get the moment.
		double m = 0f;
		for(int i = 0; i < r.getHeight(); i++)
			for(int j = 0; j < r.getWidth(); j++)
				m += Math.pow(i, k) * Math.pow(j, l) * r.getSample(j, i, 0) / 255;
		
		return m;
	}
	
	private double [] position(BufferedImage image)
	{
		//calculate Centroid at M01
		double i = Math.round((moment(image, 0, 1))/ moment(image, 0, 0));
		//calculate Centroid at M10
		double j = Math.round((moment(image, 1, 0))/ moment(image, 0, 0));
		
		double [] Cij = {i, j};
		
		return Cij;
	}
	
	private double getPerimeter(BufferedImage image)
	{
		return getArea(image) - getArea(PostProcessor.erode(image));
	}
	
	private double compactness(BufferedImage image)
	{
		return Math.pow(getPerimeter(image), 2) / getArea(image);
	}
	

}
