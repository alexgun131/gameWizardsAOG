package com.simplebojocs.pondskater2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 29/06/2016.
 */
public class CONSTANTS {

    //GAME CONSTANTS
    public static final float WORLD_SIZE = 480.0f;
    public static final float PLAYER_RAD = WORLD_SIZE/35;
    public static final float ENEMY_WIDTH = WORLD_SIZE/20;
    public static final float ENEMY_HEIGHT = WORLD_SIZE/22;
    public static final float POINT_WIDTH = WORLD_SIZE/30 /2;
    public static final float SUPERPOINT_WIDTH = WORLD_SIZE/27 /2;
    public static final int NUMBER_TOPSCORES = 15;

    public static final Color PLAYER_COLOR = Color.ORANGE;
    public static final Color ENEMY_COLOR = Color.DARK_GRAY;
    public static final Color POINT_COLOR = Color.FIREBRICK;
    public static final Color BACKGROUND_COLOR = Color.FIREBRICK;
    public static final Color SAND_COLOR = Color.FOREST;

    public static final float ACCELEROMETER_SENSITIVITY = 0.5f * WORLD_SIZE/480.0f;
    public static final float GRAVITATIONAL_ACCELERATION = 9.8f * WORLD_SIZE/480.0f;

    public static final float JUMP_VELOCITY = 300.0f * WORLD_SIZE/480.0f;
    public static final float JUMP_GRAVITY_MULT = 1.0f * WORLD_SIZE/480.0f;
    public static final float PLAYER_VELOCITY = 100.0f * WORLD_SIZE/480.0f;
    public static final float PLAYER_VELOCITY_KEY = 2.0f * WORLD_SIZE/480.0f;

    public static final float SPAWN_RATE = 1.27f;
    public static final float ENEMY_VELOCITY = 300.0f * WORLD_SIZE/480.0f;
    public static final float ENEMY_POINTS_HARDMODE = 100000;
    public static final float ENEMY_AMP_SIN = WORLD_SIZE/1000;
    public static final float ENEMY_W_SIN = WORLD_SIZE/40;
    public static final float POINT_AMP_SIN = WORLD_SIZE/3000;
    public static final float POINT_W_SIN = WORLD_SIZE/300;
    public static final float SUPERPOINT_VELOCITY = 245.0f * WORLD_SIZE/480.0f;
    public static final float SUPERPOINT_AMP_SIN = WORLD_SIZE/70;
    public static final float SUPERPOINT_W_SIN = WORLD_SIZE/170;
    public static final float FRAME_THIKNESS = 35.0f * WORLD_SIZE/480.0f;

    public static final float TIME_SPAWN_POINTS = 2;
    public static final float TIME_SPAWN_SUPERPOINTS = 10;
    public static final int VALUE_SCORE_SPAWN_SUPERPOINTS = 5;
    public static final float TIME_SHOW_DEATH = 1.2f;

    public static final float ADD_BANNER_HEIGHT = 50;
    public static final float ADD_BANNER_WIDTH = 320;
    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = WORLD_SIZE/1.5f;

    public static final float HUD_MARGIN = 20.0f * WORLD_SIZE/480.0f;

    public static final String TOP_FILE_NAME = "TOP_SCORES2";

    // Background animation
    public static final float WATER_HIGHLIGHT_SPEED = 1.0f;
    public static final float WATER_SPEED = 75.0f * WORLD_SIZE/480.0f;
    public static final float RIVER_BANK_SPEED = 20.0f * WORLD_SIZE/480.0f;



    //MENU CONSTANTS

    public static final Color MENU_BACKGROUND_COLOR = Color.YELLOW;

    public static final float MENU_WORLD_SIZE = 480.0f;
    public static final float MENU_BUBBLE_RADIUS = MENU_WORLD_SIZE / 9;
    public static final float MENU_LABEL_SCALE = 1.0f;

    public static final float MENU_AUTO_AD_WIDTH = MENU_WORLD_SIZE*1.1f;


