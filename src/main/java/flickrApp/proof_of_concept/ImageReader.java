package flickrApp.proof_of_concept;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;

import javax.imageio.ImageIO;

import com.flickr4java.flickr.util.IOUtilities;

public class ImageReader {

	public ImageReader() {
		
	}

	public BufferedImage readImage(String UrlString) throws IOException {
	    URL url = new URL(UrlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        InputStream in = null;
        try {
            in = conn.getInputStream();
            BufferedImage _tmp = ImageIO.read(in);
            
//            BufferedReader inReader = new BufferedReader(new InputStreamReader(in));
//            String _sb = inReader.readLine();
            
//            return null;
            return _tmp;
//            return ImageIO.read(in);
        } 
        catch (Exception _x) {
        	System.out.println("Error getting image " + _x.toString());
        	return null;
        }
        finally {
            IOUtilities.close(in);
        }
	  }
	
	public void downloadImage(String UrlString) throws IOException {
		BufferedImage img = readImage(UrlString);
		if (img != null) {
			try {
			File f = new File("/Users/ufukujo/Desktop/test.jpg");
			ImageIO.write(img,  "jpg", f );
		}
			catch (Exception _e) {
				System.out.println("Error writing image file " + _e.toString());
			}
		}
	}
	
}