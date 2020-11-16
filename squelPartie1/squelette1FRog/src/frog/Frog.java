package frog;

import gameCommons.Game;
import gameCommons.IEnvironment;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

public class Frog implements IFrog {
	
	private Game game;
	private Case position;
	private Direction direction;

	public Frog (Game game) {
		this.game = game;
		this.position = new Case(game.width/2, 0);
		this.direction = Direction.up;
	}

	@Override
	public Case getPosition() {
		return this.position;
	}

	@Override
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public void move(Direction key) {
		if (key == Direction.up){
			//if (isSafe)
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
		//this.direction = key;
	}
}
