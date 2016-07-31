package com.simplebojocs.pondskater2.utils;

/**
 * esta "maravilla" de android no deja, asi que baja reusabilidad de esta clase
 * public class EmptyExternalServices<V> implements iExternalServices<iAchievement>
 */
public class EmptyExternalServices implements iExternalServices<com.simplebojocs.pondskater2.utils.PondSkaterAchievement>{
    public void showAd(boolean visibility){}

    public void submitScore(int score){}
    public void showLeaderboard(){}

    public void submitAchievement(com.simplebojocs.pondskater2.utils.PondSkaterAchievement achievement){}
    public void unlockAchievement(com.simplebojocs.pondskater2.utils.PondSkaterAchievement achievement){}
    public void incrementAchievement(com.simplebojocs.pondskater2.utils.PondSkaterAchievement achievement, int increment){}
    public void showAchievements(){}

    public ConnectionStatus getConnectionStatus(){
        return ConnectionStatus.DISCONNECTED;
    }

    public void connect(){}
    public void disconnect(){}
}
