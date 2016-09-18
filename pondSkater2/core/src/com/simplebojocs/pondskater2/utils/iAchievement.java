package com.simplebojocs.pondskater2.utils;

public interface iAchievement {
    enum AchievementType{
        UNLOCK, INCREMENTAL
    }
    AchievementType getType();
}
