
package game;


import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;


public class Music {

	private boolean on = true; 
	static Clip clip = null;
 


  /**
   * Spielt Audiodateien als Hintergrundmusik ab, nur mit der Dateiendung .au
   *
   */
  public  Music() {
    String musicFile = "music.wav";
      try {

        clip = AudioSystem.getClip();
    
        AudioInputStream BackgroundStream =
        AudioSystem.getAudioInputStream(new File(musicFile).getAbsoluteFile());
    
        clip.open(BackgroundStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
      } catch (Exception ex) {
  		System.out.println(ex);  		 
       }
    return;
  }

  /** stoppt sound */
  public  void stop() {
    if (clip != null) {
    	on= false; 
      clip.stop();
    }
  }

  /** startet die Musik in Endlosschleife */
  public  void start() {
	  on= true;
    if (clip != null) clip.loop(Clip.LOOP_CONTINUOUSLY);
    
  }
  
  public boolean isMusicOn() {
	  return on;
  }

  
}
