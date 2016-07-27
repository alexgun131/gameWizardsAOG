package com.simplebojocs.pondskater;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class PondSkater extends Game {

	public static Music music;
	@Override
	public void create() {
		music = Gdx.audio.newMusic(Gdx.files.internal("PondSkaterTheme.mid"));
		showMenuScreen();
	}

	public void showMenuScreen(){
		setScreen(new com.simplebojocs.pondskater.Menu.MenuScreen(this));
	}

	public void showDeadScreen(int score, int eaten, Music soundDeath){
		setScreen(new com.simplebojocs.pondskater.Menu.DeadScreen(this, score, eaten, soundDeath));
	}

	public void showAccelerometerConfigScreen(){
		setScreen(new com.simplebojocs.pondskater.Menu.AccelerometerConfigScreen(this));
	}

	public void showTopScoreScreen(){
		setScreen(new com.simplebojocs.pondskater.Menu.TopScoresScreen(this));
	}

	public void showGameScreen(){
		setScreen(new com.simplebojocs.pondskater.Game.GameScreen(this));
	}

	public void showAd(boolean visibility){}
}
