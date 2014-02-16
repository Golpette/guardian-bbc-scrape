
// Class for Word object: 
//   the word (String) and it's number of occurrences (int)

public class Word {
	
	String word;
	int occurrences;
	
	public Word( String word ){
		this.word = word;
		this.occurrences = 1;		
	}
	
	
	public int getOccurrences(){
		return this.occurrences;
	}
	public String getWord(){
		return this.word;
	}
	
	
	public void incrementOcc(){
		this.occurrences++;
	}
	
	
	public void addCounts(int a){
		this.occurrences = this.occurrences + a;
	}
	
	
	public String toString(){
		String s = this.word + " " + Integer.toString(this.occurrences);
		return s;
	}
	

}
