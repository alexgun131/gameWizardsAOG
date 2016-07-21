package com.mygdx.game.Menu;

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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.FirstGame;

/**
 * Created by Alex on 04/07/2016.
 */
public class DeadScreen extends InputAdapter implements Screen {

    FirstGame game;

    SpriteBatch batch;
    ExtendViewport viewport;

    BitmapFont font;
    BitmapFont fontScore;

    int score;
    int eaten;
    int select = -1;
    int languaje = 0;
    Music musicDeath;
    Music musicDeathTom;
    Texture FlyButton;
    Texture WormButton;
    Texture FishButton;
    TextureRegion[] FlyButtonSprite;
    TextureRegion[] WormButtonSprite;
    TextureRegion[] FishButtonSprite;
    float flyFps;
    float wormFps;
    float fishFps;
    Vector2 DEAD_MENU ;
    Vector2 DEAD_PLAYGAME;
    Vector2 DEAD_SCORES;
    ShaderProgram shader;
    String fragmentShader;
    String vertexShader;

    public DeadScreen(FirstGame game, int score, int eaten){
        this.game = game;
        this.score = score;
        this.eaten = eaten;
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

        batch = new SpriteBatch();

        viewport = new ExtendViewport(CONSTANTS.DEAD_WORLD_SIZE, CONSTANTS.DEAD_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        fontScore = new BitmapFont();
        fontScore.getData().setScale(CONSTANTS.DEAD_SCORE_LABEL_SCALE);
        fontScore.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        font = new BitmapFont();
        font.getData().setScale(CONSTANTS.DEAD_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        game.showAd(true);
        readConfig();
        musicDeath = Gdx.audio.newMusic(Gdx.files.internal("Deaththeme2.mid"));
        musicDeathTom = Gdx.audio.newMusic(Gdx.files.internal("Tom.mid"));
        musicDeath.setVolume(0.3f);                 // sets the volume to half the maximum volume
        musicDeath.setLooping(true);                // will repeat playback until music.stop() is called
        musicDeath.play();


    }

    @Override
    public void render(float delta) {

        viewport.apply();
        Gdx.gl.glClearColor(CONSTANTS.DEAD_BACKGROUND_COLOR.r, CONSTANTS.DEAD_BACKGROUND_COLOR.g, CONSTANTS.DEAD_BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();
        DEAD_MENU = new Vector2(width / 5, height / 2);
        DEAD_PLAYGAME = new Vector2(width / 2, height / 2);
        DEAD_SCORES = new Vector2(width*4 / 5, height / 2);

        final GlyphLayout scoreLayout = new GlyphLayout(fontScore, CONSTANTS.YOUR_SCORE_LABEL[languaje]+String.valueOf(score));
        fontScore.draw(batch, CONSTANTS.YOUR_SCORE_LABEL[languaje]+String.valueOf(score), width/2, DEAD_PLAYGAME.y + CONSTANTS.MENU_BUBBLE_RADIUS*5/2 + scoreLayout.height, 0, Align.center, false);

        final GlyphLayout eatenLayout = new GlyphLayout(fontScore, CONSTANTS.EATEN_LABEL[languaje]+String.valueOf(eaten));
        fontScore.draw(batch, CONSTANTS.EATEN_LABEL[languaje]+String.valueOf(eaten), width/2, DEAD_PLAYGAME.y+ CONSTANTS.MENU_BUBBLE_RADIUS*5/2 - eatenLayout.height, 0, Align.center, false);

        batch.draw(FlyButtonSprite[getFlySprite(delta)], DEAD_MENU.x - CONSTANTS.MENU_BUBBLE_RADIUS*2, DEAD_MENU.y-CONSTANTS.MENU_BUBBLE_RADIUS*2, CONSTANTS.MENU_BUBBLE_RADIUS*4, CONSTANTS.MENU_BUBBLE_RADIUS*4);
        batch.draw(WormButtonSprite[getWormSprite(delta)], DEAD_PLAYGAME.x-CONSTANTS.MENU_BUBBLE_RADIUS*2, DEAD_PLAYGAME.y-CONSTANTS.MENU_BUBBLE_RADIUS*2, CONSTANTS.MENU_BUBBLE_RADIUS*4, CONSTANTS.MENU_BUBBLE_RADIUS*4);
        batch.draw(FishButtonSprite[getFishSprite(delta)], DEAD_SCORES.x-CONSTANTS.MENU_BUBBLE_RADIUS*2, DEAD_SCORES.y-CONSTANTS.MENU_BUBBLE_RADIUS*2, CONSTANTS.MENU_BUBBLE_RADIUS*4, CONSTANTS.MENU_BUBBLE_RADIUS*4);


        final GlyphLayout easyLayout = new GlyphLayout(font, CONSTANTS.OPTIONS_LABEL[languaje]);
        font.draw(batch, CONSTANTS.MENU_LABEL[languaje], DEAD_MENU.x, DEAD_MENU.y + easyLayout.height / 2, 0, Align.center, false);

        final GlyphLayout mediumLayout = new GlyphLayout(font, CONSTANTS.PLAY_LABEL[languaje]);
        font.draw(batch, CONSTANTS.PLAYAGAIN_LABEL[languaje], DEAD_PLAYGAME.x, DEAD_PLAYGAME.y + mediumLayout.height / 2, 0, Align.center, false);

        final GlyphLayout hardLayout = new GlyphLayout(font, CONSTANTS.SCORES_LABEL[languaje]);
        font.draw(batch, CONSTANTS.SCORES_LABEL[languaje], DEAD_SCORES.x, DEAD_SCORES.y + hardLayout.height / 2, 0, Align.center, false);

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

        if (worldTouch.dst(DEAD_MENU) < CONSTANTS.DEAD_BUBBLE_RADIUS*2) {
            game.showMenuScreen();
            musicDeath.dispose();
        }

        if (worldTouch.dst(DEAD_PLAYGAME) < CONSTANTS.DEAD_BUBBLE_RADIUS*2) {
            game.showGameScreen();
            musicDeath.dispose();
        }

        if (worldTouch.dst(DEAD_SCORES) < CONSTANTS.DEAD_BUBBLE_RADIUS*2) {
            game.showTopScoreScreen();
            musicDeath.dispose();
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
