package com.simplebojocs.pondskater.mainmenu2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.simplebojocs.pondskater.CONSTANTS;
import com.simplebojocs.utils.Scene.iClickable;
import com.simplebojocs.utils.Scene.iDisposable;
import com.simplebojocs.utils.Scene.iRenderable;
import com.simplebojocs.utils.lang.CtrlLanguage;

public abstract class CircularButton implements iClickable, iRenderable, iDisposable {
    public Float depth;
    public Vector2 position;
    public String langKey;
    public BitmapFont font;

    private float fps;
    private ExtendViewport viewport;

    Texture texture;
    TextureRegion[] tRegion;

    public CircularButton(Float depth, Vector2 position, String langKey, BitmapFont font, String textureLocation, ExtendViewport viewport){
        this.depth = depth;
        this.position = position;
        this.langKey = langKey;
        this.font = font;
        this.viewport = viewport;
        fps = 0.0f;

        texture = new Texture(textureLocation);
        tRegion = new TextureRegion[2 * 1];
        for (int i = 0; i < 2; i++)
            tRegion[i] = new TextureRegion(texture, 256 * i, 0, 256, 256);
    }

    public boolean isClicked(Vector2 worldTouch){
        Vector2 absPosition = new Vector2(position.x * viewport.getWorldWidth(), position.y * viewport.getWorldHeight()) ;
        return worldTouch.dst(position) < CONSTANTS.MENU_BUBBLE_RADIUS * 2;
    }
    public boolean render(float delta, SpriteBatch batch){
        fps += delta;
        fps %= 100;
        int sprite = 0;
        if ((fps % 0.3) > 0.15) {
            sprite = 1;
        }
        batch.draw(tRegion[sprite],
                (position.x * viewport.getWorldWidth()) - CONSTANTS.MENU_BUBBLE_RADIUS * 2,
                (position.y * viewport.getWorldHeight()) - CONSTANTS.MENU_BUBBLE_RADIUS * 2,
                CONSTANTS.MENU_BUBBLE_RADIUS * 4,
                CONSTANTS.MENU_BUBBLE_RADIUS * 4);

        String message = CtrlLanguage.getInstance().get(langKey);
        final GlyphLayout layout = new GlyphLayout(font, message);
        font.draw(batch, message, (position.x * viewport.getWorldWidth()), (position.y * viewport.getWorldHeight()) + layout.height / 2, 0, Align.center, false);

        return false;
    }
    public Float getDepth(){
        return depth;
    }
    public void dispose(){
        texture.dispose();
    }
}
