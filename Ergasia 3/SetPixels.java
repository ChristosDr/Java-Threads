import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class SetPixels {
   public static void main(String args[]) {
	
		String fileNameR = null;
		String fileNameW = null;
		
		if (args.length != 2) {
			System.out.println("Usage: java SetPixel <file to read > <file to write");
			System.exit(1);
		}
		fileNameR = args[0];
		fileNameW = args[1];
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(fileNameR));
		} catch (IOException e) {}

		long start = System.currentTimeMillis();
		
		int numThreads = Runtime.getRuntime().availableProcessors();
		SqrtGroupThread threads[] = new SqrtGroupThread[numThreads];
		
		

		for (int i= 0; i < numThreads; i++) {
			threads[i] =  new SqrtGroupThread(i, numThreads, img.getHeight(), img);
			threads[i].start();
		}
      
		// for (int y = 0; y < img.getHeight(); y++) {
		// 	for (int x = 0; x < img.getWidth(); x++) {
		// 		//Retrieving contents of a pixel
		// 		int pixel = img.getRGB(x,y);
		// 		//Creating a Color object from pixel value
		// 		Color color = new Color(pixel, true);
		// 		//Retrieving the R G B values, 8 bits per r,g,b
		// 		int red = color.getRed();
		// 		int green = color.getGreen();
		// 		int blue = color.getBlue();
		// 		//Modifying the RGB values
		// 		red = (red + redShift)%256;
		// 		green = (green + greenShift)%256;
		// 		blue = (blue + blueShift)%256;
		// 		//Creating new Color object
		// 		color = new Color(red, green, blue);
		// 		//Setting new Color object to the image
		// 		img.setRGB(x, y, color.getRGB());
		// 	}
		// }

		for(int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {}
		}
	
	    long elapsedTimeMillis = System.currentTimeMillis()-start;
       
		try {
		  File file = new File(fileNameW);
		  ImageIO.write(img, "jpg", file);
		} catch (IOException e) {}
      
		System.out.println("Done...");
		System.out.println("time in ms = "+ elapsedTimeMillis);
   }

   static class SqrtGroupThread extends Thread
   {

	   private int myStart;
	   private int myStop;
	   private BufferedImage img;
	   //private int id;

   // constructor
   public SqrtGroupThread(int myId, int numThreads, int size, BufferedImage img)
   {
	   
	   myStart = myId * (size / numThreads);
	   myStop = myStart + (size / numThreads);
	   this.img = img;
	   //id = myId;
	   if (myId == (numThreads - 1)) myStop = size;
   }

   // thread code
   public void run()
   {

	int redShift = 100;
		int greenShift = 100;
		int blueShift = 100;

	for (int y = myStart; y < myStop; y++) {
		for (int x = 0; x < img.getWidth(); x++) {
			//Retrieving contents of a pixel
			int pixel = img.getRGB(x,y);
			//Creating a Color object from pixel value
			Color color = new Color(pixel, true);
			//Retrieving the R G B values, 8 bits per r,g,b
			int red = color.getRed();
			int green = color.getGreen();
			int blue = color.getBlue();
			//Modifying the RGB values
			red = (red + redShift)%256;
			green = (green + greenShift)%256;
			blue = (blue + blueShift)%256;
			//Creating new Color object
			color = new Color(red, green, blue);
			//Setting new Color object to the image
			img.setRGB(x, y, color.getRGB());
		}
	}
		   
   }
   }

}
