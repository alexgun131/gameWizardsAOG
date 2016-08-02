package com.simplebojocs.pondskater;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.simplebojocs.pondskater.utils.EmptyExternalServices;
import com.simplebojocs.pondskater.utils.PondSkaterAchievement;
import com.simplebojocs.pondskater.utils.iExternalServices;

public class PondSkater extends Game {
	private Screen screen;
	public final iExternalServices<PondSkaterAchievement> externalServices;

	public static Music music;

	public PondSkater(){
		externalServices = new EmptyExternalServices();
	}
	public PondSkater(iExternalServices<PondSkaterAchievement> externalServices){
		this.externalServices = externalServices;
	}

	@Override
	public void create() {
		music = Gdx.audio.newMusic(Gdx.files.internal("PondSkaterTheme.mid"));
		showMenuScreen();
	}

	public void showMenuScreen(){
		screen = new com.simplebojocs.pondskater.Menu.MenuScreen(this);
		setScreen(screen);
	}

	public void showDeadScreen(int score, int eaten, Music soundDeath){
		screen = new com.simplebojocs.pondskater.Menu.DeadScreen(this, score, eaten, soundDeath);
		setScreen(screen);
	}

	public void showAccelerometerConfigScreen(){
		screen = new com.simplebojocs.pondskater.Menu.AccelerometerConfigScreen(this);
		setScreen(screen);
	}

	public void showTopScoreScreen(){
		screen = new com.simplebojocs.pondskater.Menu.TopScoresScreen(this);
		setScreen(screen);
	}

	public void showGameScreen(){
		screen = new com.simplebojocs.pondskater.Game.GameScreen(this);
		setScreen(screen);
	}
	public Screen getScreen(){
		return screen;
	}
}
