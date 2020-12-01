package frog;

import gameCommons.Game;
import gameCommons.IEnvironment;
import gameCommons.IFrog;
import graphicalElements.Element;
import util.Case;
import util.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Frog implements IFrog {
	
	private Game game;
	protected Case position;
	private Direction direction;
	private BufferedImage sprit = null;

	public Frog (Game game) {
		this.game = game;
		this.position = new Case(game.width/2, 0);
		this.direction = Direction.up;

		try {
			this.sprit = ImageIO.read(new File("frog.png"));
		} catch (IOException ioException) {
			System.out.println(ioException);
		}
	}

	@Override
	public Case getPosition() {
		return this.position;
	}

	@Override
	public Direction getDirection() {
		return this.direction;
	}

	public BufferedImage getSprit() {
		return this.sprit;
	}

	@Override
	public void move(Direction key) {
		this.direction = key;
		if (key == Direction.up){
			this.position = new Case(position.absc, position.ord + 1 );
		}
		else if (key == Direction.down){
			this.position = new Case(position.absc, position.ord - 1 );
		}
		else if (key == Direction.right){
			this.position = new Case(position.absc + 1, position.ord);
		}
		else if (key == Direction.left){
			this.position = new Case(position.absc - 1, position.ord);
		}
		this.game.getGraphic().add(new Element(this.getPosition(), Color.GREEN, this.sprit));
		this.game.testLose();
		this.game.testWin();
	}
}
