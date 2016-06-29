package com.teo_finds_games.miniJuegos.tresEnRaya;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.teo_finds_games.Application;

import java.util.HashMap;

public class TresEnRayaPieceActor extends Actor {

    private Vector2 piecePosition;
    public static int pieceWidth, pieceHeight;
    private Texture pieceTexture;
    private Sound tapSound;

    //Link each matrix' position (matrix' indexes) with screen's position (pixels)
    private HashMap<Vector2, Vector2> positionsMap;

    public TresEnRayaPieceActor(Texture pieceTexture){
        tapSound = Gdx.audio.newSound( Gdx.files.internal("sounds/tresenraya/tap.wav"));

        this.pieceTexture = pieceTexture;
        pieceWidth = pieceTexture.getWidth();
        pieceHeight = pieceTexture.getHeight();

        positionsMap = new HashMap<Vector2,Vector2>();
        //Filling the Map
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                Vector2 posPiece = new Vector2( (TresEnRayaBoardActor.boardPosition.x + i*TresEnRayaBoardActor.squareSideLength) + (TresEnRayaBoardActor.squareSideLength - pieceWidth)/2 , (TresEnRayaBoardActor.boardPosition.y + j*TresEnRayaBoardActor.squareSideLength) + (TresEnRayaBoardActor.squareSideLength - pieceHeight)/2 );
                positionsMap.put(new Vector2(i, j), posPiece); // Adds an element to the map
            }
        }
        piecePosition = new Vector2 (Application.vpWidth, Application.vpHeight);
        setPosition(piecePosition.x, piecePosition.y);
        setSize(pieceWidth, pieceHeight);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(pieceTexture, getX(), getY(), pieceWidth, pieceHeight);
    }

    public void putPiece(Vector2 casilla){
        this.setPosition(positionsMap.get(casilla).x, positionsMap.get(casilla).y);
        tapSound.play(0.5f);
    }

}
