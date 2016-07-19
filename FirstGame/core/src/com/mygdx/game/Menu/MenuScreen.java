package com.mygdx.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
public class MenuScreen extends InputAdapter implements Screen {

    FirstGame game;

    ShapeRenderer renderer;
    SpriteBatch batch;
    FitViewport viewport;
    int languaje = 1;

    BitmapFont font;

    int select = -1;
    Music music;

    Texture FlyButton;
    Texture WormButton;
    Texture FishButton;
    TextureRegion[] FlyButtonSprite;
    TextureRegion[] WormButtonSprite;
    TextureRegion[] FishButtonSprite;
    float flyFps;
    float wormFps;
    float fishFps;

    public MenuScreen(FirstGame game, Music music){
        this.game = game;
        this.music = music;
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

        game.showAd(true);

        readConfig();
        music.setVolume(0.2f);                 // sets the volume to half the maximum volume
        music.setLooping(true);                // will repeat playback until music.stop() is called
        music.play();
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

        batch.draw(FlyButtonSprite[getFlySprite(delta)], CONSTANTS.MENU_OPTIONS.x-CONSTANTS.MENU_BUBBLE_RADIUS*2, CONSTANTS.MENU_OPTIONS.y-CONSTANTS.MENU_BUBBLE_RADIUS*2, CONSTANTS.MENU_BUBBLE_RADIUS*4, CONSTANTS.MENU_BUBBLE_RADIUS*4);
        batch.draw(WormButtonSprite[getWormSprite(delta)], CONSTANTS.MENU_PLAYGAME.x-CONSTANTS.MENU_BUBBLE_RADIUS*2, CONSTANTS.MENU_PLAYGAME.y-CONSTANTS.MENU_BUBBLE_RADIUS*2, CONSTANTS.MENU_BUBBLE_RADIUS*4, CONSTANTS.MENU_BUBBLE_RADIUS*4);
        batch.draw(FishButtonSprite[getFishSprite(delta)], CONSTANTS.MENU_SCORES.x-CONSTANTS.MENU_BUBBLE_RADIUS*2, CONSTANTS.MENU_SCORES.y-CONSTANTS.MENU_BUBBLE_RADIUS*2, CONSTANTS.MENU_BUBBLE_RADIUS*4, CONSTANTS.MENU_BUBBLE_RADIUS*4);


        final GlyphLayout easyLayout = new GlyphLayout(font, CONSTANTS.OPTIONS_LABEL[languaje]);
        font.draw(batch, CONSTANTS.OPTIONS_LABEL[languaje], CONSTANTS.MENU_OPTIONS.x, CONSTANTS.MENU_OPTIONS.y + easyLayout.height / 2, 0, Align.center, false);

        final GlyphLayout mediumLayout = new GlyphLayout(font, CONSTANTS.PLAY_LABEL[languaje]);
        font.draw(batch, CONSTANTS.PLAY_LABEL[languaje], CONSTANTS.MENU_PLAYGAME.x, CONSTANTS.MENU_PLAYGAME.y + mediumLayout.height / 2, 0, Align.center, false);

        final GlyphLayout hardLayout = new GlyphLayout(font, CONSTANTS.SCORES_LABEL[languaje]);
        font.draw(batch, CONSTANTS.SCORES_LABEL[languaje], CONSTANTS.MENU_SCORES.x, CONSTANTS.MENU_SCORES.y + hardLayout.height / 2, 0, Align.center, false);

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
