package com.teo_finds_games.miniJuegos.cambioRopa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.teo_finds_games.Application;


public class CambioRopaPlayScreen implements Screen {

    private final Application app;
    private Stage stage;

    private TextButton backButton;

    private Texture textureBackground, textureMan, textureMannequin;
    private Texture brownC1Texture, brownC2Texture, brownC3Texture, brownC4Texture, blueC1Texture, blueC2Texture, blueC3Texture, blueC4Texture;

    private CambioRopaActorFigure man1, man2, mannequin1, mannequin2;
    public static CambioRopaActorClothes[] brownCArray, blueCArray;

    public CambioRopaPlayScreen(Application app){
        this.app = app;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(app.vpWidth, app.vpHeight)){
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    System.exit(0);
                }
                return super.keyDown(keyCode);
            }
        };
        Gdx.input.setInputProcessor(stage);

        initTextures();
        setBackground();
        initButtons();
        initActors();
    }

    public void update(float delta){
        if (CambioRopaActorClothes.garmentTouched == true){ //If any garment is being touched
            if( garmentTouched().isOnTop()){
                //The piece have to move with your finger
                garmentTouched().getRectangle().setPosition(Gdx.input.getX() - garmentTouched().getWidth()/2, app.vpHeight - (Gdx.input.getY() + garmentTouched().getHeight()/2 ));
            }else{
                //This piece can not be moved
            }
        }
        if (haveWon()){
            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    app.cambioRopaCS.setNumCoins(10);
                    app.setScreen(app.cambioRopaCS);
                }
            })));
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 0.7f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.act();
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
        stage.dispose();
        textureBackground.dispose();
        textureMan.dispose();
        textureMannequin.dispose();
        brownC1Texture.dispose();
        brownC2Texture.dispose();
        brownC3Texture.dispose();
        brownC4Texture.dispose();
        blueC1Texture.dispose();
        blueC2Texture.dispose();
        blueC3Texture.dispose();
        blueC4Texture.dispose();
    }


    public void setBackground(){
        //Setting background
        //You can put a background behind actors by adding the background as an Image (subclass of Actor) to the Stage and using the z-index to make sure it is drawn as a background.
        Image imageBackground = new Image(textureBackground);
        imageBackground.setSize(app.vpWidth, app.vpHeight);
        stage.addActor(imageBackground);
    }

    public void initButtons(){
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
    }


    public void initTextures(){
        textureBackground = new Texture(Gdx.files.internal("images/cambioropa/background.png"));
        textureMan = new Texture((Gdx.files.internal("images/cambioropa/man.png")));
        textureMannequin = new Texture(Gdx.files.internal("images/cambioropa/mannequin.png"));

        //BROWN CLOTHES TEXTURES
        brownC1Texture = new Texture(Gdx.files.internal("images/cambioropa/brownClothes1.png"));
        brownC2Texture = new Texture(Gdx.files.internal("images/cambioropa/brownClothes2.png"));
        brownC3Texture = new Texture(Gdx.files.internal("images/cambioropa/brownClothes3.png"));
        brownC4Texture = new Texture(Gdx.files.internal("images/cambioropa/brownClothes4.png"));

        //BLUE CLOTHES TEXTURES
        blueC1Texture = new Texture(Gdx.files.internal("images/cambioropa/blueClothes1.png"));
        blueC2Texture = new Texture(Gdx.files.internal("images/cambioropa/blueClothes2.png"));
        blueC3Texture = new Texture(Gdx.files.internal("images/cambioropa/blueClothes3.png"));
        blueC4Texture = new Texture(Gdx.files.internal("images/cambioropa/blueClothes4.png"));

    }


    public void initActors(){
        man1 = new CambioRopaActorFigure(textureMan, 1);
        stage.addActor(man1);

        mannequin1 = new CambioRopaActorFigure(textureMannequin, 2);
        stage.addActor(mannequin1);

        mannequin2 = new CambioRopaActorFigure(textureMannequin, 3);
        stage.addActor(mannequin2);

        man2 = new CambioRopaActorFigure(textureMan, 4);
        stage.addActor(man2);

        //RED CLOTHES ACTORS
        brownCArray = new CambioRopaActorClothes[4];
        brownCArray[0] = new CambioRopaActorClothes(brownC4Texture,4,man1,mannequin1,mannequin2,man2);
        brownCArray[1] = new CambioRopaActorClothes(brownC3Texture,3,man1,mannequin1,mannequin2,man2);
        brownCArray[2] = new CambioRopaActorClothes(brownC2Texture,2,man1,mannequin1,mannequin2,man2);
        brownCArray[3] = new CambioRopaActorClothes(brownC1Texture,1,man1,mannequin1,mannequin2,man2);

        //BLUE CLOTHES ACTORS
        blueCArray = new CambioRopaActorClothes[4];
        blueCArray[0] = new CambioRopaActorClothes(blueC4Texture,4,man2,mannequin1,mannequin2,man1);
        blueCArray[1] = new CambioRopaActorClothes(blueC3Texture,3,man2,mannequin1,mannequin2,man1);
        blueCArray[2] = new CambioRopaActorClothes(blueC2Texture,2,man2,mannequin1,mannequin2,man1);
        blueCArray[3] = new CambioRopaActorClothes(blueC1Texture,1,man2,mannequin1,mannequin2,man1);

        //ADD TO STAGE
        for(int i=0; i<brownCArray.length; i++){
            stage.addActor(brownCArray[i]);
            stage.addActor(blueCArray[i]);
            man1.stack.push(brownCArray[i].getNumber());
            man2.stack.push(blueCArray[i].getNumber());
        }
    }


    public CambioRopaActorClothes garmentTouched(){
        CambioRopaActorClothes sol = null;
        for (int i = 0; i<brownCArray.length; i++){
            if (brownCArray[i].getIsTouched()){
                sol = brownCArray[i];
            }
        }
        for (int j = 0; j<blueCArray.length; j++){
            if(blueCArray[j].getIsTouched()){
                sol = blueCArray[j];
            }
        }
        return sol;
    }

    public boolean haveWon(){
        boolean sol = true;
        for(int i = 0; i<brownCArray.length; i++){
            if(brownCArray[i].getFigureNumber() != 4){
                sol = false;
            }
        }
        for(int j = 0; j<blueCArray.length; j++){
            if(blueCArray[j].getFigureNumber() != 1){
                sol = false;
            }
        }
        return sol;
    }

}
