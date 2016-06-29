package com.teo_finds_games.miniJuegos.cambioRopa;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class CambioRopaActorClothes extends Actor {

    private ShapeRenderer shapeRenderer;
    private Texture textureGarment;
    private Rectangle rectangle;
    private int number; //This number sets the clothes' order, for example underwear would have larger number than jeans
    private boolean isTouched = false; //It is true when the garmnet is touched
    public static boolean garmentTouched = false; // It is true when any garmnet is touched, and false when it is dropped
    private boolean onTop = false; //It is true if the garment is above of the rest of clothes
    private int figureNumber; //figurenumber is the number of the figure where this garment is in each moment, 1 = man1, 2 = mannequin1, 3 = mannequin2, 4 = man2
    private CambioRopaActorFigure man1, mannequin1, mannequin2, man2;

    public CambioRopaActorClothes(Texture textureGarment, Integer number, CambioRopaActorFigure startMan, CambioRopaActorFigure mannequin1, CambioRopaActorFigure mannequin2, CambioRopaActorFigure finishMan){
        shapeRenderer = new ShapeRenderer();

        //startMan es el ActorFigure donde comienza esta prenda
        //finishMan es el ActorFigure donde queremos que acabe la prenda
        this.textureGarment = textureGarment;
        this.number = number;
        this.man1 = startMan;
        this.mannequin1 = mannequin1;
        this.mannequin2 = mannequin2;
        this.man2 = finishMan;

        figureNumber = startMan.getNumberFigure();

        setSize(textureGarment.getWidth()*CambioRopaActorFigure.CONVERSE_FACTOR, textureGarment.getHeight()*CambioRopaActorFigure.CONVERSE_FACTOR);

        setPosition(startMan.getX(), startMan.getY());
        rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());

        //Adding listener to our screen
        addListener((new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                //It runs when you click on the actor.
                isTouched = true;
                garmentTouched = true;
                onTop = onTop();
                if (onTop){ //If touched piece is on top then unstack
                    removeLastPiece(); //Delete the piece of its stack
                }
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //If the piece was on top of any post
                if (onTop){ wear();}
                isTouched = false;
                garmentTouched = false;
            }
        }));

    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureGarment, rectangle.x, rectangle.y, getWidth(), getHeight());
    }



    public boolean onTop(){
        return ( (!man1.stack.empty() && man1.stack.peek() == number) || ( (!mannequin1.stack.empty() && mannequin1.stack.peek() == number) || ( (!mannequin2.stack.empty() && mannequin2.stack.peek() == number) || (!man2.stack.empty() && man2.stack.peek() == number) ) ) );
    }

    public void removeLastPiece(){
        //Remove the piece of its stack
        switch (figureNumber){
            case 1: //It depends on who is start man
                if (man1.getNumberFigure() == 1) { //Red Clothes
                    man1.stack.pop();
                } else if(man1.getNumberFigure() == 4){
                    man2.stack.pop();
                }
                break;
            case 2: mannequin1.stack.pop();
                break;
            case 3: mannequin2.stack.pop();
                break;
            case 4: //It depends on who is start man
                if (man1.getNumberFigure() == 1){
                    man2.stack.pop();
                } else if (man1.getNumberFigure() == 4){
                    man1.stack.pop();
                }
                break;
        }
    }


    public void wear(){
        //If the garment overlaps man1's figure
        if (rectangle.overlaps(man1.rectangleFigure)){
            if(man1.stack.isEmpty() || man1.stack.peek()> number) { //If the stack is empty or there is a garment with a higher number (remember that you can put pieces in this order, 7,6,5,4,3,2,1)
                rectangle.setPosition(man1.getX(), man1.getY());
                setPosition(man1.getX(), man1.getY());
                man1.stack.push(number);

                //It depends on who is start man
                if(man1.getNumberFigure() == 1){
                    figureNumber = 1;
                } else if(man1.getNumberFigure() == 4){
                    figureNumber = 4;
                }

            } else {
                returnClothes();}


            //If the garment overlaps mannequin1's figure
        } else if (rectangle.overlaps(mannequin1.rectangleFigure)){
            if (mannequin1.stack.isEmpty() || mannequin1.stack.peek()> number){
                rectangle.setPosition(mannequin1.getX(), mannequin1.getY());
                setPosition(mannequin1.getX(), mannequin1.getY());
                mannequin1.stack.push(number);
                figureNumber = 2;
            } else {
                returnClothes();}


            //If the garment overlaps mannequin2's figure
        } else if (rectangle.overlaps(mannequin2.rectangleFigure)){
            if (mannequin2.stack.isEmpty() || mannequin2.stack.peek()> number) {
                rectangle.setPosition(mannequin2.getX(), mannequin2.getY());
                setPosition(mannequin2.getX(), mannequin2.getY());
                mannequin2.stack.push(number);
                figureNumber = 3;
            } else {
                returnClothes();}


            //If the garment overlaps man2's figure
        } else if (rectangle.overlaps(man2.rectangleFigure)){
            if(man2.stack.isEmpty() || man2.stack.peek()> number){
                rectangle.setPosition(man2.getX(), man2.getY());
                setPosition(man2.getX(), man2.getY());
                man2.stack.push(number);

                //It depends on who is start man
                if(man1.getNumberFigure() == 1){
                    figureNumber = 4;
                } else if(man1.getNumberFigure() == 4){
                    figureNumber = 1;
                }
            } else {
                returnClothes();}
            //If the garment overlaps nothing
        } else{ returnClothes();}
    }


    public void returnClothes(){
        // Set the garment in the last figure where it was
        switch(figureNumber){
            case 1: //It depends on who is start man
                if ( man1.getNumberFigure() == 1){
                    rectangle.setPosition(man1.getX(), man1.getY());
                    setPosition(man1.getX(), man1.getY());
                    man1.stack.push(number);
                } else if (man1.getNumberFigure() == 4){
                    rectangle.setPosition(man2.getX(), man2.getY());
                    setPosition(man2.getX(), man2.getY());
                    man2.stack.push(number);
                }

                break;
            case 2: rectangle.setPosition(mannequin1.getX(), mannequin1.getY());
                setPosition(mannequin1.getX(), mannequin1.getY());
                mannequin1.stack.push(number);
                break;
            case 3: rectangle.setPosition(mannequin2.getX(), mannequin2.getY());
                setPosition(mannequin2.getX(), mannequin2.getY());
                mannequin2.stack.push(number);
                break;
            case 4: //It depends on who is start man
                if (man1.getNumberFigure() == 1 ){
                    rectangle.setPosition(man2.getX(), man2.getY());
                    setPosition(man2.getX(), man2.getY());
                    man2.stack.push(number);
                } else if (man1.getNumberFigure() == 4){
                    rectangle.setPosition(man1.getX(), man1.getY());
                    setPosition(man1.getX(), man1.getY());
                    man1.stack.push(number);
                }
                break;
        }
    }

    public boolean getIsTouched() {
        return isTouched;
    }

    public boolean isOnTop() {
        return onTop;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getFigureNumber() {
        return figureNumber;
    }

    public int getNumber() {
        return number;
    }

}
