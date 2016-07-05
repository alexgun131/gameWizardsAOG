package com.mygdx.game.Game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.CONSTANTS;

/**
 * Created by Alex on 29/06/2016.
 */
public class Enemies {
    DelayedRemovalArray<Enemy> enemyList;
    Viewport viewport;

    int enemiesCounter;

    public Enemies(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {

        enemyList = new DelayedRemovalArray<Enemy>(false, 100);
        enemiesCounter = 0;
    }

    public void update(float delta) {
        if (MathUtils.random() < delta * CONSTANTS.SPAWN_RATE) {
            Vector2 newEnemyPosition = new Vector2(
                    viewport.getWorldWidth(),
                    MathUtils.random() * viewport.getWorldHeight()
            );

            Vector2 newEnemyVelocity = new Vector2(- MathUtils.random((float)0.3, (float) 1.0) * CONSTANTS.ENEMY_VELOCITY / viewport.getWorldWidth()*480, 0);

            Enemy newEnemy = new Enemy(newEnemyPosition, newEnemyVelocity);
            enemyList.add(newEnemy);
        }

        for ( Enemy enemy : enemyList) {
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

    public void render(ShapeRenderer renderer) {
        renderer.setColor(CONSTANTS.ENEMY_COLOR);
        for (Enemy enemy : enemyList) {
            enemy.render(renderer);
        }
    }
}
