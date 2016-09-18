package com.simplebojocs.pondskater.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.simplebojocs.pondskater.CONSTANTS;
import com.simplebojocs.pondskater.PondSkater;

/**
 * Created by Alex on 12/07/2016.
 */
public class AccelerometerConfigScreen extends InputAdapter implements Screen {

    PondSkater game;

    ShapeRenderer renderer;
    ExtendViewport viewport;
    BouncingBall ball;
    SpriteBatch batch;
    BitmapFont fontScore;
    BitmapFont sbfont;

    boolean invertXY = false;
    boolean invertX = false;
    boolean invertY = false;
    boolean musicON = true;
    boolean soundsON = true;
    int language = 0;

    Vector2 MUSICON;
    Vector2 SOUNDON;
    Vector2 INVERTXY;
    Vector2 INVERTX;
    Vector2 INVERTY;
    Vector2 LANGUAJES;
    Vector2 LANGUAJES_ENG;
    Vector2 LANGUAJES_ESP;
    Vector2 LANGUAJES_CHI;
    Vector2 LANGUAJES_JAP;
    Vector2 LANGUAJES_COR;
    Vector2 LANGUAJES_AR;

    Vector2 LANGUAJES_ENG_TOUCH;
    Vector2 LANGUAJES_ESP_TOUCH;
    Vector2 LANGUAJES_CHI_TOUCH;
    Vector2 LANGUAJES_JAP_TOUCH;
    Vector2 LANGUAJES_COR_TOUCH;
    Vector2 LANGUAJES_AR_TOUCH;
    Texture AUTO_AD;
    Texture Back_Button;

    float tamLan = 2.3f;
    Music music;

    public AccelerometerConfigScreen(PondSkater game){
        this.game = game;
    }

