package gameCommons;

import util.Case;
import util.Direction;

import java.awt.image.BufferedImage;

public interface IFrog {
	
	/**
	 * Donne la position actuelle de la grenouille
	 * @return
	 */
	public Case getPosition();
	
	/**
	 * Donne la direction de la grenouille, c'est à dire de son dernier mouvement 
	 * @return
	 */
	public Direction getDirection();

	/**
	 * Donne l'image (sprite) de la grenouille
	 * @return
	 */
	public BufferedImage getSprite();

	/**
	 * Déplace la grenouille dans la direction donnée et teste la fin de partie
	 * @param key
	 */
	public void move(Direction key);

	/**
	 * Deplace la grenouille encore une fois ou le fait reculer
	 * @param key
	 */
	public void Glisse(Direction key); // Changement
	public void Bloque(Direction key); // Changement

	//public boolean goUp();

}
