import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

//Classe de test midi
public class Midi {

	/**
	 * @param args
	 * @throws MidiUnavailableException 
	 * @throws InvalidMidiDataException 
	 */
	
	static Sequence sec;
	static Track track;
	static Track track2;
	static Track track3;
	static Track track4;
	static MidiChannel channel;
	static Sequencer sequencer;
	static Synthesizer synth;
	
	public static void mainTest(String[] args) throws MidiUnavailableException, InvalidMidiDataException {
		synth = MidiSystem.getSynthesizer();
		synth.open(); 
		final MidiChannel[] mc = synth.getChannels();
		channel = mc[1];
		channel.programChange(0, 24);
		Instrument[] instr = synth.getAvailableInstruments();
		
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

	}
	
    public static void CreateOnEvent(int note, int tick, Track track)
{
    ShortMessage myMsg = new ShortMessage();
    try {
        myMsg.setMessage(ShortMessage.NOTE_ON, 0, note, 127);
    } catch (InvalidMidiDataException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }
    MidiEvent me = new MidiEvent(myMsg,(long) tick);
    track.add(me);
}
    
    public static void CreateOffEvent(int note, int tick, Track track)
{
    ShortMessage myMsg = new ShortMessage();
    try {
        myMsg.setMessage(ShortMessage.NOTE_OFF, 0, note, 127);
    } catch (InvalidMidiDataException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }
    MidiEvent me = new MidiEvent(myMsg,(long) tick);
    track.add(me);
}
    
   public static void changeProgram(int instr, Track track, int tick) throws InvalidMidiDataException {
		ShortMessage mm = new ShortMessage();
		mm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instr, 0);
		MidiEvent me = new MidiEvent(mm,(long)tick);
		track.add(me);	   
   }
	
  public static void mainTest2(String argv[]) {
	    System.out.println("midifile begin ");
		try
		{
	//****  Create a new MIDI sequence with 24 ticks per beat  ****
			sec = new Sequence(javax.sound.midi.Sequence.PPQ,50);

	//****  Obtain a MIDI track from the sequence  ****
			track = sec.createTrack();
			track2 = sec.createTrack();
			track3 = sec.createTrack();
			track4 = sec.createTrack();

	//****  General MIDI sysex -- turn on General MIDI sound set  ****
			byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
			SysexMessage sm = new SysexMessage();
			sm.setMessage(b, 6);
			MidiEvent me = new MidiEvent(sm,(long)0);
			track.add(me);
			track2.add(me);
			track3.add(me);
			track4.add(me);

	//****  set tempo (meta event)  ****
			MetaMessage mt = new MetaMessage();
	        byte[] bt = {0x02, (byte)0x00, 0x00};
			mt.setMessage(0x51 ,bt, 3);
			me = new MidiEvent(mt,(long)0);
			track.add(me);
			track2.add(me);
			track3.add(me);
			track4.add(me);

	//****  set track name (meta event)  ****
			mt = new MetaMessage();
			String TrackName = new String("Piste de test");
			mt.setMessage(0x03 ,TrackName.getBytes(), TrackName.length());
			me = new MidiEvent(mt,(long)0);
			track.add(me);
			track2.add(me);
			track3.add(me);
			track4.add(me);

	//****  set omni on  ****
			ShortMessage mm = new ShortMessage();
			mm.setMessage(0xB0, 0x7D,0x00);
			me = new MidiEvent(mm,(long)0);
			track.add(me);
			track2.add(me);
			track3.add(me);
			track4.add(me);

	//****  set poly on  ****
			mm = new ShortMessage();
			mm.setMessage(0xB0, 0x7F,0x00);
			me = new MidiEvent(mm,(long)0);
			track.add(me);
			track2.add(me);
			track3.add(me);
			track4.add(me);

	//****  set instrument to Piano  ****
			mm = new ShortMessage();
			mm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 1, 0);
			me = new MidiEvent(mm,(long)0);
			track.add(me);
			track2.add(me);
			track3.add(me);
			track4.add(me);
			
			
			int j = 100;
			int[] note = new int[]{60,60,60,62,64,64,62,62,60,64,62,62,60};
			int[] note2 = new int[]{55,57,57,59,60,59,57,59,57,60,59,59,57};
			int[] note3 = new int[]{52,53,52,55,55,55,53,55,53,55,55,55,53};
			int[] note4 = new int[]{36,41,45,43,36,40,38,43,41,36,43,43,41};
			for(int i = 0; i <= note.length-1; i++) {
				changeProgram(1, track, j);
				CreateOnEvent(note[i], j+50, track);
				CreateOffEvent(note[i], j+100, track);
				j+=100;
			}
			
			j = 100;
			for(int i = 0; i <= note.length-1; i++) {
				changeProgram(2, track2, j);
				CreateOnEvent(note2[i], j+50, track2);
				CreateOffEvent(note2[i], j+100, track2);
				j+=100;
			}	
			
			j = 100;
			for(int i = 0; i <= note.length-1; i++) {
				changeProgram(5, track3, j);
				CreateOnEvent(note3[i], j+50, track3);
				CreateOffEvent(note3[i], j+100, track3);
				j+=100;
			}
			
			j = 100;
			for(int i = 0; i <= note.length-1; i++) {
				changeProgram(1, track4, j);
				CreateOnEvent(note4[i], j+50, track4);
				CreateOffEvent(note4[i], j+100, track4);
				j+=100;
			}
			
	//****  set end of track (meta event) 19 ticks later  ****
			mt = new MetaMessage();
	        byte[] bet = {}; // empty array
			mt.setMessage(0x2F,bet,0);
			me = new MidiEvent(mt, (long) j+20);
			track.add(me);
			track2.add(me);
			track3.add(me);
			track4.add(me);

	//****  write the MIDI sequence to a MIDI file  ****
			File f = new File("midifile.mid");
			MidiSystem.write(sec,1,f);
			
			try { 
				  Sequence sequence = MidiSystem.getSequence(new File("midifile.mid" )); 
				  Sequencer sequencer = MidiSystem.getSequencer(); 
				  sequencer.open(); 
				  sequencer.setSequence(sequence); 
				  sequencer.start(); 
				} catch (Exception e) {} 
		} //try
			catch(Exception e)
		{
			System.out.println("Exception caught " + e.toString());
		} //catch
	    System.out.println("midifile end ");
	  } //main
}
