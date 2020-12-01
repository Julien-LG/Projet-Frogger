package environment;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import util.Case;
import gameCommons.Game;
import graphicalElements.Element;

import javax.imageio.ImageIO;

public class Car {
	private Game game;
	private Case leftPosition;
	private boolean leftToRight;
	private int length;
	private final Color colorLtR = Color.BLACK;;
	private final Color colorRtL = Color.BLUE;
	private BufferedImage sprit = null;

	/*private BufferedImage frogSprit = null;
	private BufferedImage rightCarSprit = null;
	private BufferedImage leftCarSprit = null;
	private BufferedImage rightCar2Sprit = null;
	private BufferedImage leftCar2Sprit = null;
	private BufferedImage rightCar3Sprit = null;
	private BufferedImage leftCar3Sprit = null;*/

	//TODO Constructeur(s)
	public Car(Game game, Case frontPosition, boolean leftToRight) {
		this.game = game;
		this.length = game.randomGen.nextInt(3) /*+ 1*/;
		this.leftPosition = new Case(frontPosition.absc - this.length, frontPosition.ord);
		this.leftToRight = leftToRight;
		this.sprit = spritGenerator();
	}

	//TODO : ajout de methodes

	public BufferedImage spritGenerator() {
		BufferedImage sprit = null;
		int random = game.randomGen.nextInt(3);
		/*if (leftToRight) {
			switch (random) {
				case 0:
					return ImageIO.read(new File("leftCar.png"));
				case 1:
					return ImageIO.read(new File("leftCar2.png"));
				case 2:
					return ImageIO.read(new File("leftCar3.png"));
			}
		}
		else {
			switch (random) {
				case 0:
					return ImageIO.read(new File("rightCar.png"));
				case 1:
					return ImageIO.read(new File("rightCar2.png"));
				case 2:
					return ImageIO.read(new File("rightCar3.png"));
			}
		}*/
		String spritName = "";
		if (leftToRight) {
			switch (random) {
				case 0:
					spritName = "leftCar.png";
					break;
				case 1:
					spritName ="leftCar2.png";
					break;
				case 2:
					spritName ="leftCar3.png";
					break;
			}
		}
		else {
			switch (random) {
				case 0:
					spritName ="rightCar.png";
					break;
				case 1:
					spritName ="rightCar2.png";
					break;
				case 2:
					spritName ="rightCar3.png";
					break;
			}
		}

		try {
			sprit = ImageIO.read(new File(spritName));
		} catch (IOException ioException) {
			System.out.println(ioException);
		}
		return sprit;
	}

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
			game.getGraphic().add(new Element(leftPosition.absc + i, leftPosition.ord, color, sprit));
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
