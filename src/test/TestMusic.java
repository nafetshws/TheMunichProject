package test;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Test;
import game.*;


public class TestMusic extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  Music myMusic; // Jana
	
	JButton bOK;
	JButton bCancel;
	
	public  TestMusic()
	{
		System.out.println("Hello world");
		System.out.println("TEST THE MUSIC");
		
		// JANA beim Starten der Anwendung 
		myMusic = new Music();		
		// Jana ende

		this.setTitle("TEST");
		this.setSize(200,200);
		
		bOK = new JButton("Ok");
		bOK.addActionListener(this);
		
		bCancel = new JButton("Cancel");
		bCancel.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.add(bOK);
		panel.add(bCancel);
		
		this.add(panel);
	
	}
	
	public static void main(String[] args) 
	{
		TestMusic tMusic = new TestMusic();
		tMusic.setVisible(true);
	
	}
	
	
	
	
	public void actionPerformed( ActionEvent ae)
	{
		if (ae.getSource()== this.bOK)
		{
        // JANA beim Aufruf von den Settings 
		  Settings mySettings = new Settings(myMusic);
		  
		  //  JANA ende#
		  
		}
		else 
			this.dispose();

	}
	


}
