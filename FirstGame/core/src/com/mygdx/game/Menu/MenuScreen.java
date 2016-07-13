package com.mygdx.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.FirstGame;

/**
 * Created by Alex on 04/07/2016.
 */
public class MenuScreen extends InputAdapter implements Screen {

    FirstGame game;

    ShapeRenderer renderer;
    SpriteBatch batch;
    FitViewport viewport;

    BitmapFont font;

    int select = -1;

    public MenuScreen(FirstGame game){
        this.game = game;
    }
    @Override
    public void show() {

        renderer = new ShapeRenderer();
        batch = new SpriteBatch();

        viewport = new FitViewport(CONSTANTS.MENU_WORLD_SIZE, CONSTANTS.MENU_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        font = new BitmapFont();
        font.getData().setScale(CONSTANTS.MENU_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    }

    @Override
    public void render(float delta) {

        viewport.apply();
        Gdx.gl.glClearColor(CONSTANTS.MENU_BACKGROUND_COLOR.r, CONSTANTS.MENU_BACKGROUND_COLOR.g, CONSTANTS.MENU_BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        if(select == 0)
            renderer.setColor(Color.RED);
        else
            renderer.setColor(CONSTANTS.OPTIONS_COLOR);
        renderer.circle(CONSTANTS.MENU_OPTIONS.x, CONSTANTS.MENU_OPTIONS.y, CONSTANTS.MENU_BUBBLE_RADIUS);

        if(select == 1)
            renderer.setColor(Color.RED);
        else
            renderer.setColor(CONSTANTS.PLAY_COLOR);
        renderer.circle(CONSTANTS.MENU_PLAYGAME.x, CONSTANTS.MENU_PLAYGAME.y, CONSTANTS.MENU_BUBBLE_RADIUS);

        if(select == 2)
            renderer.setColor(Color.RED);
        else
            renderer.setColor(CONSTANTS.SCORES_COLOR);
        renderer.circle(CONSTANTS.MENU_SCORES.x, CONSTANTS.MENU_SCORES.y, CONSTANTS.MENU_BUBBLE_RADIUS);

        renderer.end();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        final GlyphLayout easyLayout = new GlyphLayout(font, CONSTANTS.OPTIONS_LABEL);
        font.draw(batch, CONSTANTS.OPTIONS_LABEL, CONSTANTS.MENU_OPTIONS.x, CONSTANTS.MENU_OPTIONS.y + easyLayout.height / 2, 0, Align.center, false);

        final GlyphLayout mediumLayout = new GlyphLayout(font, CONSTANTS.PLAY_LABEL);
        font.draw(batch, CONSTANTS.PLAY_LABEL, CONSTANTS.MENU_PLAYGAME.x, CONSTANTS.MENU_PLAYGAME.y + mediumLayout.height / 2, 0, Align.center, false);

        final GlyphLayout hardLayout = new GlyphLayout(font, CONSTANTS.SCORES_LABEL);
        font.draw(batch, CONSTANTS.SCORES_LABEL, CONSTANTS.MENU_SCORES.x, CONSTANTS.MENU_SCORES.y + hardLayout.height / 2, 0, Align.center, false);

        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        renderer.dispose();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if (worldTouch.dst(CONSTANTS.MENU_OPTIONS) < CONSTANTS.MENU_BUBBLE_RADIUS) {
            game.showAccelerometerConfigScreen();
        }

        if (worldTouch.dst(CONSTANTS.MENU_PLAYGAME) < CONSTANTS.MENU_BUBBLE_RADIUS) {
            game.showGameScreen();
        }

        if (worldTouch.dst(CONSTANTS.MENU_SCORES) < CONSTANTS.MENU_BUBBLE_RADIUS) {
           game.showTopScoreScreen();
        }

        return true;
    }

    @Override
    public boolean keyUp(int key){
        if(key == Input.Keys.RIGHT){
            select ++;
            if(select == 3)
                select = 0;
        }
        else if(key == Input.Keys.LEFT){
            select--;
            if(select == -1)
                select = 2;
        }
        else if(key == Input.Keys.SPACE || key == Input.Keys.ENTER){
            switch (select) {
                case 0:
                    game.showAccelerometerConfigScreen();
                    break;
                case 1:
                    game.showGameScreen();
                    break;
                case 2:
                    game.showTopScoreScreen();
                    break;
            }
        }

        return true;
    }
}
