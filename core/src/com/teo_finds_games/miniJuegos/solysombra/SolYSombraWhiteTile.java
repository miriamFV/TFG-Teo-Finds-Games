package com.teo_finds_games.miniJuegos.solysombra;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.teo_finds_games.Application;


public class SolYSombraWhiteTile extends Actor {

    private Texture tileTexture;
    private int[] array;
    private int index;

    private Vector2 tileSize, tilePosition;

    public SolYSombraWhiteTile(Texture texture, int[] array, int index){
        tileTexture = texture;
        this.array = array;
        this.index = index;

        array[index] = -1;

        tileSize = new Vector2(tileTexture.getWidth(), tileTexture.getHeight());
        tilePosition = new Vector2( (((Application.vpWidth-(array.length* SolYSombraPlayScreen.tileWidth)-((array.length-1)*SolYSombraPlayScreen.space))/2)+(SolYSombraPlayScreen.tileWidth+SolYSombraPlayScreen.space)*index ) , (Application.vpHeight-SolYSombraPlayScreen.tileHeight)/2 );
        setSize(tileSize.x, tileSize.y);
        setPosition(tilePosition.x, tilePosition.y);

        //Adding listener to our actor
        addListener((new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                move();
                if (solutionFound()){
                    SolYSombraPlayScreen.solutionFound = true;
                }
                return false;
            }
        }));

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tileTexture, getX(), getY(), getWidth(), getHeight());
    }

    public void move(){
        if(index+1<array.length && array[index+1]==0){
            array[index+1] = -1;
            array[index] = 0;
            index = index+1;
            setPosition( ((Application.vpWidth-(array.length* SolYSombraPlayScreen.tileWidth)-((array.length-1)*SolYSombraPlayScreen.space))/2)+(SolYSombraPlayScreen.tileWidth+SolYSombraPlayScreen.space)*index , (Application.vpHeight-SolYSombraPlayScreen.tileHeight)/2 );
        } else if (index+2<array.length && array[index+2]==0){
            array[index+2] = -1;
            array[index] = 0;
            index = index+2;
            setPosition( ((Application.vpWidth-(array.length* SolYSombraPlayScreen.tileWidth)-((array.length-1)*SolYSombraPlayScreen.space))/2)+(SolYSombraPlayScreen.tileWidth+SolYSombraPlayScreen.space)*index , (Application.vpHeight-SolYSombraPlayScreen.tileHeight)/2 );
        }else{
            //You can not move
        }
    }

    public boolean solutionFound(){
        if ( (array[0] == array[1] && array[1] == array[2] && array[2] == 1) &&
                (array[3] == 0) &&
                (array[4] == array[5] && array[5] == array[6] && array[6] == -1)){
            return true;
        }else{
            return false;
        }
    }

}
