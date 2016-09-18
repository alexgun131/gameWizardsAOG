package com.simplebojocs.pondskater2.Menu;

/**
 * Created by Alex on 12/07/2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.simplebojocs.pondskater2.CONSTANTS;


public class BouncingBall extends InputAdapter {

    private static final Color COLOR = Color.RED;
    private static final float RADIUS_FACTOR = 1.0f / 20;

    Vector2 ballTarget;

    float baseRadius;
    float radiusMultiplier;

    Vector2 position;
    Vector2 velocity;

    Viewport viewport;
    boolean onTouch = false;

    boolean invertXY = false;
    boolean invertX = false;
    boolean invertY = false;

    Texture playerTexture;
    TextureRegion[] playerSprites;


    public BouncingBall(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {

        position = new Vector2(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2);
        velocity = new Vector2();
        ballTarget = new Vector2();
        baseRadius = RADIUS_FACTOR * Math.min(viewport.getWorldWidth(), viewport.getWorldHeight());
        radiusMultiplier = 1;
        loadTextures();
    }

    private void loadTextures() {
        int playerImgSize = 128;
        int animationColumns = 3;
        int animationRows = 4;

        //Load Textures
        playerTexture = new Texture("player.png");
        playerSprites = new TextureRegion[animationColumns * animationRows];
        for (int i = 0; i < animationColumns; i++) {
            playerSprites[i] = new TextureRegion(playerTexture, playerImgSize * i, 0, playerImgSize, playerImgSize);
            playerSprites[3 + i] = new TextureRegion(playerTexture, playerImgSize * i, playerImgSize, playerImgSize, playerImgSize); //Bonus, You are on Super Saiyan!
            playerSprites[6 + i] = new TextureRegion(playerTexture, playerImgSize * i, playerImgSize * 2, playerImgSize, playerImgSize); //Bonus, You are on Super Saiyan2!
            playerSprites[9 + i] = new TextureRegion(playerTexture, playerImgSize * i, playerImgSize * 3, playerImgSize, playerImgSize); //Bonus, You are on Super Saiyan3!
        }
    }

    public void update(float delta){

        readConfig();
        if(Gdx.input.getAccelerometerY() != 0) {
            float accelerometerInput;
            if(!invertXY)
                accelerometerInput = Gdx.input.getAccelerometerY();
            else
                accelerometerInput = Gdx.input.getAccelerometerX();

            if(!invertX)
                position.x += accelerometerInput * delta * CONSTANTS.PLAYER_VELOCITY;
            else
                position.x -= accelerometerInput * delta * CONSTANTS.PLAYER_VELOCITY;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            velocity.x = CONSTANTS.PLAYER_VELOCITY_KEY;
            position.x += delta* velocity.x * CONSTANTS.PLAYER_VELOCITY;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            velocity.x = -CONSTANTS.PLAYER_VELOCITY_KEY;
            position.x += delta* velocity.x * CONSTANTS.PLAYER_VELOCITY;
        }

        if((Gdx.input.isTouched()||Gdx.input.isKeyPressed(Input.Keys.SPACE)) && (onTouch == false)){
            onTouch = true;
            if(!invertY)
                velocity.y = CONSTANTS.JUMP_VELOCITY;
            else
                velocity.y = -CONSTANTS.JUMP_VELOCITY;
        }
        else{
            if(!invertY)
                velocity.y -= CONSTANTS.GRAVITATIONAL_ACCELERATION * CONSTANTS.JUMP_GRAVITY_MULT;
            else
                velocity.y += CONSTANTS.GRAVITATIONAL_ACCELERATION * CONSTANTS.JUMP_GRAVITY_MULT;
        }

        if(!(Gdx.input.isTouched()||Gdx.input.isKeyPressed(Input.Keys.SPACE)))
            onTouch=false;

        collideWithWalls(baseRadius, viewport.getWorldWidth(), viewport.getWorldHeight());
        position.y += delta * velocity.y;
    }

    private void collideWithWalls(float radius, float viewportWidth, float viewportHeight) {
        if (position.x - radius < 0) {
            position.x = radius;
            velocity.x = 0;
        }
        if (position.x + radius > viewportWidth) {
            position.x = viewportWidth - radius;
            velocity.x = 0;
        }
        if (position.y - radius < 0) {
            position.y = radius;
            velocity.y = 0;
        }
        if (position.y + radius > viewportHeight) {
            position.y = viewportHeight - radius;
            velocity.y = 0;
        }
    }

    public void render(SpriteBatch batch) {
        int sprite = 0;
        int jumpsprite = 0;
        batch.begin();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.draw(playerSprites[jumpsprite], position.x - CONSTANTS.PLAYER_RAD * 2, position.y - CONSTANTS.PLAYER_RAD, CONSTANTS.PLAYER_RAD * 4, CONSTANTS.PLAYER_RAD * 4);
        batch.end();
    }

    public void readConfig() {

        FileHandle topDataFile = Gdx.files.local(CONSTANTS.INVERTCONFIG_FILE_NAME);
        Json json = new Json();

        if (topDataFile.exists()) {
            try {
                String topAsCode = topDataFile.readString();
                String topAsText = Base64Coder.decodeString(topAsCode);
                boolean[] config = json.fromJson(boolean[].class, topAsText);
                invertXY = config[0];
                invertX = config[1];
                invertY = config[2];

            } catch (Exception e) {
                invertXY = false;
                invertX = false;
                invertY = false;

            }
        }
    }
}
