package com.teo_finds_games.miniJuegos.matatopos;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.Random;

public class MatatoposMoleActor extends Actor {

    //Init position's mole array
    private Vector2[] molesPosition = {new Vector2(56* MatatoposPlayScreen.cFactor.x,-77*MatatoposPlayScreen.cFactor.y),
            new Vector2(256* MatatoposPlayScreen.cFactor.x,-77*MatatoposPlayScreen.cFactor.y),
            new Vector2(456* MatatoposPlayScreen.cFactor.x,-77*MatatoposPlayScreen.cFactor.y),
            new Vector2(656* MatatoposPlayScreen.cFactor.x,-77*MatatoposPlayScreen.cFactor.y),
            new Vector2(156* MatatoposPlayScreen.cFactor.x,-27*MatatoposPlayScreen.cFactor.y),
            new Vector2(356* MatatoposPlayScreen.cFactor.x,-27*MatatoposPlayScreen.cFactor.y),
            new Vector2(556* MatatoposPlayScreen.cFactor.x,-27*MatatoposPlayScreen.cFactor.y)};

    public Texture moleTexture;
    private int moleNumber;

    public boolean isAlive = true; //It gets false when we hit the mole

    private Rectangle rectangleMole;
    private TextureRegion moleRegion;
    public static int hitedMoles = 0;

    public MatatoposMoleActor(Texture texture,int moleNumber){
        this.moleNumber = moleNumber;
        moleTexture = texture;

        hitedMoles = 0;
        settingCharacteristics(); //Sets object's characteristics
        addingListener();
        addingActions();

    }

    @Override
    public void act(float delta){
        super.act(delta);

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(moleTexture, getX(), getY(), getWidth(), getHeight());
    }

    public void settingCharacteristics(){
        //setting position
        setPosition(molesPosition[moleNumber - 1].x, molesPosition[moleNumber - 1].y);

        // Mole's width and height
        int moleWidth = (int) (moleTexture.getWidth()*MatatoposPlayScreen.cFactor.x);
        int moleHeight = (int) (moleTexture.getHeight()*MatatoposPlayScreen.cFactor.y);
        //setting width and height according to the texture and conversion factor
        setWidth(moleWidth);
        setHeight(moleHeight);
        //setting bound of the actor
        setBounds(getX(), getY(), getWidth(), getHeight());
    }

    public void addingListener(){
        //Adding a listener to our actor
        addListener((new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)        {
                MatatoposPlayScreen.ntLabel.setText("Topos golpeados = "+hitedMoles);
                isAlive = false;
                hitedMoles++;
                Action delay = Actions.delay(hitedMoles * 0.5f);
                addAction(delay);
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //The mole is not touched
            }

        }));
    }

    public float randomFloat(){
        Random rnd = new Random();
        return rnd.nextFloat();
    }

    public void addingActions(){
        Action delay = Actions.delay(randomFloat()+hitedMoles*0.5f);
        Action up = Actions.moveBy(0,200,0.5f);
        Action down = Actions.moveBy(0,-200, 0.5f);
        SequenceAction seq = new SequenceAction(delay,up,down);
        RepeatAction forever = Actions.forever(seq);
        addAction(forever);
    }

}
