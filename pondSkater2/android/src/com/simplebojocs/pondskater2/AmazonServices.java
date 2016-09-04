package com.simplebojocs.pondskater2;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdRegistration;
import com.amazon.device.ads.AdSize;
import com.amazon.device.ads.AdTargetingOptions;
import com.simplebojocs.pondskater2.utils.PondSkaterAchievement;
import com.simplebojocs.pondskater2.utils.iExternalServices;

/**
 * Created by Oriol on 28/08/16.
 */
public class AmazonServices implements iExternalServices<PondSkaterAchievement>  {
    private iExternalServices.ConnectionStatus status;
    private boolean debugMode = true;
    private String amazonKey = "sample-app-v1_pub-2";
    private Activity activity;
    private Context context;


    public AdLayout adView;

    public AmazonServices(Activity activity, Context context){
        status = ConnectionStatus.DISCONNECTED;
        this.activity = activity;
        this.context = context;
        AdRegistration.enableTesting(debugMode);
        AdRegistration.setAppKey(amazonKey);
        this.adView = new AdLayout(activity, AdSize.SIZE_320x50);
    }

    @Override
    public void showAd(final boolean visibility){
        if (visibility) {
            AdTargetingOptions adOptions = new AdTargetingOptions();
            adView.loadAd(adOptions);
            adView.enableAutoShow();
        } else {
            adView.disableAutoShow();
        }
        adView.showAd();
        /*activity.runOnUiThread(
                new Runnable(){
                    public void run(){
                        if (visibility) {
                            adView.loadAd(View.VISIBLE);
                        }
                    }
                }
        );*/
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
    public void showToastFromGame(CharSequence text){}

    public void dispose(){
        this.adView.destroy();
    }
}
