package leifdev.sandbox;

import java.util.ArrayList;
import java.util.Collections;

public class Sorter {

	

	/*
	 * Version: 0.1
	 * Last update: 14.02.2013
	 * Author: Leif Andreas Rudlang
	 * 
	 * Description: Sorts objects based on their toString method
	 *
	 */
	  
		
	
	
	// static ints to separate sorting algorithms
	public static final int REVERSE = 111135;
	public static final int STANDARD = 111134;
	
	
	
	// sorting function takes arraylist with objects, and sorts from their toString method
	public static <T>  ArrayList<T> sort(ArrayList<T> in){
		return sort(in,STANDARD);
	}
	
	
	public static <T> ArrayList<T> sort(ArrayList<T> in, int algorithm){

		
		String delta = "";

		String check = "";
		T adder = null;
		int adderIndex = 0;
		
		
		ArrayList<T> buffer = new ArrayList<T>();
		boolean sorting = true;


			while(sorting) {

				delta = "";
				adderIndex = 0;


				for(int i = 0; i < in.size(); i++) {
					
					
					check = in.get(i).toString();				
				
					int result = check.compareToIgnoreCase(delta);
					
					if(result > 0) {	
						adder = in.get(i);						
						delta = check;						
						adderIndex = i;										
					}		
				
				
				}


				buffer.add(adder);
				in.remove(adderIndex);

				if(in.size() == 0){
					in.addAll(buffer); 
					buffer.clear();
					buffer = null;
					sorting = false;
				}			
				
			}

			
			if(algorithm != REVERSE){
				Collections.reverse(in);
			}
	

		return  in;
	}

	

	/*
	  example of usage:
	 
	 
	import java.util.ArrayList;

	public class Main {

	public static void main(String[] args){

		ArrayList<Song> s = new ArrayList<Song>();
		s.add(new Song("Metallica - Nothing else matters"));
		s.add(new Song("AC/DC - Highway to hell"));
		s.add(new Song("Metallica - Enter Sandman"));
		s.add(new Song("Black Sabbath - Sunday"));
		s.add(new Song("John Lennon - Imagine"));
		s.add(new Song("AC/DC - Thunderstruck"));
		s.add(new Song("DDE - E6"));	
		s.add(new Song("DDE - Rai Rai"));
		
		
		
		s = Sorter.sort(s,Sorter.STANDARD);

		
		
		
		for(Song song : s){
			System.out.println(song.toString());
		}



	}


	 public class Song {

	private String  name;
	
	public Song(String in){
		this.name = in;
		
	}
	
	public String toString(){
		return name;
	}
	
} 
	 */	
	
	
	
}
