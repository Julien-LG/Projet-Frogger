package environment;

import java.awt.Color;

import util.Case;
import gameCommons.Game;
import graphicalElements.Element;

public class Car {
	private Game game;
	private Case leftPosition;
	private boolean leftToRight;
	private int length;
	private final Color colorLtR = Color.BLACK;;
	private final Color colorRtL = Color.BLUE;

	//TODO Constructeur(s)
	public Car(Game game, Case frontPosition, boolean leftToRight) {
		this.game = game;
		this.length = game.randomGen.nextInt(3) /*+ 1*/;
		this.leftPosition = new Case(frontPosition.absc - this.length, frontPosition.ord);
		this.leftToRight = leftToRight;
	}

	//TODO : ajout de methodes

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

	public boolean inLimits() {
		return this.leftPosition.absc + this.length > 0 || this.leftPosition.absc < this.game.width;
	}
	
	/* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
	private void addToGraphics() {
		for (int i = 0; i < length; i++) {
			Color color = colorRtL;
			if (this.leftToRight){
				color = colorLtR;
			}
			game.getGraphic().add(new Element(leftPosition.absc + i, leftPosition.ord, color));
			/*if (!game.infinityMode) {

				game.getGraphic().add(new Element(leftPosition.absc + i, leftPosition.ord - this.game.score, color));
			}
			else {
				game.getGraphic().add(new Element(leftPosition.absc + i, leftPosition.ord, color));
			}*/
			/*if (this.game.infinityMode == true) {
				game.getGraphic().add(new Element(leftPosition.absc + i, leftPosition.ord - this.game.score, color));
			}*/
			//marche pour le mode infini seulement
			//game.getGraphic().add(new Element(leftPosition.absc + i, leftPosition.ord - this.game.score, color));

		}
	}

	public boolean aboveCase(Case c) {
		//return c.ord == this.leftPosition.ord && (c.absc >= this.leftPosition.absc && c.absc < this.leftPosition.absc + this.length);

		if (c.ord == this.leftPosition.ord) {
			if (c.absc >= this.leftPosition.absc && c.absc < this.leftPosition.absc + this.length) {
				return true;
			}
		}
		return false;

	}

}
