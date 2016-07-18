package com.mygdx.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.FirstGame;

/**
 * Created by Alex on 12/07/2016.
 */
public class AccelerometerConfigScreen extends InputAdapter implements Screen {

    FirstGame game;

    ShapeRenderer renderer;
    ExtendViewport viewport;
    BouncingBall ball;
    SpriteBatch batch;
    BitmapFont fontScore;

    boolean invertXY = false;
    boolean invertX = false;
    boolean invertY = false;
    int languaje = 0;

    Vector2 INVERTXY;
    Vector2 INVERTX;
    Vector2 INVERTY;
    Vector2 LANGUAJE;

    public AccelerometerConfigScreen(FirstGame game){
        this.game = game;
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        viewport = new ExtendViewport(CONSTANTS.WORLD_SIZE, CONSTANTS.WORLD_SIZE);
        Gdx.input.setInputProcessor(this);
        fontScore = new BitmapFont();
        fontScore.getData().setScale(CONSTANTS.SCORE_LABEL_SCALE);
        fontScore.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ball = new BouncingBall(viewport);
    }



    @Override
    public void render(float delta) {
        readConfig();
        viewport.apply();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        ball.update(delta);

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(CONSTANTS.MENU_COLOR);
        renderer.circle(CONSTANTS.BACK_TO_MENU.x, CONSTANTS.BACK_TO_MENU.y, CONSTANTS.SCORES_BUBBLE_RADIUS);

        INVERTXY = new Vector2(viewport.getWorldWidth() - CONSTANTS.SCORES_BUBBLE_RADIUS, viewport.getWorldHeight() * 18/20 - CONSTANTS.ADD_BANNER_HEIGHT);
        INVERTX = new Vector2(viewport.getWorldWidth() - CONSTANTS.SCORES_BUBBLE_RADIUS, viewport.getWorldHeight() * 18/20 - 2* CONSTANTS.SCORES_BUBBLE_RADIUS - CONSTANTS.ADD_BANNER_HEIGHT);
        INVERTY = new Vector2(viewport.getWorldWidth() - CONSTANTS.SCORES_BUBBLE_RADIUS, viewport.getWorldHeight() * 18/20 -  4* CONSTANTS.SCORES_BUBBLE_RADIUS - CONSTANTS.ADD_BANNER_HEIGHT);
        LANGUAJE = new Vector2(viewport.getWorldWidth() - CONSTANTS.SCORES_BUBBLE_RADIUS, viewport.getWorldHeight() * 18/20 -  6* CONSTANTS.SCORES_BUBBLE_RADIUS - CONSTANTS.ADD_BANNER_HEIGHT);

        if(!invertXY)
            renderer.setColor(Color.RED);
        else
            renderer.setColor(Color.GREEN);
        renderer.circle(INVERTXY.x, INVERTXY.y, CONSTANTS.SCORES_BUBBLE_RADIUS);

        if(!invertX)
            renderer.setColor(Color.RED);
        else
            renderer.setColor(Color.GREEN);
        renderer.circle(INVERTX.x, INVERTX.y, CONSTANTS.SCORES_BUBBLE_RADIUS);

        if(!invertY)
            renderer.setColor(Color.RED);
        else
            renderer.setColor(Color.GREEN);
        renderer.circle(INVERTY.x, INVERTY.y, CONSTANTS.SCORES_BUBBLE_RADIUS);

        renderer.setColor(Color.BLUE);
        renderer.circle(LANGUAJE.x, LANGUAJE.y, CONSTANTS.SCORES_BUBBLE_RADIUS);

        ball.render(renderer);
        renderer.end();

        batch.begin();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        final GlyphLayout easyLayout = new GlyphLayout(fontScore, CONSTANTS.MENU_LABEL[languaje]);
        fontScore.draw(batch, CONSTANTS.MENU_LABEL[languaje], CONSTANTS.BACK_TO_MENU.x, CONSTANTS.BACK_TO_MENU.y + easyLayout.height / 2, 0, Align.center, false);

        final GlyphLayout invertXYLayout = new GlyphLayout(fontScore, CONSTANTS.INVERTXY_LABEL[languaje]);
        fontScore.draw(batch, CONSTANTS.INVERTXY_LABEL[languaje], INVERTXY.x, INVERTXY.y + invertXYLayout.height / 2, 0, Align.center, false);

        final GlyphLayout invertXLayout = new GlyphLayout(fontScore, CONSTANTS.INVERTX_LABEL[languaje]);
        fontScore.draw(batch, CONSTANTS.INVERTX_LABEL[languaje], INVERTX.x, INVERTX.y + invertXLayout.height / 2, 0, Align.center, false);

        final GlyphLayout invertYLayout = new GlyphLayout(fontScore, CONSTANTS.INVERTY_LABEL[languaje]);
        fontScore.draw(batch, CONSTANTS.INVERTY_LABEL[languaje], INVERTY.x, INVERTY.y + invertYLayout.height / 2, 0, Align.center, false);

        final GlyphLayout languajeLayout = new GlyphLayout(fontScore, CONSTANTS.SELECT_LANGUAJE[languaje]);
        fontScore.draw(batch, CONSTANTS.SELECT_LANGUAJE[languaje], LANGUAJE.x, LANGUAJE.y + languajeLayout.height / 2, 0, Align.center, false);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        ball.init();
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
        fontScore.dispose();
        renderer.dispose();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if (worldTouch.dst(CONSTANTS.BACK_TO_MENU) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            game.showMenuScreen();
        }

        if (worldTouch.dst(INVERTXY) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            invertXY = !invertXY;
            writeConfig();
        }
        if (worldTouch.dst(INVERTX) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            invertX = !invertX;
            writeConfig();
        }
        if (worldTouch.dst(INVERTY) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            invertY = !invertY;
            writeConfig();
        }
        if (worldTouch.dst(LANGUAJE) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            languaje = languaje+1;
            if(languaje >= CONSTANTS.SELECT_LANGUAJE.length){
                languaje = 0;
            }
            writeConfig();
        }

        return true;
    }

