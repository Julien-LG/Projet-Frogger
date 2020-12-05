package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import graphicalElements.Element;
import util.Case;
import util.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FrogInf implements IFrog {

    private Game game;
    private Case position;
    private Direction direction;
    private BufferedImage sprite = null;

    public FrogInf (Game game) {
        this.game = game;
        this.position = new Case(game.width/2, 1);
        this.direction = Direction.up;

        //Charge le sprite de la grnouille
        try {
            this.sprite = ImageIO.read(new File("images/frog.png"));
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
    }
/*
    public Case getLastPosition() {
        return this.lastPosition;
    }*/
    /*public boolean goUp() {
        if (lastPosition.ord - position.ord != 0) {
            return true;
        }
        return false;
    }*/

    /**
     * Getter Postion
     * @return la Position de la grenouille
     */
    @Override
    public Case getPosition() {
        return this.position;
    }

    /**
     * Getter Direction
     * @return la Direction de la grenouille
     */
    @Override
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Getter Sprite
     * @return le sprite de la grenouille
     */
    public BufferedImage getSprite() {
        return this.sprite;
    }

    /**
     * Modifie la position de la grenouille en fonction de la touche de clavier enfoncée
     * @param key la touche du clavier
     */
    @Override
    public void move(Direction key) {
        this.direction = key;
        if (key == Direction.up /*&& this.position.ord < game.height-1*/){
            this.position = new Case(position.absc, position.ord + 1 );
            game.score++;
            if (game.maxScore < game.score) {
                game.maxScore = game.score;
            }
            this.game.addRoad();
        }
        else if (key == Direction.down && this.position.ord > 0){
            this.position = new Case(position.absc, position.ord - 1 );
            game.score--;
        }
        else if (key == Direction.right && this.position.absc < game.width-1){
            this.position = new Case(position.absc + 1, position.ord);
        }
        else if (key == Direction.left && this.position.absc > 0){
            this.position = new Case(position.absc - 1, position.ord);
        }
        this.game.getGraphic().add(new Element(this.position.absc, 1, Color.GREEN, sprite));
        this.game.testLose();
        this.game.testWin();
    }

}
