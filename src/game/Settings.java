package game;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Settings implements ActionListener{
	private Music myMusic; 
	private JDialog dialog;
	private JCheckBox cbox;
	private JButton bOk;
	private JButton bCancel;

	public Settings(Music music) {
		myMusic = music; 
		
		dialog = new JDialog();
		dialog.setTitle("Einstellung");
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panelBox = new JPanel();
	    cbox = new JCheckBox("Musik abspielen", myMusic.isMusicOn());
	    panelBox.add(cbox);
		
		JPanel panelBtn = new JPanel();
		bOk = new JButton("Ok");
		bCancel = new JButton("Abbrechen");
		bOk.addActionListener(this);
		bCancel.addActionListener(this);
		panelBtn.add(bOk);
		panelBtn.add(bCancel);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(panelBox);
		panel.add(panelBtn);
	
		dialog.add(panel);
		
		dialog.pack();
		dialog.setModal(true);	
		dialog.setVisible(true);
	}
	
	public void actionPerformed( ActionEvent ae)
	{
		if (ae.getSource()== this.bOk)
		{
			if (cbox.isSelected()) {
				myMusic.start();
			}
			else {
				myMusic.stop();
			}
		}
		dialog.dispose();
	}
}
