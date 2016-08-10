package com.simplebojocs.pondskater.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.simplebojocs.pondskater.CONSTANTS;
import com.simplebojocs.pondskater.utils.Language;
import com.simplebojocs.pondskater.PondSkater;
import com.simplebojocs.utils.audio.CtrlAudio;
import com.simplebojocs.utils.lang.CtrlLanguage;

/**
 * Created by Alex on 04/07/2016.
 */
public class DeadScreen extends InputAdapter implements Screen {

    PondSkater game;

    SpriteBatch batch;
    ExtendViewport viewport;

    BitmapFont font;
    BitmapFont fontScore;

    int score;
    int eaten;
    int select = -1;
    int language = 0;
    //Music musicDeath;
    //Music soundDeath;
    Texture FlyButton;
    Texture WormButton;
    Texture FishButton;
    TextureRegion[] FlyButtonSprite;
    TextureRegion[] WormButtonSprite;
    TextureRegion[] FishButtonSprite;
    float flyFps;
    float wormFps;
    float fishFps;
    Vector2 DEAD_SHOW_SCORE;
    Vector2 DEAD_MENU;
    Vector2 DEAD_PLAYGAME;
    Vector2 DEAD_SCORES;
    ShaderProgram shader;

    float riverPosition;
    float riverBankPosition;
    float riverWaterHighlightTimer;
    Texture RIVER_WATER;
    TextureRegion[] RIVER_WATERS;
    Texture RIVER_BANK_TOP;
    Texture RIVER_BANK_BOTTOM;
    float timeSinceDead;

    boolean musicON = true;

    CtrlLanguage lang = CtrlLanguage.getInstance();

