package gameCommons;

import java.awt.Color;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import environment.EnvInf;
import graphicalElements.Element;
import graphicalElements.FroggerGraphic;
import graphicalElements.IFroggerGraphics;
import util.Case;
import tools.ManageFile;

public class Game {

	public final Random randomGen = new Random();

	// Caracteristique de la partie
	public final int width;
	public final int height;
	public final int minSpeedInTimerLoops;
	public final double defaultDensity;
	public int score;
	public int maxScore;
	public int bestScore;
	public boolean infinityMode;
	public boolean timerMode;
	public int gameTime;
	public int timer;
	public long debutTimer;
	public boolean gameEnd;

	private String bestScoreFileName = "BestScore.txt";

	// Lien aux objets utilisés
	private IEnvironment environment;
	private IFrog frog;
	private IFroggerGraphics graphic;

	/**
	 * Lance une partie
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
	 * @param timerMode
	 *            indique si le jeu est en mode contre la montre si true
	 * @param gameTime
 	 *            indique si la durée d'une partie en contre la montre
	 *
	 */
	public Game(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity, boolean infinityMode, boolean timerMode, int gameTime) {
		super();
		this.graphic = graphic;
		this.width = width;
		this.height = height;
		this.minSpeedInTimerLoops = minSpeedInTimerLoop;
		this.defaultDensity = defaultDensity;
		this.infinityMode = infinityMode;
		this.timerMode = timerMode;
		this.gameTime = gameTime;
		this.score = 0;
		this.maxScore = 0;
		this.bestScore = getBestScore();
		this.timer = 0;
		this.debutTimer = System.currentTimeMillis();
		this.gameEnd = false;
	}

	/**
	 * Récupére le meilleur score obtenu dans le fichier BestScore.txt
	 * @return le meilleur score précédemment obtenu
	 */
	private int getBestScore() {
		String result = "0";
		//Récupération des lignes du fichier "BestScore"
		ArrayList<String> linesList = ManageFile.extractFile(bestScoreFileName);

		//Si le fichier est vide ou s'il n'existe pas on en crée un
		if (linesList.size() == 0) {
			System.out.println("Reset tab");
			ArrayList<String> emptyLines = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				emptyLines.add("0");
			}
			ManageFile.createFile(bestScoreFileName, emptyLines);
			//linesList = emptyLines;
		}

		if (!infinityMode) {
			result = ManageFile.getLineFile(bestScoreFileName, 0);
		}
		else if (!timerMode){
			result = ManageFile.getLineFile(bestScoreFileName, 1);
		}
		else {
			result = ManageFile.getLineFile(bestScoreFileName, 3);
		}
		return Integer.parseInt(result);
	}

	/**
	 * Mets à jour le fichier BestScore.txt si le meilleur score à été battu
	 */
	public void updateBestScore() {
		//Vérification de si le meilleur score pour le mode classique est battu
		if (!infinityMode &&(this.maxScore < this.bestScore || (bestScore == 0 && this.maxScore > 0))) {
			ManageFile.rewriteFile(bestScoreFileName, 0, String.valueOf(maxScore));
		}	//Vérification pour le mode Infinity & Infinity Timer
		else if (infinityMode && this.maxScore > this.bestScore) {
			if (!timerMode) {
				ManageFile.rewriteFile(bestScoreFileName, 1, String.valueOf(maxScore));
			}
			else {
				ManageFile.rewriteFile(bestScoreFileName, 3, String.valueOf(maxScore));
			}
		}
	}

	/**
	 * Lie l'objet frog à la partie
	 * @param frog la grenouille
	 */
	public void setFrog(IFrog frog) {
		this.frog = frog;
	}

	/**
	 * Lie l'objet environment a la partie
	 * @param environment l'environnement
	 */
	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * Getter de graphic
	 * @return l'interface graphique
	 */
	public IFroggerGraphics getGraphic() {
		return graphic;
	}

	/**
	 * Teste si la partie est perdue et lance un écran de fin approprié si tel
	 * est le cas
	 * @return true si le partie est perdue
	 */
	public boolean testLose() {
		if (!environment.isSafe(frog.getPosition())) {
			//System.out.println("Perdu !");
			if (infinityMode) {
				//Mise à jour du score
				updateBestScore();
				if (maxScore > bestScore) {
					graphic.endGameScreen("New BEST SCORE ! :" + maxScore + "\n Temps: " + timer + "s");
				}
				else {
					graphic.endGameScreen("Score :" + maxScore + "\n Temps: " + timer + "s");
				}
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
	 * Teste si la partie est gagnée et lance un écran de fin approprié si tel
	 * est le cas
	 * @return true si la partie est gagnée
	 */
	public boolean testWin() {
		//Ne fonctionne qu'en mode classique (puisque il n'y a pas de winning postion accessible en mode infinity)
		if (environment.isWinningPosition(frog.getPosition())) {
			//System.out.println("GG !");
			maxScore = timer;
			//Mise à jour du score
			updateBestScore();
			if (maxScore < bestScore) {
				graphic.endGameScreen("New BEST SCORE ! \n Temps: " + timer + "s");
			}
			else {
				graphic.endGameScreen("GG ! \n Temps: " + timer + "s");
			}
			gameEnd = true;
			return true;
		}
		//Termine la partie si le mode contre la montre atteint 0
		if (infinityMode && timerMode) {
			if (timer >= gameTime) {
				//Mise à jour du score
				updateBestScore();
				if (maxScore > bestScore) {
					graphic.endGameScreen("New BEST SCORE ! :" + maxScore + "\n Temps: " + timer + "s");
				}
				else {
					graphic.endGameScreen("GG ! \n Score :" + maxScore + "\n Temps: " + timer + "s");
				}
				gameEnd = true;
				return true;
			}
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
	 * Ajoute une route en mode Infinity
	 */
	public void addRoad() {
		this.environment.addRoad();
	}

	/**
	 * Actualise l'environnement, affiche la grenouille et verifie la fin de
	 * partie.
	 */
	public void update() {

		graphic.clear();
		//Envoie le score dans FroggerGraphic pour permettre l'affichage en temps réel
		graphic.getScore(maxScore, bestScore);
		//Envoie le timer dans FroggerGraphic pour permettre l'affichage en temps réel pour le mode contre la montre
		if (timerMode || !infinityMode) {
			graphic.getTimer(timer, gameTime);
		}

		environment.update();
		// Si on joue en mode Infinity, la grenouille reste toujours en position 1 sur l'écran
		if (infinityMode) {
			this.graphic.add(new Element(frog.getPosition().absc, 1, Color.GREEN, frog.getSprite()));
		}
		else {
			this.graphic.add(new Element(frog.getPosition(), Color.GREEN, frog.getSprite()));
		}
		testLose();
		testWin();

		updateTimer();
	}
}
