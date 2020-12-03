package environment;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameCommons.IEnvironment;
import util.Case;
import gameCommons.Game;
//import environment.Environment;

public class Lane {
	private Game game;
	private int ord;
	private int speed;
	private ArrayList<Car> cars = new ArrayList<>(); //new ArrayList<Car>(); ??? c'est une proposition, le code de base c'est l'autre qui n'est pas en commentaire
	private boolean leftToRight;
	private double density;
	/*private boolean colorRoad;
	private BufferedImage sprite = null;*/
	private int tic = 0; //les tic de l'horloge

	// TODO : Constructeur(s)
	public Lane(Game game, int ord, double density) {
		this.game = game;
		this.ord = ord;
		this.speed = /*speed;*/ game.randomGen.nextInt(game.minSpeedInTimerLoops); //+1 ?
		this.leftToRight = game.randomGen.nextBoolean();
		this.density = density;
		for (int i = 0; i < 4 * game.width; i++) {
			this.displaceCars(true);
			this.mayAddCar();
		}
	}

	public Lane(Game game, int ord) {
		this(game, ord, game.defaultDensity);
	}

	private void deleteCars() {
		ArrayList<Car> deletedCars = new ArrayList<>();
		for (Car car: this.cars) {
			if (!car.inLimits()) {
				deletedCars.add(car);
			}
		}
		for (Car car: deletedCars) {
			this.cars.remove(car);
		}
	}

	private void displaceCars(boolean b) {
		for (Car car: this.cars) {
			car.displace(b);
		}
		this.deleteCars();
	}

	public void update() {
		// TODO
		this.tic++;
		if (this.tic <= this.speed) {
			this.displaceCars(false);
		}
		else {
			this.displaceCars(true);
			this.mayAddCar();
			this.tic = 0;
		}
		// Toutes les voitures se déplacent d'une case au bout d'un nombre "tic
		// d'horloge" égal à leur vitesse
		// Notez que cette méthode est appelée à chaque tic d'horloge

		// Les voitures doivent etre ajoutes a l interface graphique meme quand
		// elle ne bougent pas

		// A chaque tic d'horloge, une voiture peut être ajoutée

	}

	// TODO : ajout de methodes

	/*public ArrayList<Car> getCars() {
		return this.cars;
	}*/

	/*
	 * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase() 
	 */

	public boolean isSafe(Case c) {
		for (Car car: this.cars) {
			if (car.aboveCase(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Ajoute une voiture au début de la voie avec probabilité égale à la
	 * densité, si la première case de la voie est vide
	 */
	private void mayAddCar() {
		if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
			if (game.randomGen.nextDouble() < density) {
				cars.add(new Car(game, getBeforeFirstCase(), leftToRight));
			}
		}
	}

	private Case getFirstCase() {
		if (leftToRight) {
			return new Case(0, ord);
		} else
			return new Case(game.width - 1, ord);
	}

	private Case getBeforeFirstCase() {
		if (leftToRight) {
			return new Case(-1, ord);
		} else
			return new Case(game.width, ord);
	}

}
