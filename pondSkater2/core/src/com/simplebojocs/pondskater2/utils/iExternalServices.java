package com.simplebojocs.pondskater2.utils;

public interface iExternalServices<V extends iAchievement> {
    enum ConnectionStatus{
        DISCONNECTED, CONNECTED, CONNECTING
    }
    void showAd(boolean visibility);

    void submitStandardScore(int score);
    void showStandardLeaderboard();

    void submitCompetitiveScore(int score);
    void showCompetitiveLeaderboard();

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
    void showToastFromGame(CharSequence text);
}
