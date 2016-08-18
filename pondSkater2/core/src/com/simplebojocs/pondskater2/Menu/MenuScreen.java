package com.simplebojocs.pondskater2.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.simplebojocs.pondskater2.CONSTANTS;
import com.simplebojocs.pondskater2.PondSkater;

import java.util.Locale;

/**
 * Created by Alex on 04/07/2016.
 */
public class MenuScreen extends InputAdapter implements Screen {

    PondSkater game;

    SpriteBatch batch;
    ExtendViewport viewport;
    int language = 0;

    BitmapFont font;
    BitmapFont sbfont;

    int select = -1;

    Texture FlyButton;
    Texture WormButton;
    Texture FishButton;
    Texture PondButton;
    Texture PondLockedButton; //For locked version
    TextureRegion[] FlyButtonSprite;
    TextureRegion[] WormButtonSprite;
    TextureRegion[] FishButtonSprite;
    TextureRegion[] PondButtonSprite;
    TextureRegion[] PondLockedButtonSprite;
    float flyFps;
    float wormFps;
    float fishFps;
    float pondFps;
    Vector2 MENU_OPTIONS;
    Vector2 MENU_PLAYGAME;
    Vector2 MENU_PLAYHARD;
    Vector2 MENU_SCORES;
    Vector2 TUTORIALON;


    float riverPosition;
    float riverBankPosition;
    float riverWaterHighlightTimer;
    Texture RIVER_WATER;
    TextureRegion[] RIVER_WATERS;
    Texture RIVER_BANK_TOP;
    Texture RIVER_BANK_BOTTOM;
    Texture AUTO_AD;
    Texture INFO_BUTTON;

    Tutorial tutorial;
    boolean isTutorial;

    boolean musicON = true;
    boolean isHardModeUnlocked;

    public MenuScreen(PondSkater game) {
        this.game = game;
        loadTextures();
        flyFps = 0.0f;
        wormFps = 0.0f;
        fishFps = 0.0f;
        pondFps = 0.0f;
        tutorial = null;
        isTutorial = false;
        isHardModeUnlocked = true;
    }

