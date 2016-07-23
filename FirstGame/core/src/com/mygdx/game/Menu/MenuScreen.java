package com.mygdx.game.Menu;

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
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.FirstGame;

/**
 * Created by Alex on 04/07/2016.
 */
public class MenuScreen extends InputAdapter implements Screen {

    FirstGame game;

    SpriteBatch batch;
    ExtendViewport viewport;
    int languaje = 1;

    BitmapFont font;

    int select = -1;

    Texture FlyButton;
    Texture WormButton;
    Texture FishButton;
    TextureRegion[] FlyButtonSprite;
    TextureRegion[] WormButtonSprite;
    TextureRegion[] FishButtonSprite;
    float flyFps;
    float wormFps;
    float fishFps;
    Vector2 MENU_OPTIONS ;
    Vector2 MENU_PLAYGAME;
    Vector2 MENU_SCORES;


    float riverPosition;
    float riverBankPosition;
    float riverWaterHighlightTimer;
    Texture RIVER_WATER;
    TextureRegion[] RIVER_WATERS;
    Texture RIVER_BANK_TOP;
    Texture RIVER_BANK_BOTTOM;

    public MenuScreen(FirstGame game){
        this.game = game;
        loadTextures();
        flyFps = 0.0f;
        wormFps = 0.0f;
        fishFps = 0.0f;
    }

    private void loadTextures(){
        // TEXTURES
        // Background
        int buttonSize = 256;
        int animationColumns = 2;
        int animationRows = 1;

        FlyButton = new Texture("FlyButton.png");
        WormButton = new Texture("WormButton.png");
        FishButton = new Texture("FishButton.png");

        FlyButtonSprite = new TextureRegion[animationColumns*animationRows];
        WormButtonSprite = new TextureRegion[animationColumns*animationRows];
        FishButtonSprite = new TextureRegion[animationColumns*animationRows];

        for (int i = 0; i < animationColumns; i++) {
            FlyButtonSprite[i] = new TextureRegion(FlyButton, buttonSize*i, 0, buttonSize, buttonSize);
            WormButtonSprite[i] = new TextureRegion(WormButton, buttonSize*i, 0, buttonSize, buttonSize);
            FishButtonSprite[i] = new TextureRegion(FishButton, buttonSize*i, 0, buttonSize, buttonSize);
        }

        // TEXTURES
        // Background
        int waterTextureSize = 512;
        RIVER_WATER = new Texture("RiverWater.png");
        RIVER_WATERS = new TextureRegion[2]; //There are two sprites in RiverWater
        RIVER_WATERS[0] = new TextureRegion(RIVER_WATER, 0, 0, waterTextureSize, waterTextureSize);
        RIVER_WATERS[1] = new TextureRegion(RIVER_WATER, waterTextureSize, 0, waterTextureSize*2, waterTextureSize);
        RIVER_BANK_TOP = new Texture("RiverBankTop.png");
        RIVER_BANK_BOTTOM = new Texture("RiverBank.png");

    }