    public void writeConfig() {
        Json json = new Json();
        FileHandle topDataFile = Gdx.files.local( CONSTANTS.INVERTCONFIG_FILE_NAME );
        FileHandle languajeDataFile = Gdx.files.local( CONSTANTS.LANGUAJECONFIG_FILE_NAME );
        boolean[] config = new boolean[3];
        config[0] = invertXY;
        config[1] = invertX;
        config[2] = invertY;
        String topAsText = json.toJson( config );
        String topAsCode = Base64Coder.encodeString( topAsText );
        topDataFile.writeString( topAsCode, false );

        String languajeAsText = json.toJson( languaje );
        String languajeAsCode = Base64Coder.encodeString( languajeAsText );
        languajeDataFile.writeString( languajeAsCode, false );
    }

    public void readConfig() {

        FileHandle topDataFile = Gdx.files.local(CONSTANTS.INVERTCONFIG_FILE_NAME);
        FileHandle languajeDataFile = Gdx.files.local( CONSTANTS.LANGUAJECONFIG_FILE_NAME );
        Json json = new Json();

        if (topDataFile.exists()) {
            try {
                String topAsCode = topDataFile.readString();
                String topAsText = Base64Coder.decodeString(topAsCode);
                boolean[] config = json.fromJson(boolean[].class, topAsText);
                invertXY = config[0];
                invertX = config[1];
                invertY = config[2];

            } catch (Exception e) {
                invertXY = false;
                invertX = false;
                invertY = false;

            }
        }

        if (languajeDataFile.exists()) {
            try {
                String languajeAsCode = languajeDataFile.readString();
                String languajeAsText = Base64Coder.decodeString(languajeAsCode);
                languaje = json.fromJson(int.class, languajeAsText);

            } catch (Exception e) {
                languaje = 0;

            }
        }
    }
}