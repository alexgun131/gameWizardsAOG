package com.simplebojocs.pondskater;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication {
	AdView adView;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout = new RelativeLayout(this);

		View gameView = initializeForView(
				new PondSkater(){
					@Override public void showAd(boolean visibility){
						AndroidLauncher.this.showAd(visibility);
					}
				},
				new AndroidApplicationConfiguration()
		);

		adView = new AdView(this);
		adView.setAdUnitId(getString(R.string.banner_ad_unit_id)); // ca-app-pub-9401292122550373/3606029643 esta mal, falta el 3 para que no nos baneen
		adView.setAdSize(AdSize.BANNER); // el de pau era ca-app-pub-3940256099942544/6300978111
		adView.loadAd(new AdRequest.Builder().build());

		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		layout.addView(gameView);
		layout.addView(adView, adParams);

		setContentView(layout);
	}
	private void showAd(boolean visibility){
		//adView.setVisibility(visibility?View.VISIBLE:View.GONE);
		//adView.setVisibility(View.VISIBLE);
	}
}
