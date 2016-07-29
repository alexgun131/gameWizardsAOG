package com.simplebojocs.pondskater.utils;

public interface iAchievement {
    enum AchievementType{
        UNLOCK, INCREMENTAL
    }
    AchievementType getType();
}
