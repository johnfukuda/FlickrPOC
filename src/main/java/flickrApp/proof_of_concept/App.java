package flickrApp.proof_of_concept;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Scanner;

import org.scribe.model.Token;
import org.scribe.model.Verifier;

import com.flickr4java.flickr.*;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.collections.Collection;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;
import com.flickr4java.flickr.test.*;
import com.flickr4java.flickr.util.IOUtilities;
import com.flickr4java.flickr.RequestContext;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FlickrException
    {
    	
        Properties properties = null;
        InputStream in = null;
			
			try {
	            in = App.class.getClassLoader().getResourceAsStream("setup.properties");
	// getClass() only works in an object context
	            properties = new Properties();
	            properties.load(in);
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            IOUtilities.close(in);
	        }
        
        
        String apiKey = properties.getProperty("apiKey");
		 String sharedSecret = properties.getProperty("secret");
        
		REST rest = new REST();
        Flickr f = new Flickr(apiKey, sharedSecret, rest);
        
        System.out.println("API Key is:  " + apiKey + ", Shared secret is:  " + sharedSecret);
//        RequestContext requestContext = RequestContext.getRequestContext();
        
        
//        TestInterface testInterface = f.getTestInterface();
//        java.util.HashMap<String, String> parms = new java.util.HashMap<String, String>();
//        parms.put("oauth_callback", "oob");
//        parms.put("oauth_signature", "HMZTkxRfAu8wc9JIMGdfjKdmQ7k=");
//        parms.put("oauth_version", "1.0");
//        parms.put("oauth_nonce", "1187931097");
//        parms.put("oauth_signature_method", "HMAC-SHA1");
//        parms.put("oauth_consumer_key", "4e6c1861b256cf47fc435d71dc79edac");
//        parms.put("oauth_timestamp", "1405111445");
//        testInterface.echo(parms);
//        testInterface.login();
        
        
//        SearchParameters params = new SearchParameters();
//        params.setText("test");
//        PhotosInterface photosInterface = f.getPhotosInterface();
//        PhotoList<Photo> photoList = photosInterface.search(params, 10, 1);
        
        
        AuthInterface authInterface = f.getAuthInterface();
        Token token = authInterface.getRequestToken();
//        auth.setPermission(PermissionProperty("token"));
//        requestContext.setAuth(auth);
        System.out.println("token: " + token);
//
        String url = authInterface.getAuthorizationUrl(token, Permission.READ);
        System.out.println("Follow this URL to authorise yourself on Flickr");
        System.out.println(url);
        System.out.println("Paste in the token it gives you:");
        System.out.print(">>");
//
        Scanner scanner = new Scanner(System.in);
        String tokenKey = scanner.nextLine();
        scanner.close();

        Token accessToken = authInterface.getAccessToken(token, new Verifier(tokenKey));
        System.out.println("Authentication success");

        Auth auth = authInterface.checkToken(accessToken);

        // This token can be used until the user revokes it.
        System.out.println("Token: " + accessToken.getToken());
        System.out.println("nsid: " + auth.getUser().getId());
        System.out.println("Realname: " + auth.getUser().getRealName());
        System.out.println("Username: " + auth.getUser().getUsername());
        System.out.println("Permission: " + auth.getPermission().getType());

        
        Flickr.debugRequest = true;
        Flickr.debugStream = false;
        
        PhotoList<Photo> pl = getPhotoList(f);
        int len = pl.size();
        String s;
        ImageReader ir = new ImageReader();
        System.out.println("PhotoList size is " + len);
        for (int _x=0; _x < len; _x++)
        {
        	s = pl.get(_x).getOriginalUrl();
//        	s = pl.get(_x).getUrl();
        	if (s != null)
        	{
        		System.out.println("Photo URL is " + s);
        		try {
        			ir.downloadImage(s);
        		}
        		catch (IOException _iox) {
        			System.out.println("Error downloading image " + _iox.toString());
        		}
        	}
        }
        
    }
    
        public static PhotoList<Photo> getPhotoList(Flickr f) 
        {
        	PhotosInterface p = f.getPhotosInterface();
        	PhotoList<Photo> _pl = new PhotoList<Photo>();
        	SearchParameters params = new SearchParameters();
        	params.setUserId("53018054@N00"); // hardcoded to my own ID for test purposes
        	params.setExtras(new java.util.HashSet<String>(Arrays.asList("url_o"))); // original photo url
        	try {
        		_pl = p.search(params, 0, 0);
//        		_pl = p.getRecent(null, 0, 0);
        	}
        	catch (Exception _e) {
        		System.out.println("Exception thrown in getPhotoList():  " + _e.toString());
        	}
        	return _pl;
        }
        
      
}
