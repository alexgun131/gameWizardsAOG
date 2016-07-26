package com.mygdx.game.Menu;

/**
 * Created by Alex on 11/07/2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.FirstGame;

/**
 * Created by Alex on 04/07/2016.
 */
public class TopScoresScreen extends InputAdapter implements Screen {

    FirstGame game;

    SpriteBatch batch;
    ExtendViewport viewport;

    BitmapFont font;
    BitmapFont fontScore;
    Texture Back_Button;


    int[] topEaten;
    int[] topScore;
    int languaje = 0;

    boolean musicON = true;
    public TopScoresScreen(FirstGame game){
        this.game = game;
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        Back_Button = new Texture("ArrowBackButton.png");
        viewport = new ExtendViewport(CONSTANTS.DEAD_WORLD_SIZE, CONSTANTS.DEAD_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        fontScore = new BitmapFont(Gdx.files.internal("data/CuteFont.fnt"),
                Gdx.files.internal("data/CuteFont.png"), false);
        fontScore.getData().setScale(CONSTANTS.SCORE_LABEL_SCALE);
        fontScore.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        topEaten = new int[CONSTANTS.NUMBER_TOPSCORES];
        topScore = new int[CONSTANTS.NUMBER_TOPSCORES];
        read();
        if(musicON) {
            game.music.setVolume(0.3f);                 // sets the volume to half the maximum volume
            game.music.setLooping(true);                // will repeat playback until music.stop() is called
            game.music.play();
        }
    }

    @Override
    public void render(float delta) {

        viewport.apply();
        Gdx.gl.glClearColor(CONSTANTS.DEAD_BACKGROUND_COLOR.r, CONSTANTS.DEAD_BACKGROUND_COLOR.g, CONSTANTS.DEAD_BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        batch.draw(Back_Button, CONSTANTS.BACK_TO_MENU.x-CONSTANTS.SCORES_BUBBLE_RADIUS, CONSTANTS.BACK_TO_MENU.y-(int)(CONSTANTS.SCORES_BUBBLE_RADIUS*1.15), CONSTANTS.SCORES_BUBBLE_RADIUS*2, CONSTANTS.SCORES_BUBBLE_RADIUS*2);
        final GlyphLayout eatenLayout = new GlyphLayout(fontScore, CONSTANTS.TOP_EATEN_LABEL[languaje]);
        fontScore.draw(batch, CONSTANTS.TOP_EATEN_LABEL[languaje], CONSTANTS.EATEN_SCORES.x, CONSTANTS.EATEN_SCORES.y + eatenLayout.height*2, 0, Align.center, false);

        final GlyphLayout scoreLayout = new GlyphLayout(fontScore, CONSTANTS.TOP_SCORES_LABEL[languaje]);
        fontScore.draw(batch, CONSTANTS.TOP_SCORES_LABEL[languaje], CONSTANTS.TOP_SCORES.x, CONSTANTS.TOP_SCORES.y + scoreLayout.height*2, 0, Align.center, false);

        for(int i=0; i<CONSTANTS.NUMBER_TOPSCORES; i++){

            final GlyphLayout eatenScoresLayout = new GlyphLayout(fontScore, String.valueOf(topEaten[i]));
            fontScore.draw(batch, String.valueOf(topEaten[i]), CONSTANTS.EATEN_SCORES.x, CONSTANTS.EATEN_SCORES.y - scoreLayout.height*2*i, 0, Align.center, false);

            final GlyphLayout scoreScoresLayout = new GlyphLayout(fontScore, String.valueOf(topScore[i]));
            fontScore.draw(batch, String.valueOf(topScore[i]), CONSTANTS.TOP_SCORES.x, CONSTANTS.TOP_SCORES.y - scoreLayout.height*2*i, 0, Align.center, false);

        }

        final GlyphLayout easyLayout = new GlyphLayout(fontScore, CONSTANTS.MENU_LABEL[languaje]);
        fontScore.draw(batch, CONSTANTS.MENU_LABEL[languaje], CONSTANTS.BACK_TO_MENU.x, CONSTANTS.BACK_TO_MENU.y + easyLayout.height / 2, 0, Align.center, false);

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
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if (worldTouch.dst(CONSTANTS.BACK_TO_MENU) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            game.showMenuScreen();
        }

        return true;
    }

    public void read() {

        FileHandle configDataFile = Gdx.files.local(CONSTANTS.INVERTCONFIG_FILE_NAME);
        FileHandle topDataFile = Gdx.files.local(CONSTANTS.TOP_FILE_NAME);
        FileHandle languajeDataFile = Gdx.files.local( CONSTANTS.LANGUAJECONFIG_FILE_NAME );
        Json json = new Json();

        if (configDataFile.exists()) {
            try {
                String topAsCode = configDataFile.readString();
                String topAsText = Base64Coder.decodeString(topAsCode);
                boolean[] config = json.fromJson(boolean[].class, topAsText);
                musicON = config[3];

            } catch (Exception e) {
                musicON = true;
            }
        }
        if (topDataFile.exists()) {
            try {
                String topAsCode = topDataFile.readString();
                String topAsText = Base64Coder.decodeString(topAsCode);
                Vector2[] av = json.fromJson(Vector2[].class, topAsText);
                for (int i=0; i<CONSTANTS.NUMBER_TOPSCORES; i++) {
                    Vector2 v = av[i];
                    topEaten[i] = (int) v.x;
                    topScore[i] = (int) v.y;
                }

            } catch (Exception e) {
                for (int i=0; i<CONSTANTS.NUMBER_TOPSCORES; i++) {
                    topEaten[i] = 0;
                    topScore[i] = 0;
                }
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

