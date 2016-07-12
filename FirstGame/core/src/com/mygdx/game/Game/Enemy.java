package com.mygdx.game.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.CONSTANTS;

/**
 * Created by Alex on 29/06/2016.
 */
public class Enemy {
    Vector2 position;
    Vector2 velocity;

    float animationFps;

    public Enemy(Vector2 pos, Vector2 vel){
        position = new Vector2(pos.x, pos.y);
        velocity = new Vector2(vel.x, vel.y);
    }

    public void update(float delta){
        position.x += delta * velocity.x;
        position.y += delta * velocity.y;

        animationFps += delta % 100; //fps up to 100 seconds (max animation time?)
    }

    public void render(ShapeRenderer renderer, SpriteBatch batch, TextureRegion[] enemySprites){
        //renderer.setColor(CONSTANTS.ENEMY_COLOR);
        //renderer.rect(position.x, position.y, CONSTANTS.ENEMY_WIDTH, CONSTANTS.ENEMY_HEIGHT);

        int sprite = getAnimationSprite();

        batch.begin();
        batch.draw(enemySprites[sprite], position.x-CONSTANTS.ENEMY_WIDTH/2, position.y-CONSTANTS.ENEMY_HEIGHT/2, CONSTANTS.ENEMY_WIDTH*2, CONSTANTS.ENEMY_WIDTH*2);
        batch.end();
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
}