    public static final Color OPTIONS_COLOR = new Color(0.2f, 0.2f, 1, 1);
    public static final Color PLAY_COLOR = new Color(0.5f, 0.5f, 1, 1);
    public static final Color SCORES_COLOR = new Color(0.7f, 0.7f, 1, 1);

    public static final String[] OPTIONS_LABEL = {"Settings", "Ajustes", "设置", "設定", "설정", "إعدادات"};
    public static final String[] PLAY_LABEL = {"Play Game", "Jugar", "玩", "遊びます", "놀이", "لعب"};
    public static final String[] SCORES_LABEL = {"Scores", "Marcador", "分数", "スコア", "점수", "عشرات"};


    //DEAD CONSTANTS

    public static final Color DEAD_BACKGROUND_COLOR = Color.BLACK;

    public static final float DEAD_WORLD_SIZE = 480.0f;
    public static final float DEAD_BUBBLE_RADIUS = DEAD_WORLD_SIZE / 9;
    public static final float DEAD_LABEL_SCALE = 0.8f;
    public static final float DEAD_SCORE_LABEL_SCALE = 1.0f;


    public static final Color MENU_COLOR = new Color(0.2f, 0.2f, 1, 1);
    public static final Color PLAYAGAIN_COLOR = new Color(0.5f, 0.5f, 1, 1);
    public static final Color SCORESD_COLOR = new Color(0.7f, 0.7f, 1, 1);

    public static final String[] MENU_LABEL = {"Menu", "Menú", "菜单", "メニュー", "메뉴", "قائمة طعام"};
    public static final String[] PLAYAGAIN_LABEL = {"Play Again", "Volver a jugar", "再玩一次", "再びプレー", "다시 플레이",  "إلعب مرة أخرى"};
    public static final String[] YOUR_SCORE_LABEL = {"Your score is: ", "Tu puntuación es: ",  "你的分数: ", "あなたのスコア: ", "너의 점수: " , "درجاتك: "};
    public static final String[] EATEN_LABEL = {"Bonus: ", "Bonus: ", "奖金: ", "ボーナス: ", "보너스: ", "علاو ة"};
    public static final String[] CURRENTSCORE = {"Score: ", "Puntuación: ",  "得分了: ", "スコア: ", "점수: ", "أحرز هدفاً " };
    public static final String[] BESTSCORE = {"NEW RECORD", "NUEVO RECORD",  "新纪录", "新記録", "새로운 기록", "رقم قياسي جديد"};
    public static final String[] TOP = {"TOP", "TOP", "前", "トップ", "상단 열", "أفضل عشرة"};
    public static final String[] NUM5 = {"5", "5", "五", "五", "五", "خمسة"};
    public static final String[] NUM10 = {"10", "10", "十", "十", "十", "عشرة"};
    public static final String[] MUSIC_LABEL  = {"Music", "Música", "音乐", "音楽", "음악", "موسيقى"};
    public static final String[] SOUNDS_LABEL   = {"Sound", "Sonidos", "声音", "音", "소리", "الأصوات"};


    //SCORES CONSTANTS
    public static final float SCORES_BUBBLE_RADIUS = DEAD_WORLD_SIZE / 15 * 1.5f;
    public static final Vector2 BACK_TO_MENU = new Vector2(DEAD_WORLD_SIZE /20 + SCORES_BUBBLE_RADIUS, DEAD_WORLD_SIZE * 18/20 - ADD_BANNER_HEIGHT);
    public static final Vector2 LEADERBOARDS_POSITION = new Vector2(DEAD_WORLD_SIZE /20 + SCORES_BUBBLE_RADIUS, DEAD_WORLD_SIZE * 18/20 - ADD_BANNER_HEIGHT-SCORES_BUBBLE_RADIUS*2);
    public static final Vector2 ACHIEVEMENTS_POSITION = new Vector2(DEAD_WORLD_SIZE /20 + SCORES_BUBBLE_RADIUS, DEAD_WORLD_SIZE * 18/20 - ADD_BANNER_HEIGHT - SCORES_BUBBLE_RADIUS*4);
    public static final Vector2 EATEN_SCORES = new Vector2(DEAD_WORLD_SIZE *10/ 20, DEAD_WORLD_SIZE * 18/20 - ADD_BANNER_HEIGHT);
    public static final Vector2 TOP_SCORES = new Vector2(DEAD_WORLD_SIZE *19/20, DEAD_WORLD_SIZE *18/20 - ADD_BANNER_HEIGHT);
    public static final float SCORE_LABEL_SCALE = 0.5f;

