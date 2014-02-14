import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class Song {

	private ArrayList<Note> noteList = new ArrayList();
	private String name;
	private File input;
	private int tick;
	
	public Song(File input, int tick) {
		this.input = input;
		this.tick = tick;
		this.readFromFile();
	}
	
	public void readSong() {
		
	}
	
	public void writeToMidi() {
		MidiWriter mw = new MidiWriter("song",tick);
		mw.writeTrack(noteList);
		mw.close();
		mw.write();
	}
	
	public void writeToLily() {
		
	}
	
	public void playSong() {
		
	}
	
	private void readFromFile() {
		String content = "";
		try(FileInputStream fis = new FileInputStream(input)) {
			byte[] buf = new byte[1];
			int n = 0;
			while((n = fis.read(buf)) >= 0){    
				content+=((char)buf[0]);         
			}
			System.out.println("Lecture terminée : "+content);
		} catch (IOException e) {
			e.printStackTrace();
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
