package com.simplebojocs.pondskater2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.simplebojocs.pondskater2.utils.EmptyExternalServices;
import com.simplebojocs.pondskater2.utils.PondSkaterAchievement;
import com.simplebojocs.pondskater2.utils.iExternalServices;
import com.simplebojocs.pondskater2.utils.iToaster;

public class PondSkater extends Game {
	public final iExternalServices<PondSkaterAchievement> externalServices;

	public static Music music;

	public iToaster itoaster;

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
		setScreen(new com.simplebojocs.pondskater2.Menu.MenuScreen(this));
	}

	public void showDeadScreen(int score, int eaten, Music soundDeath){
		setScreen(new com.simplebojocs.pondskater2.Menu.DeadScreen(this, score, eaten, soundDeath));
	}

	public void showAccelerometerConfigScreen(){
		setScreen(new com.simplebojocs.pondskater2.Menu.AccelerometerConfigScreen(this));
	}

	public void showTopScoreScreen(){
		setScreen(new com.simplebojocs.pondskater2.Menu.TopScoresScreen(this));
	}

	public void showGameScreen(){
		setScreen(new com.simplebojocs.pondskater2.Game.GameScreen(this));
	}
}
