import java.util.*;

public class TesterClass {
	
	public static void main(String[] args){
		
		//String s = "'singlequotestart";
		String s = "cameron's";
		System.out.println( s );
//		s = WordAnalyser_2.removeSingleQuotes( s );
		s = WordAnalyser_2.removeApostropheSEnding( s );
		System.out.println( s );
		System.exit(0);
		
		
		
		String mainText = GetMainText_2.getBody("http://www.theguardian.com/uk-news/2013/dec/16/two-charged-caroline-criado-perez-tweets");
		//String mainText = GetMainText_2.getBody("http://www.theguardian.com/uk-news/2013/dec/16/afghanistan-mission-accomplished-david-cameron");
		//String mainText = GetMainText_2.getBody("http://www.theguardian.com/world/2013/dec/16/nsa-phone-surveillance-likely-unconstitutional-judge");
		System.out.println( mainText );
		
		mainText = WordAnalyser_2.removePunctuation(mainText);
		System.out.println( mainText );
		
		System.exit(0);
		
		
		
		
		
		
		// Method to sort list
		ArrayList<Integer> sortThis = new ArrayList<Integer>();
		sortThis.add(11);  sortThis.add(36);   sortThis.add(8);
		sortThis.add(27);  
		sortThis.add(191);  sortThis.add(1);   sortThis.add(5);		
		sortThis.add(2);  sortThis.add(4);
		sortThis.add(111);  sortThis.add(6);   sortThis.add(5);
		sortThis.add(97);  sortThis.add(9);  sortThis.add(1);
		
				
		for( int i=0; i<sortThis.size(); i++){
			
			int place = -9;
			System.out.println( sortThis.toString() );
			
			for( int j=sortThis.size()-1; j>=0; j-- ){
				
				if( sortThis.get(i) < sortThis.get(j) ){
					place = j;
				}
			}		
			
			if( place != -9 ){
				//update places
				//System.out.println("xx " + place );
				if( i > place ){
					int value = sortThis.get(i);
					sortThis.add(place, value);
					sortThis.remove( i+1 );	
					i=i-1;
				}
				else if (i < place){
					int value = sortThis.get(i);
					sortThis.add(place, value);
					sortThis.remove( i );
				}
			}
//			else{
//				int value = sortThis.get(i);
//				sortThis.add( value );
//				sortThis.remove(i);
//				
//			}
		}
		
		System.out.println( sortThis.toString() );
	
		
		//       a  b  pl  d  e  i  f  g
		//       a  b  i  pl  d  e  i  f  g
		//       a  b  i  pl  d  e  f  g
		//       _  _  _   _  _  _  _  _
		
		
		
//		ArrayList<String> visitedURLS = new ArrayList<String>();
//		String mainURL = "http://www.theguardian.com/uk";
//		String nextUrl = GetNextURL.getURL( mainURL, visitedURLS);	
//		System.out.println( nextUrl );
		
		
	}
	
	
}
