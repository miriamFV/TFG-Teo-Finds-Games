package com.teo_finds_games.miniJuegos.matatopos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;


public class MatatoposPlayScreen implements Screen {

    private final Application app;
    private long startTime;
    private Stage stage;
    private Texture backgroundTexture, moleTexture, maskTexture;
    public static Vector2 cFactor; //Conversion factor to convert texture's coordinates into screen's coordinates

    private MatatoposMoleActor[] molesArray = new MatatoposMoleActor[7];

    public static Label ntLabel; //Label that says how many moles has been touched


    public MatatoposPlayScreen(Application app) {
        this.app = app;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(app.vpWidth, app.vpHeight, app.camera));
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        initTextures();
        initBackground();

        initLabels();

        cFactor = new Vector2((float)app.vpWidth/(float)backgroundTexture.getWidth(), (float)app.vpHeight/(float)backgroundTexture.getHeight());
        initActors();
        initMasks();

        startTime = TimeUtils.millis(); //Tiempo en el que comienza el juego
    }

    public void update(float delta){
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        if(elapsedTime > 10*1000){
            if(MatatoposMoleActor.hitedMoles > 5){
                app.setScreen(app.matatoposCS);
            }else{
                app.setScreen(app.matatoposSS);
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 0.4f, 0.4f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        stage.act(); //Calls each Actor's act method
        stage.draw();
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
        backgroundTexture.dispose();
        moleTexture.dispose();
        maskTexture.dispose();
    }

    public void initTextures(){
        //Background texture
        backgroundTexture = new Texture(Gdx.files.internal("images/matatopos/background.png"));
        //Mole texture
        moleTexture = new Texture(Gdx.files.internal("images/matatopos/mole.png"));
        //Mask texture
        maskTexture = new Texture(Gdx.files.internal("images/matatopos/mask.png"));
    }

    public void initBackground(){
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(app.vpWidth, app.vpHeight);
        stage.addActor(backgroundImage);
    }

    public void initLabels(){
        ntLabel = new Label("Topos golpeados = 0", app.skin);
        ntLabel.setFontScale(2);
        ntLabel.setColor(Color.BLACK);
        ntLabel.setPosition( (app.vpWidth - 400)/2 , app.vpHeight-ntLabel.getHeight()*3);

        stage.addActor(ntLabel);
    }

    public void initActors(){
        for(int i=0; i<molesArray.length; i++){
            molesArray[i] = new MatatoposMoleActor(moleTexture,i+1);
            stage.addActor(molesArray[i]);
        }
    }

    public void initMasks(){

        Vector2 maskSize = new Vector2(maskTexture.getWidth()*cFactor.x, maskTexture.getHeight()*cFactor.y);
        //Mole's mask 1
        Image maskImage1 = new Image(maskTexture);
        maskImage1.setSize(maskSize.x, maskSize.y);
        maskImage1.setPosition(50*cFactor.x,-78*cFactor.y);
        stage.addActor(maskImage1);

        //Mole's mask 2
        Image maskImage2 = new Image(maskTexture);
        maskImage2.setSize(maskSize.x, maskSize.y);
        maskImage2.setPosition(250*cFactor.x,-78*cFactor.y);
        stage.addActor(maskImage2);

        //Mole's mask 3
        Image maskImage3 = new Image(maskTexture);
        maskImage3.setSize(maskSize.x, maskSize.y);
        maskImage3.setPosition(450*cFactor.x,-78*cFactor.y);
        stage.addActor(maskImage3);

        //Mole's mask 4
        Image maskImage4 = new Image(maskTexture);
        maskImage4.setSize(maskSize.x, maskSize.y);
        maskImage4.setPosition(650 * cFactor.x, -78 * cFactor.y);
        stage.addActor(maskImage4);

        //Mole's mask 5
        Image maskImage5 = new Image(maskTexture);
        maskImage5.setSize(maskSize.x, maskSize.y);
        maskImage5.setPosition(150*cFactor.x,-28*cFactor.y);
        stage.addActor(maskImage5);

        //Mole's mask 6
        Image maskImage6 = new Image(maskTexture);
        maskImage6.setSize(maskSize.x, maskSize.y);
        maskImage6.setPosition(350*cFactor.x,-28*cFactor.y);
        stage.addActor(maskImage6);

        //Mole's mask 7
        Image maskImage7 = new Image(maskTexture);
        maskImage7.setSize(maskSize.x, maskSize.y);
        maskImage7.setPosition(550*cFactor.x,-28*cFactor.y);
        stage.addActor(maskImage7);
    }

}
