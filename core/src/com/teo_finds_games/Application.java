package com.teo_finds_games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.teo_finds_games.miniJuegos.cambioRopa.CambioRopaPlayScreen;
import com.teo_finds_games.miniJuegos.colCabraLobo.ColCabraLoboPlayScreen;
import com.teo_finds_games.miniJuegos.dosmilcuarentayocho.DosMilCuarentaYOchoPlayScreen;
import com.teo_finds_games.miniJuegos.flip.FlipMenuScreen;
import com.teo_finds_games.miniJuegos.flip.FlipPlayScreen;
import com.teo_finds_games.miniJuegos.llave.LlavePlayScreen;
import com.teo_finds_games.miniJuegos.matatopos.MatatoposPlayScreen;
import com.teo_finds_games.miniJuegos.ochoNumeros.OchoNumerosPlayScreen;
import com.teo_finds_games.miniJuegos.rompecabezas.RompecabezasPlayScreen;
import com.teo_finds_games.miniJuegos.solysombra.SolYSombraPlayScreen;
import com.teo_finds_games.miniJuegos.tangram.TangramMenuScreen;
import com.teo_finds_games.miniJuegos.tangram.TangramPlayScreen;
import com.teo_finds_games.miniJuegos.torresHanoi.TorresHanoiMenuScreen;
import com.teo_finds_games.miniJuegos.torresHanoi.TorresHanoiPlayScreen;
import com.teo_finds_games.miniJuegos.tresEnRaya.TresEnRayaPlayScreen;
import com.teo_finds_games.miniJuegos.unblockIt.UnblockItPlayScreen;
import com.teo_finds_games.screens.CongratulationsScreen;
import com.teo_finds_games.screens.EndScreen;
import com.teo_finds_games.screens.HowToPlayScreen;
import com.teo_finds_games.screens.MapScreen;
import com.teo_finds_games.screens.PlayScreen;
import com.teo_finds_games.screens.SorryScreen;
import com.teo_finds_games.screens.SplashScreen;
import com.teo_finds_games.screens.WelcomeScreen;

import com.teo_finds_games.shortQuestions.AlimentarAnimalesPlayScreen;
import com.teo_finds_games.shortQuestions.NueveLadrillosPlayScreen;

public class Application extends Game {


    public static int vpWidth, vpHeight;
    public OrthographicCamera camera;

    public SpriteBatch batch;
    public static TextureAtlas spriteSheet;
    public TextureAtlas atlas;
    public static Skin skin;
    public BitmapFont myfont;

    //Screens
    public SplashScreen splashScreen;
    public PlayScreen playScreen;
    public MapScreen mapScreen;
    public EndScreen endScreen;

    FileHandle file;
    String[] arrayWelcomeTexts, arrayExplanationTexts, arrayShortQuestionsText;

    //WelcomeScreens
    public WelcomeScreen solYSombraWS, colCabraLoboWS, llaveWS, torresHanoiWS, rompecabezasWS, dosmilcuarentayochoWS, flipWS, tresEnRayaWS, ochoNumerosWS, cambioRopaWS, tangramWS, matatoposWS, unblockItWS, alimentarAnimalesWS, nueveLadrillosWS;
    //MenuScreens
    public TorresHanoiMenuScreen torresHanoiMenuScreen;
    public FlipMenuScreen flipMenuScreen;
    public TangramMenuScreen tangramMenuScreen;
    //HowToPlayScreens
    public HowToPlayScreen playHTPS, solYSombraHTPS, colCabraLoboHTPS, llaveHTPS, torresHanoiHTPS, rompecabezasHTPS, dosmilcuarentayochoHTPS, flipHTPS, tresEnRayaHTPS, ochoNumerosHTPS, cambioRopaHTPS, tangramHTPS, matatoposHTPS, unblockItHTPS;
    //PlayScreens
    public SolYSombraPlayScreen solYSombraPlayScreen;
    public ColCabraLoboPlayScreen colCabraLoboPlayScreen;
    public LlavePlayScreen llavePlayScreen;
    public TorresHanoiPlayScreen torresHanoiPlayScreen;
    public RompecabezasPlayScreen rompecabezasPlayScreen;
    public DosMilCuarentaYOchoPlayScreen dosmilcuarentayochoPlayScreen;
    public FlipPlayScreen flipPlayScreen;
    public TresEnRayaPlayScreen tresEnRayaPlayScreen;
    public OchoNumerosPlayScreen ochoNumerosPlayScreen;
    public CambioRopaPlayScreen cambioRopaPlayScreen;
    public TangramPlayScreen tangramPlayScreen;
    public MatatoposPlayScreen matatoposPlayScreen;
    public UnblockItPlayScreen unblockItPlayScreen;
    public AlimentarAnimalesPlayScreen alimentarAnimalesPlayScreen;
    public NueveLadrillosPlayScreen nueveLadrillosPlayScreen;
    //CongratulationsScreen
    public CongratulationsScreen solYSombraCS, colCabraLoboCS, torresHanoiCS, rompecabezasCS, dosMilCuarentaYOchoCS, unblockItCS, tresEnRayaCS, matatoposCS, cambioRopaCS, ochoNumerosCS, flipCS, llaveCS, alimentarAnimalesCS, nueveLadrillosCS, tangramCS;
    //SorryScreens
    public SorryScreen colCabraLoboSS, tresEnRayaSS, alimentarAnimalesSS, nueveLadrillosSS, matatoposSS;

