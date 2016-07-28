package com.simplebojocs.pondskater;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.games.Games;
import com.simplebojocs.pondskater.utils.iExternalServices;

public class GoogleServices implements iExternalServices, ConnectionCallbacks, OnConnectionFailedListener{
    private ConnectionStatus status;
    private Activity activity;

    public AdView adView;
    public GoogleApiClient googleApiClient;

    public GoogleServices(Activity activity){
        status = ConnectionStatus.DISCONNECTED;
        this.activity = activity;

        adView = new AdView(activity);
        adView.setAdUnitId(activity.getString(R.string.banner_ad_unit_id)); // ca-app-pub-9401292122550373/3606029643 esta mal, falta el 3 para que no nos baneen
        adView.setAdSize(AdSize.BANNER); // el de pau era ca-app-pub-3940256099942544/6300978111
        adView.loadAd(new AdRequest.Builder().build());

        googleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
    }
    @Override
    public void showAd(boolean visibility){
        //adView.setVisibility(visibility?View.VISIBLE:View.GONE);
        //adView.setVisibility(View.VISIBLE);
    }
    @Override
    public void submitScore(int score){
        if(googleApiClient.isConnected())
            Games.Leaderboards.submitScore(googleApiClient, activity.getString(R.string.leaderboard_pond_skater_records), score);

    }
    @Override
    public void showLeaderboard(){
        if(googleApiClient.isConnected()) {
            activity.startActivityForResult(
                    Games.Leaderboards.getLeaderboardIntent(
                            googleApiClient,
                            activity.getString(R.string.leaderboard_pond_skater_records)
                    ),
                    1
            );
        }else
            connect();
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
}
