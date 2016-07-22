package com.mygdx.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.CONSTANTS;

/**
 * Created by Alex on 29/06/2016.
 */
public class Player{


    Vector2 position;
    Vector2 velocity;

    Viewport viewport;
    boolean onTouch = false;

    float animationFps;
    float playerJumpFPS;
    Texture playerTexture;
    TextureRegion[] playerSprites;

    boolean invertXY = false;
    boolean invertX = false;
    boolean invertY = false;



    public Player(Viewport viewport) {
        this.viewport = viewport;
        init();
        animationFps = 0.0f;
        playerJumpFPS = 0.0f;
        loadTextures();

    }

    private void loadTextures() {
        int playerImgSize = 128;
        int animationColumns = 3;
        int animationRows = 4;

        //Load Textures
        playerTexture = new Texture("player.png");
        playerSprites = new TextureRegion[animationColumns*animationRows];
        for (int i= 0; i<animationColumns; i++){
            playerSprites[i] = new TextureRegion(playerTexture, playerImgSize*i, 0, playerImgSize, playerImgSize);
            playerSprites[3+i] = new TextureRegion(playerTexture, playerImgSize*i, playerImgSize, playerImgSize, playerImgSize); //Bonus, You are on Super Saiyan!
            playerSprites[6+i] = new TextureRegion(playerTexture, playerImgSize*i, playerImgSize*2, playerImgSize, playerImgSize); //Bonus, You are on Super Saiyan2!
            playerSprites[9+i] = new TextureRegion(playerTexture, playerImgSize*i, playerImgSize*3, playerImgSize, playerImgSize); //Bonus, You are on Super Saiyan3!
        }
    }

    public void init(){
        position = new Vector2(viewport.getWorldWidth()/3, viewport.getWorldHeight()/2);
        velocity = new Vector2(0,0);
        readConfig();
    }

    public void update(float delta){
        animationFps += delta % 100; //fps up to 100 seconds (max animation time?)
        if (playerJumpFPS>0.0f) {
            playerJumpFPS += delta;
        }

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

            playerJumpFPS = delta;
        }
        else{
            if(!invertY)
                velocity.y -= CONSTANTS.GRAVITATIONAL_ACCELERATION * CONSTANTS.JUMP_GRAVITY_MULT;
            else
                velocity.y += CONSTANTS.GRAVITATIONAL_ACCELERATION * CONSTANTS.JUMP_GRAVITY_MULT;
        }

        if(!(Gdx.input.isTouched()||Gdx.input.isKeyPressed(Input.Keys.SPACE)))
            onTouch=false;

