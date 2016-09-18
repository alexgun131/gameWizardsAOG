package com.simplebojocs.pondskater2;

import android.app.Activity;
import android.content.Context;

import com.amazon.device.ads.AdLayout;
import com.simplebojocs.pondskater2.utils.PondSkaterAchievement;
import com.simplebojocs.pondskater2.utils.iExternalServices;

/**
 * Created by Oriol on 18/09/16.
 */
public class ServicesManager implements iExternalServices<PondSkaterAchievement> {
    AmazonServices amazonServices;
    GoogleServices googleServices;
    public AdLayout adView;

    public ServicesManager(Activity activity, Context context){
        amazonServices = new AmazonServices(activity, context);
        googleServices = new GoogleServices(activity, context);
        adView = amazonServices.adView;
    }

    @Override
    public void showAd(final boolean visibility){
        amazonServices.showAd(visibility);
    }

    @Override
    public void submitStandardScore(int score){
        googleServices.submitStandardScore(score);
    }

    @Override
    public void showStandardLeaderboard(){
        googleServices.showStandardLeaderboard();
    }

    @Override
    public void submitCompetitiveScore(int score){
        googleServices.submitCompetitiveScore(score);
    }

    @Override
    public void showCompetitiveLeaderboard(){
        googleServices.showCompetitiveLeaderboard();
    }

    @Override
    public void submitAchievement(PondSkaterAchievement achievement){
        googleServices.submitAchievement(achievement);
    }

    @Override
    public void unlockAchievement(PondSkaterAchievement achievement){
        googleServices.unlockAchievement(achievement);
    }

    @Override
    public void incrementAchievement(PondSkaterAchievement achievement, int increment){
        googleServices.incrementAchievement(achievement, increment);
    }

    @Override
    public void showAchievements(){
        googleServices.showAchievements();
    }

    @Override
    public ConnectionStatus getConnectionStatus(){
        return googleServices.getConnectionStatus();
    }

    @Override
    public void connect(){
        googleServices.connect();
    }

    @Override
    public void disconnect(){
        googleServices.disconnect();
    }

    @Override
    public void showToastFromGame(final CharSequence text){
    }

    @Override
    public boolean getShowAd()
    {
        return amazonServices.getShowAd();
    }
}
