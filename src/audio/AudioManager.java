package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioManager {

    public static int BACKGROUND = 0;
	

	public static int MARIODIES = 0;
	public static int JUMP = 1;
	public static int STOMP = 2;
	public static int ONEUP = 3;
	public static int BIGMUSHROOM = 4;
	public static int FIREBALL = 5;
	public static int COIN = 6;
	public static int GAMEOVER = 7;
	
	
	private Clip[] songs, effects;
	private int currentSongId;


	public AudioManager() {
		loadSongs();
		loadEffects();
	}

    public void playSong(int song) {
		stopSong();

		currentSongId = song;
		songs[currentSongId].setMicrosecondPosition(0);
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }

	public long pauseSong(){
		long currentTime = songs[0].getMicrosecondLength();
		stopSong();

		return currentTime;
	}

	public void playSongAt(long time){
		stopSong();
		songs[0].setMicrosecondPosition(time);
		songs[0].loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stopSong() {
		if (songs[currentSongId].isActive())
			songs[currentSongId].stop();
	}

    public void playEffect(int effect) {
		if (effects[effect].getMicrosecondPosition() > 0)
			// effects[effect].setMicrosecondPosition(0);
			effects[effect].setFramePosition(0);
		
		effects[effect].start();
    }
	private void loadSongs() {
		String[] names = {"background"};
		songs = new Clip[names.length];
		for (int i = 0; i < songs.length; i++)
			songs[i] = getClip(names[i]);
	}

	private void loadEffects() {
		String[] effectNames = { "marioDies", "jump", "stomp", "oneUp", "superMushroom", "fireball", "coin", "gameOver"};
		effects = new Clip[effectNames.length];
		for (int i = 0; i < effects.length; i++)
			effects[i] = getClip(effectNames[i]);

	}

	private Clip getClip(String name) {
		URL url = getClass().getResource("/audio/" + name + ".wav");
		AudioInputStream audio;

		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

			e.printStackTrace();
		}

		return null;

	}


	
}
