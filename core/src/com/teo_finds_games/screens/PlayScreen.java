package com.teo_finds_games.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;
import com.teo_finds_games.Controller;
import com.teo_finds_games.actors.Inhabitants;
import com.teo_finds_games.actors.Player;

public class PlayScreen implements Screen{

    private final Application app;

    //Stage, touchpad...
    private Stage stage;
    private Controller touchpad;
    private TextButton mapButton;
    public static final int SCALE = 4;

    //TileMap
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Textures
    private Texture transparentTexture;

    //Coins
    private Label numCoinsLabel;
    public static float stateTime = 0f;
    private Animation coinsAnimation;
    private TextureRegion coinsFrame;

    //Player
    private Player player;
    private Vector2 playerPosition;

    //Inhabitants
    private Inhabitants inhabitantSolYSombra, inhabitantColCabraLobo, inhabitantTorresHanoi, inhabitantRompecabezas, inhabitant2048,inhabitantUnblockIt, inhabitantTresEnRaya, inhabitantFlip, inhabitantMatatopos, inhabitantCambioRopa, inhabitantOchoNumeros, inhabitantLlave, inhabitantAlimentarAnimales, inhabitantNueveLadrillos, inhabitantTangram ;

    //Music
    private Music music;

    public PlayScreen(Application app){
        this.app = app;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera)){
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    System.exit(0);
                }
                return super.keyDown(keyCode);
            }
        };

        //Set processor
        Gdx.input.setInputProcessor(stage);
        //Create the map
        map = new TmxMapLoader().load("maps/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        this.playerPosition = app.getPlayerPosition();

        initMusic();
        initTextures();
        initAnimations();
        initLabels();
        initPlayer();
        initInhabitants();
        initButtons();
        initTouchpad();

        if(hasWon()){
            float delay = 1.5f; // seconds

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    app.setScreen(app.endScreen);
                }
            }, delay);
        }


    }

    public void update(float delta){
        //update position of objects which move with the player
        float centerX = player.getX()+player.getWidth()/2; //centerX = center of the stage
        float centerY = player.getY()+player.getHeight()/2; //centerY = center of the stage
        mapButton.setPosition(centerX + app.camera.viewportWidth / 2 - mapButton.getWidth() - 10, centerY - app.camera.viewportHeight / 2 + 10);
        numCoinsLabel.setPosition(centerX - app.camera.viewportWidth / 2 + coinsFrame.getRegionWidth() + 20, player.getY() + app.camera.viewportHeight / 2 - 10);
        touchpad.setPosition(centerX - app.camera.viewportWidth / 2, centerY - app.camera.viewportHeight / 2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f,0.15f,0.15f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update current state of coinsAnimation.
        stateTime += Gdx.graphics.getDeltaTime();
        coinsFrame = coinsAnimation.getKeyFrame(stateTime, true);

        app.camera.position.set(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2,0);
        app.camera.update();

        renderer.setView(app.camera);
        renderer.render();

        update(delta);
        stage.act();
        stage.draw();

        renderer.getBatch().begin();
        //Draw coinsAnimation
        renderer.getBatch().draw(coinsFrame, player.getX() + player.getWidth() / 2 - app.camera.viewportWidth / 2 + 10, player.getY() + app.camera.viewportHeight / 2 - 10, numCoinsLabel.getWidth(), numCoinsLabel.getHeight());
        player.draw(renderer.getBatch());
        renderer.getBatch().draw(player.getPlayerFrame(), player.getX(), player.getY(), player.getWidth(), player.getHeight()); //Para la animacion del player

        renderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        app.camera.viewportWidth = width/SCALE;
        app.camera.viewportHeight = height/SCALE;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }


    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
        touchpad.touchpadSkin.dispose();
        map.dispose();
        renderer.dispose();
        transparentTexture.dispose();
        music.dispose();
    }

    public void initMusic(){
        music = Gdx.audio.newMusic( Gdx.files.internal("sounds/forestSong.wav"));
        music.setVolume(1);
        music.play();
    }

    private void initTextures(){
        transparentTexture = new Texture(Gdx.files.internal("images/pantallaprincipal/transparent.png"));
    }

    public void initAnimations(){
        coinsAnimation = new Animation(0.2f, Application.getSprites("images/pantallaprincipal/coinAnimation.png", 6, 1));
    }

    public void initLabels(){
        numCoinsLabel = new Label(""+app.getNumCoins(), app.skin);
        numCoinsLabel.setFontScale(1);
        numCoinsLabel.setColor(Color.YELLOW);
        stage.addActor(numCoinsLabel);
    }

    public void initPlayer(){
        player = new Player(new Sprite(new Texture("images/pantallaprincipal/transparentMan.png")), (TiledMapTileLayer) map.getLayers().get(0));
        player.setPosition(playerPosition.x, playerPosition.y);
    }

    public void initInhabitants(){
        inhabitantSolYSombra = new Inhabitants(transparentTexture, app, player, new Vector2(7, 37) , app.solYSombraWS);
        stage.addActor(inhabitantSolYSombra);

        inhabitantColCabraLobo = new Inhabitants(transparentTexture, app, player, new Vector2(18,27), app.colCabraLoboWS);
        stage.addActor(inhabitantColCabraLobo);

        inhabitantTorresHanoi = new Inhabitants(transparentTexture, app, player, new Vector2(2,7), app.torresHanoiWS);
        stage.addActor(inhabitantTorresHanoi);

        inhabitantRompecabezas = new Inhabitants(transparentTexture, app, player, new Vector2(22,38), app.rompecabezasWS);
        stage.addActor(inhabitantRompecabezas);

        inhabitant2048 = new Inhabitants(transparentTexture, app, player, new Vector2(31,19), app.dosmilcuarentayochoWS);
        stage.addActor(inhabitant2048);

        inhabitantUnblockIt = new Inhabitants(transparentTexture, app, player, new Vector2(38,10), app.unblockItWS);
        stage.addActor(inhabitantUnblockIt);

        inhabitantTresEnRaya = new Inhabitants(transparentTexture, app, player, new Vector2(43,27), app.tresEnRayaWS);
        stage.addActor(inhabitantTresEnRaya);

        inhabitantFlip = new Inhabitants(transparentTexture, app, player, new Vector2(61, 36) , app.flipWS);
        stage.addActor(inhabitantFlip);

        inhabitantMatatopos = new Inhabitants(transparentTexture, app, player, new Vector2(53,11), app.matatoposWS);
        stage.addActor(inhabitantMatatopos);

        inhabitantCambioRopa = new Inhabitants(transparentTexture, app, player, new Vector2(40,19), app.cambioRopaWS);
        stage.addActor(inhabitantCambioRopa);

        inhabitantOchoNumeros = new Inhabitants(transparentTexture, app, player, new Vector2(55,29), app.ochoNumerosWS);
        stage.addActor(inhabitantOchoNumeros);

        inhabitantLlave = new Inhabitants(transparentTexture, app, player, new Vector2(14,16), app.llaveWS);
        stage.addActor(inhabitantLlave);


        inhabitantAlimentarAnimales = new Inhabitants(transparentTexture, app, player, new Vector2(63,1), app.alimentarAnimalesWS);
        stage.addActor(inhabitantAlimentarAnimales);

        inhabitantNueveLadrillos = new Inhabitants(transparentTexture, app, player, new Vector2(27,30), app.nueveLadrillosWS);
        stage.addActor(inhabitantNueveLadrillos);

        inhabitantTangram = new Inhabitants(transparentTexture, app, player, new Vector2(57,19), app.tangramWS);
        stage.addActor(inhabitantTangram);
    }

    public void initButtons(){
        //mapButton
        mapButton = new TextButton("MAPA", Application.skin);
        mapButton.setSize(80, 25);
        mapButton.setPosition(player.getX(), player.getY());

        mapButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                app.setPlayerPosition(new Vector2(player.getX(), player.getY()));
                app.setScreen(app.mapScreen);
                return false;
            }
        });
        stage.addActor(mapButton);

    }

    public void initTouchpad(){
        touchpad = new Controller(player, app.camera);
        stage.addActor(touchpad);
    }

    public boolean hasWon(){
        return app.getNumCoins()>=100;
    }

}
