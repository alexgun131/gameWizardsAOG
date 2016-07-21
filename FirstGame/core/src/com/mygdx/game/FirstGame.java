package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class FirstGame extends Game {

	Music music;
	@Override
	public void create() {
		music = Gdx.audio.newMusic(Gdx.files.internal("PondSkaterTheme.mid"));
 		showMenuScreen();
	}

	public void showMenuScreen(){
		setScreen(new com.mygdx.game.Menu.MenuScreen(this));
	}

	public void showDeadScreen(int score, int eaten){
		setScreen(new com.mygdx.game.Menu.DeadScreen(this, score, eaten));
	}

	public void showAccelerometerConfigScreen(){
		setScreen(new com.mygdx.game.Menu.AccelerometerConfigScreen(this));
	}

	public void showTopScoreScreen(){
		setScreen(new com.mygdx.game.Menu.TopScoresScreen(this));
	}

	public void showGameScreen(){
		setScreen(new com.mygdx.game.Game.GameScreen(this));
	}

	public void showAd(boolean visibility){}

	public Music getMusic(){
		return music;
	}
}
