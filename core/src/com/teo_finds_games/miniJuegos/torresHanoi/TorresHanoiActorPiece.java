package com.teo_finds_games.miniJuegos.torresHanoi;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.teo_finds_games.Application;

import java.util.HashMap;
import java.util.Map;

public class TorresHanoiActorPiece extends Actor {

    private int pieceWidth, pieceHeight;
    private int pieceNumber;
    private Texture texturePiece;

    private Rectangle rectanglePiece;
    private boolean isTouched = false;
    private boolean onTop = false; //onTop will be true if this piece is on top of the post

    private int postNumber = 1; //postnumber is the number of the post (1,2 or 3) where the piece is in each moment

    private TorresHanoiActorPost leftPost, middlePost, rightPost;
    private Map<Integer,TorresHanoiActorPost> postsMap;

    public TorresHanoiActorPiece(Texture texturePiece, Integer pieceNumber, final TorresHanoiActorPost leftPost, final TorresHanoiActorPost middlePost, final TorresHanoiActorPost rightPost){

        this.texturePiece = texturePiece;
        this.pieceNumber = pieceNumber;

        this.leftPost = leftPost;
        this.middlePost = middlePost;
        this.rightPost = rightPost;

        postsMap = new HashMap<Integer, TorresHanoiActorPost>();
        postsMap.put(1,leftPost);
        postsMap.put(2,middlePost);
        postsMap.put(3,rightPost);

        pieceWidth = TorresHanoiActorPost.postWidth + 30*pieceNumber;
        pieceHeight = texturePiece.getHeight();
        setPosition((Application.vpWidth/4)*1 - pieceWidth/2, TorresHanoiActorFloor.floorHeight + pieceHeight*(TorresHanoiPlayScreen.pieceArray.length - pieceNumber));
        setSize(pieceWidth,pieceHeight);
        //We need the retangle to overlaps
        rectanglePiece = new Rectangle(getX(),getY(),pieceWidth,pieceHeight);

        //Adding listener to our screen
        addListener((new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                //It runs when you click on the actor.
                isTouched = true;
                onTop = onTop();
                if (onTop){ //If touched piece is on top then unstack
                    removeLastPiece(); //Delete the piece of its stack
                }
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //If the piece was on top of any post
                if (onTop){
                    drop();
                }
                isTouched = false;
            }
        }));
    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texturePiece,rectanglePiece.x,rectanglePiece.y,pieceWidth,pieceHeight);
    }


    public boolean onTop(){
        //Returns true if the piece is on top of any post
        return (!leftPost.stack.empty() && leftPost.stack.peek() == getPieceNumber()) ||
                ( (!middlePost.stack.empty() && middlePost.stack.peek() == getPieceNumber()) ||
                        (!rightPost.stack.empty() && rightPost.stack.peek() == getPieceNumber()) );
    }


    public void removeLastPiece(){
        //Remove the piece of its stack
        switch (postNumber){
            case 1: leftPost.stack.pop();
                break;
            case 2: middlePost.stack.pop();
                break;
            case 3: rightPost.stack.pop();
                break;
        }
    }


    public void drop(){
        //If the piece overlaps left post
        if (rectanglePiece.overlaps(leftPost.getRectanglePost())){
            //Putting the piece on top of left post

            if (leftPost.stack.empty()){//If that post is empty
                rectanglePiece.setPosition(leftPost.getX() + leftPost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight);
                setPosition(leftPost.getX() + leftPost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight);
                //Add the piece to the post
                leftPost.stack.push(getPieceNumber());
                setPostNumber(1);

            }else if (leftPost.stack.peek()>getPieceNumber()){ //If the top of left post has another larger piece than our piece. We can put the piece.
                rectanglePiece.setPosition(leftPost.getX() + leftPost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight + getPieceHeight()*(leftPost.stack.size()));
                setPosition(leftPost.getX() + leftPost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight + getPieceHeight()*(leftPost.stack.size()));
                //Add the piece to the post
                leftPost.stack.push(getPieceNumber());
                setPostNumber(1);

            }else{ //If the top of left post has another smaller piece than our piece. We can not put the piece.
                //Back to its site
                pieceReturn();
            }

            //If the piece overlaps middle post
        }else if (rectanglePiece.overlaps(middlePost.getRectanglePost())){

            //Putting the piece on top of middle post
            if (middlePost.stack.empty()){ //If that post is empty
                rectanglePiece.setPosition(middlePost.getX() + middlePost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight);
                setPosition(middlePost.getX() + middlePost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight);
                //Add the piece to the post
                middlePost.stack.push(getPieceNumber());
                setPostNumber(2);

            }else if(middlePost.stack.peek() > getPieceNumber()){ //If the top of middle post has another larger piece than our piece. We can put the piece.
                rectanglePiece.setPosition(middlePost.getX() + middlePost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight + getPieceHeight()*(middlePost.stack.size()));
                setPosition(middlePost.getX() + middlePost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight + getPieceHeight()*(middlePost.stack.size()));
                //Add the piece to the post
                middlePost.stack.push(getPieceNumber());
                setPostNumber(2);

            } else { //If the top of middle post has another smaller piece than our piece. We can not put the piece.
                //Back to its site
                pieceReturn();
            }

            //If the piece overlaps right post
        }else if (rectanglePiece.overlaps(rightPost.getRectanglePost())){
            //Putting the piece on top of right post

            if (rightPost.stack.empty()){ //If that post is empty
                rectanglePiece.setPosition(rightPost.getX() + rightPost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight);
                setPosition(rightPost.getX() + rightPost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight);
                //Add the piece to the post
                rightPost.stack.push(getPieceNumber());
                setPostNumber(3);

            }else if (rightPost.stack.peek() > getPieceNumber()){ //If the top of right post has another larger piece than our piece. We can put the piece.
                rectanglePiece.setPosition(rightPost.getX() + rightPost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight + getPieceHeight()*(rightPost.stack.size()));
                setPosition(rightPost.getX() + rightPost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight + getPieceHeight()*(rightPost.stack.size()));
                //Add the piece to this stack
                rightPost.stack.push(getPieceNumber());
                setPostNumber(3);

            } else { //If the top of right post has another smaller piece than our piece. We can not put the piece.
                //Back to its site
                pieceReturn();
            }

            //If the piece overlaps nothing
        }else{
            //Back to its site
            pieceReturn();
        }
    }


    public void pieceReturn(){
        // Set the piece in the last post where it was
        switch (getPostNumber()) {
            case 1: //If it was in left post, back to it.
                rectanglePiece.setPosition(leftPost.getX() + leftPost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight + getPieceHeight()*(leftPost.stack.size()));
                setPosition(leftPost.getX() + leftPost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight + getPieceHeight()*(leftPost.stack.size()));
                leftPost.stack.push(getPieceNumber());
                break;
            case 2: //If it was in middle post, back to it.
                rectanglePiece.setPosition(middlePost.getX() + middlePost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight + getPieceHeight()*(middlePost.stack.size()));
                setPosition(middlePost.getX() + middlePost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight + getPieceHeight()*(middlePost.stack.size()));
                middlePost.stack.push(getPieceNumber());
                break;
            case 3: //If it was in right post, back to it.
                rectanglePiece.setPosition(rightPost.getX() + rightPost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight + getPieceHeight()*(rightPost.stack.size()));
                setPosition(rightPost.getX() + rightPost.getWidth() / 2 - getWidth() / 2, TorresHanoiActorFloor.floorHeight + getPieceHeight()*(rightPost.stack.size()));
                rightPost.stack.push(getPieceNumber());
                break;
            default:
        }
    }

    public Rectangle getRectanglePiece() {
        return rectanglePiece;
    }

    public void setRectanglePiece(Rectangle rectanglePiece) {
        this.rectanglePiece = rectanglePiece;
    }

    public int getPieceWidth() {
        return pieceWidth;
    }

    public void setPieceWidth(int pieceWidth) {
        this.pieceWidth = pieceWidth;
    }

    public int getPieceHeight() {
        return pieceHeight;
    }

    public void setPieceHeight(int pieceHeight) {
        this.pieceHeight = pieceHeight;
    }

    public int getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(int pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }


    public boolean getIsTouched() {
        return isTouched;
    }

    public void setIsTouched(boolean isTouched) {
        this.isTouched = isTouched;
    }

    public boolean isOnTop() {
        return onTop;
    }

    public void setOnTop(boolean onTop) {
        this.onTop = onTop;
    }

}
