package environment;

import java.util.ArrayList;

import util.Case;
import gameCommons.Game;

public class LaneInf {
    private Game game;
    private int ord;
    private int speed;
    private ArrayList<CarInf> cars = new ArrayList<>();
    private boolean leftToRight;
    private double density;
    private int tic = 0; //les tic de l'horloge

    // TODO : Constructeur(s)
    public LaneInf(Game game, int ord, double density/*, boolean colorRoad*/) {
        this.game = game;
        this.ord = ord;
        this.speed = game.randomGen.nextInt(game.minSpeedInTimerLoops); //+1 ?
        this.leftToRight = game.randomGen.nextBoolean();
        this.density = density;

        //Permet d'avoir des voitures au milieu de l'�cran au d�but de la partie
        for (int i = 0; i < 2 * game.width; i++) {
            this.displaceCars(true);
            this.mayAddCar();
        }
    }

    //Constructeur avec la densit� par default
    public LaneInf(Game game, int ord/*, boolean colorRoad*/) {
        this(game, ord, game.defaultDensity/*, colorRoad*/);
    }
/*
    /**
     * Getter de l'ordonn�e
     * @return l'ordonn�
     */
    /*public int getOrd() {
        return ord;
    }*/

    /**
     * Supprime les voitures en dehors de la fenetre
     */
    private void deleteCars() {
        ArrayList<CarInf> deletedCars = new ArrayList<>();
        for (CarInf car: this.cars) {
            if (!car.inLimits()) {
                deletedCars.add(car);
            }
        }
        for (CarInf car: deletedCars) {
            this.cars.remove(car);
        }
    }

    /**
     * Actualise l'emplacement des voitures et supprime celle qui ne sont plus visibles
     * @param b
     */
    private void displaceCars(boolean b) {
        for (CarInf car: this.cars) {
            car.displace(b);
        }
        this.deleteCars();
    }

    /**
     * Mets � jour la Lane en fonction de la vitesse des voitures
     */
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
        // Toutes les voitures se d�placent d'une case au bout d'un nombre "tic
        // d'horloge" �gal � leur vitesse
        // Notez que cette m�thode est appel�e � chaque tic d'horloge

        // Les voitures doivent etre ajoutes a l interface graphique meme quand
        // elle ne bougent pas

        // A chaque tic d'horloge, une voiture peut �tre ajout�e

    }

    // TODO : ajout de methodes

	/*public ArrayList<Car> getCars() {
		return this.cars;
	}*/

    /*
     * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase()
     */

    /**
     * Indique si la case est libre
     * @param c une Case
     * @return true si la case est libre
     */
    public boolean isSafe(Case c) {
        for (CarInf car: this.cars) {
            if (car.aboveCase(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
     * densit�, si la premi�re case de la voie est vide
     */
    private void mayAddCar() {
        if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
            if (game.randomGen.nextDouble() < density) {
                cars.add(new CarInf(game, getBeforeFirstCase(), leftToRight));
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
