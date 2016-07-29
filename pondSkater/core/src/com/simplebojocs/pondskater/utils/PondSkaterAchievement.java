package com.simplebojocs.pondskater.utils;

public enum PondSkaterAchievement implements iAchievement{
    FIRST_GAME(AchievementType.UNLOCK),
    POINTS_100(AchievementType.UNLOCK),
    POINTS_1K(AchievementType.UNLOCK),
    POINTS_5K(AchievementType.UNLOCK),
    POINTS_10K(AchievementType.UNLOCK),
    POINTS_20K(AchievementType.UNLOCK),
    POINTS_1K_x_10(AchievementType.INCREMENTAL),
    POINTS_10K_x_10(AchievementType.INCREMENTAL),
    POINTS_20K_x_5(AchievementType.INCREMENTAL),
    MOSKITO_1(AchievementType.UNLOCK),
    MOSKITO_10(AchievementType.UNLOCK);

    final AchievementType type;

    private PondSkaterAchievement(AchievementType type){
        this.type = type;
    }
    public AchievementType getType(){
        return type;
    }
}