    private Vector2 playerPosition = new Vector2(320,32);
    private int numCoins = 0;

    @Override
    public void create () {
        vpWidth = Gdx.graphics.getWidth();
        vpHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,vpWidth,vpHeight);

        batch = new SpriteBatch();

        initSkin();
        readFile();

        splashScreen = new SplashScreen(this);
        playScreen = new PlayScreen(this);
        mapScreen = new MapScreen(this);
        endScreen = new EndScreen(this);

        // PlayScreens
        solYSombraPlayScreen = new SolYSombraPlayScreen(this);
        colCabraLoboPlayScreen = new ColCabraLoboPlayScreen(this);
        torresHanoiPlayScreen = new TorresHanoiPlayScreen(this);
        rompecabezasPlayScreen = new RompecabezasPlayScreen(this);
        dosmilcuarentayochoPlayScreen = new DosMilCuarentaYOchoPlayScreen(this);
        unblockItPlayScreen = new UnblockItPlayScreen(this);
        flipPlayScreen = new FlipPlayScreen(this);
        tresEnRayaPlayScreen = new TresEnRayaPlayScreen(this);
        matatoposPlayScreen = new MatatoposPlayScreen(this);
        cambioRopaPlayScreen = new CambioRopaPlayScreen(this);
        ochoNumerosPlayScreen = new OchoNumerosPlayScreen(this);
        llavePlayScreen = new LlavePlayScreen(this);
        tangramPlayScreen = new TangramPlayScreen(this);

        alimentarAnimalesPlayScreen = new AlimentarAnimalesPlayScreen(this, arrayShortQuestionsText[0]);
        nueveLadrillosPlayScreen = new NueveLadrillosPlayScreen(this, arrayShortQuestionsText[1]);

        // MenuScreens
        torresHanoiMenuScreen = new TorresHanoiMenuScreen(this);
        flipMenuScreen = new FlipMenuScreen(this);
        tangramMenuScreen = new TangramMenuScreen(this);

        // HowToPlayScreens
        playHTPS = new HowToPlayScreen(this, arrayExplanationTexts[0], playScreen);
        solYSombraHTPS = new HowToPlayScreen(this, arrayExplanationTexts[1], solYSombraPlayScreen);
        colCabraLoboHTPS = new HowToPlayScreen(this, arrayExplanationTexts[2], colCabraLoboPlayScreen);
        torresHanoiHTPS = new HowToPlayScreen(this, arrayExplanationTexts[3], torresHanoiMenuScreen);
        rompecabezasHTPS = new HowToPlayScreen(this, arrayExplanationTexts[4], rompecabezasPlayScreen);
        dosmilcuarentayochoHTPS = new HowToPlayScreen(this, arrayExplanationTexts[5], dosmilcuarentayochoPlayScreen);
        unblockItHTPS = new HowToPlayScreen(this, arrayExplanationTexts[6], unblockItPlayScreen);
        flipHTPS = new HowToPlayScreen(this, arrayExplanationTexts[7], flipMenuScreen);
        tresEnRayaHTPS = new HowToPlayScreen(this, arrayExplanationTexts[8], tresEnRayaPlayScreen);
        matatoposHTPS = new HowToPlayScreen(this, arrayExplanationTexts[9], matatoposPlayScreen);
        cambioRopaHTPS = new HowToPlayScreen(this, arrayExplanationTexts[10], cambioRopaPlayScreen);
        ochoNumerosHTPS = new HowToPlayScreen(this, arrayExplanationTexts[11], ochoNumerosPlayScreen);
        llaveHTPS = new HowToPlayScreen(this, arrayExplanationTexts[12], llavePlayScreen);
        tangramHTPS = new HowToPlayScreen(this, arrayExplanationTexts[13], tangramMenuScreen);


