package com.mygdx.game.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.CONSTANTS;

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

    public void render(ShapeRenderer renderer, SpriteBatch batch, Texture enemyTexture){
        //renderer.setColor(CONSTANTS.ENEMY_COLOR);
        //renderer.rect(position.x, position.y, CONSTANTS.ENEMY_WIDTH, CONSTANTS.ENEMY_HEIGHT);

        batch.begin();
        batch.draw(enemyTexture, position.x-CONSTANTS.ENEMY_WIDTH/2, position.y-CONSTANTS.ENEMY_HEIGHT/2, CONSTANTS.ENEMY_WIDTH*2, CONSTANTS.ENEMY_WIDTH*2);
        batch.end();
    }
}
