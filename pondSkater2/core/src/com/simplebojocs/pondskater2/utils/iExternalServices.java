package com.simplebojocs.pondskater2.utils;

public interface iExternalServices<V extends iAchievement> {
    enum ConnectionStatus{
        DISCONNECTED, CONNECTED, CONNECTING
    }
    void showAd(boolean visibility);

    void submitScore(int score);
    void showLeaderboard();

    /**
     * - Si @achievement.type = UNLOCK llama a unlockAchievement
     * - Si @achievement.type = INCREMENT llama a incrementAchievement en 1
     * @param achievement
     */
    void submitAchievement(V achievement);
    void unlockAchievement(V achievement);
    void incrementAchievement(V achievement, int increment);
    void showAchievements();

    ConnectionStatus getConnectionStatus();
    void connect();
    void disconnect();
}
