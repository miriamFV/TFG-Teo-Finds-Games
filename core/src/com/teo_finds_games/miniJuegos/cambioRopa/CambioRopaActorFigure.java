package com.teo_finds_games.miniJuegos.cambioRopa;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.teo_finds_games.Application;

import java.util.Stack;

public class CambioRopaActorFigure extends Actor {

    public static final float CONVERSE_FACTOR = 0.9f;
    private Texture textureFigure;
    private int numberFigure;
    private Vector2 positionFigure;
    public Rectangle rectangleFigure;
    public Stack<Integer> stack = new Stack<Integer>();

    public CambioRopaActorFigure(Texture textureFigure, int numberFigure){
        this.textureFigure = textureFigure;
        this.numberFigure = numberFigure;

        //setOrigin(textureFigure.getWidth()/2, textureFigure.getHeight()/2);
        //System.out.println("Origin = ("+textureFigure.getWidth()/2+","+textureFigure.getHeight()/2+")");

        setSize(textureFigure.getWidth()*CONVERSE_FACTOR, textureFigure.getHeight()*CONVERSE_FACTOR);

        int distance = (Application.vpWidth - textureFigure.getWidth()*4)/5;
        positionFigure = new Vector2(distance + textureFigure.getWidth()*(numberFigure-1), getY()+60);
        System.out.println("positionFigura "+numberFigure+" (x,y) = ("+distance + textureFigure.getWidth()*(numberFigure-1)+","+ getY()+60+")");
        //positionFigure = new Vector2((Application.vpWidth/5)*numberFigure-getWidth()/2, getY()+60);
        //startingPost = (numberPost == 1);
        setPosition(positionFigure.x, positionFigure.y);
        System.out.println("Rectangle figura "+numberFigure+" = (getX(), getY(), getWidth(), getHeight()) = "+getX()+","+getY()+","+getWidth()+","+getHeight());
        rectangleFigure = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureFigure, rectangleFigure.x, rectangleFigure.y, getWidth(), getHeight());
    }

    public int getNumberFigure() {
        return numberFigure;
    }

}
