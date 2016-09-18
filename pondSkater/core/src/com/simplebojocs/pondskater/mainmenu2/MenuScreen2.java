package com.simplebojocs.pondskater.mainmenu2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.simplebojocs.pondskater.CONSTANTS;
import com.simplebojocs.pondskater.PondSkater;
import com.simplebojocs.pondskater.utils.Audio;
import com.simplebojocs.pondskater.utils.Language;
import com.simplebojocs.utils.Scene.Scene;
import com.simplebojocs.utils.audio.CtrlAudio;
import com.simplebojocs.utils.audio.PlayList;
import com.simplebojocs.utils.lang.CtrlLanguage;

public class MenuScreen2 extends Scene{
    PondSkater game;
    BitmapFont font;

    public MenuScreen2(PondSkater game){
        super(CONSTANTS.MENU_WORLD_SIZE, CONSTANTS.MENU_WORLD_SIZE);
        this.game = game;

        font = new BitmapFont(Gdx.files.internal("data/CuteFont.fnt"),
                Gdx.files.internal("data/CuteFont.png"), false);

        //settings
        add(new CircularButton(1.0f, new Vector2(0.25f, 0.25f), "button.settings", font, "FlyButton.png", viewport){
            public void click(Vector2 worldTouch){
                MenuScreen2.this.game.showAccelerometerConfigScreen();
            }
        });
        //play
        add(new CircularButton(2.0f, new Vector2(0.5f, 0.25f), "button.play", font, "WormButton.png", viewport){
            public void click(Vector2 worldTouch){
                MenuScreen2.this.game.showGameScreen();
            }
        });
        //score
        add(new CircularButton(1.0f, new Vector2(0.75f, 0.25f), "button.score", font, "FishButton.png", viewport){
            public void click(Vector2 worldTouch){
                MenuScreen2.this.game.showTopScoreScreen();
            }
        });

        CtrlAudio.getInstance().disposeOnLoadBGM = false;
        //BGM
        add(new CircularButton(2.0f, new Vector2(0.25f, 0.75f), "BGM", font, "FlyButton.png", viewport){
            int audioSwitch = 0;
            public void click(Vector2 worldTouch){
                PlayList playlist = CtrlAudio.getInstance().BGM;
                float x = (position.x * viewport.getWorldWidth()) - worldTouch.x;
                float y = (position.y * viewport.getWorldHeight()) - worldTouch.y;
                if(x<0 && y<0) playlist.setVolume(playlist.getVolume() - 0.1f);
                if(x<0 && y>0) playlist.setVolume(playlist.getVolume() + 0.1f);
                if(x>0 && y<0) playlist.setMuted(!playlist.isMuted());
                if(x>0 && y>0){
                    Audio toPlay;
                    switch(audioSwitch){
                        case 0: toPlay = Audio.POND_SKATER; break;
                        default:toPlay = Audio.DEATH_THEME; break;
                    }
                    audioSwitch = (audioSwitch + 1) & 1;
                    CtrlAudio.getInstance().loadAudio(toPlay, true);
                }
            }
        });
        //SFX
        add(new CircularButton(2.0f, new Vector2(0.5f, 0.75f), "SFX", font, "FlyButton.png", viewport){
            public void click(Vector2 worldTouch){
                PlayList playlist = CtrlAudio.getInstance().SFX;
                float x = (position.x * viewport.getWorldWidth()) - worldTouch.x;
                float y = (position.y * viewport.getWorldHeight()) - worldTouch.y;
                if(x<0 && y<0) playlist.setVolume(playlist.getVolume() - 0.1f);
                if(x<0 && y>0) playlist.setVolume(playlist.getVolume() + 0.1f);
                if(x>0 && y<0) playlist.setMuted(!playlist.isMuted());
                if(x>0 && y>0)CtrlAudio.getInstance().loadAudio(Audio.MOSKITO, true);
            }
        });
        //LANG
        add(new CircularButton(2.0f, new Vector2(0.75f, 0.75f), "LANG", font, "FlyButton.png", viewport){
            public void click(Vector2 worldTouch){
                CtrlLanguage lang = CtrlLanguage.getInstance();
                float x = (position.x * viewport.getWorldWidth()) - worldTouch.x;
                float y = (position.y * viewport.getWorldHeight()) - worldTouch.y;
                if(x<0 && y<0) lang.setLanguage(Language.en_US);
                if(x<0 && y>0) lang.setLanguage(Language.es_ES);
                if(x>0 && y<0) lang.setLanguage(Language.ja_JP);
                if(x>0 && y>0) lang.setLanguage(Language.zh_CN);
            }
        });
    }
    @Override
    public void show() {
        CtrlAudio.getInstance().clear();
        CtrlAudio.getInstance().loadAudio(Audio.POND_SKATER, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
    }
}
