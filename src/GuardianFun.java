import java.util.*;
import java.io.*;

public class GuardianFun {

	public static void main( String[] args )throws IOException{
				
		PrintWriter out = new PrintWriter( new FileWriter("Guardian_words_xx.dat") );
		PrintWriter out2 = new PrintWriter( new FileWriter("Guardian_pages_visited_xx.dat") );

		ArrayList<String> visitedURLs = new ArrayList<String>();
		ArrayList<Word> allWords = new ArrayList<Word>();


		for( int pages=0; pages<100; pages++){

			//Main URL
			String mainURL = "http://www.theguardian.com/uk";
			
			// Choose URL of a Guardian article
			String nextURL = GetNextURL.getURL(mainURL, visitedURLs);
						
			visitedURLs.add(  nextURL  );

			// Fetch it's main article body, without html tags			
			//String mainBody = GetMainText.getBody(  nextURL  );
			String mainBody = GetMainText.getBody(  nextURL  );
			
			//System.out.println( mainBody );
			
		
			// Take article, and tally up occurrences of words, ignoring
			// capitals, punctuation,... [ this needs finalised ]
			ArrayList<Word> singleList = new ArrayList<Word>();
			singleList = WordAnalyser.analyseArticle(  mainBody  );

			
			//   NOTE  there is a problem with getting main body from pages containing a video above article
			out2.println( nextURL + " singleList.size()="+  singleList.size() + " mainBody.length()=" + mainBody.length()  );


			// Take this list, and add it to ULTIMATE list
			for( int w=0; w<singleList.size(); w++ ){

				String wrd = singleList.get(w).getWord();
				int occs = singleList.get(w).getOccurrences();

				//Check word is in the list
				boolean doesContain = containsWord( allWords,  wrd  );
				if( doesContain ){
					//Get index and update occurrences
					int index = getIndex( allWords, wrd );
					allWords.get(index).addCounts( occs );
				}
				else{
					allWords.add(  singleList.get(w)  );
				}
			}


		}// end for pages

		
		//Sort list (lowest to highest)	
		allWords = sortWordList( allWords );
		
		
		out.println( "Number of distinct words = " + allWords.size()  );
		out.println();
		// Print words (most common to rarest)
		for( int z = allWords.size()-1; z>=0; z-- ){
			out.println(  allWords.get(z).toString()  );			
		}
		
		out.close();
		out2.close();
		
		
		// Get data for plot of number of words with x occurrences vs occurrences
		PrintWriter out3 = new PrintWriter(new FileWriter("plotData.dat"));
		int maxOccurrences = allWords.get( allWords.size()-1 ).getOccurrences();
		int[] numOfWords = new int[ maxOccurrences+1 ];
		for( Word w: allWords ){
			int occ = w.getOccurrences();
			numOfWords[ occ ]++;
		}
		for( int i=0; i<numOfWords.length; i++ ){
			if( numOfWords[i] != 0 ){
				out3.println( i + "\t" + numOfWords[i] );
			}
		}
		
		out3.close();
		
		
		
		
	}//end main
	
	
	
	
	
	
	
	
	public static int getIndex( ArrayList<Word> ultimate, String word){
		int index = -4;
		for( int i=0; i<ultimate.size(); i++){
			if( ultimate.get(i).getWord().equals( word ) ){
				index = i;
			}
		}
		return index;
	}//=======================================================================
	
	public static boolean containsWord( ArrayList<Word> ultimate, String word){
		boolean contains = false;
		for( int i=0; i<ultimate.size(); i++ ){
			if( ultimate.get(i).getWord().equals(  word  )){
				contains = true;
			}
		}
		return contains;
	}//========================================================================	
	
	
	// Method takes list of 'Word' objects and sorts them according to their number
	// of occurrences, lowest to highest
	public static ArrayList<Word> sortWordList( ArrayList<Word> list){
		
		for( int i=0; i < list.size(); i++){
			
			int place = -9;
			
			for( int j = list.size()-1; j>=0; j-- ){		
				if( list.get(i).getOccurrences() < list.get(j).getOccurrences() ){
					place = j;
				}
			}				
			if( place != -9 ){
				//update places
				if( i > place ){
					list.add( place, list.get(i) );
					list.remove( i+1 );	
					i=i-1;
				}
				else if (i < place){
					list.add( place, list.get(i)  );
					list.remove( i );
				}
			}			
		}		
		return list;
	}//===============================================================================
	
	

}//end class
