package gameCommons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import environment.Environment;
import environment.EnvInf;
import frog.Frog;
import frog.FrogInf;
import givenEnvironment.GivenEnvironment;
import graphicalElements.FroggerGraphic;
import graphicalElements.IFroggerGraphics;


public class Main {

	public static void play() {
		boolean infinityMode = true;
		boolean timerMode = true;
		//Temps en secondes de la durée d'une partie (utilisé seulement si timerMode == true)
		int gameTime = 60;

		/*if (args.length != 0) {
			System.out.println(args[0]);
			infinityMode = true;
		}*/

		//Il n'y a pas de timerMode en dans le mode classique
		if (!infinityMode) {
			timerMode = false;
		}
		//Caractéristiques du jeu
		//boolean infinityMode = true;

		int width = 26;
		int height = 20;
		if (infinityMode) {
			height = 28;
		}
		int tempo = 100;
		int minSpeedInTimerLoops = 10;
		double defaultDensity = 0.2;


		//Création de l'interface graphique
		IFroggerGraphics graphic = new FroggerGraphic(width, height, false, infinityMode, timerMode);
		//Création de la partie
		Game game = new Game(graphic, width, height, minSpeedInTimerLoops, defaultDensity, infinityMode, timerMode, gameTime);
		//Création et liason de la grenouille
		IFrog frog;
		if (infinityMode) {
			frog = new FrogInf(game);
		}
		else {
			frog = new Frog(game);
		}
		game.setFrog(frog);
		graphic.setFrog(frog);
		//Création et liaison de l'environnement

		//IEnvironment env = new GivenEnvironment(game);
		IEnvironment env;
		if (infinityMode) {
			env = new EnvInf(game);
		}
		else {
			env = new Environment(game);
		}
		//IEnvironment env = new Environment(game);
		game.setEnvironment(env);
/*
		for (int i = 0; i < 40; ++i) {
			game.update();
		}*/
		//Timer gameTime = new Timer(1000);

		//Boucle principale : l'environnement s'actualise tous les tempo milisecondes
		Timer timer = new Timer(tempo, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.update();
				graphic.repaint();

			}
		});
		timer.setInitialDelay(0);
		timer.start();
	}

	public static void main(String[] args) {
		IFroggerGraphics graphic = new FroggerGraphic(15, 15,true, false,false);
		graphic.menuScreen();
	}
}
