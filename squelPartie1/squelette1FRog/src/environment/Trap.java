package environment;

import java.awt.Color;
import java.awt.image.BufferedImage;

import util.Case;
import gameCommons.Game;
import graphicalElements.Element;

public class Trap {
    private Game game;
    private Case leftPosition;
    private boolean leftToRight;
    private int length;
    private final Color colorTrap = Color.RED;

    public Trap(Game game, Case leftPosition, boolean leftToRight){
        this.game = game;
        this.leftPosition = new Case(leftPosition.absc - this.length + 1, leftPosition.ord);
        this.leftToRight = leftToRight;
        this.length = 1;
    }

    private void addToGraphicsTrap() {

        Color color = colorTrap;

        game.getGraphic()
                .add(new Element(leftPosition.absc, leftPosition.ord, color, null));
    }

    public boolean aboveCase(Case c) {	// Vérifie si un objet se situe au meme emplacement d'un autre objet
        if(c.ord == this.leftPosition.ord) {
            if(c.absc >= this.leftPosition.absc && c.absc < this.leftPosition.absc + this.length) {
                return true;
            }
        }
        return false;
    }
    public void unDisplace(boolean b) { // déplace la voiture si l'entrée est true
        if(b) {
            if(this.leftToRight) {
                this.leftPosition = new Case(this.leftPosition.absc - 1, this.leftPosition.ord);
            }
            else {
                this.leftPosition = new Case(this.leftPosition.absc + 1, this.leftPosition.ord);
            }
        }
        this.addToGraphicsTrap();

    }
}
