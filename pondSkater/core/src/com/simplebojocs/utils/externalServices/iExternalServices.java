package com.simplebojocs.utils.externalServices;

public interface iExternalServices<K extends iAchievement> {
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
    void submitAchievement(K achievement);
    void unlockAchievement(K achievement);
    void incrementAchievement(K achievement, int increment);
    void showAchievements();

    ConnectionStatus getConnectionStatus();
    void connect();
    void disconnect();
}
