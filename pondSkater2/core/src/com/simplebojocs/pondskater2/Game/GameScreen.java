package com.simplebojocs.pondskater2.Game;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.simplebojocs.pondskater2.CONSTANTS;
import com.simplebojocs.pondskater2.PondSkater;
import com.simplebojocs.pondskater2.utils.PondSkaterAchievement;

/**
 * Created by Alex on 29/06/2016.
 */
public class GameScreen extends InputAdapter implements Screen {

    PondSkater game;
    Music soundDeath;
    public GameScreen(PondSkater game){
        this.game = game;
    }

    ExtendViewport viewport;
    SpriteBatch batch;
    ShaderProgram shader;
    int language = 0;
    int[] topScore;
    int[] topEaten;
    int currentTopScore;
    int currentTopEaten;
    int currentScore;
    int scoreBeforeMult;
    int eatenPoints;
    int beatHighestScore;
    int moskitoEatCount;
    BitmapFont font;
    BitmapFont sbfont;

    com.simplebojocs.pondskater2.Game.Player player;
    Enemies enemies;
    Point point;
    com.simplebojocs.pondskater2.Game.SuperPoint superPoint;
    long timePointElapsed;
    long timeSuperPointElapsed;
    boolean isPoint;
    boolean isSuperPoint;
    boolean isAlive;
    float timeSinceDead;
    float riverPosition;
    float riverBankPosition;
    float riverWaterHighlightTimer;
    Texture RIVER_WATER;
    TextureRegion[] RIVER_WATERS;
    Texture RIVER_BANK_TOP;
    Texture RIVER_BANK_BOTTOM;
    Texture AUTO_AD;
    Music moskitoMusic;
    Music eatLarvae;
    Music eatMoskito;

    boolean musicON = true;
    boolean soundsON = true;
    String topScorenow;
    Vector2 topScores;