        //WelcomeScreens
        solYSombraWS = new WelcomeScreen(this, arrayWelcomeTexts[0], solYSombraHTPS);
        colCabraLoboWS = new WelcomeScreen(this, arrayWelcomeTexts[1], colCabraLoboHTPS);
        llaveWS = new WelcomeScreen(this, arrayWelcomeTexts[2], llaveHTPS);
        torresHanoiWS = new WelcomeScreen(this, arrayWelcomeTexts[3], torresHanoiHTPS);
        rompecabezasWS = new WelcomeScreen(this, arrayWelcomeTexts[4], rompecabezasHTPS);
        dosmilcuarentayochoWS = new WelcomeScreen(this, arrayWelcomeTexts[5], dosmilcuarentayochoHTPS);
        flipWS = new WelcomeScreen(this, arrayWelcomeTexts[6],flipHTPS);
        tresEnRayaWS = new WelcomeScreen(this, arrayWelcomeTexts[7], tresEnRayaHTPS);
        ochoNumerosWS = new WelcomeScreen(this, arrayWelcomeTexts[8], ochoNumerosHTPS);
        cambioRopaWS = new WelcomeScreen(this, arrayWelcomeTexts[9], cambioRopaHTPS);
        tangramWS = new WelcomeScreen(this, arrayWelcomeTexts[10], tangramHTPS);
        matatoposWS = new WelcomeScreen(this, arrayWelcomeTexts[11], matatoposHTPS);
        unblockItWS = new WelcomeScreen(this, arrayWelcomeTexts[12], unblockItHTPS);
        alimentarAnimalesWS = new WelcomeScreen(this, arrayWelcomeTexts[13], alimentarAnimalesPlayScreen);
        nueveLadrillosWS = new WelcomeScreen(this, arrayWelcomeTexts[13], nueveLadrillosPlayScreen);

        //CongratulationsScreens
        colCabraLoboCS = new CongratulationsScreen(this);
        solYSombraCS = new CongratulationsScreen(this);
        torresHanoiCS = new CongratulationsScreen(this);
        rompecabezasCS = new CongratulationsScreen(this);
        dosMilCuarentaYOchoCS = new CongratulationsScreen(this);
        unblockItCS = new CongratulationsScreen(this);
        tresEnRayaCS = new CongratulationsScreen(this);
        matatoposCS = new CongratulationsScreen(this);
        cambioRopaCS = new CongratulationsScreen(this);
        ochoNumerosCS = new CongratulationsScreen(this);
        flipCS = new CongratulationsScreen(this);
        llaveCS = new CongratulationsScreen(this);
        alimentarAnimalesCS = new CongratulationsScreen(this);
        nueveLadrillosCS = new CongratulationsScreen(this);
        tangramCS = new CongratulationsScreen(this);

        //SorryScreens
        colCabraLoboSS = new SorryScreen(this, colCabraLoboPlayScreen);
        tresEnRayaSS = new SorryScreen(this, tresEnRayaPlayScreen);
        alimentarAnimalesSS = new SorryScreen(this, alimentarAnimalesPlayScreen);
        nueveLadrillosSS = new SorryScreen(this, nueveLadrillosPlayScreen);
        matatoposSS = new SorryScreen(this, matatoposPlayScreen);

