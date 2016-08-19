package com.simplebojocs.pondskater2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.games.Games;
import com.simplebojocs.pondskater2.utils.PondSkaterAchievement;
import com.simplebojocs.pondskater2.utils.iExternalServices;
import com.simplebojocs.pondskater2.utils.iToaster;

public class GoogleServices implements iExternalServices<PondSkaterAchievement>, ConnectionCallbacks, OnConnectionFailedListener, iToaster{
    private iExternalServices.ConnectionStatus status;
    private Activity activity;

    public AdView adView;
    public GoogleApiClient googleApiClient;

    Handler handler;
    Context context;
    AdRequest add;

    public GoogleServices(Activity activity, Context context){
        status = ConnectionStatus.DISCONNECTED;
        this.activity = activity;

        adView = new AdView(activity);
        adView.setAdUnitId(activity.getString(R.string.banner_ad_unit_id)); // ca-app-pub-9401292122550373/3606029643 esta mal, falta el 3 para que no nos baneen
        adView.setAdSize(AdSize.BANNER); // el de pau era ca-app-pub-3940256099942544/6300978111
        add = new AdRequest.Builder().build();
        adView.loadAd(add);

        googleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

        handler = new Handler();
        this.context = context;

        //if (adView != null) {
        //}
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdOpened() {
                // Save app state before going to the ad overlay.
                 if (adView != null) {
                     adView.destroy();
                    }
            }
        });
    }

    @Override
    public void showAd(final boolean visibility){
        /*activity.runOnUiThread(
            new Runnable(){
                public void run(){
                    if (visibility) {
                        adView.setVisibility(View.VISIBLE);
                    } else {
                        adView.setVisibility(View.GONE);
                    }
                }
            }
        );*/
    }

    @Override
    public void submitStandardScore(int score){
        if(googleApiClient.isConnected()){
            Games.Leaderboards.submitScore(googleApiClient, activity.getString(R.string.leaderboard_pond_skater_standard_scores), score);
        }
    }
    @Override
    public void showStandardLeaderboard(){
        if(googleApiClient.isConnected()){
            activity.startActivityForResult(
                    Games.Leaderboards.getLeaderboardIntent(
                            googleApiClient,
                            activity.getString(R.string.leaderboard_pond_skater_standard_scores)
                    ),
                    1
            );}
        else{
            showToast("Google Play Games must be connected");
        }
    }

    @Override
    public void submitCompetitiveScore(int score){
        if(googleApiClient.isConnected()){
            Games.Leaderboards.submitScore(googleApiClient, activity.getString(R.string.leaderboard_pond_skater_competitive_scores), score);
        }
    }
    @Override
    public void showCompetitiveLeaderboard(){
        if(googleApiClient.isConnected()){
            activity.startActivityForResult(
                    Games.Leaderboards.getLeaderboardIntent(
                            googleApiClient,
                            activity.getString(R.string.leaderboard_pond_skater_competitive_scores)
                    ),
                    1
            );}
        else{
            showToast("Google Play Games must be connected");
        }
    }

    @Override
    public void submitAchievement(PondSkaterAchievement achievement){
        switch(achievement.getType()){
            case UNLOCK:
                unlockAchievement(achievement);
                break;
            case INCREMENTAL:
                incrementAchievement(achievement, 1);
                break;
        }
    }
    @Override
    public void unlockAchievement(PondSkaterAchievement achievement){
        if(googleApiClient.isConnected())
            Games.Achievements.unlock(googleApiClient, getAchievementID(achievement));

    }
    @Override
    public void incrementAchievement(PondSkaterAchievement achievement, int increment){
        if(googleApiClient.isConnected())
            Games.Achievements.increment(googleApiClient, getAchievementID(achievement), increment);
    }
    @Override
    public void showAchievements(){
        if(googleApiClient.isConnected()){
            activity.startActivityForResult(
                    Games.Achievements.getAchievementsIntent(googleApiClient),
                    1
            );
        }
        else{
            showToast("Google Play Games must be connected");
        }
    }



    private String getAchievementID(PondSkaterAchievement achievement){
        int id;
        switch(achievement){
            case FIRST_GAME:      id = R.string.achievement_first_game;            break;
            case POINTS_100:      id = R.string.achievement_100_points;            break;
            case POINTS_1K:       id = R.string.achievement_1000_points;           break;
            case POINTS_5K:       id = R.string.achievement_5000_points;           break;
            case HARDMODE_UNLOCK:       id = R.string.achievement_competitive_mode_unlocked;           break;
            case POINTS_10K:      id = R.string.achievement_10000_points;          break;
            case POINTS_20K:      id = R.string.achievement_20000_points;          break;
            case POINTS_1K_x_10:  id = R.string.achievement_10_games_over_1000;    break;
            case POINTS_10K_x_10: id = R.string.achievement_10_games_over_10000;   break;
            case POINTS_20K_x_5:  id = R.string.achievement_5_games_over_20000;    break;
            case MOSKITO_1:       id = R.string.achievement_first_moskito;         break;
            case MOSKITO_10:      id = R.string.achievement_10_moskitos_in_a_game; break;
            default:              id = -1;
        }
        return activity.getString(id);
    }

    @Override
    public ConnectionStatus getConnectionStatus(){
        return status;
    }
    @Override
    public void connect(){
        googleApiClient.connect();
        status = ConnectionStatus.CONNECTING;
    }
    @Override
    public void disconnect(){
        googleApiClient.disconnect();
        status = ConnectionStatus.DISCONNECTED;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        status = ConnectionStatus.CONNECTED;
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        status = ConnectionStatus.DISCONNECTED;
    }
    @Override
    public void onConnectionSuspended(int i) {
        status = ConnectionStatus.DISCONNECTED;
    }

    @Override
    public void showToast(final CharSequence text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
@Override
 public void showToastFromGame(CharSequence text){
            showToast(text);

    }

}