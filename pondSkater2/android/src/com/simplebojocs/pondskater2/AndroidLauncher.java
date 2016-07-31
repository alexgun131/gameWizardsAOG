package com.simplebojocs.pondskater2;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	GoogleServices externalServices;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout = new RelativeLayout(this);

		externalServices = new GoogleServices(this);

		View gameView = initializeForView(
				new PondSkater(externalServices),
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
}
