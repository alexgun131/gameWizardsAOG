package com.simplebojocs.pondskater.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.simplebojocs.pondskater.CONSTANTS;


/**
 * Created by Alex on 30/06/2016.
 */
public class Point {

    Viewport viewport;
    Vector2 position;
    float t;
    static Texture pointTexture;
    static TextureRegion[] pointSprites;
    float animationFps;

    private void loadTextures() {
        int pointImgSize = 128;
        int animationColumns = 2;
        int animationRows = 1;
        pointTexture = new Texture("points.png");
        pointSprites = new TextureRegion[animationColumns * animationRows];
        for (int i = 0; i < animationColumns; i++) {
            pointSprites[i] = new TextureRegion(pointTexture, pointImgSize * i, 0, pointImgSize, pointImgSize);
        }
    }

    public Point(Viewport view){
        viewport = view;
        animationFps = 0.0f;
        loadTextures();
    }

    public void newPosition(){
        float posx = viewport.getWorldWidth() * MathUtils.random(0.2f, 0.6f);
        float posy = viewport.getWorldHeight() * MathUtils.random(0.1f, 0.8f);
        position = new Vector2(posx, posy);
    }

    public void disappear(){
        float posx = -CONSTANTS.WORLD_SIZE;
        float posy = -CONSTANTS.WORLD_SIZE;
        position = new Vector2(posx, posy);
    }

    public void update(float delta){
        t+=delta;
        position.y += CONSTANTS.POINT_AMP_SIN * MathUtils.sin(t*CONSTANTS.POINT_W_SIN);

        animationFps += delta;
        animationFps %= 100;
    }

    public void render(SpriteBatch batch){
        //renderer.setColor(CONSTANTS.POINT_COLOR);
        //renderer.circle(position.x, position.y + CONSTANTS.POINT_WIDTH, CONSTANTS.POINT_WIDTH);

        int sprite = getAnimationSprite();
        //TODO Sprites causes unalignment, why?
        //TODO Bonus texture has some glaring
        batch.draw(pointSprites[sprite], position.x-(int)(CONSTANTS.POINT_WIDTH*1.5), position.y-(int)(CONSTANTS.POINT_WIDTH*0.5) , CONSTANTS.POINT_WIDTH*3, CONSTANTS.POINT_WIDTH*3);
    }

    private int getAnimationSprite() {
        //TODO: this is poorly coded
        //TODO animation fps are hardcoded
        int sprite = 0;
        if ((animationFps%0.7)>0.35){
            sprite = 1;
        }
        return sprite;
    }
}
