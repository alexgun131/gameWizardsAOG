package com.simplebojocs.pondskater2.Menu;

/**
 * Created by Alex on 11/07/2016.
 */

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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.simplebojocs.pondskater2.CONSTANTS;
import com.simplebojocs.pondskater2.PondSkater;

/**
 * Created by Alex on 04/07/2016.
 */
public class TopScoresScreen extends InputAdapter implements Screen {

    PondSkater game;

    ShapeRenderer renderer;
    SpriteBatch batch;
    ExtendViewport viewport;

    BitmapFont font;
    BitmapFont fontScore;
    Texture Back_Button;
    Texture Back_Button_invert;
    Texture BackGround;
    Texture ScoreStripes;
    TextureRegion[] ScoreStripesSprites;
    Texture PondSkull;
    Texture PondSkull2;

    boolean hardMode;


    int[] topEaten;
    int[] topScore;
    int languaje = 0;
    Vector2 LEADERBOARDS_POSITION;
    Vector2 ACHIEVEMENTS_POSITION;
    Vector2 HARDMODE_SWITCH_POSITION;
    //Vector2 HARDMODE_LEADERBOARD_POSITION;

    boolean musicON = true;
    public TopScoresScreen(PondSkater game){
        this.game = game;
    }
    @Override
    public void show() {
        game.externalServices.showAd(true);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        batch = new SpriteBatch();
        Back_Button = new Texture("ArrowBackButton.png");
        Back_Button_invert = new Texture("ArrowBackButtonInvert.png");
        BackGround = new Texture("ScoreBackground.png");
        ScoreStripes = new Texture("ScoreStripes.png");
        PondSkull = new Texture("PondSkull.png");
        PondSkull2 = new Texture("PondSkull2.png");
        ScoreStripesSprites = new TextureRegion[2];
        ScoreStripesSprites[0] = new TextureRegion(ScoreStripes, 0, 0, ScoreStripes.getWidth()/2, ScoreStripes.getHeight());
        ScoreStripesSprites[1] = new TextureRegion(ScoreStripes, ScoreStripes.getWidth(), 0, ScoreStripes.getWidth()/2, ScoreStripes.getHeight());

        viewport = new ExtendViewport(CONSTANTS.DEAD_WORLD_SIZE, CONSTANTS.DEAD_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        font = new BitmapFont(Gdx.files.internal("data/CuteFont2.fnt"),
                Gdx.files.internal("data/CuteFont2.png"), false);
        font.getData().setScale(CONSTANTS.SCORE_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        fontScore = new BitmapFont(Gdx.files.internal("data/CuteFont.fnt"),
                Gdx.files.internal("data/CuteFont.png"), false);
        fontScore.getData().setScale(CONSTANTS.SCORE_LABEL_SCALE);
        fontScore.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        topEaten = new int[CONSTANTS.NUMBER_TOPSCORES];
        topScore = new int[CONSTANTS.NUMBER_TOPSCORES];
        read();
        if(musicON) {
            game.gamemusic.play();
        }
    }

    @Override
    public void render(float delta) {

        LEADERBOARDS_POSITION = new Vector2(viewport.getWorldWidth()-CONSTANTS.SCORES_BUBBLE_RADIUS*2f, CONSTANTS.LEADERBOARDS_POSITION.y);
        ACHIEVEMENTS_POSITION = new Vector2(viewport.getWorldWidth()-CONSTANTS.SCORES_BUBBLE_RADIUS*2f, CONSTANTS.ACHIEVEMENTS_POSITION.y);
        //HARDMODE_LEADERBOARD_POSITION = new Vector2(viewport.getWorldWidth()-CONSTANTS.SCORES_BUBBLE_RADIUS*2f, CONSTANTS.ACHIEVEMENTS_POSITION.y - CONSTANTS.SCORES_BUBBLE_RADIUS*2);
        HARDMODE_SWITCH_POSITION = new Vector2(CONSTANTS.SCORES_BUBBLE_RADIUS, (CONSTANTS.ACHIEVEMENTS_POSITION.y+CONSTANTS.LEADERBOARDS_POSITION.y)/2);

        viewport.apply();
        Gdx.gl.glClearColor(CONSTANTS.DEAD_BACKGROUND_COLOR.r, CONSTANTS.DEAD_BACKGROUND_COLOR.g, CONSTANTS.DEAD_BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        final GlyphLayout scoreLayout = new GlyphLayout(fontScore, CONSTANTS.TOP_SCORES_LABEL[languaje]);

        float WORLD_SIZE = viewport.getWorldWidth();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        float BackgroundWidth = WORLD_SIZE;
        float BackgroundHeight = BackGround.getHeight()*BackgroundWidth/WORLD_SIZE;
        batch.draw(BackGround, (WORLD_SIZE-BackgroundWidth)/2, (viewport.getWorldHeight()-BackgroundHeight)/2, BackgroundWidth, BackgroundHeight);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        float SCORE_STRIPE_WIDTH = WORLD_SIZE*5/8;
        for(int i=0; i<CONSTANTS.NUMBER_TOPSCORES; i++) {

            final GlyphLayout eatenScoresLayout = new GlyphLayout(fontScore, String.valueOf(topEaten[i]));
            final GlyphLayout scoreScoresLayout = new GlyphLayout(fontScore, String.valueOf(topScore[i]));
            batch.draw(ScoreStripesSprites[i%2], (WORLD_SIZE-SCORE_STRIPE_WIDTH)/2, CONSTANTS.EATEN_SCORES.y - (int)(scoreLayout.height*(2*i+1.55)), SCORE_STRIPE_WIDTH, scoreLayout.height*2);
        }

        batch.draw(Back_Button, CONSTANTS.BACK_TO_MENU.x-CONSTANTS.SCORES_BUBBLE_RADIUS, CONSTANTS.BACK_TO_MENU.y-(int)(CONSTANTS.SCORES_BUBBLE_RADIUS*1.15), CONSTANTS.SCORES_BUBBLE_RADIUS*2, CONSTANTS.SCORES_BUBBLE_RADIUS*2);
        final GlyphLayout eatenLayout = new GlyphLayout(fontScore, CONSTANTS.TOP_EATEN_LABEL[languaje]);
        fontScore.draw(batch, CONSTANTS.TOP_EATEN_LABEL[languaje], CONSTANTS.EATEN_SCORES.x, CONSTANTS.EATEN_SCORES.y + eatenLayout.height*2, 0, Align.center, false);

        fontScore.draw(batch, CONSTANTS.TOP_SCORES_LABEL[languaje], CONSTANTS.TOP_SCORES.x, CONSTANTS.TOP_SCORES.y + scoreLayout.height*2, 0, Align.center, false);

        //if(hardMode)
            //batch.draw(PondSkull, CONSTANTS.TOP_SCORES.x + scoreLayout.width, CONSTANTS.TOP_SCORES.y, CONSTANTS.SCORES_BUBBLE_RADIUS, CONSTANTS.SCORES_BUBBLE_RADIUS);
        for(int i=0; i<CONSTANTS.NUMBER_TOPSCORES; i++){

            final GlyphLayout eatenScoresLayout = new GlyphLayout(fontScore, String.valueOf(topEaten[i]));
            if (hardMode)
                font.draw(batch, String.valueOf(topEaten[i]), CONSTANTS.EATEN_SCORES.x, CONSTANTS.EATEN_SCORES.y - scoreLayout.height*2*i, 0, Align.center, false);
            else
                fontScore.draw(batch, String.valueOf(topEaten[i]), CONSTANTS.EATEN_SCORES.x, CONSTANTS.EATEN_SCORES.y - scoreLayout.height*2*i, 0, Align.center, false);

            final GlyphLayout scoreScoresLayout = new GlyphLayout(fontScore, String.valueOf(topScore[i]));
            if (hardMode)
                font.draw(batch, String.valueOf(topScore[i]), CONSTANTS.TOP_SCORES.x, CONSTANTS.TOP_SCORES.y - scoreLayout.height*2*i, 0, Align.center, false);
            else
                fontScore.draw(batch, String.valueOf(topScore[i]), CONSTANTS.TOP_SCORES.x, CONSTANTS.TOP_SCORES.y - scoreLayout.height*2*i, 0, Align.center, false);

        }

        final GlyphLayout easyLayout = new GlyphLayout(fontScore, CONSTANTS.MENU_LABEL[languaje]);
        fontScore.draw(batch, CONSTANTS.MENU_LABEL[languaje], CONSTANTS.BACK_TO_MENU.x, CONSTANTS.BACK_TO_MENU.y + easyLayout.height / 2, 0, Align.center, false);

        batch.draw(Back_Button_invert, viewport.getWorldWidth()-CONSTANTS.SCORES_BUBBLE_RADIUS*4f, CONSTANTS.LEADERBOARDS_POSITION.y-(int)(CONSTANTS.SCORES_BUBBLE_RADIUS), CONSTANTS.SCORES_BUBBLE_RADIUS*4f, CONSTANTS.SCORES_BUBBLE_RADIUS*2);
        if(hardMode)
            batch.draw(PondSkull2, viewport.getWorldWidth()-CONSTANTS.SCORES_BUBBLE_RADIUS*2.4f, CONSTANTS.LEADERBOARDS_POSITION.y + easyLayout.height / 3f - CONSTANTS.SCORES_BUBBLE_RADIUS*0.8f, CONSTANTS.SCORES_BUBBLE_RADIUS, CONSTANTS.SCORES_BUBBLE_RADIUS);

        final GlyphLayout LeaderboardsLayout = new GlyphLayout(font, CONSTANTS.LEADERBOARDS_LABEL[languaje]);
        font.draw(batch, CONSTANTS.LEADERBOARDS_LABEL[languaje], viewport.getWorldWidth()-CONSTANTS.SCORES_BUBBLE_RADIUS*0.6f, CONSTANTS.LEADERBOARDS_POSITION.y + LeaderboardsLayout.height / 3f, 0, Align.bottomRight, false);

        batch.draw(Back_Button_invert, viewport.getWorldWidth()-CONSTANTS.SCORES_BUBBLE_RADIUS*4f, CONSTANTS.ACHIEVEMENTS_POSITION.y-(int)(CONSTANTS.SCORES_BUBBLE_RADIUS), CONSTANTS.SCORES_BUBBLE_RADIUS*4f, CONSTANTS.SCORES_BUBBLE_RADIUS*2);

        final GlyphLayout achievementLayout = new GlyphLayout(font, CONSTANTS.ACHIEVEMENTS_LABEL[languaje]);
        font.draw(batch, CONSTANTS.ACHIEVEMENTS_LABEL[languaje], viewport.getWorldWidth()-CONSTANTS.SCORES_BUBBLE_RADIUS*0.6f, CONSTANTS.ACHIEVEMENTS_POSITION.y + achievementLayout.height / 3f, 0, Align.right, false);

        if (hardMode)
            batch.draw(PondSkull2, CONSTANTS.SCORES_BUBBLE_RADIUS, HARDMODE_SWITCH_POSITION.y-(int)(CONSTANTS.SCORES_BUBBLE_RADIUS), CONSTANTS.SCORES_BUBBLE_RADIUS, CONSTANTS.SCORES_BUBBLE_RADIUS);
        else
            batch.draw(PondSkull, CONSTANTS.SCORES_BUBBLE_RADIUS, HARDMODE_SWITCH_POSITION.y-(int)(CONSTANTS.SCORES_BUBBLE_RADIUS), CONSTANTS.SCORES_BUBBLE_RADIUS, CONSTANTS.SCORES_BUBBLE_RADIUS);

        //batch.draw(Back_Button_invert, viewport.getWorldWidth()-CONSTANTS.SCORES_BUBBLE_RADIUS*4f, CONSTANTS.ACHIEVEMENTS_POSITION.y - CONSTANTS.SCORES_BUBBLE_RADIUS*2-(int)(CONSTANTS.SCORES_BUBBLE_RADIUS), CONSTANTS.SCORES_BUBBLE_RADIUS*4f, CONSTANTS.SCORES_BUBBLE_RADIUS*2);

        //batch.draw(PondSkull, viewport.getWorldWidth()-CONSTANTS.SCORES_BUBBLE_RADIUS*2.4f, CONSTANTS.ACHIEVEMENTS_POSITION.y + achievementLayout.height / 3f - CONSTANTS.SCORES_BUBBLE_RADIUS*2.8f, CONSTANTS.SCORES_BUBBLE_RADIUS, CONSTANTS.SCORES_BUBBLE_RADIUS);

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
        renderer.dispose();
        fontScore.dispose();
        font.dispose();
        game.musicDeath.dispose();
        game.soundDeath.dispose();
        game.gamemusic.dispose();
        game.soundDeath.dispose();
        game.musicDeath.dispose();
        game.moskitoMusic.dispose();
        game.eatLarvae.dispose();
        game.eatMoskito.dispose();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if (worldTouch.dst(CONSTANTS.BACK_TO_MENU) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            game.showMenuScreen();
        }

        if (worldTouch.dst(LEADERBOARDS_POSITION) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            if(!hardMode)
                game.externalServices.showStandardLeaderboard();
            else
                game.externalServices.showCompetitiveLeaderboard();
        }
        //if (worldTouch.dst(HARDMODE_LEADERBOARD_POSITION) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
          //  game.externalServices.showCompetitiveLeaderboard();
        //}
        if (worldTouch.dst(ACHIEVEMENTS_POSITION) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            game.externalServices.showAchievements();
        }
        if (worldTouch.dst(HARDMODE_SWITCH_POSITION) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            hardMode ^= true;
            read();
        }
        return true;
    }

    public void read() {

        FileHandle configDataFile = Gdx.files.local(CONSTANTS.INVERTCONFIG_FILE_NAME);
        FileHandle topDataFile = Gdx.files.local(CONSTANTS.TOP_FILE_NAME);
        if(hardMode)
            topDataFile = Gdx.files.local(CONSTANTS.TOP_COMPETITIVE_NAME);
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
                    if(i==0){
                        topEaten[i] = 15;
                        topScore[i] = 500;
                    }
                    else if(i<5){
                        topEaten[i] = 10;
                        topScore[i] = 200;
                    }
                    else if(i<10){
                        topEaten[i] = 5;
                        topScore[i] = 100;
                    }else{
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

