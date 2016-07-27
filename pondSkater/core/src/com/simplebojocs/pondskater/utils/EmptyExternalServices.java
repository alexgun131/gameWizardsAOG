package com.simplebojocs.pondskater.utils;

public class EmptyExternalServices implements iExternalServices{
    public void showAd(boolean visibility){}
    public void submitScore(int score){}
    public void showLeaderboard(){}
    public ConnectionStatus getConnectionStatus(){
        return ConnectionStatus.DISCONNECTED;
    }
    public void connect(){}
    public void disconnect(){}
}
