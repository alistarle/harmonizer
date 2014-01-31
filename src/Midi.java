import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Midi {

	/**
	 * @param args
	 * @throws MidiUnavailableException 
	 */
	public static void main(String[] args) throws MidiUnavailableException {
		Synthesizer synth = MidiSystem.getSynthesizer();
		synth.open(); 
		final MidiChannel[] mc = synth.getChannels();
		Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
		synth.loadInstrument(instr[90]);
		JFrame frame = new JFrame("Sound1");                
		JPanel pane = new JPanel();     
		final JTextField text = new JTextField("Test de champ");
		JButton button1 = new JButton("Click me!");            
		frame.getContentPane().add(pane);                   
		pane.add(button1);
		pane.add(text);
		frame.pack();  
		frame.show();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				//mc[2].noteOn(50,600);
				mc[10].noteOn(Integer.valueOf(text.getText()), 500);
			}
		});
	}

}
