package flickrApp.proof_of_concept;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHandler {
	private static PropertyHandler instance;
	private Properties props = null;
	
	private PropertyHandler(){
		  InputStream in = null;
			
			try {
//	            in = getClass().getResourceAsStream("setup.properties");
				in = App.class.getClassLoader().getResourceAsStream("setup.properties");
	            // getClass() only works in an object context
	            props = new Properties();
	            props.load(in);
	        } catch (IOException e) {
				// TODO Auto-generated catch block
	        	System.out.println("Error loading properties " + e.toString());
				e.printStackTrace();
			
	        }
      
	}
	public static synchronized PropertyHandler getInstance() {
		if (instance == null) {
			instance = new PropertyHandler();
		}
		return instance;
	}
	
	public String getValue(String propKey){
		return this.props.getProperty(propKey);
	}
}
