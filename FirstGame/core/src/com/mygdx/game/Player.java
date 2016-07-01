package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Alex on 29/06/2016.
 */
public class Player{


    Vector2 position;
    Vector2 velocity;

    Viewport viewport;
    boolean onTouch = false;



    public Player(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init(){
        position = new Vector2(viewport.getWorldWidth()/3, viewport.getWorldHeight()/2);
        velocity = new Vector2(0,0);
    }

    public void update(float delta){
        if(Gdx.input.getAccelerometerY() != 0) {
            float accelerometerInput = Gdx.input.getAccelerometerY();// (CONSTANTS.GRAVITATIONAL_ACCELERATION * CONSTANTS.ACCELEROMETER_SENSITIVITY);
            position.x += accelerometerInput * delta * CONSTANTS.PLAYER_VELOCITY;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            velocity.x = CONSTANTS.PLAYER_VELOCITY_KEY;
            position.x += delta* velocity.x * CONSTANTS.PLAYER_VELOCITY;;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            velocity.x = -CONSTANTS.PLAYER_VELOCITY_KEY;
            position.x += delta* velocity.x * CONSTANTS.PLAYER_VELOCITY;;
        }

        if((Gdx.input.isTouched()||Gdx.input.isKeyPressed(Input.Keys.SPACE)) && (onTouch == false)){
            onTouch = true;
            velocity.y = CONSTANTS.JUMP_VELOCITY;
        }
        else{
            velocity.y -= CONSTANTS.GRAVITATIONAL_ACCELERATION * CONSTANTS.JUMP_GRAVITY_MULT;
        }

        if(!(Gdx.input.isTouched()||Gdx.input.isKeyPressed(Input.Keys.SPACE)))
            onTouch=false;

        //ensureInBounds();
        position.y += delta * velocity.y;



    }

    boolean ensureInBounds(){
        boolean outOfBounds = false;
        if(position.x>viewport.getWorldWidth()-CONSTANTS.PLAYER_RAD){
            //position.x = viewport.getWorldWidth()-CONSTANTS.PLAYER_RAD;
            outOfBounds = true;
        }
        else if(position.x<CONSTANTS.PLAYER_RAD){
            //position.x = CONSTANTS.PLAYER_RAD;
            outOfBounds = true;
        }

        if(position.y < 0){
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
        for (Enemy enemy : enemies.enemyList) {
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
    public void render(ShapeRenderer renderer){
        renderer.setColor(CONSTANTS.PLAYER_COLOR);

        renderer.circle(position.x, position.y + CONSTANTS.PLAYER_RAD, CONSTANTS.PLAYER_RAD);
    }


}
