import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.*;

// Seems to be 2 tags that might hold main text: "article-body-blocks" and "flexible-content-body"
/*
 *  This class holds a method to go to a "Guardian" web page and retrieve the main article
 *  as a single String object.
 *  
 *  It also gets rid of all html tags (i.e. hyperlinks) along with the initial space + tab characters.
 *  Any further editing (punctuation, numbers, non-standard characters, etc) can be done in main prog.
 */


public class GetMainText {


	public static String getBody( String desiredUrl ){
	
		String body = "";
		body = tryArticleBodyBlocks( desiredUrl );			
		body = removeScripts( body );
		body = removeTags( body );		
		body = replaceHtmlText( body );
		
		return body.trim();		
	}
	//=============================================================
	
	
	
	// Method to look for main body within "article-body-blocks" tag
	public static String tryArticleBodyBlocks( String desiredURL ){
		
		URL url;
	    InputStream is = null;  
	    BufferedReader br;
	    String line;
	    
	    boolean foundMain = false;
		String mainBody = "";
    
	    try {
	    	url = new URL( desiredURL );
	        is = url.openStream();
	        br = new BufferedReader(new InputStreamReader(is));
	      
	        
    		int countOpendiv = 1;
        	int countClosediv = 0;
	        while ((line = br.readLine()) != null) {
	    	        	

	        	if( foundMain ){
	        		//Then we have found the "<div id="article-body-blocks"> tag
	        		// Now count how many extra <div openers and </div closers there are, and when
	        		//they are equal, we have found the end of the article-body-blocks.
	        		
	        		//POSSIBLE BUG: poorly formatted html could be a problem if we close the
	        		// desired block but open another one in the same line!
	        		        		
	        		if( line.contains("<div") || line.contains("</div") ){
	        			// count occurrences of each, add to counters.
	        			int o_div = countOccurrences( line, "<div");
	        			int c_div = countOccurrences( line, "</div");
	        			
	        			countOpendiv = countOpendiv + o_div;
	        			countClosediv = countClosediv + c_div;
	        		}
	        		
	        		// Add line to main body text
	        		mainBody = mainBody + line.trim();
	        		
	        		if( countOpendiv == countClosediv ){
	        			//Then we have reached end of main text block
	        			//foundMain = false;
	        			break;
	        		}
	        			
		        		
	        	}
	        	if( line.contains("article-body-") ){
	        		foundMain=true;	        		
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
           
	    return mainBody.trim();
	   	
	}// end method ================================================================================
	
	
	
	
	//Method to count number of occurrences of some pattern string within a string
	public static int countOccurrences(String text, String pattern ){
		
		int lastIndex = 0;
		int count =0;

		while(lastIndex != -1){

		       lastIndex = text.indexOf(pattern, lastIndex);

		       if( lastIndex != -1){
		             count ++;
		             lastIndex+=pattern.length();
		      }
		}	
		return count;		
	}//=================================================================
	
	
	
	
	//Method to delete scripts (start with "<script type="text/javascript">" and end
	//with <script type="text/javascript">. Keep running method til all scripts are deleted
	public static String removeScripts( String text ){
		
		String finalString=text.trim();
		
		//boolean scriptsPresent = true;
		while( true ){
			
			int indexStart = finalString.indexOf( "<script");
	        int indexEnd = finalString.indexOf("</script>") + 9;			
			
	        if( indexStart == -1 || indexEnd == -1  ){
	        	// then all scripts found
	        	break;
	        }
	        
        	String firstbit = finalString.substring( 0,indexStart ).trim();
        	String secondbit = finalString.substring( indexEnd, finalString.length() ).trim();
        	
        	finalString = firstbit + secondbit;
		}        
		return finalString.trim();
	}//====================================================================================
	
	
	
	//Method to remove all tags ( things contained in <...> )
	public static String removeTags( String text ){
		
		String finalText = "";		
		// Get rid of <..> html tags
		boolean inTag=false;
		for( int i=0; i<text.length(); i++){

			if (!inTag){
				if( text.charAt(i) != '<'){
					finalText = finalText + text.charAt( i );
				}
				else{
					inTag = true;
				}
			}
			else{
				// don't print
				if( text.charAt(i) == '>'){
					inTag = false;
					finalText = finalText + " ";
				}
			}
		}   	
		return finalText.trim();
	}
	//=================================================================
	
	
	// Take text and remove any non-text characters
	//   (things like ... appear in the source code)
	public static String replaceHtmlText( String text ){
		String maintxt = text;
		//Replace all html text 	
		maintxt = maintxt.replace("&#39;", "'");
		maintxt = maintxt.replace("&rsquo;", "'");
		maintxt = maintxt.replace("&rdquo;", "\"");
		maintxt = maintxt.replace("&lsquo;", "'");
		maintxt = maintxt.replace("&ldquo;", "\"");
		maintxt = maintxt.replace("&nbsp;", " ");
		maintxt = maintxt.replace("&ndash;", "-");
		maintxt = maintxt.replace("&mdash;", "-");
		maintxt = maintxt.replace("&amp;", "&");		
		return maintxt;
	}//==================================================================
	
	
	
	
}//end class


