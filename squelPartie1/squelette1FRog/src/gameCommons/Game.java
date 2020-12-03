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
		this.infinityMode = infinityMode;
		this.score = 0;
		this.maxScore = 0;
		this.bestScore = getBestScore();
		this.timer = 0;
		this.debutTimer = System.currentTimeMillis();
		this.gameEnd = false;
	}

	private int getBestScore() {
		/*String bestScoreClassique = "";
		String bestScoreInfinity = "";
		String bestScoreClassiqueTimer = "";
		String bestScoreInfinityTimer = "";

		try {
			//File f = new File("src/com/mkyong/data.txt");
			BufferedReader bRead = new BufferedReader(new FileReader("BestScore.txt"));
			String readLine = "";
			System.out.println("Reading file using Buffered Reader");

			int compteur = 0;
			while ((readLine = bRead.readLine()) != null) {
				switch(compteur) {
					case 0:
						bestScoreClassique = readLine;
						break;
					case 1:
						bestScoreInfinity = readLine;
						break;
					case 2:
						bestScoreClassiqueTimer = readLine;
						break;
					case 3:
						bestScoreInfinityTimer = readLine;
						break;
				}
				compteur++;
			}
			bRead.close();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				BufferedWriter bWrite = new BufferedWriter(new FileWriter("BestScore.txt"));
				bWrite.write("0\n0\n0\n0");
				bWrite.close();
			} catch (IOException exep) {
				exep.printStackTrace();
			}
		}

		if (infinityMode) {
			if (bestScoreInfinity != "") {
				return Integer.parseInt(bestScoreInfinity);
			}
			else {
				return 0;
			}
		}
		else {
			if (bestScoreClassique != "") {
				return Integer.parseInt(bestScoreClassique);
			}
			else {
				return 0;
			}
		}*/
		String result = "0";
		//Récupération des lignes du fichier "BestScore"
		ArrayList<String> linesList = ManageFile.extractFile("BestScore.txt");

		//Si le fichier est vide ou s'il n'existe pas on en crée un
		if (linesList.size() == 0) {
			System.out.println("Reser tab");
			ArrayList<String> emptyLines = new ArrayList<>();
			emptyLines.add("0");
			emptyLines.add("0");
			emptyLines.add("0");
			emptyLines.add("0");
			ManageFile.createFile("BestScore.txt", emptyLines);
			linesList = ManageFile.extractFile("BestScore.txt");
		}

		if (!infinityMode) {
			result = ManageFile.getLineFile("BestScore.txt", 0);
		}
		else {
			result = ManageFile.getLineFile("BestScore.txt", 1);
		}
		return Integer.parseInt(result);
	}

	/**
	 * Mets à jour le fichier BestScore si le meilleur score à été battu
	 */
	public void updateBestScore() {
		//Si le meilleur score est battu
		if (this.maxScore > this.bestScore) {
			//this.bestScore = this.maxScore;

			if (!infinityMode) {
				ManageFile.rewriteFile("BestScore.txt", 0, String.valueOf(/*bestScore*/ maxScore));
			}
			else {
				ManageFile.rewriteFile("BestScore.txt", 1, String.valueOf(/*bestScore*/ maxScore));
			}
		}
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
				updateBestScore();
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
		if (environment.isWinningPosition(frog.getPosition() /*new Case(frog.getPosition().absc, frog.getPosition().ord)*/)) {
			//System.out.println("GG !");
			updateBestScore();
			graphic.endGameScreen("GG ! \n Temps: " + timer + "s");
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
		graphic.getScore(maxScore, bestScore);
		//graphic.test();
		//graphic.scoreScreen(maxScore, bestScore);
		//graphic.testLable();

		/*graphic.addRoads(environment.getRoads());*/

		environment.update();
		if (infinityMode) {
			this.graphic.add(new Element(frog.getPosition().absc, 1, Color.GREEN, frog.getSprite()));
		}
		else {
			this.graphic.add(new Element(frog.getPosition(), Color.GREEN, frog.getSprite()));
		}


		testLose();
		testWin();


		//graphic.scoreScreen(maxScore, bestScore);

		updateTimer();
	}
	public void addRoad() {
		this.environment.addRoad();
	}
}