        position.y += delta * velocity.y;
    }

    boolean ensureInBounds(){
        boolean outOfBounds = false;
        if(position.x>viewport.getWorldWidth()-CONSTANTS.PLAYER_RAD){
            position.x = viewport.getWorldWidth()-CONSTANTS.PLAYER_RAD;
            velocity.x = -velocity.x;
            //outOfBounds = true;
        }
        else if(position.x<CONSTANTS.PLAYER_RAD){
            position.x = CONSTANTS.PLAYER_RAD;
            velocity.x = -velocity.x;
            //outOfBounds = true;
        }

        if(position.y < (0)){
            //position.y = 0;
            //velocity.y = - velocity.y/2;
            outOfBounds = true;
        }
        if(position.y > viewport.getWorldHeight()-CONSTANTS.PLAYER_RAD * 2){
            //position.y= viewport.getWorldHeight()-CONSTANTS.PLAYER_RAD * 2;
            //velocity.y = - velocity.y/2;
            outOfBounds = true;
        }
        return outOfBounds;
    }

    public boolean hitByIcicle(Enemies enemies) {
        boolean isHit = false;
        for (com.mygdx.game.Game.Enemy enemy : enemies.enemyList) {
            if (enemyDistance(enemy.position) < CONSTANTS.PLAYER_RAD+CONSTANTS.ENEMY_HEIGHT/2) {
                isHit = true;
            }
        }

        return isHit;
    }

    public boolean getPoint(Point point){
        boolean got = false;
        if(pointDistance(point.position) < CONSTANTS.PLAYER_RAD+CONSTANTS.POINT_WIDTH){
            got = true;
        }

        return got;
    }

    public boolean getSuperPoint(SuperPoint superPoint){
        boolean got = false;
        if(pointDistance(superPoint.position) < CONSTANTS.PLAYER_RAD+CONSTANTS.SUPERPOINT_WIDTH){
            got = true;
        }

        return got;
    }

    public double enemyDistance(Vector2 enemyPosition){
        float x2 = (enemyPosition.x + CONSTANTS.ENEMY_WIDTH/2 - position.x) * (enemyPosition.x + CONSTANTS.ENEMY_WIDTH/2 - position.x);
        float y2 = ((enemyPosition.y + CONSTANTS.ENEMY_HEIGHT/2) - (position.y + CONSTANTS.PLAYER_RAD))
                * ((enemyPosition.y + CONSTANTS.ENEMY_HEIGHT/2) - (position.y + CONSTANTS.PLAYER_RAD));
        double dist = Math.sqrt(x2+y2);
        return dist;
    }

    public double pointDistance(Vector2 pointPosition){
        float x2 = (pointPosition.x - position.x) * (pointPosition.x - position.x);
        float y2 = ((pointPosition.y + CONSTANTS.POINT_WIDTH) - (position.y + CONSTANTS.PLAYER_RAD))
                * ((pointPosition.y + CONSTANTS.POINT_WIDTH) - (position.y + CONSTANTS.PLAYER_RAD));
        double dist = Math.sqrt(x2+y2);
        return dist;
    }
    public void render(SpriteBatch batch, int beatHighestScore){
        //renderer.setColor(CONSTANTS.PLAYER_COLOR);
        //renderer.circle(position.x, position.y + CONSTANTS.PLAYER_RAD, CONSTANTS.PLAYER_RAD);

        int sprite = getAnimationSprite();
        int jumpsprite = getJumpSprite();

        batch.draw(playerSprites[jumpsprite], position.x-CONSTANTS.PLAYER_RAD*2, position.y-CONSTANTS.PLAYER_RAD, CONSTANTS.PLAYER_RAD*4, CONSTANTS.PLAYER_RAD*4);
        if (beatHighestScore!= 0){
            batch.draw(playerSprites[sprite+3*beatHighestScore], position.x-(int)(CONSTANTS.PLAYER_RAD*2.5), position.y-(int)(CONSTANTS.PLAYER_RAD*5/4), CONSTANTS.PLAYER_RAD*5, CONSTANTS.PLAYER_RAD*5);
        }
    }

    private int getAnimationSprite() {
        //TODO: this is poorly coded
        //TODO animation fps are hardcoded
        int sprite = 0;
        if ((animationFps%1)>0.75){
            sprite = 1;
        } else if ((animationFps%1)>0.5){
            sprite = 2;
        } else if ((animationFps%1)>0.25){
            sprite = 1;
        }
        return sprite;
    }

    private int getJumpSprite() {
        //TODO: this is poorly coded
        //TODO animation fps are hardcoded
        int sprite = 0;
        /*if (playerJumpFPS>0.525f){
            playerJumpFPS = 0.0f;
        } else if (playerJumpFPS>0.45f){
            sprite = 1;
        } else if ((playerJumpFPS%1)>0.375f){
            sprite = 2;
        } else if ((playerJumpFPS%1)>0.3f){
            sprite = 1;
        } else if (playerJumpFPS>0.225f){
            sprite = 0;*/
        if (playerJumpFPS>0.225f){
            playerJumpFPS = 0.0f;
        } else if (playerJumpFPS>0.15f){
            sprite = 1;
        } else if ((playerJumpFPS%1)>0.075f){
            sprite = 2;
        } else if ((playerJumpFPS%1)>0.0f){
            sprite = 1;
        }
        return sprite;
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
