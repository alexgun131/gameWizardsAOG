package com.simplebojocs.pondskater2;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.simplebojocs.pondskater2.utils.EmptyExternalServices;
import com.simplebojocs.pondskater2.utils.PondSkaterAchievement;
import com.simplebojocs.pondskater2.utils.iExternalServices;
import com.simplebojocs.pondskater2.utils.iToaster;

public class PondSkater extends Game {
	public final iExternalServices<PondSkaterAchievement> externalServices;

	public static Music gamemusic;
	public static Music soundDeath;
	public static Music musicDeath;
	public static Music moskitoMusic;
	public static Sound eatLarvae;
	public static Sound eatMoskito;


	public iToaster itoaster;

	public PondSkater(){
		externalServices = new EmptyExternalServices();
	}
	public PondSkater(iExternalServices<PondSkaterAchievement> externalServices){
		this.externalServices = externalServices;
	}


	@Override
	public void create() {
		gamemusic = Gdx.audio.newMusic(Gdx.files.getFileHandle("PondSkaterTheme.mid", Files.FileType.Internal));
        gamemusic.setVolume(1.2f);
        gamemusic.setLooping(true);

        soundDeath = Gdx.audio.newMusic(Gdx.files.getFileHandle("Death_sound_2.mp3", Files.FileType.Internal));
        soundDeath.setVolume(1.2f);

		moskitoMusic = Gdx.audio.newMusic(Gdx.files.getFileHandle("Kito_the_Moskito.mid", Files.FileType.Internal));
        moskitoMusic.setVolume(0.45f);

		eatLarvae = Gdx.audio.newSound(Gdx.files.internal("Eat_larvae_cut.mp3"));
		eatMoskito = Gdx.audio.newSound(Gdx.files.internal("eatMoskito.mp3"));

        musicDeath = Gdx.audio.newMusic(Gdx.files.internal("Death_theme_2.mid"));
        musicDeath.setVolume(0.9f);                 // sets the volume to half the maximum volume
        musicDeath.setLooping(true);                // will repeat playback until music.stop() is called
		showMenuScreen();
	}

	public void showMenuScreen(){
		setScreen(new com.simplebojocs.pondskater2.Menu.MenuScreen(this));
	}

	public void showDeadScreen(int score, int eaten){
		setScreen(new com.simplebojocs.pondskater2.Menu.DeadScreen(this, score, eaten));
	}

	public void showAccelerometerConfigScreen(){
		setScreen(new com.simplebojocs.pondskater2.Menu.AccelerometerConfigScreen(this));
	}

	public void showTopScoreScreen(){
		setScreen(new com.simplebojocs.pondskater2.Menu.TopScoresScreen(this));
	}

	public void showGameScreen(){
		setScreen(new com.simplebojocs.pondskater2.Game.GameScreen(this, false));
	}

	public void showHardModeScreen(){
		setScreen(new com.simplebojocs.pondskater2.Game.GameScreen(this, true));
	}
}
