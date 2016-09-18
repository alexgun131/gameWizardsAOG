package com.simplebojocs.pondskater2.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.simplebojocs.pondskater2.CONSTANTS;


/**
 * Created by Alex on 29/06/2016.
 */
public class Enemies {
    DelayedRemovalArray<com.simplebojocs.pondskater2.Game.Enemy> enemyList;
    Viewport viewport;

    int enemiesCounter;

    static Texture enemyTexture;
    static TextureRegion[] enemySprites;
    float multHardMode;
    float hardPoints;

    public Enemies(Viewport viewport, boolean hardMode) {
        this.viewport = viewport;
        init();
        if(hardMode == true){
            multHardMode = 0.01f;
            hardPoints = 100000;
        } else {
            multHardMode = 1;
            hardPoints = 1;
        }

        loadTextures();
    }

    private void loadTextures() {
        int playerImgSize = 128;
        int animationColumns = 3;
        int animationRows = 1;

        //Load Textures
        enemyTexture = new Texture("enemy.png");
        enemySprites = new TextureRegion[animationColumns*animationRows];
        for (int i= 0; i<animationColumns; i++){
            enemySprites[i] = new TextureRegion(enemyTexture, playerImgSize*i, 0, playerImgSize, playerImgSize);
        }
    }

    public void init() {

        enemyList = new DelayedRemovalArray<com.simplebojocs.pondskater2.Game.Enemy>(false, 100);
        enemiesCounter = 0;
    }

    public void update(float delta, int points) {
        if (MathUtils.random() < delta * CONSTANTS.SPAWN_RATE * (1+((float)Math.log10((hardPoints + (double)points*hardPoints*multHardMode)/50+1)/3.0f))) {
            Vector2 newEnemyPosition = new Vector2(
                    viewport.getWorldWidth(),
                    MathUtils.random(0.15f*viewport.getWorldHeight(),viewport.getWorldHeight() - CONSTANTS.ADD_BANNER_HEIGHT-CONSTANTS.ENEMY_HEIGHT)
            );

            Vector2 newEnemyVelocity = new Vector2(- MathUtils.random((float)0.3 + 0.3f*(float)Math.log10((hardPoints + (double)points*hardPoints*multHardMode)/10+1)/3.0f, (float) 1.0 + (float)Math.log10((hardPoints + (double)points*hardPoints*multHardMode)/10+1)/3.0f) * CONSTANTS.ENEMY_VELOCITY, 0);

            com.simplebojocs.pondskater2.Game.Enemy newEnemy = new com.simplebojocs.pondskater2.Game.Enemy(newEnemyPosition, newEnemyVelocity);
            enemyList.add(newEnemy);
        }

        for ( com.simplebojocs.pondskater2.Game.Enemy enemy : enemyList) {
            enemy.update(delta);
        }

        enemyList.begin();
        for (int i = 0; i < enemyList.size; i++) {
            if (enemyList.get(i).position.x < -CONSTANTS.ENEMY_WIDTH) {
                //iciclesDodged += 1;
                enemyList.removeIndex(i);
                enemiesCounter++;
            }
        }
        enemyList.end();
    }

    public void render(SpriteBatch batch) {
        for (com.simplebojocs.pondskater2.Game.Enemy enemy : enemyList) {
            enemy.render(batch, enemySprites);
        }
    }
}
