
public class Note {
	private String name;
	private int octave;
	private int midi;
	private int code;
	private String lilypond;
	private int duration;
	
	public Note(String data) throws ParsingException {
		if(data.matches("^((do|re|mi|fa|sol|la|si)[1-4]|-):[1-9][0-9]*$")) {
			String[] noteData = data.split(":");
			duration = Integer.valueOf(noteData[1]);
			if(!noteData[0].equals("-")) {
				name = noteData[0].substring(0, noteData[0].length()-1);
				octave = Integer.valueOf(noteData[0].substring(noteData[0].length()-1, noteData[0].length()));
				midi = NoteUtils.getMidi(name, octave);
				code = NoteUtils.getCode(name, octave);
				lilypond = NoteUtils.getLilypond(name, octave, duration);
			} else {
				name = "-";
				lilypond = "r"+duration;
			}
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

	public String getLilypond() {
		return lilypond;
	}

	public void setLilypond(String lilypond) {
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
		return "Note [name=" + name + ", lilypond ="+ lilypond +", octave=" + octave + ", midi=" + midi + ", code=" + code + ", duration="
				+ duration + "]";
	}
	
}