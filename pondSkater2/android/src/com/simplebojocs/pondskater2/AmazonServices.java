package com.simplebojocs.pondskater2;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdListener;
import com.amazon.device.ads.AdRegistration;
import com.amazon.device.ads.AdSize;
import com.amazon.device.ads.AdTargetingOptions;
import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdError;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.DefaultAdListener;
import com.simplebojocs.pondskater2.utils.PondSkaterAchievement;
import com.simplebojocs.pondskater2.utils.iExternalServices;
import com.simplebojocs.pondskater2.utils.iToaster;

/**
 * Created by Oriol on 28/08/16.
 */
public class AmazonServices implements iExternalServices<PondSkaterAchievement>  {
    private iExternalServices.ConnectionStatus status;
    private boolean debugMode = true;
    private String amazonKey = "sample-app-v1_pub-2";
    private Activity activity;
    private Context context;
    private Handler handler;
    private boolean showAd;
    private boolean adInitialized;
    public AdLayout adView;

    private class MyAdListerner extends DefaultAdListener{
        /*@Override
        public void onAdLoaded(Ad var1, AdProperties var2)
        {
            showToast("Ads Loaded");
        }*/

        /*@Override
        public void onAdFailedToLoad(Ad var1, AdError var2)
        {
            showToast("Ads failed to load");
        }*/

        @Override
        public void onAdExpanded(Ad var1)
        {
            if (adView != null) {
                showToast("Thank you! Karma destroyed the ads!");
                adView.destroy();
                showAd = false;
            }
        }

        @Override
        public void onAdCollapsed(Ad var1)
        {
            if (adView != null) {
                showToast("Thank you! Karma destroyed the ads!");
                adView.destroy();
                showAd = false;
            }
        }

        @Override
        public void onAdDismissed(Ad var1)
        {
            if (adView != null) {
                showToast("Thank you! Karma destroyed the ads!");
                adView.destroy();
                showAd = false;
            }
        }
    }

    public AmazonServices(Activity activity, Context context){
        this.status = ConnectionStatus.DISCONNECTED;
        this.showAd = true;
        this.adInitialized = false;
        this.activity = activity;
        this.context = context;
        handler = new Handler();
        AdRegistration.enableTesting(debugMode);
        AdRegistration.setAppKey(amazonKey);
        this.adView = new AdLayout(activity, AdSize.SIZE_320x50);
        this.adView.enableAutoShow();

        adView.setListener(new MyAdListerner());
    }

    @Override
    public void showAd(final boolean visibility){
        if (!adInitialized){
            adView.loadAd(new AdTargetingOptions()); //refresh ad
            adView.showAd();
            adInitialized = true;
        }
        activity.runOnUiThread(
                new Runnable(){
                    public void run(){
                        if (visibility) {
                            adView.setVisibility(View.VISIBLE);
                        }
                        else{
                            adView.setVisibility(View.GONE);
                        }
                    }
                }
        );
    }

    @Override
    public void submitStandardScore(int score){}

    @Override
    public void showStandardLeaderboard(){}

    @Override
    public void submitCompetitiveScore(int score){}

    @Override
    public void showCompetitiveLeaderboard(){}

    @Override
    public void submitAchievement(PondSkaterAchievement achievement){}

    @Override
    public void unlockAchievement(PondSkaterAchievement achievement){}

    @Override
    public void incrementAchievement(PondSkaterAchievement achievement, int increment){}

    @Override
    public void showAchievements(){}

    @Override
    public ConnectionStatus getConnectionStatus(){return status;}

    @Override
    public void connect(){}

    @Override
    public void disconnect(){}

    @Override
    public void showToastFromGame(final CharSequence text){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showToast(CharSequence text)
    {
        showToastFromGame(text);
    }

    @Override
    public boolean getShowAd()
    {
        return showAd;
    }

    public void dispose(){
        this.adView.destroy();
    }
}
