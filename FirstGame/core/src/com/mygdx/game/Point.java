package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by Alex on 30/06/2016.
 */
public class Point {

    Viewport viewport;
    Vector2 position;
    public Point(Viewport view){
        viewport = view;
    }

    public void newPosition(){
        float posx = viewport.getWorldWidth() * MathUtils.random(0.2f, 0.8f);
        float posy = viewport.getWorldHeight() * MathUtils.random(0.2f, 0.8f);
        position = new Vector2(posx, posy);
    }

    public void disappear(){
        float posx = -CONSTANTS.WORLD_SIZE;
        float posy = -CONSTANTS.WORLD_SIZE;
        position = new Vector2(posx, posy);
    }

    public void render(ShapeRenderer renderer){
        renderer.setColor(CONSTANTS.POINT_COLOR);
        renderer.circle(position.x, position.y + CONSTANTS.POINT_WIDTH, CONSTANTS.POINT_WIDTH);
    }
}
