package com.simplebojocs.pondskater.utils;

public interface iExternalServices {
    enum ConnectionStatus{
        DISCONNECTED, CONNECTED, CONNECTING
    }
    void showAd(boolean visibility);
    void submitScore(int score);
    void showLeaderboard();
    ConnectionStatus getConnectionStatus();
    void connect();
    void disconnect();
}
