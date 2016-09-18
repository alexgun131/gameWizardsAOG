package com.simplebojocs.pondskater2;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.simplebojocs.pondskater2.utils.iToaster;

public class AndroidLauncher extends AndroidApplication {
	GoogleServices externalServices;
	PondSkater ps;
	iToaster itoaster;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout = new RelativeLayout(this);

		externalServices = new GoogleServices(this, getApplicationContext());
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
		if (!(ps.getScreen() instanceof com.simplebojocs.pondskater2.Game.GameScreen)) {
			externalServices.adView.setVisibility(View.GONE);
		} else {
			externalServices.showAd(true);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed(){
		if(ps != null) {
			if (!(ps.getScreen() instanceof com.simplebojocs.pondskater2.Game.GameScreen) &&
					!(ps.getScreen() instanceof com.simplebojocs.pondskater2.Menu.MenuScreen)) {
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						ps.showMenuScreen();
					}
				});
			}
			if (ps.getScreen() instanceof com.simplebojocs.pondskater2.Menu.MenuScreen){
				Gdx.app.exit();
			}
		}
	}
}
