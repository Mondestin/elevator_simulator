/**
 * @(#)Elevator.java
 *
 * Elevator application
 *
 * @author  Mondestin
 * @version 2.00
 */

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;


public class Sound
{
	private Clip clip; //clip

   // Constructor
	public Sound()
    {
    	try
      	{
      		// Open an audio input stream.
         	URL url = this.getClass().getClassLoader().getResource("media/ElevatorSound.wav");
         	AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);

         	// Get a sound clip resource.
         	clip = AudioSystem.getClip();
         	// Open audio clip and load samples from the audio input stream.
         	clip.open(audioIn);
      	} catch (UnsupportedAudioFileException e) {
         	e.printStackTrace();
      	} catch (IOException e) {
         	e.printStackTrace();
      	} catch (LineUnavailableException e) {
         	e.printStackTrace();
      	} catch (Exception e) {
      		e.printStackTrace();
      	}
    }
    //-------------------------------------------------- ///////////////// -----------------------------------------------------

   //play clip
	public void play()
    {
         clip.start();
    }
    //-------------------------------------------------- ///////////////// -----------------------------------------------------

    //stop clip
    public void stop()
    {
		if( clip.isRunning() )
          	clip.stop();   // Stop the player if it is still running
       	clip.setFramePosition(0); // rewind to the beginning
    }
    //-------------------------------------------------- ///////////////// -----------------------------------------------------
}