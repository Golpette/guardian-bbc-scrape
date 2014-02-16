import java.util.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class WordAnalyser {
	
	// TO DO: fix  	some words appearing with "/":  /aps, 
	//              hashtags: #word
	//              directed messages:  @obama
	//              plurals, contracted things like don't and didn't, hyphenated words,
	
	
	
	public static final ArrayList<String> punctuation = new ArrayList<String>();
	/**
	 The static block only gets called once, no matter how many objects of that type you create. Using {...} without the
	 'static' will lead to the code being called every time the class is constructed.
	 */
	static{ 
		punctuation.add("\"");
		punctuation.add(".");
		punctuation.add(";");
		punctuation.add(",");
		punctuation.add("?");		
		punctuation.add(":");
		punctuation.add("(");
		punctuation.add(")");
		punctuation.add("[");
		punctuation.add("]");
		punctuation.add("!");
//		punctuation.add("-");
        // cant just delete this... maybe use it to split string
		// i.e. one-year-old is 3 words
	}
	
	
	public static String removePunctuation(String text){
		String txt = text;
		for( String punct: punctuation ){
			// NB: replaceAll() didn't work here, something to do with it using regexps 
			// while replace() can use Character or CharacterSequence
			txt = txt.replace( punct, "");
		}		
		return txt;
	}
	
	public static String removeSingleQuotes(String word){
		//Since the apostrophe and single quotes are the same, can't just replace
		//them all without messing up some words. Need to handle these separately.
		//This will also remove plurals such as Jesus'...
		String txt = word;
		// Sometimes removeApostropheSEnding() leaves an empty String...
		if( txt.length() > 0 ){
			if( txt.charAt(0)=='\''  ){  
				txt = txt.substring( 1, txt.length() );
			}
		}
		if( txt.length() > 0 ){
			if(txt.charAt( txt.length()-1 )=='\''  ){
				txt = txt.substring( 0, txt.length()-1 );
			}				
		}
		return txt;
	}
	
	public static String removeApostropheSEnding( String word ){
		//i.e. make "cameron's" count as "cameron"
		String txt = word;
		if(  txt.length() >= 2  ){
			if( txt.charAt(txt.length()-1)=='s'  &&  txt.charAt( txt.length()-2 )=='\'' ){
				txt = txt.substring( 0, txt.length()-2 );
			}
		}
		return txt;
	}
	
	
	

	public static ArrayList<Word> analyseArticle( String text ){
		// Take main article as a single String and tally up unique words, not including 
		// numbers and removing words with non-text characters
		
		// Make lower case and remove punctuation
		String mainBody = text.toLowerCase();
		mainBody = removePunctuation( mainBody );
			
		
		ArrayList<Word> wordList = new ArrayList<Word>();
		
		// Get rid of punctuation, numbers, non-standard chars and tally up the words...
		Scanner scan = new Scanner( mainBody );
		String nextWord = "";
		int wrdCnt=0;
		while(true){
			try{
				nextWord = scan.next();
				wrdCnt++;
				
				//First check everything is standard characters
				boolean containsNonStandardText =false;
				for( int c=0; c<nextWord.length(); c++){
					int ascii = (int)(  nextWord.charAt(c)  );
					if( ascii >= 128  &&  ascii <=255 ){
						// then we have a non-standard character
						containsNonStandardText = true;
					}
				}
				//Check it doesn't contain numbers
				boolean CONTAINSNUMBERS =false;
				for( int c=0; c<nextWord.length(); c++){
					int ascii = (int)(  nextWord.charAt(c)  );
					if( ascii >= 48  &&  ascii <= 57 ){
						// then we have a non-standard character
						CONTAINSNUMBERS = true;
					}
				}	

				String finalWord = "";
				if( !CONTAINSNUMBERS  &&  !containsNonStandardText ){
					// Remove any "'s" endings to words and single quotes
                    finalWord = nextWord;
                    finalWord = removeApostropheSEnding( finalWord );
                    finalWord = removeSingleQuotes( finalWord );
                    
                    
    				// If the word has not been encountered before, then we create a new
    				// Word object. If it has, we find the entry and increment its count by one.
    				if( !containsWord(wordList, finalWord)  ){
    					Word new_word = new Word( finalWord );
    					wordList.add( new_word );						
    				}
    				else{					
    					int index = getIndex( wordList, finalWord );
    					wordList.get( index ).incrementOcc();
    				}           
                    
				}
								
			}catch(NoSuchElementException e){
				break;
			}
		}
				
		return wordList;
	
	}
	
	
	
	
	
	public static boolean containsWord( ArrayList<Word> wordList, String word){
		boolean contains = false;
		for( int i=0; i<wordList.size(); i++ ){
			if( wordList.get(i).getWord().equals(  word  )){
				contains = true;
			}
		}
		return contains;
	}
	
	
	
	public static int getIndex( ArrayList<Word> wordList, String word){
		int index = -4;
		for( int i=0; i<wordList.size(); i++){
			if( wordList.get(i).getWord().equals( word ) ){
				index = i;
			}
		}
		return index;
	}
	
	

//	// Maybe split certain hyphenated words?
//	if( nextWord.contains("-") ){
//		System.out.println( nextWord );
//	}
	

}
