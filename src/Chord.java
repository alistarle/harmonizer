
public class Chord {

	private Note soprano;
	private Note alto;
	private Note tenor;
	private Note basse;
	
	public Chord(Note note) {
		this.soprano = note;
		try {
			this.alto = (note.getName() != "-") ? new Note(note.getName()+note.getOctave()+":1") : new Note("do4:1");
			this.tenor = (note.getName() != "-") ? new Note(note.getName()+note.getOctave()+":1") : new Note("do4:1");
			this.basse = (note.getName() != "-") ? new Note(note.getName()+note.getOctave()+":1") : new Note("do4:1");
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Chord(Chord chord, Note note) {
		this.soprano = note;
		try {
			this.alto = (note.getName() != "-") ? new Note(note.getName()+note.getOctave()+":1") : new Note("do4:1");
			this.tenor = (note.getName() != "-") ? new Note(note.getName()+note.getOctave()+":1") : new Note("do4:1");
			this.basse = (note.getName() != "-") ? new Note(note.getName()+note.getOctave()+":1") : new Note("do4:1");
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Note getSoprano() {
		return soprano;
	}

	public Note getAlto() {
		return alto;
	}

	public Note getTenor() {
		return tenor;
	}

	public Note getBasse() {
		return basse;
	}
	
}
