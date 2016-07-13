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
    int[] topScore;
    int[] topEaten;
    int currentTopScore;
    int currentTopEaten;
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
    boolean isAlive;
    float timeSinceDead;
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
        topEaten = new int[CONSTANTS.NUMBER_TOPSCORES];
        topScore = new int[CONSTANTS.NUMBER_TOPSCORES];
        currentTopScore = 0;
        read();
        currentScore = 0;
        scoreBeforeMult = 0;
        eatenPoints = 0;
        beatHighestScore = false;

        riverPosition = 0.0f;
        riverBankPosition = 0.0f;
        riverWaterHighlightTimer = 0.0f;
        isAlive = true;
        isPoint = false;

        game.showAd(false);
    }

    private void loadTextures(){
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
    public void render(float delta) {
        if(isAlive){
            player.update(delta);
            enemies.update(delta, currentScore);
            point.update(delta);
        }



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
        point.render(renderer, batch);
        enemies.render(renderer, batch);





        renderer.end();
        if ((player.hitByIcicle(enemies) || player.ensureInBounds()) && isAlive) {
            isAlive = false;
            timeSinceDead = TimeUtils.nanoTime();

        }
        if(!isAlive) {
            if((TimeUtils.nanoTime() - timeSinceDead)*1E-9 > CONSTANTS.TIME_SHOW_DEATH)
            {
                enemies.init();
                player.init();
                game.showDeadScreen(currentScore, eatenPoints);
                write();
                currentScore = 0;
                currentTopScore = 0;
                currentTopEaten = 0;
                scoreBeforeMult = 0;
                eatenPoints = 0;
                isAlive = true;
             }
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
        //for(int i = 0; i<CONSTANTS.NUMBER_TOPSCORES; i++) {
        currentTopScore = Math.max(topScore[0], currentScore);
        currentTopEaten = Math.max(topEaten[0], eatenPoints);
        beatHighestScore = currentScore == currentTopScore;
        //}

        hudViewport.apply();

        batch.setProjectionMatrix(hudViewport.getCamera().combined);

        batch.begin();

        font.draw(batch, "Points: " + eatenPoints + "\nTop Eaten :" + currentTopEaten,
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

    public void addToTopScoresList() {
        for(int i=0; i<CONSTANTS.NUMBER_TOPSCORES; i++){
            if(currentScore >= topScore[i]){
                for(int j=CONSTANTS.NUMBER_TOPSCORES-1 ; j>i; j--){
                    topScore[j] = topScore[j-1];
                }
                topScore[i] = currentScore;
                break;
            }
        }

        for(int i=0; i<CONSTANTS.NUMBER_TOPSCORES; i++){
            if(eatenPoints >= topEaten[i]){
                for(int j=CONSTANTS.NUMBER_TOPSCORES-1 ; j>i; j--){
                    topEaten[j] = topEaten[j-1];
                }
                topEaten[i] = eatenPoints;
                break;
            }
        }

        currentScore = 0;
        eatenPoints = 0;
    }
    public void write() {
        Json json = new Json();
        FileHandle topDataFile = Gdx.files.local( CONSTANTS.TOP_FILE_NAME );
        addToTopScoresList();
        Vector2[] av = new Vector2[15];
        for(int i=0; i<CONSTANTS.NUMBER_TOPSCORES; i++){
            Vector2 v = new Vector2(topEaten[i], topScore[i]);
            av[i] = v;
        }
        String topAsText = json.toJson( av );
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
                Vector2[] av = json.fromJson(Vector2[].class, topAsText);
                for (int i=0; i<CONSTANTS.NUMBER_TOPSCORES; i++) {
                    Vector2 v = av[i];
                    topEaten[i] = (int)v.x;
                    topScore[i] = (int)v.y;
                }

            } catch (Exception e) {
                for (int i=0; i<CONSTANTS.NUMBER_TOPSCORES; i++) {
                    topEaten[i] = 0;
                    topScore[i] = 0;
                }
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
}
