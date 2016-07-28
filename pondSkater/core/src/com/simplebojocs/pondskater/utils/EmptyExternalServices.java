package com.simplebojocs.pondskater.utils;

/**
 * esta "maravilla" de android no deja, asi que baja reusabilidad de esta clase
 * public class EmptyExternalServices<V> implements iExternalServices<iAchievement>
 */
public class EmptyExternalServices implements iExternalServices<PondSkaterAchievement>{
    public void showAd(boolean visibility){}

    public void submitScore(int score){}
    public void showLeaderboard(){}

    public void submitAchievement(PondSkaterAchievement achievement){}
    public void unlockAchievement(PondSkaterAchievement achievement){}
    public void incrementAchievement(PondSkaterAchievement achievement, int increment){}
    public void showAchievements(){}

    public ConnectionStatus getConnectionStatus(){
        return ConnectionStatus.DISCONNECTED;
    }

    public void connect(){}
    public void disconnect(){}
}