    @Override
    public void show() {

        batch = new SpriteBatch();

        viewport = new ExtendViewport(CONSTANTS.MENU_WORLD_SIZE, CONSTANTS.MENU_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        font = new BitmapFont();
        font.getData().setScale(CONSTANTS.MENU_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        game.showAd(true);

        readConfig();
        game.music.setVolume(0.3f);                 // sets the volume to half the maximum volume
        game.music.setLooping(true);                // will repeat playback until music.stop() is called
        game.music.play();
    }

    @Override
    public void render(float delta) {

        viewport.apply();
        Gdx.gl.glClearColor(CONSTANTS.MENU_BACKGROUND_COLOR.r, CONSTANTS.MENU_BACKGROUND_COLOR.g, CONSTANTS.MENU_BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        drawBackground(delta, batch); // draw river with animation

        MENU_OPTIONS = new Vector2(viewport.getWorldWidth() / 5, viewport.getWorldHeight() / 2);
        MENU_PLAYGAME = new Vector2(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2);
        MENU_SCORES = new Vector2(viewport.getWorldWidth()*4 / 5, viewport.getWorldHeight() / 2);


        batch.draw(FlyButtonSprite[getFlySprite(delta)], MENU_OPTIONS.x - CONSTANTS.MENU_BUBBLE_RADIUS*2, MENU_OPTIONS.y-CONSTANTS.MENU_BUBBLE_RADIUS*2, CONSTANTS.MENU_BUBBLE_RADIUS*4, CONSTANTS.MENU_BUBBLE_RADIUS*4);
        batch.draw(WormButtonSprite[getWormSprite(delta)], MENU_PLAYGAME.x-CONSTANTS.MENU_BUBBLE_RADIUS*2, MENU_PLAYGAME.y-CONSTANTS.MENU_BUBBLE_RADIUS*2, CONSTANTS.MENU_BUBBLE_RADIUS*4, CONSTANTS.MENU_BUBBLE_RADIUS*4);
        batch.draw(FishButtonSprite[getFishSprite(delta)], MENU_SCORES.x-CONSTANTS.MENU_BUBBLE_RADIUS*2, MENU_SCORES.y-CONSTANTS.MENU_BUBBLE_RADIUS*2, CONSTANTS.MENU_BUBBLE_RADIUS*4, CONSTANTS.MENU_BUBBLE_RADIUS*4);


        final GlyphLayout easyLayout = new GlyphLayout(font, CONSTANTS.OPTIONS_LABEL[languaje]);
        font.draw(batch, CONSTANTS.OPTIONS_LABEL[languaje], MENU_OPTIONS.x, MENU_OPTIONS.y + easyLayout.height / 2, 0, Align.center, false);

        final GlyphLayout mediumLayout = new GlyphLayout(font, CONSTANTS.PLAY_LABEL[languaje]);
        font.draw(batch, CONSTANTS.PLAY_LABEL[languaje], MENU_PLAYGAME.x, MENU_PLAYGAME.y + mediumLayout.height / 2, 0, Align.center, false);

        final GlyphLayout hardLayout = new GlyphLayout(font, CONSTANTS.SCORES_LABEL[languaje]);
        font.draw(batch, CONSTANTS.SCORES_LABEL[languaje], MENU_SCORES.x, MENU_SCORES.y + hardLayout.height / 2, 0, Align.center, false);

        batch.end();

    }

    private int getFlySprite(float delta) {
        flyFps += delta;
        flyFps %= 100;
        int sprite = 0;
        if ((flyFps%0.3)>0.15){
            sprite = 1;
        }
        return sprite;
    }

    private int getWormSprite(float delta) {
        wormFps += delta;
        wormFps %= 100;
        int sprite = 0;
        if ((wormFps%0.6)>0.3){
            sprite = 1;
        }
        return sprite;
    }

    private int getFishSprite(float delta) {
        fishFps += delta;
        wormFps %= 100;
        int sprite = 0;
        if ((wormFps%0.5)>0.25){
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
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if (worldTouch.dst(MENU_OPTIONS) < CONSTANTS.MENU_BUBBLE_RADIUS*2) {
            game.showAccelerometerConfigScreen();
        }

        if (worldTouch.dst(MENU_PLAYGAME) < CONSTANTS.MENU_BUBBLE_RADIUS*2) {
            game.showGameScreen();
        }

        if (worldTouch.dst(MENU_SCORES) < CONSTANTS.MENU_BUBBLE_RADIUS*2) {
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
    /* Draw river with flow */
    public void drawBackground(float delta, SpriteBatch batch) {
        float screenWidth = viewport.getWorldWidth();
        float screenHeight = viewport.getWorldHeight();
        float imageWidth = (screenHeight/RIVER_WATER.getHeight())*RIVER_WATER.getWidth()/2;
        int spritesNeeded = (int)(screenWidth/imageWidth) + 2;

        riverPosition += delta*CONSTANTS.WATER_SPEED;
        if (riverPosition > imageWidth) {
            riverPosition = 0.0f;
        }

        riverBankPosition += delta*CONSTANTS.RIVER_BANK_SPEED;
        if (riverBankPosition > imageWidth) {
            riverBankPosition = 0.0f;
        }

        riverWaterHighlightTimer += delta;
        int hightlightWater = (riverWaterHighlightTimer < CONSTANTS.WATER_HIGHLIGHT_SPEED) ? 0 : 1;
        if (riverWaterHighlightTimer > 2*CONSTANTS.WATER_HIGHLIGHT_SPEED) {
            riverWaterHighlightTimer = 0.0f;
        }

        for (int i=0; i<=spritesNeeded; i++) {
            if ((-riverPosition + i*imageWidth) <= screenWidth) {
                batch.draw(RIVER_WATERS[hightlightWater], -riverPosition + i * imageWidth, 0.0f, imageWidth * (1 + hightlightWater), screenHeight); //Weird thing to make region width correct
            }
        }
        for (int i=0; i<=spritesNeeded; i++) {
            if ((-riverBankPosition + i*imageWidth) <= screenWidth) {
                batch.draw(RIVER_BANK_TOP, -riverBankPosition + i * imageWidth, screenHeight - CONSTANTS.FRAME_THIKNESS * 5, imageWidth, CONSTANTS.FRAME_THIKNESS * 5); //TODO: change magic number *5
                batch.draw(RIVER_BANK_BOTTOM, -riverBankPosition + i * imageWidth, 0.0f, imageWidth, CONSTANTS.FRAME_THIKNESS * 5);
            }
        }
    }

}
