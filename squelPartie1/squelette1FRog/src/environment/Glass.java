package environment;

import java.awt.Color;

import util.Case;
import gameCommons.Game;
import graphicalElements.Element;

public class Glass {
    private Game game;
    private Case leftPosition;
    private boolean leftToRight;
    private int length;
    private final Color colorGlass = Color.cyan;

    public Glass(Game game, Case leftPosition, boolean leftToRight){
        this.game = game;
        this.leftPosition = new Case(leftPosition.absc - this.length + 1, leftPosition.ord);
        this.length = 1;
    }

    private void addToGraphicsGlass() {

        Color color = colorGlass;

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
        this.addToGraphicsGlass();

    }

}
