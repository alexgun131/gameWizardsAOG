package com.simplebojocs.utils.externalServices;

public interface iAchievement {
    enum AchievementType{
        UNLOCK, INCREMENTAL
    }
    AchievementType getType();
}
