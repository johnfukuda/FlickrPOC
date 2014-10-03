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

	private PropertyHandler ph;
	public ImageReader() {
		ph = PropertyHandler.getInstance();
	}

	public BufferedImage readImage(String UrlString) throws IOException {
	    URL url = new URL(UrlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        InputStream in = null;
        try {
            in = conn.getInputStream();
//            BufferedImage _tmp = ImageIO.read(in);
//            
//
//            return _tmp;
            return ImageIO.read(in);
        } 
        catch (Exception _x) {
        	System.out.println("Error getting image " + _x.toString());
        	return null;
        }
        finally {
            IOUtilities.close(in);
        }
	  }
	
	public void downloadImage(String UrlString, String fileName) throws IOException {
		BufferedImage img = readImage(UrlString);
		if (img != null) {
			String path = ph.getValue("downloadPath").trim();
			File f = new File(path + fileName);
			ImageIO.write(img,  "jpg", f );
		}
	}
	
}