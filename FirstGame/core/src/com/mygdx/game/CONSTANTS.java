package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 29/06/2016.
 */
public class CONSTANTS {

    //GAME CONSTANTS
    public static final float WORLD_SIZE = 480.0f;
    public static final float PLAYER_RAD = WORLD_SIZE/35;
    public static final float ENEMY_WIDTH = WORLD_SIZE/20;
    public static final float ENEMY_HEIGHT = WORLD_SIZE/22;
    public static final float POINT_WIDTH = WORLD_SIZE/30 /2;
    public static final float SUPERPOINT_WIDTH = WORLD_SIZE/27 /2;
    public static final int NUMBER_TOPSCORES = 15;

    public static final Color PLAYER_COLOR = Color.ORANGE;
    public static final Color ENEMY_COLOR = Color.DARK_GRAY;
    public static final Color POINT_COLOR = Color.FIREBRICK;
    public static final Color BACKGROUND_COLOR = Color.FIREBRICK;
    public static final Color SAND_COLOR = Color.FOREST;

    public static final float ACCELEROMETER_SENSITIVITY = 0.5f * WORLD_SIZE/480.0f;
    public static final float GRAVITATIONAL_ACCELERATION = 9.8f * WORLD_SIZE/480.0f;

    public static final float JUMP_VELOCITY = 300.0f * WORLD_SIZE/480.0f;
    public static final float JUMP_GRAVITY_MULT = 1.0f * WORLD_SIZE/480.0f;
    public static final float PLAYER_VELOCITY = 100.0f * WORLD_SIZE/480.0f;
    public static final float PLAYER_VELOCITY_KEY = 2.0f * WORLD_SIZE/480.0f;

    public static final float SPAWN_RATE = 1.27f;
    public static final float ENEMY_VELOCITY = 300.0f * WORLD_SIZE/480.0f;
    public static final float ENEMY_AMP_SIN = WORLD_SIZE/1000;
    public static final float ENEMY_W_SIN = WORLD_SIZE/40;
    public static final float POINT_AMP_SIN = WORLD_SIZE/3000;
    public static final float POINT_W_SIN = WORLD_SIZE/300;
    public static final float SUPERPOINT_VELOCITY = 245.0f * WORLD_SIZE/480.0f;
    public static final float SUPERPOINT_AMP_SIN = WORLD_SIZE/70;
    public static final float SUPERPOINT_W_SIN = WORLD_SIZE/170;
    public static final float FRAME_THIKNESS = 35.0f * WORLD_SIZE/480.0f;

    public static final float TIME_SPAWN_POINTS = 2;
    public static final float TIME_SPAWN_SUPERPOINTS = 10;
    public static final int VALUE_SCORE_SPAWN_SUPERPOINTS = 5;
    public static final float TIME_SHOW_DEATH = 1;

    public static final float ADD_BANNER_HEIGHT = 50;
    public static final float ADD_BANNER_WIDTH = 320;
    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = WORLD_SIZE/1.5f;

    public static final float HUD_MARGIN = 20.0f * WORLD_SIZE/480.0f;

    public static final String TOP_FILE_NAME = "TOP_SCORES2";

    // Background animation
    public static final float WATER_HIGHLIGHT_SPEED = 1.0f;
    public static final float WATER_SPEED = 75.0f * WORLD_SIZE/480.0f;
    public static final float RIVER_BANK_SPEED = 20.0f * WORLD_SIZE/480.0f;



    //MENU CONSTANTS

    public static final Color MENU_BACKGROUND_COLOR = Color.YELLOW;

    public static final float MENU_WORLD_SIZE = 480.0f;
    public static final float MENU_BUBBLE_RADIUS = MENU_WORLD_SIZE / 9;
    public static final float MENU_LABEL_SCALE = 1.5f;

    public static final Vector2 MENU_OPTIONS = new Vector2(MENU_WORLD_SIZE / 5, MENU_WORLD_SIZE / 2);
    public static final Vector2 MENU_PLAYGAME = new Vector2(MENU_WORLD_SIZE / 2, MENU_WORLD_SIZE / 2);
    public static final Vector2 MENU_SCORES = new Vector2(MENU_WORLD_SIZE - MENU_WORLD_SIZE / 5, MENU_WORLD_SIZE / 2);

    public static final Color OPTIONS_COLOR = new Color(0.2f, 0.2f, 1, 1);
    public static final Color PLAY_COLOR = new Color(0.5f, 0.5f, 1, 1);
    public static final Color SCORES_COLOR = new Color(0.7f, 0.7f, 1, 1);

    public static final String[] OPTIONS_LABEL = {"Settings", "Ajustes", "设置", "設定", "설정", "إعدادات"};
    public static final String[] PLAY_LABEL = {"Play Game", "Jugar", "玩", "遊びます", "놀이", "لعب"};
    public static final String[] SCORES_LABEL = {"Scores", "Marcador", "分数", "スコア", "점수", "عشرات"};


