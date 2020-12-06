package gameCommons;

import environment.LaneInf;
import graphicalElements.Element;
import util.Case;

import java.util.ArrayList;

public interface IEnvironment {

	/**
	 * Teste si une case est sure, c'est � dire que la grenouille peut s'y poser
	 * sans mourir
	 * 
	 * @param c la case � tester
	 * @return vrai s'il n'y a pas danger
	 */
	public boolean isSafe(Case c);
	public boolean onTrap(Case c);
	public boolean onGlass(Case c);
	public boolean onWall(Case c);

	/**
	 * Teste si la case est une case d'arrivee
	 * 
	 * @param c
	 * @return vrai si la case est une case de victoire
	 */
	public boolean isWinningPosition(Case c);

	/**
	 * Effectue une �tape d'actualisation de l'environnement
	 */
	public void update();

	/**
	 * Ajoute une route tout en haut du tableau de route
	 */
	public void addRoad();
}
