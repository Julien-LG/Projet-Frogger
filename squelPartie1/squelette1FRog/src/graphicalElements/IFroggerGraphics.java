package graphicalElements;

import environment.LaneInf;
import gameCommons.IFrog;

import java.util.ArrayList;

public interface IFroggerGraphics {
	
	/**
	 * Ajoute l'élément aux éléments à afficher
	 * @param e
	 */
    public void add(Element e);

    /*public void addRoads(ArrayList<LaneInf> roads);*/
    
    /**
     * Enlève tous les éléments actuellement affichés
     */
    public void clear();
    
    /***
     * Actualise l'affichage
     */
    public void repaint();
    
    /**
     * Lie la grenouille à l'environneemnt graphique
     * @param frog
     */
    public void setFrog(IFrog frog);

    /**
     * Récupére les scores pour pouvoir y accéder dans FroggerGraphics et l'afficher
     * @param maxScore Le plus haut score actuel
     * @param bestScore Le meilleur score des précédente parties
     */
    public void getScore(int maxScore, int bestScore);

    /**
     * Récupére le timer pour pouvoir y accéder dans FroggerGraphics et l'afficher
     * @param timer La durée actuelle de la partie (en seconde)
     * @param gameTime Le temps max que doit duré une partie
     */
    public void getTimer(int timer, int gameTime);

    /**
     * Lance un écran de sélection du mode de jeu
     */
    public void menuScreen();

    /**
     * Affiche l'état actuel des scores de la partie
     */
    public void scoreScreen();

    //public  void testLable();

    /**
     * Lance un écran de fin de partie
     * @param message le texte à afficher
     */
    public void endGameScreen(String message);
}
