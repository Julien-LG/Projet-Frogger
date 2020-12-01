package gameCommons;

import java.awt.Color;
import java.util.Random;

import graphicalElements.Element;
import graphicalElements.FroggerGraphic;
import graphicalElements.IFroggerGraphics;
import util.Case;

public class Game {

	public final Random randomGen = new Random();

	// Caracteristique de la partie
	public final int width;
	public final int height;
	public final int minSpeedInTimerLoops;
	public final double defaultDensity;
	public int score;
	public int maxScore;
	public boolean infinityMode;
	public int timer;
	public long debutTimer;
	public boolean gameEnd;

	// Lien aux objets utilisés
	private IEnvironment environment;
	private IFrog frog;
	private IFroggerGraphics graphic;

	/**
	 * 
	 * @param graphic
	 *            l'interface graphique
	 * @param width
	 *            largeur en cases
	 * @param height
	 *            hauteur en cases
	 * @param minSpeedInTimerLoop
	 *            Vitesse minimale, en nombre de tour de timer avant déplacement
	 * @param defaultDensity
	 *            densite de voiture utilisee par defaut pour les routes
	 * @param infinityMode
	 *            indique si le jeu est en mode infi si true
	 */
	public Game(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity, boolean infinityMode) {
		super();
		this.graphic = graphic;
		this.width = width;
		this.height = height;
		this.minSpeedInTimerLoops = minSpeedInTimerLoop;
		this.defaultDensity = defaultDensity;
		this.score = 0;
		this.maxScore = 0;
		this.infinityMode = infinityMode;
		this.timer = 0;
		this.debutTimer = System.currentTimeMillis();
		this.gameEnd = false;
	}

	/**
	 * Lie l'objet frog à la partie
	 * 
	 * @param frog
	 */
	public void setFrog(IFrog frog) {
		this.frog = frog;
	}

	/**
	 * Lie l'objet environment a la partie
	 * 
	 * @param environment
	 */
	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * 
	 * @return l'interface graphique
	 */
	public IFroggerGraphics getGraphic() {
		return graphic;
	}

	/**
	 * Teste si la partie est perdue et lance un écran de fin approprié si tel
	 * est le cas
	 * 
	 * @return true si le partie est perdue
	 */
	public boolean testLose() {
		//if (frog.getPosition().ord  == or frog.getPosition().absc == ){
		if (!environment.isSafe(new Case(frog.getPosition().absc, frog.getPosition().ord))) {
			//System.out.println("Perdu !");
			if (infinityMode) {
				graphic.endGameScreen("Perdu ! \n Score :" + maxScore + "\n Temps: " + timer + "s");
			}
			else {
				graphic.endGameScreen("Perdu ! \n Temps: " + timer + "s");
			}
			gameEnd = true;
			return true;
		}
		return false;
	}

	/**
	 * Teste si la partie est gagnee et lance un écran de fin approprié si tel
	 * est le cas
	 * 
	 * @return true si la partie est gagnée
	 */
	public boolean testWin() {
		if (environment.isWinningPosition(new Case(frog.getPosition().absc, frog.getPosition().ord))) {
			//System.out.println("GG !");
			graphic.endGameScreen("GG ! \n Temps: " + timer);
			gameEnd = true;
			return true;
		}
		return false;
	}

	/**
	 * Actualise le timer si la partie n'est pas finie
	 */
	public void updateTimer() {
		if (!gameEnd) {
			if (this.timer < (System.currentTimeMillis()-debutTimer)/1000) {
				this.timer = (int) ((System.currentTimeMillis()-debutTimer) / 1000);
			}
		}
	}

	/**
	 * Actualise l'environnement, affiche la grenouille et verifie la fin de
	 * partie.
	 */
	public void update() {
		graphic.clear();
		environment.update();
		if (infinityMode) {
			this.graphic.add(new Element(frog.getPosition().absc, 1, Color.GREEN));
		}
		else {
			this.graphic.add(new Element(frog.getPosition(), Color.GREEN));
		}
		testLose();
		testWin();
		updateTimer();
	}
	public void addRoad() {
		this.environment.addRoad();
	}
}
