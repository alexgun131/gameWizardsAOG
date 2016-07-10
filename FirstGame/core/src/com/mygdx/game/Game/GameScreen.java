package com.mygdx.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.CONSTANTS;
import com.mygdx.game.FirstGame;

/**
 * Created by Alex on 29/06/2016.
 */
public class GameScreen extends InputAdapter implements Screen {

    FirstGame game;

    public GameScreen(FirstGame game){
        this.game = game;
    }

    ExtendViewport viewport;
    ScreenViewport hudViewport;
    SpriteBatch batch;
    int topScore;
    int topEaten;
    int currentScore;
    int scoreBeforeMult;
    int eatenPoints;
    boolean beatHighestScore;
    BitmapFont font;
    ShapeRenderer renderer;

    Player player;
    Enemies enemies;
    Point point;
    long timeElapsed;
    boolean isPoint;
    float riverPosition;
    float riverBankPosition;
    float riverWaterHighlightTimer;
    Texture RIVER_WATER;
    TextureRegion[] RIVER_WATERS;
    Texture RIVER_BANK_TOP;
    Texture RIVER_BANK_BOTTOM;

    @Override
    public void show() {
        viewport = new ExtendViewport(CONSTANTS.WORLD_SIZE, CONSTANTS.WORLD_SIZE);
        Gdx.input.setInputProcessor(this);
        loadTextures();
        renderer = new ShapeRenderer();
        hudViewport = new ScreenViewport();

        batch = new SpriteBatch();

        font = new BitmapFont();

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        player = new Player(viewport);
        enemies = new Enemies(viewport);

        point = new Point(viewport);
        read();
        currentScore = 0;
        scoreBeforeMult = 0;
        eatenPoints = 0;
        beatHighestScore = false;

        riverPosition = 0.0f;
        riverBankPosition = 0.0f;
        riverWaterHighlightTimer = 0.0f;
    }

    @Override
    public void render(float delta) {
        player.update(delta);
        enemies.update(delta);

        viewport.apply(true);

        Gdx.gl.glClearColor(CONSTANTS.BACKGROUND_COLOR.r, CONSTANTS.BACKGROUND_COLOR.g, CONSTANTS.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        drawBackground(delta); // draw river with animation

        //renderer.setColor(CONSTANTS.SAND_COLOR);
        //renderer.rect(0,0,viewport.getWorldWidth(),CONSTANTS.FRAME_THIKNESS);
        //renderer.rect(0,0,CONSTANTS.FRAME_THIKNESS,viewport.getWorldHeight());
        //renderer.rect(0,viewport.getWorldHeight()-CONSTANTS.FRAME_THIKNESS,viewport.getWorldWidth(),CONSTANTS.FRAME_THIKNESS);
        //renderer.rect(viewport.getWorldWidth()-CONSTANTS.FRAME_THIKNESS,
        //0, CONSTANTS.FRAME_THIKNESS, viewport.getWorldHeight());

        player.render(renderer, batch, beatHighestScore);
        point.render(renderer);
        enemies.render(renderer);





        renderer.end();
        if (player.hitByIcicle(enemies) || player.ensureInBounds()) {
            enemies.init();
            player.init();
            write();
            game.showDeadScreen(currentScore, eatenPoints);
            currentScore = 0;
            scoreBeforeMult = 0;
            eatenPoints = 0;
        }

        if(player.getPoint(point)){
            point.disappear();
            timeElapsed = TimeUtils.nanoTime();
            isPoint = false;
            eatenPoints++;
            scoreBeforeMult = currentScore;
            enemies.enemiesCounter = 0;
        }

        if(!isPoint){
            if((TimeUtils.nanoTime() - timeElapsed)*1E-9 > CONSTANTS.TIME_SPAWN_POINTS*MathUtils.random(0.3f,1.2f)){
                point.newPosition();
                isPoint = true;
            }
        }

        currentScore = scoreBeforeMult + enemies.enemiesCounter*eatenPoints;
        topScore = Math.max(topScore, currentScore);
        topEaten = Math.max(topEaten, eatenPoints);
        beatHighestScore = currentScore==topScore;

        hudViewport.apply();

        batch.setProjectionMatrix(hudViewport.getCamera().combined);

        batch.begin();

        font.draw(batch, "Points: " + eatenPoints + "\nTop Eaten :" + topEaten,
                CONSTANTS.HUD_MARGIN, hudViewport.getWorldHeight() - CONSTANTS.HUD_MARGIN);

        font.draw(batch, "Score: " + currentScore + "\nTop Score: " + topScore,
                hudViewport.getWorldWidth() - CONSTANTS.HUD_MARGIN, hudViewport.getWorldHeight() - CONSTANTS.HUD_MARGIN,
                0, Align.right, false);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        font.getData().setScale(Math.min(width, height) / CONSTANTS.HUD_FONT_REFERENCE_SCREEN_SIZE);
        player.init();
        enemies.init();
        point.newPosition();
        isPoint = true;
    }

    @Override
    public void pause() {
        write();
    }

    @Override
    public void resume() {
        read();
    }

    @Override
    public void hide() {
        write();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
        write();
    }

    public void write() {
        Json json = new Json();
        FileHandle topDataFile = Gdx.files.local( CONSTANTS.TOP_FILE_NAME );
        Vector2 v = new Vector2(topEaten, topScore);
        String topAsText = json.toJson( v );
        String topAsCode = Base64Coder.encodeString( topAsText );
        topDataFile.writeString( topAsCode, false );
    }

    public void read() {

        FileHandle topDataFile = Gdx.files.local(CONSTANTS.TOP_FILE_NAME);
        Json json = new Json();

        if (topDataFile.exists()) {
            try {
                String topAsCode = topDataFile.readString();
                String topAsText = Base64Coder.decodeString(topAsCode);
                Vector2 v = json.fromJson(Vector2.class, topAsText);

                topEaten = (int)v.x;
                topScore = (int)v.y;

            } catch (Exception e) {
                topEaten = 0;
                topScore = 0;
            }
        }
    }

    /* Draw river with flow */
    public void drawBackground(float delta) {
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

        batch.begin();
        batch.setProjectionMatrix(viewport.getCamera().combined);
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
        batch.end();
    }

    public void loadTextures(){
        // TEXTURES
        // Background
        RIVER_WATER = new Texture("RiverWater.png");
        RIVER_WATERS = new TextureRegion[2]; //There are two sprites in RiverWater
        RIVER_WATERS[0] = new TextureRegion(RIVER_WATER, 0, 0, 1024, 1024);
        RIVER_WATERS[1] = new TextureRegion(RIVER_WATER, 1024, 0, 2048, 1024);
        RIVER_BANK_TOP = new Texture("RiverBankTop.png");
        RIVER_BANK_BOTTOM = new Texture("RiverBank.png");
    }
}
