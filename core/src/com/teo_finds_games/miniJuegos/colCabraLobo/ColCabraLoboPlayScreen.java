package com.teo_finds_games.miniJuegos.colCabraLobo;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

public class ColCabraLoboPlayScreen implements Screen {

    private final Application app;
    private Stage stage;
    private TextureRegion backgroundTR, boatTR, wolfTR, goatTR, cabbageTR;
    private TextButton backButton, goButton;
    private ColCabraLoboBoatActor boat;
    private ColCabraLoboAnimalActor wolf, goat, cabbage;

    public ColCabraLoboPlayScreen(Application app){
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
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        initTextureRegions();
        setBackground();
        initButtons();
        initActors();
    }

    public void update(float delta){
        stage.act(delta);
        changeAnimalSide();
        if (gameOver()){
            stage.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    app.setScreen(app.colCabraLoboSS);
                }
            })));
        }

        if (solutionFound()){
            stage.addAction(Actions.sequence(Actions.delay(3f),Actions.run(new Runnable(){
                @Override
                public void run() {
                    app.colCabraLoboCS.setNumCoins(5);
                    app.setScreen(app.colCabraLoboCS);
                }
            })));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.7f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.act();
        stage.draw();
        app.batch.begin();
        app.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        backgroundTR.getTexture().dispose();
        boatTR.getTexture().dispose();
        wolfTR.getTexture().dispose();
        goatTR.getTexture().dispose();
        cabbageTR.getTexture().dispose();
    }

    private void initTextureRegions(){
        backgroundTR = new TextureRegion(new Texture(Gdx.files.internal("images/colcabralobo/background.png")));
        boatTR = new TextureRegion(new Texture(Gdx.files.internal("images/colcabralobo/boat.png")));
        wolfTR = new TextureRegion(new Texture(Gdx.files.internal("images/colcabralobo/wolf.png")));
        goatTR = new TextureRegion(new Texture(Gdx.files.internal("images/colcabralobo/goat.png")));
        cabbageTR = new TextureRegion(new Texture(Gdx.files.internal("images/colcabralobo/cabbage.png")));
    }

    public void setBackground(){
        //Setting background
        //You can put a background behind actors by adding the background as an Image (subclass of Actor) to the Stage and using the z-index to make sure it is drawn as a background.
        Image imageBackground = new Image(backgroundTR);
        imageBackground.setSize(app.vpWidth, app.vpHeight);
        stage.addActor(imageBackground);
    }


    private void initButtons(){
        //BACK BUTTON
        backButton = new TextButton("ATRAS", app.skin);
        backButton.setSize(125, 75);
        backButton.setPosition(20, app.vpHeight - (backButton.getHeight() + 20));
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });
        stage.addActor(backButton);
        //GO BUTTON
        goButton = new TextButton("GO!",app.skin);
        goButton.setSize(125,75);
        goButton.setPosition(app.vpWidth / 2 - goButton.getWidth() / 2, app.vpHeight - (goButton.getHeight() + 20));
        goButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boat.moveToTheOtherSide();
                if(wolf.getInBoat()){
                    wolf.moveToTheOtherSide();
                }else if(goat.getInBoat()){
                    goat.moveToTheOtherSide();
                }else if(cabbage.getInBoat()){
                    cabbage.moveToTheOtherSide();
                }

            }
        });
        stage.addActor(goButton);
    }


    private void initActors(){
        boat = new ColCabraLoboBoatActor(boatTR);
        stage.addActor(boat);

        wolf = new ColCabraLoboAnimalActor(wolfTR, 1);
        stage.addActor(wolf);
        goat = new ColCabraLoboAnimalActor(goatTR, 2);
        stage.addActor(goat);
        cabbage = new ColCabraLoboAnimalActor(cabbageTR, 3);
        stage.addActor(cabbage);
    }

    public void changeAnimalSide() {
        if (cabbage.getInBoat()) {
            cabbage.setSide(boat.side);
        } else if (goat.getInBoat()) {
            goat.setSide(boat.side);
        } else if (wolf.getInBoat()) {
            wolf.setSide(boat.side);
        }
    }

    public boolean gameOver(){
        return (((cabbage.getSide() == goat.getSide() && goat.getSide() != wolf.getSide()) && (cabbage.getSide() == goat.getSide()) && (boat.side != cabbage.getSide()) ) ||
                ((wolf.getSide() == goat.getSide() && goat.getSide() != cabbage.getSide()) && (wolf.getSide() == goat.getSide()) && boat.side != wolf.getSide()));
    }

    public boolean solutionFound(){
        return (wolf.getSide() == 1  &&  (cabbage.getSide() == goat.getSide()  && goat.getSide() == wolf.getSide()));
    }

}
