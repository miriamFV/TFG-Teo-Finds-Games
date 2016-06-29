package com.teo_finds_games.miniJuegos.torresHanoi;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.teo_finds_games.Application;

import java.util.Stack;

public class TorresHanoiActorPost extends Actor{
    public static int postWidth = 32, postHeight = 512;
    private int numberPost;
    private Vector2 postPosition;
    private boolean startingPost;
    public Stack<Integer> stack = new Stack<Integer>();
    private Texture texturePost;
    private Rectangle rectanglePost;

    public TorresHanoiActorPost(Texture texturePost,int numberPost){

        this.texturePost = texturePost;
        this.numberPost = numberPost;

        postPosition = new Vector2((Application.vpWidth/4)*numberPost - postWidth/2, TorresHanoiActorFloor.floorHeight);
        startingPost = (numberPost == 1);
        setPosition(postPosition.x, postPosition.y);
        setSize(postWidth,postHeight);
        rectanglePost = new Rectangle(getX(),getY(),postWidth,postHeight);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texturePost, rectanglePost.x, rectanglePost.y, postWidth, postHeight);
    }



    public Rectangle getRectanglePost() {
        return rectanglePost;
    }

    public void setRectanglePost(Rectangle rectanglePost) {
        this.rectanglePost = rectanglePost;
    }

    public int getPostWidth() {
        return postWidth;
    }

    public void setPostWidth(int postWidth) {
        this.postWidth = postWidth;
    }

    public int getPostHeight() {
        return postHeight;
    }

    public void setPostHeight(int postHeight) {
        this.postHeight = postHeight;
    }

    public int getNumberPost() {
        return numberPost;
    }

    public void setNumberPost(int numberPost) {
        this.numberPost = numberPost;
    }

    public Vector2 getPostPosition() {
        return postPosition;
    }

    public void setPostPosition(Vector2 postPosition) {
        this.postPosition = postPosition;
    }

    public boolean isStartingPost() {
        return startingPost;
    }

    public void setStartingPost(boolean startingPost) {
        this.startingPost = startingPost;
    }

}