    @Override
    public void show() {
        viewport = new ExtendViewport(CONSTANTS.WORLD_SIZE, CONSTANTS.WORLD_SIZE);
        Gdx.input.setInputProcessor(this);
        loadTextures();
        topScorenow = "";

        batch = new SpriteBatch();
        shader = new ShaderProgram(CONSTANTS.vertexShader, CONSTANTS.fragmentShader);

        font = new BitmapFont(Gdx.files.internal("data/CuteFont.fnt"),
                Gdx.files.internal("data/CuteFont.png"), false);
        font.getData().setScale(CONSTANTS.SCORE_LABEL_SCALE*1.25f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        sbfont = new BitmapFont(Gdx.files.internal("data/CuteFont2.fnt"),
                Gdx.files.internal("data/CuteFont2.png"), false);
        sbfont.getData().setScale(CONSTANTS.SCORE_LABEL_SCALE);
        sbfont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        player = new com.simplebojocs.pondskater2.Game.Player(viewport);
        enemies = new Enemies(viewport);

        point = new Point(viewport);
        superPoint = new com.simplebojocs.pondskater2.Game.SuperPoint(viewport);
        superPoint.newPosition();
        superPoint.disappear();
        timeSuperPointElapsed = TimeUtils.nanoTime();
        topEaten = new int[CONSTANTS.NUMBER_TOPSCORES];
        topScore = new int[CONSTANTS.NUMBER_TOPSCORES];
        currentTopScore = 0;
        read();
        currentScore = 0;
        scoreBeforeMult = 0;
        eatenPoints = 0;
        beatHighestScore = 0;
        moskitoEatCount = 0;

        riverPosition = 0.0f;
        riverBankPosition = 0.0f;
        riverWaterHighlightTimer = 0.0f;
        isAlive = true;
        isPoint = false;
        isSuperPoint = false;

        if(soundsON) {
            moskitoMusic = Gdx.audio.newMusic(Gdx.files.internal("Kito_the_Moskito.mid"));
            moskitoMusic.setLooping(true);
            moskitoMusic.setVolume(0.15f);

            eatLarvae = Gdx.audio.newMusic(Gdx.files.internal("eatLarvae.mp3"));
            eatLarvae.setLooping(false);
            eatLarvae.setVolume(0.18f);

            eatMoskito = Gdx.audio.newMusic(Gdx.files.internal("eatMoskito.mp3"));
            eatMoskito.setLooping(false);
            eatMoskito.setVolume(0.18f);
        }

        if(musicON) {
            game.music.setVolume(0.4f);
            game.music.play();
        }

        game.externalServices.submitAchievement(PondSkaterAchievement.FIRST_GAME);
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
        AUTO_AD = new Texture("AutoAd.png");
    }

    @Override
    public void render(float delta) {

        if(isAlive){
            player.update(delta);
            enemies.update(delta, currentScore);
            point.update(delta);
            superPoint.update(delta);
        }



        viewport.apply(true);

        Gdx.gl.glClearColor(CONSTANTS.BACKGROUND_COLOR.r, CONSTANTS.BACKGROUND_COLOR.g, CONSTANTS.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        drawBackground(delta, batch); // draw river with animation

        //renderer.setColor(CONSTANTS.SAND_COLOR);
        //renderer.rect(0,0,viewport.getWorldWidth(),CONSTANTS.FRAME_THIKNESS);
        //renderer.rect(0,0,CONSTANTS.FRAME_THIKNESS,viewport.getWorldHeight());
        //renderer.rect(0,viewport.getWorldHeight()-CONSTANTS.FRAME_THIKNESS,viewport.getWorldWidth(),CONSTANTS.FRAME_THIKNESS);
        //renderer.rect(viewport.getWorldWidth()-CONSTANTS.FRAME_THIKNESS,
        //0, CONSTANTS.FRAME_THIKNESS, viewport.getWorldHeight());

        player.render(batch, beatHighestScore);
        enemies.render(batch);

        topScores = new Vector2(viewport.getWorldWidth()- CONSTANTS.HUD_MARGIN, viewport.getWorldHeight()- 2* CONSTANTS.HUD_MARGIN - CONSTANTS.ADD_BANNER_HEIGHT);

        font.draw(batch, CONSTANTS.CURRENTSCORE[language] + currentScore +"\n"+ CONSTANTS.EATEN_LABEL[language] + eatenPoints ,
                CONSTANTS.HUD_MARGIN, viewport.getWorldHeight() - 2*CONSTANTS.FRAME_THIKNESS); //scores down to point but up to fishes

        final GlyphLayout musicLayout = new GlyphLayout(sbfont, topScorenow);
        sbfont.draw(batch, topScorenow, topScores.x, topScores.y + musicLayout.height / 2, 0, Align.right, false);

        point.render(batch);
        superPoint.render(batch);

        if ((player.hitByIcicle(enemies) || player.ensureInBounds()) && isAlive) {
            isAlive = false;
            game.externalServices.submitScore(currentScore);
            if(soundsON) {
                moskitoMusic.dispose();
                eatLarvae.dispose();
                eatMoskito.dispose();
                player.jumpSound.dispose();
            }
            timeSinceDead = TimeUtils.nanoTime();
            if(musicON) {
                soundDeath = Gdx.audio.newMusic(Gdx.files.internal("Death_sound_2.mp3"));
                soundDeath.setLooping(false);
                soundDeath.setVolume(0.6f);
                soundDeath.play();
            }
            if(musicON) {
                game.music.pause();
                game.music.setPosition(0);
            }
        }
        if(!isAlive) {
            if((TimeUtils.nanoTime() - timeSinceDead)*1E-9 > CONSTANTS.TIME_SHOW_DEATH)
            {
                enemies.init();
                player.init();
                game.showDeadScreen(currentScore, eatenPoints, soundDeath);
                game.externalServices.submitScore(currentScore);
                write();
                currentScore = 0;
                currentTopScore = 0;
                currentTopEaten = 0;
                scoreBeforeMult = 0;
                eatenPoints = 0;
                isAlive = true;
            }
            else
            {
                float timeElapsed = (float)(TimeUtils.nanoTime()*1E-9 - timeSinceDead*1E-9);
                float gray = 0;
                if(timeElapsed < 1.1f) {
                    gray = (float) (TimeUtils.nanoTime() * 1E-9 - timeSinceDead * 1E-9) + 0.1f;
                }else{
                    gray = 1.1f;
                }
                shader.begin();
                shader.setUniformf("gray", gray);
                shader.end();
                batch.setShader(shader);
            }
        }

        if(player.getPoint(point)){
            //eatLarvae.stop(); //covering eat some larvaes in a short time
            if(soundsON) {
                eatLarvae.setPosition(0.1f); // si no parece que vaya retrasado
                eatLarvae.play();
            }
            point.disappear();
            timePointElapsed = TimeUtils.nanoTime();
            isPoint = false;
            eatenPoints++;
            scoreBeforeMult = currentScore;
            enemies.enemiesCounter = 0;
        }

        if(player.getSuperPoint(superPoint)){
            int oldMoskitoEatCount = moskitoEatCount++;
            checkForAchievement(oldMoskitoEatCount, moskitoEatCount, PondSkaterAchievement.PondSkaterAchievementType.MOSKITOS);

            if(soundsON){
                eatMoskito.play();
                moskitoMusic.setLooping(false);
                moskitoMusic.pause();
            }
            superPoint.disappear();
            timeSuperPointElapsed = TimeUtils.nanoTime();
            isSuperPoint = false;
            eatenPoints = eatenPoints + CONSTANTS.VALUE_SCORE_SPAWN_SUPERPOINTS;
            scoreBeforeMult = currentScore;
            enemies.enemiesCounter = 0;
        }

        if(superPoint.ensureInBounds() && isSuperPoint){
            isSuperPoint = false;
            if(soundsON) {
                moskitoMusic.setLooping(false);
                moskitoMusic.pause();
            }
            timeSuperPointElapsed = TimeUtils.nanoTime();
        }
        if(!isPoint){
            if((TimeUtils.nanoTime() - timePointElapsed)*1E-9 > CONSTANTS.TIME_SPAWN_POINTS*MathUtils.random(0.3f,1.2f)){
                point.newPosition();
                isPoint = true;
            }
        }

        if(!isSuperPoint){
            if((TimeUtils.nanoTime() - timeSuperPointElapsed)*1E-9 > CONSTANTS.TIME_SPAWN_SUPERPOINTS*MathUtils.random(1.0f,1.5f)){
                if(soundsON) {
                    moskitoMusic.setLooping(true);
                    moskitoMusic.setPosition(0);
                    moskitoMusic.play();
                }
                superPoint.newPosition();
                isSuperPoint = true;
            }
        }

        int oldScore = currentScore;
        currentScore = scoreBeforeMult + enemies.enemiesCounter*eatenPoints;
        checkForAchievement(oldScore, currentScore, PondSkaterAchievement.PondSkaterAchievementType.POINTS);

        //for(int i = 0; i<CONSTANTS.NUMBER_TOPSCORES; i++) {
        currentTopScore = Math.max(topScore[0], currentScore);
        currentTopEaten = Math.max(topEaten[0], eatenPoints);
        if(currentScore >= topScore[0]){
            beatHighestScore = 1;
            topScorenow = CONSTANTS.BESTSCORE[language];
        }
        else if(currentScore >= topScore[4]){
            beatHighestScore = 2;
            topScorenow = CONSTANTS.TOP[language] + CONSTANTS.NUM5[language];
        }
        else if(currentScore >= topScore[9]) {
            beatHighestScore = 3;
            topScorenow = CONSTANTS.TOP[language] + CONSTANTS.NUM10[language];
        }
        //}



        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        player.init();
        enemies.init();
        point.newPosition();
        isPoint = true;
    }

    @Override
    public void pause() {
        game.externalServices.submitScore(currentScore);
        write();

    }

    @Override
    public void resume() {
        game.showMenuScreen();
    }

    @Override
    public void hide() {
        game.externalServices.submitScore(currentScore);
        write();

    }

    @Override
    public void dispose() {
        game.externalServices.submitScore(currentScore);
        batch.dispose();
        font.dispose();
        sbfont.dispose();
        shader.dispose();
        if(soundsON) {
            moskitoMusic.dispose();
            eatLarvae.dispose();
            eatMoskito.dispose();
            player.jumpSound.dispose();
        }
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
        FileHandle languajeDataFile = Gdx.files.local( CONSTANTS.LANGUAJECONFIG_FILE_NAME );
        FileHandle configDataFile = Gdx.files.local(CONSTANTS.INVERTCONFIG_FILE_NAME);
        Json json = new Json();

        if (configDataFile.exists()) {
            try {
                String topAsCode = configDataFile.readString();
                String topAsText = Base64Coder.decodeString(topAsCode);
                boolean[] config = json.fromJson(boolean[].class, topAsText);
                musicON = config[3];
                soundsON = config[4];

            } catch (Exception e) {
                musicON = true;
                soundsON = true;
            }
        }
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
                if(i==0){
                    topEaten[i] = 15;
                    topScore[i] = 500;
                }
                else if(i<4){
                    topEaten[i] = 10;
                    topScore[i] = 200;
                }
                else if(i<9){
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
        batch.draw(AUTO_AD, 0, screenHeight - CONSTANTS.FRAME_THIKNESS * 2, AUTO_AD.getWidth()*CONSTANTS.FRAME_THIKNESS * 2/AUTO_AD.getHeight(), CONSTANTS.FRAME_THIKNESS * 2); //TODO: very hardcoded difficult to follow
    }

    private void checkForAchievement(int oldValue, int newValue, PondSkaterAchievement.PondSkaterAchievementType psaType){
        for(PondSkaterAchievement achievement : PondSkaterAchievement.values())
            if(achievement.psaType == psaType && oldValue < achievement.amount && newValue >= achievement.amount)
                game.externalServices.submitAchievement(achievement);
    }
}