    //DEAD CONSTANTS

    public static final Color DEAD_BACKGROUND_COLOR = Color.FIREBRICK;

    public static final float DEAD_WORLD_SIZE = 480.0f;
    public static final float DEAD_BUBBLE_RADIUS = DEAD_WORLD_SIZE / 9;
    public static final float DEAD_LABEL_SCALE = 1.5f;
    public static final float DEAD_SCORE_LABEL_SCALE = 1.5f;



    public static final Vector2 DEAD_MENU = new Vector2(DEAD_WORLD_SIZE / 5, DEAD_WORLD_SIZE / 2);
    public static final Vector2 DEAD_PLAYGAME = new Vector2(DEAD_WORLD_SIZE / 2, DEAD_WORLD_SIZE / 2);
    public static final Vector2 DEAD_SCORES = new Vector2(DEAD_WORLD_SIZE -  DEAD_WORLD_SIZE / 5, DEAD_WORLD_SIZE / 2);
    public static final Vector2 DEAD_YOUR_SCORE = new Vector2(DEAD_WORLD_SIZE / 2, DEAD_WORLD_SIZE * 3 / 4);

    public static final Color MENU_COLOR = new Color(0.2f, 0.2f, 1, 1);
    public static final Color PLAYAGAIN_COLOR = new Color(0.5f, 0.5f, 1, 1);
    public static final Color SCORESD_COLOR = new Color(0.7f, 0.7f, 1, 1);

    public static final String[] MENU_LABEL = {"Menu", "Menú", "菜单", "メニュー", "메뉴", "قائمة طعام"};
    public static final String[] PLAYAGAIN_LABEL = {"Play Again", "Volver a jugar", "再玩一次", "再びプレー", "다시 플레이",  "إلعب مرة أخرى"};
    public static final String[] SCORESD_LABEL = {"Scores", "Marcador", "成绩", "スコア", "점수", "شرات"};
    public static final String[] YOUR_SCORE_LABEL = {"Your score is: ", "Tu puntuación es: ",  "你的标点符号: ", "あなたの句読点は、: ", "당신의 문장 은: " , "علامات الترقيم الخاص بك هو"};
    public static final String[] EATEN_LABEL = {"Bonus: ", "Bonus: ", "奖金 ", "ボーナス ", "보너스 ", "علاو ة"};
    public static final String[] CURRENTSCORE = {"Score: ", "Puntuación: ",  "得分了: ", "スコア: ", "점수: ", "أحرز هدفاً " };


    //SCORES CONSTANTS
    public static final float SCORES_BUBBLE_RADIUS = DEAD_WORLD_SIZE / 15;
    public static final Vector2 BACK_TO_MENU = new Vector2(DEAD_WORLD_SIZE /20 + SCORES_BUBBLE_RADIUS, DEAD_WORLD_SIZE * 18/20 - ADD_BANNER_HEIGHT);
    public static final Vector2 EATEN_SCORES = new Vector2(DEAD_WORLD_SIZE *10/ 20, DEAD_WORLD_SIZE * 18/20 - ADD_BANNER_HEIGHT);
    public static final Vector2 TOP_SCORES = new Vector2(DEAD_WORLD_SIZE *19/20, DEAD_WORLD_SIZE *18/20 - ADD_BANNER_HEIGHT);
    public static final float SCORE_LABEL_SCALE = 1.0f;

    public static final String[] TOP_EATEN_LABEL = {"TOP BONUS", "MEJORES BONUS", "頂級獎金", "トップボーナス", "최고 보너스", "أعلى مكافأة"};
    public static final String[] TOP_SCORES_LABEL = {"TOP SCORES", "MEJORES PUNTUACIONES", "最佳射手", "得点王", "득점 왕", "الاعلى نقاطا"};

    //CONFIG CONSTANTS
    public static final String[] INVERTXY_LABEL = {"InvertXY", "InvertirXY", "倒置XY", "反転XY", "거꾸로 하다XY", "عكسXY" } ;
    public static final String[] INVERTX_LABEL = {"InvertX", "InvertirX", "倒置X", "反転X", "거꾸로 하다X", "عكسX"};
    public static final String[] INVERTY_LABEL = {"InvertY", "InvertirY", "倒置Y", "反転Y", "거꾸로 하다Y", "عكسY"};

    public static final String INVERTCONFIG_FILE_NAME = "INVERT_CONFIG";
    public static final String LANGUAJECONFIG_FILE_NAME = "LANGUAJE_CONFIG";

    public static final String[] SELECT_LANGUAJE = {"ENGLISH", "ESPAÑOL", "中国", "日本の", "한국의", "العربية"}; //eng, esp, chi, jap, kor, arabe

}