    @Override
    public void show() {
        AUTO_AD = new Texture("AutoAd_Words.png");
        Back_Button = new Texture("ArrowBackButton.png");
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        viewport = new ExtendViewport(CONSTANTS.WORLD_SIZE, CONSTANTS.WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        fontScore = new BitmapFont(Gdx.files.internal("data/CuteFont.fnt"),
                Gdx.files.internal("data/CuteFont.png"), false);
        fontScore.getData().setScale(CONSTANTS.SCORE_LABEL_SCALE);
        fontScore.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        sbfont = new BitmapFont(Gdx.files.internal("data/CuteFont2.fnt"),
                Gdx.files.internal("data/CuteFont2.png"), false);
        sbfont.getData().setScale(CONSTANTS.SCORE_LABEL_SCALE*0.6f);
        sbfont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

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

        //renderer.setColor(CONSTANTS.MENU_COLOR);
        //renderer.circle(CONSTANTS.BACK_TO_MENU.x, CONSTANTS.BACK_TO_MENU.y, CONSTANTS.SCORES_BUBBLE_RADIUS);

        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();

        MUSICON = new Vector2(CONSTANTS.SCORES_BUBBLE_RADIUS, height * 18/20 - 2* CONSTANTS.SCORES_BUBBLE_RADIUS - CONSTANTS.ADD_BANNER_HEIGHT);
        SOUNDON = new Vector2(CONSTANTS.SCORES_BUBBLE_RADIUS, height * 18/20 -  4* CONSTANTS.SCORES_BUBBLE_RADIUS - CONSTANTS.ADD_BANNER_HEIGHT);
        INVERTXY = new Vector2(width - CONSTANTS.SCORES_BUBBLE_RADIUS*1.3f, height * 18/20 - CONSTANTS.ADD_BANNER_HEIGHT);
        INVERTX = new Vector2(width - CONSTANTS.SCORES_BUBBLE_RADIUS*1.3f, height * 18/20 - 2* CONSTANTS.SCORES_BUBBLE_RADIUS - CONSTANTS.ADD_BANNER_HEIGHT);
        INVERTY = new Vector2(width - CONSTANTS.SCORES_BUBBLE_RADIUS*1.3f, height * 18/20 -  4* CONSTANTS.SCORES_BUBBLE_RADIUS - CONSTANTS.ADD_BANNER_HEIGHT);
        LANGUAJES = new Vector2(CONSTANTS.SCORES_BUBBLE_RADIUS, viewport.getWorldHeight() * 1/20 - CONSTANTS.ADD_BANNER_HEIGHT);
        LANGUAJES_ENG = new Vector2((width - CONSTANTS.SCORES_BUBBLE_RADIUS)/8 + CONSTANTS.SCORES_BUBBLE_RADIUS, height * 1/20 );
        LANGUAJES_ESP = new Vector2((width - CONSTANTS.SCORES_BUBBLE_RADIUS)*2/8 + CONSTANTS.SCORES_BUBBLE_RADIUS, height * 1/20);
        LANGUAJES_CHI = new Vector2((width - CONSTANTS.SCORES_BUBBLE_RADIUS)*3/8 + CONSTANTS.SCORES_BUBBLE_RADIUS, height * 1/20);
        LANGUAJES_JAP = new Vector2((width- CONSTANTS.SCORES_BUBBLE_RADIUS)*4/8 + CONSTANTS.SCORES_BUBBLE_RADIUS, height * 1/20);
        LANGUAJES_COR = new Vector2((width - CONSTANTS.SCORES_BUBBLE_RADIUS)*5/8 + CONSTANTS.SCORES_BUBBLE_RADIUS, height * 1/20);
        LANGUAJES_AR = new Vector2((width - CONSTANTS.SCORES_BUBBLE_RADIUS)*6/8 + CONSTANTS.SCORES_BUBBLE_RADIUS, height * 1/20);

        ball.render(renderer);

        if(!musicON)
            renderer.setColor(Color.RED);
        else
            renderer.setColor(Color.GREEN);
        renderer.circle(MUSICON.x, MUSICON.y, CONSTANTS.SCORES_BUBBLE_RADIUS);
        if(!soundsON)
            renderer.setColor(Color.RED);
        else
            renderer.setColor(Color.GREEN);
        renderer.circle(SOUNDON.x, SOUNDON.y, CONSTANTS.SCORES_BUBBLE_RADIUS);
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

        renderer.set(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.BLACK);
        renderer.rect(LANGUAJES.x, LANGUAJES.y, CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan, CONSTANTS.SCORES_BUBBLE_RADIUS);
        if(language ==0)
            renderer.setColor(Color.WHITE);
        else
            renderer.setColor(Color.BLACK);
        renderer.rect(LANGUAJES_ENG.x, LANGUAJES_ENG.y, CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan, CONSTANTS.SCORES_BUBBLE_RADIUS);
        if(language ==1)
            renderer.setColor(Color.WHITE);
        else
            renderer.setColor(Color.BLACK);
        renderer.rect(LANGUAJES_ESP.x, LANGUAJES_ESP.y, CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan, CONSTANTS.SCORES_BUBBLE_RADIUS);
        if(language ==2)
            renderer.setColor(Color.WHITE);
        else
            renderer.setColor(Color.BLACK);
        renderer.rect(LANGUAJES_CHI.x, LANGUAJES_CHI.y, CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan, CONSTANTS.SCORES_BUBBLE_RADIUS);
        if(language ==3)
            renderer.setColor(Color.WHITE);
        else
            renderer.setColor(Color.BLACK);
        renderer.rect(LANGUAJES_JAP.x, LANGUAJES_JAP.y, CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan, CONSTANTS.SCORES_BUBBLE_RADIUS);
        if(language ==4)
            renderer.setColor(Color.WHITE);
        else
            renderer.setColor(Color.BLACK);
        renderer.rect(LANGUAJES_COR.x, LANGUAJES_COR.y, CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan, CONSTANTS.SCORES_BUBBLE_RADIUS);
        if(language ==5)
            renderer.setColor(Color.WHITE);
        else
            renderer.setColor(Color.BLACK);
        renderer.rect(LANGUAJES_AR.x, LANGUAJES_AR.y, CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan, CONSTANTS.SCORES_BUBBLE_RADIUS);


        renderer.end();

        batch.begin();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.draw(Back_Button, CONSTANTS.BACK_TO_MENU.x-CONSTANTS.SCORES_BUBBLE_RADIUS, CONSTANTS.BACK_TO_MENU.y-(int)(CONSTANTS.SCORES_BUBBLE_RADIUS*1.15), CONSTANTS.SCORES_BUBBLE_RADIUS*2, CONSTANTS.SCORES_BUBBLE_RADIUS*2);

        float autoAdEndX = INVERTY.x - CONSTANTS.SCORES_BUBBLE_RADIUS -5; //TODO: hardcoded padding
        float autoAdWidth = width/2.2f;

        batch.draw(AUTO_AD, autoAdEndX-autoAdWidth, height*2/3-(AUTO_AD.getHeight()*width*8/10/AUTO_AD.getWidth())/2, autoAdWidth, autoAdWidth*AUTO_AD.getHeight()/AUTO_AD.getWidth());

        final GlyphLayout simpleBojocsLayout = new GlyphLayout(fontScore, "by SimpleBojocs");
        sbfont.draw(batch, "by SimpleBojocs", autoAdEndX,  height*2/3-(AUTO_AD.getHeight()*width*8/10/AUTO_AD.getWidth())/2, 0, Align.right, false);


        final GlyphLayout musicLayout = new GlyphLayout(fontScore, CONSTANTS.MUSIC_LABEL[language]);
        fontScore.draw(batch, CONSTANTS.MUSIC_LABEL[language], MUSICON.x, MUSICON.y + musicLayout.height / 2, 0, Align.center, false);

        final GlyphLayout soundLayout = new GlyphLayout(fontScore, CONSTANTS.SOUNDS_LABEL[language]);
        fontScore.draw(batch, CONSTANTS.SOUNDS_LABEL[language], SOUNDON.x, SOUNDON.y + soundLayout.height / 2, 0, Align.center, false);

        final GlyphLayout easyLayout = new GlyphLayout(fontScore, CONSTANTS.MENU_LABEL[language]);
        fontScore.draw(batch, CONSTANTS.MENU_LABEL[language], CONSTANTS.BACK_TO_MENU.x, CONSTANTS.BACK_TO_MENU.y + easyLayout.height / 2, 0, Align.center, false);

        final GlyphLayout invertXYLayout = new GlyphLayout(fontScore, CONSTANTS.INVERTXY_LABEL[language]);
        fontScore.draw(batch, CONSTANTS.INVERTXY_LABEL[language], INVERTXY.x, INVERTXY.y + invertXYLayout.height / 2, 0, Align.center, false);

        final GlyphLayout invertXLayout = new GlyphLayout(fontScore, CONSTANTS.INVERTX_LABEL[language]);
        fontScore.draw(batch, CONSTANTS.INVERTX_LABEL[language], INVERTX.x, INVERTX.y + invertXLayout.height / 2, 0, Align.center, false);

        final GlyphLayout invertYLayout = new GlyphLayout(fontScore, CONSTANTS.INVERTY_LABEL[language]);
        fontScore.draw(batch, CONSTANTS.INVERTY_LABEL[language], INVERTY.x, INVERTY.y + invertYLayout.height / 2, 0, Align.center, false);

        final GlyphLayout languajeLayout = new GlyphLayout(fontScore, CONSTANTS.SELECT_LANGUAGE[0]);
        fontScore.draw(batch, CONSTANTS.SELECT_LANGUAGE[0], LANGUAJES_ENG.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, LANGUAJES_ENG.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2, 0, Align.center, false);
        fontScore.draw(batch, CONSTANTS.SELECT_LANGUAGE[1], LANGUAJES_ESP.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, LANGUAJES_ESP.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2, 0, Align.center, false);
        fontScore.draw(batch, CONSTANTS.SELECT_LANGUAGE[2], LANGUAJES_CHI.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, LANGUAJES_CHI.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2, 0, Align.center, false);
        fontScore.draw(batch, CONSTANTS.SELECT_LANGUAGE[3], LANGUAJES_JAP.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, LANGUAJES_JAP.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2, 0, Align.center, false);
        fontScore.draw(batch, CONSTANTS.SELECT_LANGUAGE[4], LANGUAJES_COR.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, LANGUAJES_COR.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2, 0, Align.center, false);
        fontScore.draw(batch, CONSTANTS.SELECT_LANGUAGE[5], LANGUAJES_AR.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, LANGUAJES_AR.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2, 0, Align.center, false);

        fontScore.draw(batch,
                CONSTANTS.LEADER_PROGRAMMER[language] + ":\nAlex Gonzalez Gonzalez\n\n"
                        + CONSTANTS.ART[language] + ":\nOriol Chiou Wang\n\n"
                        + CONSTANTS.PROGRAMMERS[language] + ":\nAlex Gonzalez Gonzalez\nOriol Chiou Wang\nPau Chiou Wang\n\n"
                        + CONSTANTS.MUSIC[language] + ":\nGuillermo Cámbara Ruiz",
                LANGUAJES_ENG.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, CONSTANTS.BACK_TO_MENU.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2, 0, Align.left, false);


        batch.end();

        LANGUAJES_ENG_TOUCH = new Vector2(LANGUAJES_ENG.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, LANGUAJES_ENG.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2);
        LANGUAJES_ESP_TOUCH = new Vector2(LANGUAJES_ESP.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, LANGUAJES_ESP.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2);
        LANGUAJES_CHI_TOUCH = new Vector2(LANGUAJES_CHI.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, LANGUAJES_CHI.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2);
        LANGUAJES_JAP_TOUCH = new Vector2(LANGUAJES_JAP.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, LANGUAJES_JAP.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2);
        LANGUAJES_COR_TOUCH = new Vector2(LANGUAJES_COR.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, LANGUAJES_COR.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2);
        LANGUAJES_AR_TOUCH = new Vector2(LANGUAJES_AR.x+CONSTANTS.SCORES_BUBBLE_RADIUS*tamLan/2, LANGUAJES_AR.y + languajeLayout.height / 2 + CONSTANTS.SCORES_BUBBLE_RADIUS/2);

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
        sbfont.dispose();
        renderer.dispose();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if (worldTouch.dst(CONSTANTS.BACK_TO_MENU) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            game.showMenuScreen();
        }
        if (worldTouch.dst(MUSICON) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            musicON = !musicON;
            if(musicON){
                game.music.setVolume(0.3f);                 // sets the volume to half the maximum volume
                game.music.setLooping(true);                // will repeat playback until music.stop() is called
                game.music.play();
            }else{
                game.music.stop();
            }
            writeConfig();
        }
        if (worldTouch.dst(SOUNDON) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            soundsON = !soundsON;
            writeConfig();
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
        if (worldTouch.dst(LANGUAJES_ENG_TOUCH) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            language =0;
            writeConfig();
        }
        if (worldTouch.dst(LANGUAJES_ESP_TOUCH) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            language =1;
            writeConfig();
        }
        if (worldTouch.dst(LANGUAJES_CHI_TOUCH) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            language =2;
            writeConfig();
        }
        if (worldTouch.dst(LANGUAJES_JAP_TOUCH) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            language =3;
            writeConfig();
        }
        if (worldTouch.dst(LANGUAJES_COR_TOUCH) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            language =4;
            writeConfig();
        }
        if (worldTouch.dst(LANGUAJES_AR_TOUCH) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
            language =5;
            writeConfig();
        }

        return true;
    }

    public void writeConfig() {
        Json json = new Json();
        FileHandle topDataFile = Gdx.files.local( CONSTANTS.INVERTCONFIG_FILE_NAME );
        FileHandle languajeDataFile = Gdx.files.local( CONSTANTS.LANGUAJECONFIG_FILE_NAME );
        boolean[] config = new boolean[5];
        config[0] = invertXY;
        config[1] = invertX;
        config[2] = invertY;
        config[3] = musicON;
        config[4] = soundsON;
        String topAsText = json.toJson( config );
        String topAsCode = Base64Coder.encodeString( topAsText );
        topDataFile.writeString( topAsCode, false );

        String languajeAsText = json.toJson(language);
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
                musicON = config[3];
                soundsON = config[4];

            } catch (Exception e) {
                invertXY = false;
                invertX = false;
                invertY = false;
                musicON = true;
                soundsON = true;
            }
        }

        if (languajeDataFile.exists()) {
            try {
                String languajeAsCode = languajeDataFile.readString();
                String languajeAsText = Base64Coder.decodeString(languajeAsCode);
                language = json.fromJson(int.class, languajeAsText);

            } catch (Exception e) {
                language = 0;

            }
        }
    }
}