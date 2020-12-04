package graphicalElements;

import environment.LaneInf;
import gameCommons.IFrog;

import java.util.ArrayList;

public interface IFroggerGraphics {
	
	/**
	 * Ajoute l'�l�ment aux �l�ments � afficher
	 * @param e
	 */
    public void add(Element e);

    /*public void addRoads(ArrayList<LaneInf> roads);*/
    
    /**
     * Enl�ve tous les �l�ments actuellement affich�s
     */
    public void clear();
    
    /***
     * Actualise l'affichage
     */
    public void repaint();
    
    /**
     * Lie la grenouille � l'environneemnt graphique
     * @param frog
     */
    public void setFrog(IFrog frog);

    /**
     * R�cup�re les scores pour pouvoir y acc�der dans FroggerGraphics et l'afficher
     * @param maxScore Le plus haut score actuel
     * @param bestScore Le meilleur score des pr�c�dente parties
     */
    public void getScore(int maxScore, int bestScore);

    /**
     * R�cup�re le timer pour pouvoir y acc�der dans FroggerGraphics et l'afficher
     * @param timer La dur�e actuelle de la partie (en seconde)
     * @param gameTime Le temps max que doit dur� une partie
     */
    public void getTimer(int timer, int gameTime);

    /**
     * Lance un �cran de s�lection du mode de jeu
     */
    public void menuScreen();

    /**
     * Affiche l'�tat actuel des scores de la partie
     */
    public void scoreScreen();

    //public  void testLable();

    /**
     * Lance un �cran de fin de partie
     * @param message le texte � afficher
     */
    public void endGameScreen(String message);
}
