package com.teo_finds_games.miniJuegos.tangram;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.teo_finds_games.Application;

public class TangramPlayScreen implements Screen {

    private final Application app;

    private Stage stage;
    public static final float SCALE = 1;
    public static Vector2 benchmark; //"benchmark" is the position of the frame where pieces are.

    public static final int NUMBER_OF_PIECES = 7;

    private TextureRegion bigTriangleGreyTR, middleTriangleGreyTR, littleTriangleGreyTR, squareGreyTR, trapezeGreyTR, bigTriangleYellowTR, bigTriangleGreenTR, middleTriangleOrangeTR, littleTriangleDarkBlueTR, littleTriangleBlueTR, squareRedTR, trapezePurpleTR;
    private static TangramDynamicPiece[] dynamicPieceArray = new TangramDynamicPiece[NUMBER_OF_PIECES];
    private static TangramStaticPiece[] staticPieceArray = new TangramStaticPiece[NUMBER_OF_PIECES];

    private TextButton menuButton, exitButton;

    private int numberOfPuzzle;
    private static boolean solutionFound = false;

    public TangramPlayScreen(Application app){
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
        benchmark = new Vector2(300,100); //"benchmark" is the position of the frame where pieces are.
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        solutionFound = false;

        initButtons();
        initTextureRegions();
        initActors();

    }

