package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Alex on 29/06/2016.
 */
public class CONSTANTS {

    public static final float WORLD_SIZE = 480.0f;
    public static final float PLAYER_RAD = WORLD_SIZE/18;
    public static final float ENEMY_WIDTH = WORLD_SIZE/20;
    public static final float ENEMY_HEIGHT = WORLD_SIZE/22;
    public static final float POINT_WIDTH = WORLD_SIZE/30 /2;

    public static final Color PLAYER_COLOR = Color.ORANGE;
    public static final Color ENEMY_COLOR = Color.DARK_GRAY;
    public static final Color POINT_COLOR = Color.FIREBRICK;
    public static final Color BACKGROUND_COLOR = Color.SKY;
    public static final Color SAND_COLOR = Color.FOREST;

    public static final float ACCELEROMETER_SENSITIVITY = 0.5f * WORLD_SIZE/480.0f;
    public static final float GRAVITATIONAL_ACCELERATION = 9.8f * WORLD_SIZE/480.0f;

    public static final float JUMP_VELOCITY = 300.0f * WORLD_SIZE/480.0f;
    public static final float JUMP_GRAVITY_MULT = 1.0f * WORLD_SIZE/480.0f;
    public static final float PLAYER_VELOCITY = 100.0f * WORLD_SIZE/480.0f;
    public static final float PLAYER_VELOCITY_KEY = 2.0f * WORLD_SIZE/480.0f;

    public static final float SPAWN_RATE = 1.6f;
    public static final float ENEMY_VELOCITY = 300.0f * WORLD_SIZE/480.0f;
    public static final float FRAME_THIKNESS = 35.0f * WORLD_SIZE/480.0f;

    public static final float TIME_SPAWN_POINTS = 2;

    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = WORLD_SIZE;

    // TODO: Add constant for the margin between the HUD and screen edge
    public static final float HUD_MARGIN = 20.0f * WORLD_SIZE/480.0f;

    public static final String TOP_FILE_NAME = "TOP_SCORES1";

    // Background animation
    public static final float WATER_HIGHLIGHT_SPEED = 1.0f;


}
