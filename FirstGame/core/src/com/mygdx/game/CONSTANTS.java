package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 29/06/2016.
 */
public class CONSTANTS {

    //GAME CONSTANTS
    public static final float WORLD_SIZE = 480.0f;
    public static final float PLAYER_RAD = WORLD_SIZE/18;
    public static final float ENEMY_WIDTH = WORLD_SIZE/20;
    public static final float ENEMY_HEIGHT = WORLD_SIZE/22;
    public static final float POINT_WIDTH = WORLD_SIZE/30 /2;
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

    public static final float SPAWN_RATE = 1.3f;
    public static final float ENEMY_VELOCITY = 300.0f * WORLD_SIZE/480.0f;
    public static final float FRAME_THIKNESS = 35.0f * WORLD_SIZE/480.0f;

    public static final float TIME_SPAWN_POINTS = 2;

    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = WORLD_SIZE;

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

    public static final Vector2 MENU_OPTIONS = new Vector2(MENU_WORLD_SIZE / 4, MENU_WORLD_SIZE / 2);
    public static final Vector2 MENU_PLAYGAME = new Vector2(MENU_WORLD_SIZE / 2, MENU_WORLD_SIZE / 2);
    public static final Vector2 MENU_SCORES = new Vector2(MENU_WORLD_SIZE * 3 / 4, MENU_WORLD_SIZE / 2);

    public static final Color OPTIONS_COLOR = new Color(0.2f, 0.2f, 1, 1);
    public static final Color PLAY_COLOR = new Color(0.5f, 0.5f, 1, 1);
    public static final Color SCORES_COLOR = new Color(0.7f, 0.7f, 1, 1);

    public static final String OPTIONS_LABEL = "Options";
    public static final String PLAY_LABEL = "Play Game";
    public static final String SCORES_LABEL = "Scores";


    //DEAD CONSTANTS

    public static final Color DEAD_BACKGROUND_COLOR = Color.FIREBRICK;

    public static final float DEAD_WORLD_SIZE = 480.0f;
    public static final float DEAD_BUBBLE_RADIUS = DEAD_WORLD_SIZE / 9;
    public static final float DEAD_LABEL_SCALE = 1.5f;
    public static final float DEAD_SCORE_LABEL_SCALE = 1.5f;



    public static final Vector2 DEAD_MENU = new Vector2(DEAD_WORLD_SIZE / 4, DEAD_WORLD_SIZE / 2);
    public static final Vector2 DEAD_PLAYGAME = new Vector2(DEAD_WORLD_SIZE / 2, DEAD_WORLD_SIZE / 2);
    public static final Vector2 DEAD_SCORES = new Vector2(DEAD_WORLD_SIZE * 3 / 4, DEAD_WORLD_SIZE / 2);
    public static final Vector2 DEAD_YOUR_SCORE = new Vector2(DEAD_WORLD_SIZE / 2, DEAD_WORLD_SIZE * 3 / 4);

    public static final Color MENU_COLOR = new Color(0.2f, 0.2f, 1, 1);
    public static final Color PLAYAGAIN_COLOR = new Color(0.5f, 0.5f, 1, 1);
    public static final Color SCORESD_COLOR = new Color(0.7f, 0.7f, 1, 1);

    public static final String MENU_LABEL = "Menu";
    public static final String PLAYAGAIN_LABEL = "Play Again";
    public static final String SCORESD_LABEL = "Scores";
    public static final String YOUR_SCORE_LABEL = "Your score is: ";
    public static final String EATEN_LABEL = "Points eaten: ";


    //SCORES CONSTANTS
    public static final float SCORES_BUBBLE_RADIUS = DEAD_WORLD_SIZE / 15;
    public static final Vector2 BACK_TO_MENU = new Vector2(DEAD_WORLD_SIZE /20 + SCORES_BUBBLE_RADIUS, DEAD_WORLD_SIZE * 18/20);
    public static final Vector2 EATEN_SCORES = new Vector2(DEAD_WORLD_SIZE *10/ 20, DEAD_WORLD_SIZE * 18/20);
    public static final Vector2 TOP_SCORES = new Vector2(DEAD_WORLD_SIZE *19/20, DEAD_WORLD_SIZE *18/20);
    public static final float SCORE_LABEL_SCALE = 1.0f;

    public static final String TOP_EATEN_LABEL = "TOP EATEN";
    public static final String TOP_SCORES_LABEL = "TOP SCORES";

    //CONFIG CONSTANTS
    public static final String INVERTXY_LABEL = "InvertXY";
    public static final String INVERTX_LABEL = "InvertX";
    public static final String INVERTY_LABEL = "InvertY";

    public static final String INVERTCONFIG_FILE_NAME = "INVERT_CONFIG";

}