    public DeadScreen(PondSkater game, int score, int eaten, Music soundDeath) {
        this.game = game;
        this.score = score;
        this.eaten = eaten;
        //this.soundDeath = soundDeath;
        loadTextures();
        flyFps = 0.0f;
        wormFps = 0.0f;
        fishFps = 0.0f;

        readConfig();
        Language lang = Language.en_US;
        if (language == 0) {
            lang = Language.en_US;
        } else if (language == 1) {
            lang = Language.es_ES;
        } else if (language == 2) {
            lang = Language.zh_CN;
        } else if (language == 3) {
            lang = Language.ja_JP;
        } else if (language == 4) {
            lang = Language.ko_KR;
        } else if (language == 5) {
            lang = Language.ar_SA;
        }
        this.lang.setLanguage(lang);
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

        FlyButtonSprite = new TextureRegion[animationColumns * animationRows];
        WormButtonSprite = new TextureRegion[animationColumns * animationRows];
        FishButtonSprite = new TextureRegion[animationColumns * animationRows];

        for (int i = 0; i < animationColumns; i++) {
            FlyButtonSprite[i] = new TextureRegion(FlyButton, buttonSize * i, 0, buttonSize, buttonSize);
            WormButtonSprite[i] = new TextureRegion(WormButton, buttonSize * i, 0, buttonSize, buttonSize);
            FishButtonSprite[i] = new TextureRegion(FishButton, buttonSize * i, 0, buttonSize, buttonSize);
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

    }

    @Override
    public void show() {

        batch = new SpriteBatch();

        shader = new ShaderProgram(CONSTANTS.vertexShader, CONSTANTS.fragmentShader);

        viewport = new ExtendViewport(CONSTANTS.DEAD_WORLD_SIZE, CONSTANTS.DEAD_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);


        fontScore = new BitmapFont(Gdx.files.internal("data/CuteFont.fnt"),
                Gdx.files.internal("data/CuteFont.png"), false);
        fontScore.getData().setScale(CONSTANTS.DEAD_SCORE_LABEL_SCALE);
        fontScore.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        font = new BitmapFont(Gdx.files.internal("data/CuteFont.fnt"),
                Gdx.files.internal("data/CuteFont.png"), false);
        font.getData().setScale(CONSTANTS.DEAD_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        game.externalServices.showAd(true);
        readConfig();
        //if (musicON)
        //    musicDeath = Gdx.audio.newMusic(Gdx.files.internal("Death_theme_2.mid"));


        timeSinceDead = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        if (musicON) {
            /*if (!soundDeath.isPlaying()) {
                soundDeath.dispose();
                    musicDeath.setVolume(0.3f);                 // sets the volume to half the maximum volume
                    musicDeath.setLooping(true);                // will repeat playback until music.stop() is called
                    musicDeath.play();
            }*/
        }


        viewport.apply();
        Gdx.gl.glClearColor(CONSTANTS.DEAD_BACKGROUND_COLOR.r, CONSTANTS.DEAD_BACKGROUND_COLOR.g, CONSTANTS.DEAD_BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        float gray = 1 + 0.1f;
        shader.begin();
        shader.setUniformf("gray", gray);
        shader.end();
        batch.setShader(shader);

        drawBackground(delta, batch); // draw river with animation
        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();
        DEAD_SHOW_SCORE = new Vector2(width / 2, height / 2 + CONSTANTS.MENU_BUBBLE_RADIUS * 5 / 2);
        DEAD_MENU = new Vector2(width / 5, height / 2.5f);
        DEAD_PLAYGAME = new Vector2(width / 2, height / 2.5f);
        DEAD_SCORES = new Vector2(width * 4 / 5, height / 2.5f);

        final GlyphLayout scoreLayout = new GlyphLayout(fontScore, lang.get("dead.score", String.valueOf(score)));
        fontScore.draw(batch, lang.get("dead.score", String.valueOf(score)), DEAD_SHOW_SCORE.x, DEAD_SHOW_SCORE.y + scoreLayout.height, 0, Align.center, false);

        final GlyphLayout eatenLayout = new GlyphLayout(fontScore, CONSTANTS.EATEN_LABEL[language] + String.valueOf(eaten));
        fontScore.draw(batch, CONSTANTS.EATEN_LABEL[language] + String.valueOf(eaten), DEAD_SHOW_SCORE.x, DEAD_SHOW_SCORE.y - eatenLayout.height, 0, Align.center, false);

        batch.draw(FlyButtonSprite[getFlySprite(delta)], DEAD_MENU.x - CONSTANTS.MENU_BUBBLE_RADIUS * 2, DEAD_MENU.y - CONSTANTS.MENU_BUBBLE_RADIUS * 2, CONSTANTS.MENU_BUBBLE_RADIUS * 4, CONSTANTS.MENU_BUBBLE_RADIUS * 4);
        batch.draw(WormButtonSprite[getWormSprite(delta)], DEAD_PLAYGAME.x - CONSTANTS.MENU_BUBBLE_RADIUS * 2, DEAD_PLAYGAME.y - CONSTANTS.MENU_BUBBLE_RADIUS * 2, CONSTANTS.MENU_BUBBLE_RADIUS * 4, CONSTANTS.MENU_BUBBLE_RADIUS * 4);
        batch.draw(FishButtonSprite[getFishSprite(delta)], DEAD_SCORES.x - CONSTANTS.MENU_BUBBLE_RADIUS * 2, DEAD_SCORES.y - CONSTANTS.MENU_BUBBLE_RADIUS * 2, CONSTANTS.MENU_BUBBLE_RADIUS * 4, CONSTANTS.MENU_BUBBLE_RADIUS * 4);


        final GlyphLayout easyLayout = new GlyphLayout(font, CONSTANTS.OPTIONS_LABEL[language]);
        font.draw(batch, CONSTANTS.MENU_LABEL[language], DEAD_MENU.x, DEAD_MENU.y + easyLayout.height / 2, 0, Align.center, false);

        final GlyphLayout mediumLayout = new GlyphLayout(font, lang.get("button.playAgain"));
        font.draw(batch, lang.get("button.playAgain"), DEAD_PLAYGAME.x, DEAD_PLAYGAME.y + mediumLayout.height / 2, 0, Align.center, false);

        final GlyphLayout hardLayout = new GlyphLayout(font, CONSTANTS.SCORES_LABEL[language]);
        font.draw(batch, CONSTANTS.SCORES_LABEL[language], DEAD_SCORES.x, DEAD_SCORES.y + hardLayout.height / 2, 0, Align.center, false);

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
        wormFps %= 100;
        int sprite = 0;
        if ((wormFps % 0.5) > 0.25) {
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
        fontScore.dispose();
        font.dispose();
        shader.dispose();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        try {
            Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

            if (worldTouch.dst(DEAD_MENU) < CONSTANTS.DEAD_BUBBLE_RADIUS * 2) {
                game.showMenuScreen();
                if (musicON)
                    //musicDeath.dispose();
                    CtrlAudio.getInstance().stop();
            }

            if (worldTouch.dst(DEAD_PLAYGAME) < CONSTANTS.DEAD_BUBBLE_RADIUS * 2) {
                game.showGameScreen();
                if (musicON)
                    //musicDeath.dispose();
                    CtrlAudio.getInstance().stop();
            }

            if (worldTouch.dst(DEAD_SCORES) < CONSTANTS.DEAD_BUBBLE_RADIUS * 2) {
                game.showTopScoreScreen();
                if (musicON)
                    //musicDeath.dispose();
                    CtrlAudio.getInstance().stop();
            }
        } catch (Exception e) {

        }
        return true;
    }

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

        FileHandle topDataFile = Gdx.files.local(CONSTANTS.INVERTCONFIG_FILE_NAME);
        FileHandle languajeDataFile = Gdx.files.local(CONSTANTS.LANGUAJECONFIG_FILE_NAME);
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