        setScreen(splashScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        //screens
        splashScreen.dispose();
        playScreen.dispose();
        mapScreen.dispose();
        endScreen.dispose();

        //WelcomeScreens
        solYSombraWS.dispose();
        colCabraLoboWS.dispose();
        llaveWS.dispose();
        torresHanoiWS.dispose();
        rompecabezasWS.dispose();
        dosmilcuarentayochoWS.dispose();
        flipWS.dispose();
        tresEnRayaWS.dispose();
        ochoNumerosWS.dispose();
        cambioRopaWS.dispose();
        tangramWS.dispose();
        matatoposWS.dispose();
        unblockItWS.dispose();
        alimentarAnimalesWS.dispose();
        nueveLadrillosWS.dispose();

        //HowToPlayScreens
        playHTPS.dispose();
        solYSombraHTPS.dispose();
        colCabraLoboHTPS.dispose();
        llaveHTPS.dispose();
        torresHanoiHTPS.dispose();
        rompecabezasHTPS.dispose();
        dosmilcuarentayochoHTPS.dispose();
        flipHTPS.dispose();
        tresEnRayaHTPS.dispose();
        ochoNumerosHTPS.dispose();
        cambioRopaHTPS.dispose();
        tangramHTPS.dispose();
        matatoposHTPS.dispose();
        unblockItHTPS.dispose();

        //MenuScreens
        torresHanoiMenuScreen.dispose();
        flipMenuScreen.dispose();
        tangramMenuScreen.dispose();

        //PlaysScreens
        solYSombraPlayScreen.dispose();
        colCabraLoboPlayScreen.dispose();
        llavePlayScreen.dispose();
        torresHanoiPlayScreen.dispose();
        rompecabezasPlayScreen.dispose();
        dosmilcuarentayochoPlayScreen.dispose();
        flipPlayScreen.dispose();
        tresEnRayaPlayScreen.dispose();
        ochoNumerosPlayScreen.dispose();
        cambioRopaPlayScreen.dispose();
        tangramPlayScreen.dispose();
        matatoposPlayScreen.dispose();
        unblockItPlayScreen.dispose();

        alimentarAnimalesPlayScreen.dispose();
        nueveLadrillosPlayScreen.dispose();

        //CongratulationsScreens
        colCabraLoboCS.dispose();
        solYSombraCS.dispose();
        torresHanoiCS.dispose();
        rompecabezasCS.dispose();
        dosMilCuarentaYOchoCS.dispose();
        unblockItCS.dispose();
        tresEnRayaCS.dispose();
        matatoposCS.dispose();
        cambioRopaCS.dispose();
        ochoNumerosCS.dispose();
        llaveCS.dispose();
        flipCS.dispose();
        alimentarAnimalesCS.dispose();
        nueveLadrillosCS.dispose();
        tangramCS.dispose();

        //SorryScreen
        colCabraLoboSS.dispose();
        tresEnRayaSS.dispose();
        alimentarAnimalesSS.dispose();
        nueveLadrillosSS.dispose();
        matatoposSS.dispose();

        batch.dispose();
        skin.dispose();
        myfont.dispose();
    }

    private void initSkin(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Amaranth-Bold.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 20;
        params.color = Color.BLACK;
        myfont = generator.generateFont(params);

        skin = new Skin();
        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin.addRegions(atlas);
        skin.add("default-font", myfont);
        skin.load(Gdx.files.internal("skin/uiskin.json"));
        generator.dispose();
    }

    public static TextureRegion[] getSprites(String file, int cols, int rows) {
        Texture texture = new Texture(Gdx.files.internal(file));
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / cols, texture.getHeight() / rows);
        TextureRegion[] Frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Frames[index++] = tmp[i][j];
            }
        }
        return Frames;
    }

    private void readFile(){
        file = Gdx.files.internal("data/welcome.txt");
        arrayWelcomeTexts = file.readString().split("-");

        file = Gdx.files.internal("data/howToPlay.txt");
        arrayExplanationTexts = file.readString().split("-");

        file = Gdx.files.internal("data/shortQuestions.txt");
        arrayShortQuestionsText = file.readString().split("-");
    }

    public Vector2 getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Vector2 playerPosition) {
        this.playerPosition = playerPosition;
    }

    public int getNumCoins() {
        return numCoins;
    }

    public void setNumCoins(int numCoins) {
        this.numCoins = numCoins;
    }
}
