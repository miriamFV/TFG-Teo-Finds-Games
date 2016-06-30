package com.teo_finds_games.miniJuegos.solysombra;


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

public class SolYSombraPlayScreen implements Screen {

    private final Application app;

    private Image[] arrayImages = new Image[7];
    private int[] arrayTiles = new int[7];
    private Stage stage;

    private Texture tileTexture, blackTexture, whiteTexture;

    public static int tileWidth;
    public static int tileHeight;
    public static int space = 20; // 50 px between each tile

    private SolYSombraBlackTile b1, b2, b3;
    private SolYSombraWhiteTile w1, w2, w3;

    public static boolean solutionFound;
    private TextButton backButton;

    public SolYSombraPlayScreen(Application app){
        this.app = app;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(app.vpWidth,app.vpHeight)){
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    System.exit(0);
                }
                return super.keyDown(keyCode);
            }
        }; //Fitviewport keeps aspect ratio with black bars
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        initArray();
        solutionFound = false;
        initButtons();
        initTextures();
        setBoardImage();
        initActors();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.3f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(solutionFound){
            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    app.solYSombraCS.setNumCoins(8);
                    app.setScreen(app.solYSombraCS);
                }
            })));
        }

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
        tileTexture.dispose();
        blackTexture.dispose();
        whiteTexture.dispose();
        stage.dispose();
    }

    public void initArray(){
        for(int index=0; index<7; index++){
            arrayTiles[index] = 0;
        }
    }

    public void initButtons() {
        backButton = new TextButton("BACK", app.skin);
        backButton.setSize(150, 100);
        backButton.setPosition(50, app.vpHeight - (backButton.getHeight() + 50));
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });
        stage.addActor(backButton);
    }

    public void initTextures(){
        tileTexture = new Texture(Gdx.files.internal("images/solysombra/square.png"));
        tileWidth = tileTexture.getWidth();
        tileHeight = tileTexture.getHeight();
        blackTexture = new Texture(Gdx.files.internal("images/solysombra/black.png"));
        whiteTexture = new Texture(Gdx.files.internal("images/solysombra/white.png"));
    }

    public void setBoardImage(){
        //Setting background
        //You can put a background behind actors by adding the background as an Image (subclass of Actor) to the Stage and using the z-index to make sure it is drawn as a background.
        for(int i=0; i< arrayImages.length; i++){
            arrayImages[i] = new Image(tileTexture);
            arrayImages[i].setPosition( ((app.vpWidth-(arrayImages.length*tileWidth)-((arrayImages.length-1)*space))/2)+(tileWidth+space)*i , (app.vpHeight-tileHeight)/2  );
            stage.addActor(arrayImages[i]);
        }
    }

    public void initActors(){
        b1 = new SolYSombraBlackTile(blackTexture, arrayTiles, 4);
        b2 = new SolYSombraBlackTile(blackTexture, arrayTiles, 5);
        b3 = new SolYSombraBlackTile(blackTexture, arrayTiles, 6);

        w1 = new SolYSombraWhiteTile(whiteTexture, arrayTiles, 0);
        w2 = new SolYSombraWhiteTile(whiteTexture, arrayTiles, 1);
        w3 = new SolYSombraWhiteTile(whiteTexture, arrayTiles, 2);

        stage.addActor(b1);
        stage.addActor(b2);
        stage.addActor(b3);
        stage.addActor(w1);
        stage.addActor(w2);
        stage.addActor(w3);
    }


}