    private void loadTextures() {
        // TEXTURES
        // Background
        int buttonSize = 256;
        int animationColumns = 2;
        int animationRows = 1;

        FlyButton = new Texture("FlyButton.png");
        WormButton = new Texture("WormButton.png");
        FishButton = new Texture("FishButton.png");
        PondButton = new Texture("PondButton.png");
        PondLockedButton = new Texture("PondButtonLocked.png");
        INFO_BUTTON = new Texture("infoBlack.png");

        FlyButtonSprite = new TextureRegion[animationColumns * animationRows];
        WormButtonSprite = new TextureRegion[animationColumns * animationRows];
        FishButtonSprite = new TextureRegion[animationColumns * animationRows];
        PondButtonSprite = new TextureRegion[animationColumns * animationRows];
        PondLockedButtonSprite = new TextureRegion[animationColumns * animationRows];

        for (int i = 0; i < animationColumns; i++) {
            FlyButtonSprite[i] = new TextureRegion(FlyButton, buttonSize * i, 0, buttonSize, buttonSize);
            WormButtonSprite[i] = new TextureRegion(WormButton, buttonSize * i, 0, buttonSize, buttonSize);
            FishButtonSprite[i] = new TextureRegion(FishButton, buttonSize * i, 0, buttonSize, buttonSize);
            PondButtonSprite[i] = new TextureRegion(PondButton, buttonSize * i, 0, buttonSize, buttonSize);
            PondLockedButtonSprite[i] = new TextureRegion(PondLockedButton, buttonSize * i, 0, buttonSize, buttonSize);
        }

        // TEXTURES
        // Background
        int waterTextureSize = 512;
        RIVER_WATER = new Texture("RiverWater.png");
        RIVER_WATERS = new TextureRegion[2]; //There are two sprites in RiverWater
        RIVER_WATERS[0] = new TextureRegion(RIVER_WATER, 0, 0, waterTextureSize, waterTextureSize);
        RIVER_WATERS[1] = new TextureRegion(RIVER_WATER, waterTextureSize, 0, waterTextureSize * 2, waterTextureSize);
        RIVER_BANK_TOP = new Texture("RiverBankTop.png");
        RIVER_BANK_BOTTOM = new Texture("RiverBank.png");

        AUTO_AD = new Texture("AutoAd_Menu.png");

    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        viewport = new ExtendViewport(CONSTANTS.MENU_WORLD_SIZE, CONSTANTS.MENU_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        font = new BitmapFont(Gdx.files.internal("data/CuteFont.fnt"),
                Gdx.files.internal("data/CuteFont.png"), false);
        font.getData().setScale(CONSTANTS.MENU_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        sbfont = new BitmapFont(Gdx.files.internal("data/CuteFont2.fnt"),
                Gdx.files.internal("data/CuteFont2.png"), false);
        sbfont.getData().setScale(CONSTANTS.MENU_LABEL_SCALE*0.8f);
        sbfont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        game.externalServices.showAd(true);

        readConfig();
        if(musicON) {
            game.gamemusic.play();                 // sets the volume to half the maximum volume
        }

    }

    @Override
    public void render(float delta) {

        viewport.apply();
        Gdx.gl.glClearColor(CONSTANTS.MENU_BACKGROUND_COLOR.r, CONSTANTS.MENU_BACKGROUND_COLOR.g, CONSTANTS.MENU_BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();

        TUTORIALON = new Vector2(CONSTANTS.SCORES_BUBBLE_RADIUS, height * 18/20 -  6* CONSTANTS.SCORES_BUBBLE_RADIUS - CONSTANTS.ADD_BANNER_HEIGHT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        drawBackground(delta, batch); // draw river with animation

        MENU_OPTIONS = new Vector2(width *1/8, height / 2.5f);
        MENU_PLAYGAME = new Vector2(width *3/8 , height / 2.5f);
        MENU_PLAYHARD = new Vector2(width *5/8 , height / 2.5f);
        MENU_SCORES = new Vector2(width *7/8, height / 2.5f);

        float MENU_AUTO_AD_Y = height / 1.6f;


        batch.draw(AUTO_AD, MENU_PLAYGAME.x-CONSTANTS.MENU_AUTO_AD_WIDTH/2, MENU_AUTO_AD_Y, CONSTANTS.MENU_AUTO_AD_WIDTH, AUTO_AD.getHeight()*CONSTANTS.MENU_AUTO_AD_WIDTH/AUTO_AD.getWidth());
        final GlyphLayout simpleBojocsLayout = new GlyphLayout(sbfont, "by SimpleBojocs");
        sbfont.draw(batch, "by SimpleBojocs", MENU_SCORES.x,  viewport.getWorldHeight() / 1.5f, 0, Align.bottomRight, false);


        batch.draw(FlyButtonSprite[getFlySprite(delta)], MENU_OPTIONS.x - CONSTANTS.MENU_BUBBLE_RADIUS * 2, MENU_OPTIONS.y - CONSTANTS.MENU_BUBBLE_RADIUS * 2, CONSTANTS.MENU_BUBBLE_RADIUS * 4, CONSTANTS.MENU_BUBBLE_RADIUS * 4);
        batch.draw(WormButtonSprite[getWormSprite(delta)], MENU_PLAYGAME.x - CONSTANTS.MENU_BUBBLE_RADIUS * 2, MENU_PLAYGAME.y - CONSTANTS.MENU_BUBBLE_RADIUS * 2, CONSTANTS.MENU_BUBBLE_RADIUS * 4, CONSTANTS.MENU_BUBBLE_RADIUS * 4);
        if (isHardModeUnlocked)
            batch.draw(PondButtonSprite[getPondSprite(delta)], MENU_PLAYHARD.x - CONSTANTS.MENU_BUBBLE_RADIUS * 2, MENU_PLAYHARD.y - CONSTANTS.MENU_BUBBLE_RADIUS * 2, CONSTANTS.MENU_BUBBLE_RADIUS * 4, CONSTANTS.MENU_BUBBLE_RADIUS * 4);
        else
            batch.draw(PondLockedButtonSprite[getPondSprite(delta)], MENU_PLAYHARD.x - CONSTANTS.MENU_BUBBLE_RADIUS * 2, MENU_PLAYHARD.y - CONSTANTS.MENU_BUBBLE_RADIUS * 2, CONSTANTS.MENU_BUBBLE_RADIUS * 4, CONSTANTS.MENU_BUBBLE_RADIUS * 4);
        batch.draw(FishButtonSprite[getFishSprite(delta)], MENU_SCORES.x - CONSTANTS.MENU_BUBBLE_RADIUS * 2, MENU_SCORES.y - CONSTANTS.MENU_BUBBLE_RADIUS * 2, CONSTANTS.MENU_BUBBLE_RADIUS * 4, CONSTANTS.MENU_BUBBLE_RADIUS * 4);


        final GlyphLayout easyLayout = new GlyphLayout(font, CONSTANTS.OPTIONS_LABEL[language]);
        font.draw(batch, CONSTANTS.OPTIONS_LABEL[language], MENU_OPTIONS.x, MENU_OPTIONS.y + easyLayout.height / 2, 0, Align.center, false);

        final GlyphLayout mediumLayout = new GlyphLayout(font, CONSTANTS.PLAY_LABEL[language]);
        font.draw(batch, CONSTANTS.PLAY_LABEL[language], MENU_PLAYGAME.x, MENU_PLAYGAME.y + mediumLayout.height / 2, 0, Align.center, false);

        final GlyphLayout hardLayout = new GlyphLayout(font, CONSTANTS.SCORES_LABEL[language]);
        font.draw(batch, CONSTANTS.SCORES_LABEL[language], MENU_SCORES.x, MENU_SCORES.y + hardLayout.height / 2, 0, Align.center, false);

        batch.draw(INFO_BUTTON, TUTORIALON.x-CONSTANTS.SCORES_BUBBLE_RADIUS/2, TUTORIALON.y-CONSTANTS.SCORES_BUBBLE_RADIUS, CONSTANTS.SCORES_BUBBLE_RADIUS, CONSTANTS.SCORES_BUBBLE_RADIUS);

        if (isTutorial) {
            tutorial.draw(batch, width, height);
        }

        batch.end();

    }

    private int getFlySprite(float delta) {
        flyFps += delta;
        flyFps %= 100;
        int sprite = 0;
        if ((flyFps % 0.3) > 0.15) {
            sprite = 1;
        }
        return sprite;
    }

    private int getWormSprite(float delta) {
        wormFps += delta;
        wormFps %= 100;
        int sprite = 0;
        if ((wormFps % 0.6) > 0.3) {
            sprite = 1;
        }
        return sprite;
    }

    private int getFishSprite(float delta) {
        fishFps += delta;
        fishFps %= 100;
        int sprite = 0;
        if ((fishFps % 0.5) > 0.25) {
            sprite = 1;
        }
        return sprite;
    }

    private int getPondSprite(float delta) {
        pondFps += delta;
        pondFps %= 100;
        int sprite = 0;
        if ((pondFps % 1) > 0.9) {
            sprite = 1;
        }
        return sprite;
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
        sbfont.dispose();
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
        try {

            Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

            if (isTutorial) {
                if ((worldTouch.x<viewport.getWorldWidth()/2))
                    isTutorial = tutorial.drawPrevious();
                else
                    isTutorial = tutorial.drawNext();
                if (!isTutorial)
                    tutorial = null;
                return true;
            }
            if (worldTouch.dst(MENU_OPTIONS) < CONSTANTS.MENU_BUBBLE_RADIUS * 2) {
                game.showAccelerometerConfigScreen();
            }

            if (worldTouch.dst(MENU_PLAYGAME) < CONSTANTS.MENU_BUBBLE_RADIUS * 2) {
                game.showGameScreen();
            }
            if (worldTouch.dst(MENU_PLAYHARD) < CONSTANTS.MENU_BUBBLE_RADIUS * 2) {
                if (isHardModeUnlocked)
                    game.showHardModeScreen();
                //else
                    //toast
            }
            if (worldTouch.dst(MENU_SCORES) < CONSTANTS.MENU_BUBBLE_RADIUS * 2) {
                game.showTopScoreScreen();
            }
            if (worldTouch.dst(TUTORIALON) < CONSTANTS.SCORES_BUBBLE_RADIUS) {
                tutorial = new Tutorial(language);
                tutorial.start();
                isTutorial = true;
            }
        } catch (Exception e) {

        }

        return true;
    }

    @Override
    public boolean keyUp(int key) {
        if (key == Input.Keys.RIGHT) {
            select++;
            if (select == 3)
                select = 0;
        } else if (key == Input.Keys.LEFT) {
            select--;
            if (select == -1)
                select = 2;
        } else if (key == Input.Keys.SPACE || key == Input.Keys.ENTER) {
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

    public void readConfig() {

        FileHandle topDataFile = Gdx.files.local(CONSTANTS.INVERTCONFIG_FILE_NAME);
        FileHandle languajeDataFile = Gdx.files.local( CONSTANTS.LANGUAJECONFIG_FILE_NAME );
        FileHandle scoreDataFile = Gdx.files.local(CONSTANTS.TOP_FILE_NAME);
        Json json = new Json();


        if (topDataFile.exists()) {
            try {
                String topAsCode = topDataFile.readString();
                String topAsText = Base64Coder.decodeString(topAsCode);
                boolean[] config = json.fromJson(boolean[].class, topAsText);
                musicON = config[3];

            } catch (Exception e) {
                musicON = true;
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
        } else {
            String idioma = Locale.getDefault().getLanguage();
            if (idioma.equals("es")){
                language = 1;
            } else if (idioma.equals("zh")){
                language = 2;
            } else if (idioma.equals("ja")){
                language = 3;
            } else if (idioma.equals("ko")){
                language = 4;
            } else if (idioma.equals("ar")){
                language = 5;
            } else{
                language = 0;
            }
            String languajeAsText = json.toJson(language);
            String languajeAsCode = Base64Coder.encodeString( languajeAsText );
            languajeDataFile.writeString( languajeAsCode, false );
        }

        if (!scoreDataFile.exists()) {
            if (tutorial == null)
                tutorial = new Tutorial(language);
            tutorial.start();
            isTutorial = true;
            scoreDataFile.writeString( "notFirstTime", false );
        }
    }

    /* Draw river with flow */
    public void drawBackground(float delta, SpriteBatch batch) {
        float screenWidth = viewport.getWorldWidth();
        float screenHeight = viewport.getWorldHeight();
        float imageWidth = (screenHeight / RIVER_WATER.getHeight()) * RIVER_WATER.getWidth() / 2;
        int spritesNeeded = (int) (screenWidth / imageWidth) + 2;

        riverPosition += delta * CONSTANTS.WATER_SPEED;
        if (riverPosition > imageWidth) {
            riverPosition = 0.0f;
        }

        riverBankPosition += delta * CONSTANTS.RIVER_BANK_SPEED;
        if (riverBankPosition > imageWidth) {
            riverBankPosition = 0.0f;
        }

        riverWaterHighlightTimer += delta;
        int hightlightWater = (riverWaterHighlightTimer < CONSTANTS.WATER_HIGHLIGHT_SPEED) ? 0 : 1;
        if (riverWaterHighlightTimer > 2 * CONSTANTS.WATER_HIGHLIGHT_SPEED) {
            riverWaterHighlightTimer = 0.0f;
        }

        for (int i = 0; i <= spritesNeeded; i++) {
            if ((-riverPosition + i * imageWidth) <= screenWidth) {
                batch.draw(RIVER_WATERS[hightlightWater], -riverPosition + i * imageWidth, 0.0f, imageWidth * (1 + hightlightWater), screenHeight); //Weird thing to make region width correct
            }
        }
        for (int i = 0; i <= spritesNeeded; i++) {
            if ((-riverBankPosition + i * imageWidth) <= screenWidth) {
                batch.draw(RIVER_BANK_TOP, -riverBankPosition + i * imageWidth, screenHeight - CONSTANTS.FRAME_THIKNESS * 5, imageWidth, CONSTANTS.FRAME_THIKNESS * 5); //TODO: change magic number *5
                batch.draw(RIVER_BANK_BOTTOM, -riverBankPosition + i * imageWidth, 0.0f, imageWidth, CONSTANTS.FRAME_THIKNESS * 5);
            }
        }
    }

}
