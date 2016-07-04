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

	public void showDeadScreen(){
		setScreen(new com.mygdx.game.Menu.DeadScreen(this));
	}

	public void showGameScreen(){
		setScreen(new com.mygdx.game.Game.GameScreen(this));
	}



}
