package com.simplebojocs.pondskater;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.simplebojocs.pondskater.mainmenu2.MenuScreen2;

public class AndroidLauncher extends AndroidApplication {
	GoogleServices externalServices;
	PondSkater ps;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout = new RelativeLayout(this);

		externalServices = new GoogleServices(this);

		ps = new PondSkater(externalServices);

		View gameView = initializeForView(
				ps,
				new AndroidApplicationConfiguration()
		);

		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		layout.addView(gameView);
		layout.addView(externalServices.adView, adParams);

		setContentView(layout);
	}

	@Override
	protected void onStart() {
		super.onStart();
		externalServices.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		externalServices.disconnect();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBackPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed(){
		if(ps != null &&
				!(ps.getScreen() instanceof com.simplebojocs.pondskater.Game.GameScreen) &&
				!(ps.getScreen() instanceof com.simplebojocs.pondskater.Menu.MenuScreen)){
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					ps.setScreen(new MenuScreen2(ps));
				}
			});
		}
	}
}
