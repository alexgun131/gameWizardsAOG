package com.mygdx.game;

import com.badlogic.gdx.Game;

public class FirstGame extends Game {

	@Override
	public void create() {
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
}
