package com.simplebojocs.pondskater2.Menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.simplebojocs.pondskater2.CONSTANTS;

/**
 * Created by Oriol on 1/08/16.
 */
public class Tutorial {

    private Texture tutorialTexture;
    private TextureRegion[] tutorialSprites;
    private int currentStep;

    private static final int GAME_AIM = 0x01;
    private static final int DRIFT    = 0x02;
    private static final int JUMP_UP  = 0x03;
    private static final int TILT     = 0x04;
    private static final int LOSE     = 0x05;


    public Tutorial(int language ) {
        if (language == 1)
            loadTextures("TutorialES.png", 2, 3);
        else
            loadTextures("Tutorial.png", 2, 3);
        currentStep = GAME_AIM;
    }

    public void loadTextures(String filename, int animationRows, int animationColumns){
        tutorialTexture = new Texture(filename);
        int texuretWidth = tutorialTexture.getWidth()/animationColumns;
        int textureHeigth = tutorialTexture.getHeight()/animationRows;

        tutorialSprites = new TextureRegion[animationColumns * animationRows];
        for (int i = 0; i < animationColumns; i++) { //Images are loaded by rows
            for (int j = 0; j < animationRows; j++) {
                tutorialSprites[i+j*animationColumns] = new TextureRegion(tutorialTexture, texuretWidth * i, textureHeigth*j, texuretWidth, textureHeigth);
            }
        }
    }

    public void draw(SpriteBatch batch, float worldWidth, float worldHeight){
        float imageWidth = worldWidth*8/10;
        float imageHeight = tutorialSprites[0].getRegionHeight()*imageWidth/tutorialSprites[0].getRegionWidth();
        batch.draw(tutorialSprites[0], (worldWidth-imageWidth)/2, (worldHeight-imageHeight)/2, imageWidth, imageHeight);
        batch.draw(tutorialSprites[currentStep], (worldWidth-imageWidth)/2, (worldHeight-imageHeight)/2, imageWidth, imageHeight);
    }

    public void start(){
        currentStep = GAME_AIM;
    }

    public boolean drawNext(){
        boolean continueTutorial = true;
        currentStep += 1;
        if (currentStep > LOSE ) {
            continueTutorial = false;
        }
        return continueTutorial;
    }
}
