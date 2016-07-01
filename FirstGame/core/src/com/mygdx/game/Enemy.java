package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 29/06/2016.
 */
public class Enemy {
    Vector2 position;
    Vector2 velocity;

    public Enemy(Vector2 pos, Vector2 vel){
        position = new Vector2(pos.x, pos.y);
        velocity = new Vector2(vel.x, vel.y);

    }

    public void update(float delta){
        position.x += delta * velocity.x;
        position.y += delta * velocity.y;
    }

    public void render(ShapeRenderer renderer){
        renderer.setColor(CONSTANTS.ENEMY_COLOR);
        renderer.rect(position.x, position.y, CONSTANTS.ENEMY_WIDTH, CONSTANTS.ENEMY_HEIGHT);
    }
}
