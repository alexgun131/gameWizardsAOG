package com.simplebojocs.pondskater.utils;

public enum PondSkaterAchievement implements com.simplebojocs.utils.externalServices.iAchievement {
    FIRST_GAME      (AchievementType.UNLOCK,      PondSkaterAchievementType.OTHERS,       0),
    POINTS_100      (AchievementType.UNLOCK,      PondSkaterAchievementType.POINTS,     100),
    POINTS_1K       (AchievementType.UNLOCK,      PondSkaterAchievementType.POINTS,    1000),
    POINTS_5K       (AchievementType.UNLOCK,      PondSkaterAchievementType.POINTS,    5000),
    POINTS_10K      (AchievementType.UNLOCK,      PondSkaterAchievementType.POINTS,   10000),
    POINTS_20K      (AchievementType.UNLOCK,      PondSkaterAchievementType.POINTS,   20000),
    POINTS_1K_x_10  (AchievementType.INCREMENTAL, PondSkaterAchievementType.POINTS,    1000),
    POINTS_10K_x_10 (AchievementType.INCREMENTAL, PondSkaterAchievementType.POINTS,   10000),
    POINTS_20K_x_5  (AchievementType.INCREMENTAL, PondSkaterAchievementType.POINTS,   20000),
    MOSKITO_1       (AchievementType.UNLOCK,      PondSkaterAchievementType.MOSKITOS,     1),
    MOSKITO_10      (AchievementType.UNLOCK,      PondSkaterAchievementType.MOSKITOS,    10);

    public enum PondSkaterAchievementType{
        POINTS, MOSKITOS, OTHERS
    }

    public final AchievementType type;
    public final PondSkaterAchievementType psaType;
    public final int amount;

    PondSkaterAchievement(AchievementType type, PondSkaterAchievementType psaType, int amount){
        this.type = type;
        this.psaType = psaType;
        this.amount = amount;
    }
    public AchievementType getType(){
        return type;
    }
}