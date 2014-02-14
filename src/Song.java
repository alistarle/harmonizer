import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Song {

	private ArrayList<Note> noteList = new ArrayList();
	private String name;
	private File input;
	
	public Song(File input) {
		this.input = input;
		this.readFromFile();
	}
	
	public void readSong() {
		
	}
	
	public void writeToMidi() {
		
	}
	
	public void writeToLily() {
		
	}
	
	public void playSong() {
		
	}
	
	private void readFromFile() {
	      FileInputStream fis = null;
	      String content = null;
	      try {
	         fis = new FileInputStream(input);
	         byte[] buf = new byte[8];
	         int n = 0;
	         while ((n = fis.read(buf)) >= 0) {
	             for (byte bit : buf) {
	                 content+=(char) bit;
	              }
	         }
	         System.out.println("Lecture terminée ! : "+content);

	      } catch (FileNotFoundException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (fis != null)
	               fis.close();
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
	      
	      if(content != null) {
	    	  String[] noteArray = content.split(" ");
	    	  for(String note : noteArray) {
	    		  try {
					noteList.add(new Note(note));
				} catch (ParsingException e) {
					e.printStackTrace();
				}
	    	  }
	      }
	}

}
