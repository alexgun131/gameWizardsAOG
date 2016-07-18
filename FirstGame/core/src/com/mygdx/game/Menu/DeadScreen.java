package com.mygdx.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.FirstGame;

/**
 * Created by Alex on 04/07/2016.
 */
public class DeadScreen extends InputAdapter implements Screen {

    FirstGame game;

    ShapeRenderer renderer;
    SpriteBatch batch;
    FitViewport viewport;

    BitmapFont font;
    BitmapFont fontScore;


    int score;
    int eaten;
    int select = -1;
    int languaje = 0;

    public DeadScreen(FirstGame game, int score, int eaten){
        this.game = game;
        this.score = score;
        this.eaten = eaten;
    }
    @Override
    public void show() {

        renderer = new ShapeRenderer();
        batch = new SpriteBatch();

        viewport = new FitViewport(CONSTANTS.DEAD_WORLD_SIZE, CONSTANTS.DEAD_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        fontScore = new BitmapFont();
        fontScore.getData().setScale(CONSTANTS.DEAD_SCORE_LABEL_SCALE);
        fontScore.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        font = new BitmapFont();
        font.getData().setScale(CONSTANTS.DEAD_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        game.showAd(true);
        readConfig();


    }

    @Override
    public void render(float delta) {

        viewport.apply();
        Gdx.gl.glClearColor(CONSTANTS.DEAD_BACKGROUND_COLOR.r, CONSTANTS.DEAD_BACKGROUND_COLOR.g, CONSTANTS.DEAD_BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        if(select == 0)
            renderer.setColor(Color.RED);
        else
            renderer.setColor(CONSTANTS.MENU_COLOR);
        renderer.circle(CONSTANTS.DEAD_MENU.x, CONSTANTS.DEAD_MENU.y, CONSTANTS.DEAD_BUBBLE_RADIUS);

        if(select == 1)
            renderer.setColor(Color.RED);
        else
            renderer.setColor(CONSTANTS.PLAY_COLOR);
        renderer.circle(CONSTANTS.DEAD_PLAYGAME.x, CONSTANTS.DEAD_PLAYGAME.y, CONSTANTS.DEAD_BUBBLE_RADIUS);

        if(select == 2)
            renderer.setColor(Color.RED);
        else
            renderer.setColor(CONSTANTS.SCORES_COLOR);
        renderer.circle(CONSTANTS.DEAD_SCORES.x, CONSTANTS.DEAD_SCORES.y, CONSTANTS.DEAD_BUBBLE_RADIUS);

        renderer.end();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        final GlyphLayout scoreLayout = new GlyphLayout(fontScore, CONSTANTS.YOUR_SCORE_LABEL[languaje]+String.valueOf(score));
        fontScore.draw(batch, CONSTANTS.YOUR_SCORE_LABEL[languaje]+String.valueOf(score), CONSTANTS.DEAD_YOUR_SCORE.x, CONSTANTS.DEAD_YOUR_SCORE.y + scoreLayout.height, 0, Align.center, false);

        final GlyphLayout eatenLayout = new GlyphLayout(fontScore, CONSTANTS.EATEN_LABEL[languaje]+String.valueOf(eaten));
        fontScore.draw(batch, CONSTANTS.EATEN_LABEL[languaje]+String.valueOf(eaten), CONSTANTS.DEAD_YOUR_SCORE.x, CONSTANTS.DEAD_YOUR_SCORE.y - eatenLayout.height, 0, Align.center, false);

        final GlyphLayout easyLayout = new GlyphLayout(font, CONSTANTS.MENU_LABEL[languaje]);
        font.draw(batch, CONSTANTS.MENU_LABEL[languaje], CONSTANTS.DEAD_MENU.x, CONSTANTS.DEAD_MENU.y + easyLayout.height / 2, 0, Align.center, false);

        final GlyphLayout mediumLayout = new GlyphLayout(font, CONSTANTS.PLAY_LABEL[languaje]);
        font.draw(batch, CONSTANTS.PLAYAGAIN_LABEL[languaje], CONSTANTS.DEAD_PLAYGAME.x, CONSTANTS.DEAD_PLAYGAME.y + mediumLayout.height / 2, 0, Align.center, false);

        final GlyphLayout hardLayout = new GlyphLayout(font, CONSTANTS.SCORES_LABEL[languaje]);
        font.draw(batch, CONSTANTS.SCORESD_LABEL[languaje], CONSTANTS.DEAD_SCORES.x, CONSTANTS.DEAD_SCORES.y + hardLayout.height / 2, 0, Align.center, false);

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

        if (worldTouch.dst(CONSTANTS.DEAD_MENU) < CONSTANTS.DEAD_BUBBLE_RADIUS) {
            game.showMenuScreen();
        }

        if (worldTouch.dst(CONSTANTS.DEAD_PLAYGAME) < CONSTANTS.DEAD_BUBBLE_RADIUS) {
            game.showGameScreen();
        }

        if (worldTouch.dst(CONSTANTS.DEAD_SCORES) < CONSTANTS.DEAD_BUBBLE_RADIUS) {
            game.showTopScoreScreen();
        }

        return true;
    }

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
                    game.showMenuScreen();
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

    public void readConfig() {

        FileHandle languajeDataFile = Gdx.files.local( CONSTANTS.LANGUAJECONFIG_FILE_NAME );
        Json json = new Json();


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
