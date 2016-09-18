package com.simplebojocs.pondskater2.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.simplebojocs.pondskater2.CONSTANTS;


/**
 * Created by Alex on 30/06/2016.
 */
public class SuperPoint {

    Viewport viewport;
    Vector2 position;
    Vector2 velocity;
    float amplitude;
    float freq;
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

    public SuperPoint(Viewport view){
        viewport = view;
        animationFps = 0.0f;
        loadTextures();
    }

    public void newPosition(){
        float posx = viewport.getWorldWidth();
        float posy = MathUtils.random(0.2f, 0.8f) * viewport.getWorldHeight();
        float vx = - MathUtils.random(0.8f , 1.0f) * CONSTANTS.SUPERPOINT_VELOCITY;
        float vy = MathUtils.random(-1f , 1f) * CONSTANTS.SUPERPOINT_VELOCITY;

        position = new Vector2(posx, posy);
        velocity = new Vector2(vx, vy);
        amplitude = MathUtils.random(0.8f,1.3f)*CONSTANTS.SUPERPOINT_AMP_SIN;
        freq = MathUtils.random(0.8f,1.2f)*CONSTANTS.SUPERPOINT_W_SIN;


    }

    public void disappear(){
        float posx = -CONSTANTS.WORLD_SIZE;
        float posy = -CONSTANTS.WORLD_SIZE;
        position = new Vector2(posx, posy);

    }

    boolean ensureInBounds(){
        boolean outOfBounds = false;
        if(position.x<CONSTANTS.SUPERPOINT_WIDTH){
            position.x = CONSTANTS.SUPERPOINT_WIDTH;
            velocity.x = -velocity.x;
            outOfBounds = true;
            disappear();
        }


        return outOfBounds;
    }

    public void update(float delta){
        t+=delta;
        position.x += delta * velocity.x * MathUtils.random(-0.3f, 3.0f);
        position.y += amplitude* MathUtils.sin(t*freq) * MathUtils.random(-0.3f, 1.0f);
        animationFps += delta * (-velocity.x) * 2 / (CONSTANTS.ENEMY_VELOCITY);

        animationFps += delta;
        animationFps %= 100;
    }

    public void render(SpriteBatch batch){
        //renderer.setColor(CONSTANTS.POINT_COLOR);
        //renderer.circle(position.x, position.y + CONSTANTS.POINT_WIDTH, CONSTANTS.POINT_WIDTH);

        int sprite = getAnimationSprite();
        //TODO Sprites causes unalignment, why?
        //TODO Bonus texture has some glaring
        batch.draw(pointSprites[sprite], position.x-(int)(CONSTANTS.SUPERPOINT_WIDTH*1.5), position.y-(int)(CONSTANTS.SUPERPOINT_WIDTH*0.5) , CONSTANTS.POINT_WIDTH*3, CONSTANTS.POINT_WIDTH*3);
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