    public void update(float delta){
        if (solutionFound){
            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    app.tangramCS.setNumCoins(9);
                    app.setScreen(app.tangramCS);
                }
            })));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.8f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        app.batch.begin();
        app.batch.end();

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

    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
        bigTriangleGreyTR.getTexture().dispose();
        middleTriangleGreyTR.getTexture().dispose();
        littleTriangleGreyTR.getTexture().dispose();
        squareGreyTR.getTexture().dispose();
        trapezeGreyTR.getTexture().dispose();
        bigTriangleGreenTR.getTexture().dispose();
        bigTriangleYellowTR.getTexture().dispose();
        middleTriangleOrangeTR.getTexture().dispose();
        littleTriangleBlueTR.getTexture().dispose();
        littleTriangleDarkBlueTR.getTexture().dispose();
        squareRedTR.getTexture().dispose();
        trapezePurpleTR.getTexture().dispose();
    }

    public void initButtons(){
        menuButton = new TextButton("MENU", app.skin);
        menuButton.setSize(130,70);
        menuButton.setPosition(20, app.vpHeight - (menuButton.getHeight() + 20));
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.tangramMenuScreen);
            }
        });
        stage.addActor(menuButton);

        exitButton = new TextButton("SALIR", app.skin);
        exitButton.setSize(130, 70);
        exitButton.setPosition(app.vpWidth - (exitButton.getWidth() + 20), app.vpHeight - (exitButton.getHeight() + 20));
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
            }
        });
        stage.addActor(exitButton);

    }

    public void initTextureRegions(){
        //Textures of static pieces
        bigTriangleGreyTR = new TextureRegion(new Texture(Gdx.files.internal("images/tangram/bigTriangleGrey.png")));
        middleTriangleGreyTR = new TextureRegion(new Texture(Gdx.files.internal("images/tangram/middleTriangleGrey.png")));
        littleTriangleGreyTR = new TextureRegion(new Texture(Gdx.files.internal("images/tangram/littleTriangleGrey.png")));
        squareGreyTR = new TextureRegion(new Texture(Gdx.files.internal("images/tangram/squareGrey.png")));
        trapezeGreyTR = new TextureRegion(new Texture(Gdx.files.internal("images/tangram/TrapezeGrey.png")));
        //Textures of dynamic
        bigTriangleGreenTR = new TextureRegion(new Texture(Gdx.files.internal("images/tangram/bigTriangleGreen.png")));
        bigTriangleYellowTR = new TextureRegion(new Texture(Gdx.files.internal("images/tangram/bigTriangleYellow.png")));
        middleTriangleOrangeTR = new TextureRegion(new Texture(Gdx.files.internal("images/tangram/middleTriangleOrange.png")));
        littleTriangleBlueTR = new TextureRegion(new Texture(Gdx.files.internal("images/tangram/littleTriangleBlue.png")));
        littleTriangleDarkBlueTR = new TextureRegion(new Texture(Gdx.files.internal("images/tangram/littleTriangleDarkBlue.png")));
        squareRedTR = new TextureRegion(new Texture(Gdx.files.internal("images/tangram/squareRed.png")));
        trapezePurpleTR = new TextureRegion(new Texture(Gdx.files.internal("images/tangram/TrapezePurple.png")));
    }

    public void initActors(){
        if(numberOfPuzzle == 1){
            //CASA
            //StaticPiece(TextureRegion texture, int index, int numVertexes, Vector2 pos, float rotation)
            staticPieceArray[0] = new TangramStaticPiece(bigTriangleGreyTR, 0, 3, new Vector2(0,0), 0);
            stage.addActor(staticPieceArray[0]);

            staticPieceArray[1] = new TangramStaticPiece(bigTriangleGreyTR, 1, 3, new Vector2(180,183), 180);
            stage.addActor(staticPieceArray[1]);

            staticPieceArray[2] = new TangramStaticPiece(middleTriangleGreyTR, 2, 3, new Vector2(117,389), 225);
            stage.addActor(staticPieceArray[2]);

            staticPieceArray[3] = new TangramStaticPiece(littleTriangleGreyTR, 3, 3, new Vector2(452,272), 180);//dsg
            stage.addActor(staticPieceArray[3]);

            staticPieceArray[4] = new TangramStaticPiece(littleTriangleGreyTR, 4, 3, new Vector2(361,181), 0);
            stage.addActor(staticPieceArray[4]);

            staticPieceArray[5] = new TangramStaticPiece(squareGreyTR, 5, 4, new Vector2(361,0), 0);
            stage.addActor(staticPieceArray[5]);

            staticPieceArray[6] = new TangramStaticPiece(trapezeGreyTR, 6, 4, new Vector2(170,388), 135);
            stage.addActor(staticPieceArray[6]);

        }else if(numberOfPuzzle == 2){
            //ZAPATO
            //StaticPiece(TextureRegion texture, int index, int numVertexes, Vector2 pos, float rotation)
            staticPieceArray[0] = new TangramStaticPiece(bigTriangleGreyTR, 0, 3, new Vector2(0,181), 0);
            stage.addActor(staticPieceArray[0]);

            staticPieceArray[1] = new TangramStaticPiece(bigTriangleGreyTR, 1, 3, new Vector2(180,364), 180); //skjdbgf
            stage.addActor(staticPieceArray[1]);

            staticPieceArray[2] = new TangramStaticPiece(middleTriangleGreyTR, 2, 3, new Vector2(298,26), 45);
            stage.addActor(staticPieceArray[2]);

            staticPieceArray[3] = new TangramStaticPiece(littleTriangleGreyTR, 3, 3, new Vector2(181,0), 0);
            stage.addActor(staticPieceArray[3]);

            staticPieceArray[4] = new TangramStaticPiece(littleTriangleGreyTR, 4, 3, new Vector2(572,146), 225);
            stage.addActor(staticPieceArray[4]);

            staticPieceArray[5] = new TangramStaticPiece(squareGreyTR, 5, 4, new Vector2(0,0), 0);
            stage.addActor(staticPieceArray[5]);

            staticPieceArray[6] = new TangramStaticPiece(trapezeGreyTR, 6, 4, new Vector2(363,0), 0);
            stage.addActor(staticPieceArray[6]);
        }

        //Dynamic pieces
        //DynamicPiece(TextureRegion texture, int index, int numVertexes, Vector2 pos, float rotation)
        dynamicPieceArray[0] = new TangramDynamicPiece(bigTriangleYellowTR, 0, 3, new Vector2(50,0), 45);
        stage.addActor(dynamicPieceArray[0]);

        dynamicPieceArray[1] = new TangramDynamicPiece(bigTriangleGreenTR, 1, 3, new Vector2(200,470), 45);
        stage.addActor(dynamicPieceArray[1]);

        dynamicPieceArray[2] = new TangramDynamicPiece(middleTriangleOrangeTR, 2, 3, new Vector2(100,480), 0);
        stage.addActor(dynamicPieceArray[2]);

        dynamicPieceArray[3] = new TangramDynamicPiece(littleTriangleBlueTR, 3, 3, new Vector2(500,250), 45);
        stage.addActor(dynamicPieceArray[3]);

        dynamicPieceArray[4] = new TangramDynamicPiece(littleTriangleDarkBlueTR, 4, 3, new Vector2(50,320), 135);
        stage.addActor(dynamicPieceArray[4]);

        dynamicPieceArray[5] = new TangramDynamicPiece(squareRedTR, 5, 4, new Vector2(650,145), 0);
        stage.addActor(dynamicPieceArray[5]);

        dynamicPieceArray[6] = new TangramDynamicPiece(trapezePurpleTR, 6, 4, new Vector2(700,340), 90);
        stage.addActor(dynamicPieceArray[6]);

    }

    public static boolean areClose(int index){
        int allowedDistance = 50;
        return ((staticPieceArray[index].getRotation() == dynamicPieceArray[index].getRotation()) &&
                (staticPieceArray[index].getPosition().dst(dynamicPieceArray[index].getPosition()) < allowedDistance)) ;
    }

    public static void joinPieces(int index){
        dynamicPieceArray[index].setPosition(staticPieceArray[index].getPosition());
        if(hasWon()){
            solutionFound = true;
        }
    }

    private static boolean hasWon(){
        boolean hasWon = true;
        for(int index = 0; index<NUMBER_OF_PIECES; index++){
            if( ! staticPieceArray[index].getPosition().epsilonEquals(dynamicPieceArray[index].getPosition(), 0.05f)){
                hasWon = false;
            }
        }
        return hasWon;
    }

    public int getNumberOfPuzzle() {
        return numberOfPuzzle;
    }

    public void setNumberOfPuzzle(int numberOfPuzzle) {
        this.numberOfPuzzle = numberOfPuzzle;
    }

}
