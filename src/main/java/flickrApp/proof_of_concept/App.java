package flickrApp.proof_of_concept;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
        System.out.println( "Initializing main" );
        Properties properties = null;
//		FileInputStream in;
        InputStream in = null;
//		try {
//			in = new FileInputStream("setup.properties");
			
			try {
	            in = App.class.getClassLoader().getResourceAsStream("setup.properties");
	// getClass() only works in an object context
//	        	in = FileInputStream("/setup.properties");
	            properties = new Properties();
	            properties.load(in);
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            IOUtilities.close(in);
	        }
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
        
        
        String apiKey = properties.getProperty("apiKey");
		 String sharedSecret = properties.getProperty("secret");
        
		REST rest = new REST();
        Flickr f = new Flickr(apiKey, sharedSecret, rest);
        System.out.println("API Key is:  " + apiKey + ", Shared secret is:  " + sharedSecret);
//        RequestContext requestContext = RequestContext.getRequestContext();
        AuthInterface authInterface = f.getAuthInterface();
        Token token = authInterface.getRequestToken();
//        auth.setPermission(Permission÷roperty("token"));
//        requestContext.setAuth(auth);
        System.out.println("token: " + token);

        String url = authInterface.getAuthorizationUrl(token, Permission.READ);
        System.out.println("Follow this URL to authorise yourself on Flickr");
        System.out.println(url);
        System.out.println("Paste in the token it gives you:");
        System.out.print(">>");

        Scanner scanner = new Scanner(System.in);
        String tokenKey = scanner.nextLine();
        scanner.close();

        Token requestToken = authInterface.getAccessToken(token, new Verifier(tokenKey));
        System.out.println("Authentication success");

        Auth auth = authInterface.checkToken(requestToken);

        // This token can be used until the user revokes it.
        System.out.println("Token: " + requestToken.getToken());
        System.out.println("nsid: " + auth.getUser().getId());
        System.out.println("Realname: " + auth.getUser().getRealName());
        System.out.println("Username: " + auth.getUser().getUsername());
        System.out.println("Permission: " + auth.getPermission().getType());

        
        Flickr.debugRequest = false;
        Flickr.debugStream = false;
    }
}
