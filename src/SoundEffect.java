import java.io.IOException;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public enum SoundEffect {
	bulletFired("Sounds/BulletFired.wav"),
	birdFlying("Sounds/BirdFlying.wav");
	
	private Clip clip;
	
	
	private SoundEffect(String Soundeffect) {
		
		
		try{
			URL soundurl = this.getClass().getResource(Soundeffect);
			AudioInputStream audiostream = AudioSystem.getAudioInputStream(soundurl);
			
			
			clip = AudioSystem.getClip();
			clip.open(audiostream);
			
			
			
		}
		catch(IOException ex){}
		catch(UnsupportedAudioFileException ex){}
		catch (LineUnavailableException e) {}
			
	}
	
	public void play(){
		clip.setFramePosition(0);
		clip.start();		
		
	}
}