    public static final String[] TOP_EATEN_LABEL = {"TOP BONUS", "MEJORES BONUS", "頂級獎金", "トップボーナス", "최고 보너스", "أعلى مكافأة"};
    public static final String[] TOP_SCORES_LABEL = {"TOP SCORES", "MEJORES PUNTUACIONES", "最佳射手", "得点王", "득점 왕", "الاعلى نقاطا"};
    public static final String[] ACHIEVEMENTS_LABEL = {"Achievements", "Logros", "成就", "実績", "업적", "الإنجازات"};
    public static final String[] LEADERBOARDS_LABEL = {"Leaderboards", "Clasificación", "排行榜", "リーダーボード", "리더", "المتصدرين"};

    public static final Color STRIPE1 = new Color(0.384f, 0.545f, 1.0f, 0.25f);
    public static final Color STRIPE2 = new Color(0.545f, 0.545f, 1.0f, 0.25f);

    //CONFIG CONSTANTS
    public static final String[] INVERTXY_LABEL = {"InvertXY", "InvertirXY", "倒置XY", "反転XY", "거꾸로 하다XY", "عكسXY" } ;
    public static final String[] INVERTX_LABEL = {"InvertX", "InvertirX", "倒置X", "反転X", "거꾸로 하다X", "عكسX"};
    public static final String[] INVERTY_LABEL = {"InvertY", "InvertirY", "倒置Y", "反転Y", "거꾸로 하다Y", "عكسY"};

    public static final String INVERTCONFIG_FILE_NAME = "INVERT_CONFIG";
    public static final String LANGUAJECONFIG_FILE_NAME = "LANGUAJE_CONFIG";

    public static final String[] SELECT_LANGUAGE = {"ENGLISH", "ESPAÑOL", "中文", "日本の", "한국의", "العربية"}; //eng, esp, chi, jap, kor, arabe


    // Creditos

    public static final String[] LEADER_PROGRAMMER = {"Leader Programmer", "Programador Líder", "领导者的程序员", "リーダープログラマー", "리더 프로그래머", "زعيم مبرمج"};
    public static final String[] PROGRAMMERS = {"Programmers", "Programadores", "程序员", "プログラマ", "프로그래머", "المبرمجين"};
    public static final String[] ART = {"Art and Graphic Design", "Arte y diseño grafico", "艺术与平面设计", "アートとグラフィックデザイン", "미술 및 그래픽 디자인", "الفن والتصميم الجرافيكي"};
    public static final String[] MUSIC = {"Music and sounds", "Música y sonidos", "音乐和声音", "音楽とサウンド", "음악과 소리", "الموسيقى والأصوات"};


    //greyScale

    public static String vertexShader = "attribute vec4 a_position;\n" +
            "attribute vec4 a_color;\n" +
            "attribute vec2 a_texCoord0;\n" +
            "\n" +
            "uniform mat4 u_projTrans;\n" +
            "\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "\n" +
            "void main() {\n" +
            "    v_color = a_color;\n" +
            "    v_texCoords = a_texCoord0;\n" +
            "    gl_Position = u_projTrans * a_position;\n" +
            "}";

    public static String fragmentShader = "#ifdef GL_ES\n" +
            "    precision mediump float;\n" +
            "#endif\n" +
            "\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "uniform sampler2D u_texture;\n" +
            "uniform float gray;\n" +
            "\n" +

            "void main() {\n" +
            "  vec4 c = v_color * texture2D(u_texture, v_texCoords);\n" +
            "  float grey = (c.r + c.g + c.b) / 3.0;\n" +
            "  vec3 blendedColor = mix(c.rgb, vec3(grey), gray);\n" +
            "  gl_FragColor = vec4(blendedColor.rgb, c.a);\n" +
            "}";
}
