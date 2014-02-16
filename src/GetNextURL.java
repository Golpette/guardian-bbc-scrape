import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

//Idea here is to start at some home page (i.e. Guardian UK main page)
//and then visit all the relevant (news) links 

public class GetNextURL {

	
	public static String getURL(String thisURL, ArrayList<String> visitedURLS){
		
		String nextURL="";
		
		URL url;
	    InputStream is = null;  
	    BufferedReader br;
	    String line;
	    
	    boolean foundNextURL = false;
	    boolean component_b5_reached=false;
        
	    try {
	    	url = new URL( thisURL );
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));
	      
	        while ((line = br.readLine()) != null) {
	        	
	        	if( component_b5_reached ){
	        		if( line.contains("link-text") ){
//	        		if( line.contains("href=\"http://") ){
//		        	if( line.contains("component b5") ){

	        			//System.out.println( line );
	        			//System.out.println( "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
	        		
	        			//Extract the url
	        			String urly="";
	        			boolean readingURL = false;
	        			for( int i=0; i<line.length(); i++){
	        				if( readingURL ){
	        					if( line.charAt(i) == '"' ){
//	        						readingURL = false;
	        						i = line.length()+1;
	        					}
	        					else{
	        						urly = urly + line.charAt(i);
	        					}
	        				}
	        				else if( line.charAt(i) == '"' ){
	        					readingURL = true;
	        				}
	        			}
	        			
	        			//System.out.println( urly );
	        			
	        			//check not visited before
	        			if( !visitedURLS.contains( urly ) ){
	        				nextURL = urly;
		        			foundNextURL=true;
	        			}
	        		}
	        	}
	        	
	        	if(line.contains("component b5") ){
	        		component_b5_reached = true;
	        	}
	        	
	        	if( foundNextURL ){
	        		break;
	        	}
	        	    	
	        	
	        	
	        }// End while.
	    } catch (MalformedURLException mue) {
	    	System.out.println(" *******   MalformedURLException  *********");
	        mue.printStackTrace();
	    } catch (IOException ioe){	    	
	    	System.out.println(" *******   IOException   ********");
	    	ioe.printStackTrace();
	    } finally {
	    	try {
	            is.close();
	        } catch (IOException ioe) {
	            // nothing to see here
	        } catch (NullPointerException e){
	        	
	        }
	       
	    }	
        
	    //System.out.println("nextURL = " + nextURL);
		
		return nextURL;
	}
	
	
	
}
