
public class Note {
	private String name;
	private int octave;
	private int midi;
	private int code;
	private char lilypond;
	private int duration;
	
	public Note(String data) throws ParsingException {
		if(data.matches("^(do|re|mi|fa|sol|la|si)[1-4]:[1-9][0-9]*$")) {
			String[] noteData = data.split(":");
			duration = Integer.valueOf(noteData[1]);
			octave = Integer.valueOf(noteData[0].substring(noteData[0].length()-1, noteData[0].length()));
			name = noteData[0].substring(0, noteData[0].length()-1);
			midi = NoteUtils.getMidi(name, octave);
			code = NoteUtils.getCode(name, octave);
		} else {
			throw new ParsingException(data);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOctave() {
		return octave;
	}

	public void setOctave(int octave) {
		this.octave = octave;
	}

	public int getMidi() {
		return midi;
	}

	public void setMidi(int midi) {
		this.midi = midi;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public char getLilypond() {
		return lilypond;
	}

	public void setLilypond(char lilypond) {
		this.lilypond = lilypond;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Note [name=" + name + ", octave=" + octave + ", midi=" + midi + ", code=" + code + ", duration="
				+ duration + "]";
	}
	
}