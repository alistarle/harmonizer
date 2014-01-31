import java.io.File;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;


public class Midi {

	/**
	 * @param args
	 * @throws MidiUnavailableException 
	 * @throws InvalidMidiDataException 
	 */
	/*public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException {
		Synthesizer synth = MidiSystem.getSynthesizer();
		synth.open(); 
		final MidiChannel[] mc = synth.getChannels();
		mc[1].programChange(0, 24);
		Instrument[] instr = synth.getAvailableInstruments();
        StringBuilder sb = new StringBuilder();
        String eol = System.getProperty("line.separator");
        sb.append(
            "The orchestra has " + 
            		instr.length + 
            " instruments." + 
            eol);
        for (Instrument instrument : instr) {
            sb.append(instrument.toString());
            sb.append(eol);
        }
        System.out.println(sb.toString());
		synth.loadInstrument(instr[40]);
		JFrame frame = new JFrame("Test de midi");                
		JPanel pane = new JPanel();     
		final JSpinner note = new JSpinner();
		JLabel labelNote = new JLabel("Saisissez une note entre 10 et 110");
		JLabel labelStrong = new JLabel("Saisissez l'instrument, entre 1 et 127");
		note.setModel(new SpinnerNumberModel());
		note.setEditor(new JSpinner.NumberEditor(note,"##.#"));
		final JSpinner strong = new JSpinner();
		strong.setModel(new SpinnerNumberModel());
		strong.setEditor(new JSpinner.NumberEditor(strong,"##.#"));
		JButton button1 = new JButton("Jouer le son");     
		JButton button2 = new JButton("Arreter le son");
		frame.getContentPane().add(pane); 
		pane.setLayout(new GridLayout(6,1));
		pane.add(labelNote);
		pane.add(note);
		pane.add(labelStrong);
		pane.add(strong);
		pane.add(button1);
		pane.add(button2);
		frame.pack();  
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);;
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//mc[2].noteOn(50,600);
				mc[1].programChange(0, (int)strong.getValue());
				mc[1].noteOn((int)note.getValue(), 500);
			}
		});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mc[1].noteOff((int)note.getValue(), 500);
			}
		});
	}*/
	
  public static void main(String argv[]) {
	    System.out.println("midifile begin ");
		try
		{
	//****  Create a new MIDI sequence with 24 ticks per beat  ****
			Sequence s = new Sequence(javax.sound.midi.Sequence.PPQ,24);

	//****  Obtain a MIDI track from the sequence  ****
			Track t = s.createTrack();

	//****  General MIDI sysex -- turn on General MIDI sound set  ****
			byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
			SysexMessage sm = new SysexMessage();
			sm.setMessage(b, 6);
			MidiEvent me = new MidiEvent(sm,(long)0);
			t.add(me);

	//****  set tempo (meta event)  ****
			MetaMessage mt = new MetaMessage();
	        byte[] bt = {0x02, (byte)0x00, 0x00};
			mt.setMessage(0x51 ,bt, 3);
			me = new MidiEvent(mt,(long)0);
			t.add(me);

	//****  set track name (meta event)  ****
			mt = new MetaMessage();
			String TrackName = new String("midifile track");
			mt.setMessage(0x03 ,TrackName.getBytes(), TrackName.length());
			me = new MidiEvent(mt,(long)0);
			t.add(me);

	//****  set omni on  ****
			ShortMessage mm = new ShortMessage();
			mm.setMessage(0xB0, 0x7D,0x00);
			me = new MidiEvent(mm,(long)0);
			t.add(me);

	//****  set poly on  ****
			mm = new ShortMessage();
			mm.setMessage(0xB0, 0x7F,0x00);
			me = new MidiEvent(mm,(long)0);
			t.add(me);

	//****  set instrument to Piano  ****
			mm = new ShortMessage();
			mm.setMessage(0xC0, 0x00, 0x00);
			me = new MidiEvent(mm,(long)0);
			t.add(me);

	//****  note on - middle C  ****
			mm = new ShortMessage();
			mm.setMessage(0x90,0x3C,0x60);
			me = new MidiEvent(mm,(long)1);
			t.add(me);

	//****  note off - middle C - 120 ticks later  ****
			mm = new ShortMessage();
			mm.setMessage(0x80,0x3C,0x40);
			me = new MidiEvent(mm,(long)121);
			t.add(me);

	//****  set end of track (meta event) 19 ticks later  ****
			mt = new MetaMessage();
	        byte[] bet = {}; // empty array
			mt.setMessage(0x2F,bet,0);
			me = new MidiEvent(mt, (long)140);
			t.add(me);

	//****  write the MIDI sequence to a MIDI file  ****
			File f = new File("midifile.mid");
			MidiSystem.write(s,1,f);
		} //try
			catch(Exception e)
		{
			System.out.println("Exception caught " + e.toString());
		} //catch
	    System.out.println("midifile end ");
	  } //main
}
