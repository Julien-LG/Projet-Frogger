package environment;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import util.Case;
import gameCommons.Game;
import graphicalElements.Element;

import javax.imageio.ImageIO;

public class CarInf {
    private Game game;
    private Case leftPosition;
    private boolean leftToRight;
    private int length;
    private final Color colorLtR = Color.BLACK;;
    private final Color colorRtL = Color.BLUE;
    private BufferedImage sprite = null;

    //TODO Constructeur(s)
    public CarInf(Game game, Case frontPosition, boolean leftToRight) {
        this.game = game;
        this.length = game.randomGen.nextInt(3) /*+ 1*/;
        this.leftPosition = new Case(frontPosition.absc - this.length, frontPosition.ord);
        this.leftToRight = leftToRight;
        this.sprite = spriteGenerator();
    }

    //TODO : ajout de methodes

    /**
     * Renvoie un sprite de voiture de couleur aléatoire
     * @return Une image (sprite) pour la voiture
     */
    public BufferedImage spriteGenerator() {
        BufferedImage spriteGen = null;
        int random = game.randomGen.nextInt(3);
        String spriteName = "";
        if (leftToRight) {
            switch (random) {
                case 0:
                    spriteName = "images/leftCar.png";
                    break;
                case 1:
                    spriteName ="images/leftCar2.png";
                    break;
                case 2:
                    spriteName ="images/leftCar3.png";
                    break;
            }
        }
        else {
            switch (random) {
                case 0:
                    spriteName ="images/rightCar.png";
                    break;
                case 1:
                    spriteName ="images/rightCar2.png";
                    break;
                case 2:
                    spriteName ="images/rightCar3.png";
                    break;
            }
        }

        try {
            spriteGen = ImageIO.read(new File(spriteName));
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
        return spriteGen;
    }

    /**
     * Actualise l'affichage de la voiture
     * @param b true si la voiture c'est déplacée
     */
    public void displace(boolean b) {
        if (b) {
            if (this.leftToRight) {
                this.leftPosition = new Case(this.leftPosition.absc + 1, this.leftPosition.ord);
            }
            else {
                this.leftPosition = new Case(this.leftPosition.absc - 1, this.leftPosition.ord);
            }
        }
        this.addToGraphics();
    }

    /**
     * Indique si l'a voiture est dans les limites du tableau
     * @return true si la voiture est dans les limites
     */
    public boolean inLimits() {
        return this.leftPosition.absc + this.length > 0 || this.leftPosition.absc < this.game.width;
    }

    /**
     * Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture
     */
    private void addToGraphics() {
        for (int i = 0; i < length; i++) {
            Color color = colorRtL;
            if (this.leftToRight){
                color = colorLtR;
            }
            //marche pour le mode infini seulement
            game.getGraphic().add(new Element(leftPosition.absc + i, leftPosition.ord - this.game.score, color, sprite));
        }
    }

    /**
     * Indique si la voiture est sur la case
     * @param c la case que l'on veut vérifier
     * @return true si la voiture est sur la case
     */
    public boolean aboveCase(Case c) {
        if (c.ord == this.leftPosition.ord) {
            if (c.absc >= this.leftPosition.absc && c.absc < this.leftPosition.absc + this.length) {
                return true;
            }
        }
        return false;
    }

}
