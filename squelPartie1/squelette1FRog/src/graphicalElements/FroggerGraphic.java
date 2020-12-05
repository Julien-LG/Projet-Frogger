package graphicalElements;

import javax.imageio.ImageIO;
import javax.swing.*;

import gameCommons.IFrog;
import gameCommons.Main;
import tools.ManageFile;
import util.Direction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FroggerGraphic extends JPanel implements IFroggerGraphics, KeyListener {
	private ArrayList<Element> elementsToDisplay;
	private int pixelByCase = 32;
	private int width;
	private int height;
	private IFrog frog;
	private JFrame frame;
	private boolean menu;
	private boolean infinity;
	private boolean timerMode;
	private int classiqueScore;
	private int timeLeft = 60;

	private String bestScoreFileName = "BestScore.txt";
	private int score = 0;
	private int bestScore = 0;

	private String optionsFileName = "Options.txt";
	private boolean minimalistMode;

	BufferedImage colorLane = null;
	BufferedImage colorRoad = null;

	JTextField field = new JTextField();
	JTextField timeField = new JTextField();

	public FroggerGraphic(int width, int height, boolean menu, boolean infinity, boolean timerMode) {
		this.width = width;
		this.height = height;
		elementsToDisplay = new ArrayList<Element>();
		this.menu = menu;
		this.infinity = infinity;
		this.timerMode = timerMode;

		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));

		JFrame frame = new JFrame("Frogger");
		this.frame = frame;
		//Récupération de l'information du mode grahique a utilisé
		if (ManageFile.getLineFile("Options.txt", 0).equals("true")) {
			this.minimalistMode = true;
		}
		else {
			this.minimalistMode = false;
		}
		System.out.println(minimalistMode);

		//Permet l'affichage d'une icône pour la fnêtre
		ImageIcon logo = new ImageIcon("images/frog.png");
		frame.setIconImage(logo.getImage());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		frame.addKeyListener(this);

		//Récupère les différents sprites de route
		if (!minimalistMode) {
			try {
				colorLane = ImageIO.read(new File("images/colorLane.png"));
				colorRoad = ImageIO.read(new File("images/colorRoad.png"));
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d2 = (Graphics2D) g;

		//Vérifie si la fenetre n'est pas le menu
		if (!menu) {
			if (!minimalistMode) {
				if (this.infinity) {
					//Affiche uniquement des routes (pour le mode infinity)
					for (int i = 0; i < height; i++) {
						g2d2.drawImage(colorRoad, 0, i*pixelByCase,26*pixelByCase,pixelByCase,null);
					}
				}
				else {
					//Affiche des routes avec des Lanes de couleurs pour le départ et l'arrivée
					g2d2.drawImage(colorLane, 0, 0,26*pixelByCase,pixelByCase,null);
					for (int i = 1; i < height-1; i++) {
						g2d2.drawImage(colorRoad, 0, i*pixelByCase,26*pixelByCase,pixelByCase,null);
					}
					g2d2.drawImage(colorLane, 0, (height-1)*pixelByCase,26*pixelByCase,pixelByCase,null);
				}
			}

			for (Element e : elementsToDisplay) {
				//Si nous sommes en minimalistMode les éléments sont des carrés de couleurs
				if (minimalistMode) {
					g.setColor(e.color);
					g.fillRect(pixelByCase * e.absc, pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase - 1);
				} //Sinon on affiches les sprites des éléments
				else {
					Graphics2D g2d = (Graphics2D) g;
					g2d.drawImage(e.sprit, pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase, null);
				}
			}
			//Affiche le score en temps réel
			this.scoreScreen();
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			frog.move(Direction.up);
			break;
		case KeyEvent.VK_DOWN:
			frog.move(Direction.down);
			break;
		case KeyEvent.VK_LEFT:
			frog.move(Direction.left);
			break;
		case KeyEvent.VK_RIGHT:
			frog.move(Direction.right);
		}
	}

	public void clear() {
		this.elementsToDisplay.clear();
	}

	public void add(Element e) {
		this.elementsToDisplay.add(e);
	}

	/**
	 * Setter pour la grenouille
	 * @param frog la grenouille
	 */
	public void setFrog(IFrog frog) {
		this.frog = frog;
	}

	/**
	 * Récupère les scores
	 * @param maxScore Le plus haut score actuel
	 * @param bestScore Le meilleur score des précédente parties
	 */
	public void getScore(int maxScore, int bestScore) {
		this.score = maxScore;
		this.bestScore = bestScore;
	}

	/**
	 * Récupère le timer et la durée de la partie
	 * @param timer La durée actuelle de la partie (en seconde)
	 * @param gameTime Le temps max que doit duré une partie
	 */
	public void getTimer(int timer, int gameTime) {
		//Donne le temps restant avant la fin de la partie
		if (timerMode) {
			if (timeLeft >= 0) {
				this.timeLeft = gameTime - timer;
			}
		} //Utilise le timer comme score pour le mode Classique
		else {
			this.classiqueScore = timer;
		}
	}

	/**
	 * Affiche le menu permettant de lancer le mode de jeu de son choix
	 */
	public void menuScreen() {
		frame.remove(this);

		//Affichage logo frogger
		ImageIcon iconFrogger = new ImageIcon("images/frogger.png");
		JLabel labelIconFrogger = new JLabel();
		labelIconFrogger.setIcon(new ImageIcon(iconFrogger.getImage().getScaledInstance(iconFrogger.getIconWidth()/3,iconFrogger.getIconHeight()/3, Image.SCALE_SMOOTH)));
		labelIconFrogger.setFont(new Font("Verdana", Font.BOLD, 20));
		labelIconFrogger.setHorizontalAlignment(SwingConstants.CENTER);
		labelIconFrogger.setVerticalAlignment(SwingConstants.TOP);
		labelIconFrogger.setSize(this.getSize());
		frame.getContentPane().add(labelIconFrogger);

		//Affichage logo frog
		ImageIcon iconFrog = new ImageIcon("images/frog.png");
		JLabel labelIconFrog = new JLabel();
		labelIconFrog.setIcon(new ImageIcon(iconFrog.getImage().getScaledInstance(iconFrogger.getIconWidth()/20,iconFrogger.getIconHeight()/10, Image.SCALE_SMOOTH)));
		labelIconFrog.setFont(new Font("Verdana", Font.BOLD, 20));
		labelIconFrog.setHorizontalAlignment(SwingConstants.CENTER);
		labelIconFrog.setVerticalAlignment(SwingConstants.BOTTOM);
		labelIconFrog.setSize(this.getSize());
		frame.getContentPane().add(labelIconFrog);

		//Bouton pour jouer en Mode Classique
		JButton buttonClassic = new JButton("Play classic mode");
		buttonClassic.setBounds((width*pixelByCase)/3,(height*pixelByCase)/2,180,40);
		buttonClassic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Mode classique
				Main.play(false,false);
			}
		});
		frame.add(buttonClassic);

		//Bouton pour jouer en Mode Infinity
		JButton buttonInfinity = new JButton("Play infinity mode");
		buttonInfinity.setBounds((width*pixelByCase)/3,(height*pixelByCase)/2+44,180,40);
		buttonInfinity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Mode Infinity
				Main.play(true,false);
			}
		});
		frame.add(buttonInfinity);

		//Bouton pour jouer en Mode Infinity Timer
		JButton buttonInfinityTimer = new JButton("Play infinity timer mode");
		buttonInfinityTimer.setBounds((width*pixelByCase)/3,(height*pixelByCase)/2+88,180,40);
		buttonInfinityTimer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Mode Infinity Timer
				Main.play(true,true);
			}
		});
		frame.add(buttonInfinityTimer);

		//Bouton pour lancer les options
		JButton buttonOptions = new JButton("");
		ImageIcon gear = new ImageIcon("images/engrenage.png");
		buttonOptions.setIcon(new ImageIcon(gear.getImage().getScaledInstance(gear.getIconWidth()/10,gear.getIconHeight()/10, Image.SCALE_SMOOTH)));
		buttonOptions.setBounds((width*pixelByCase)-40,(height*pixelByCase)-40,40,40);
		buttonOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Options");
				optionsScreen();
			}
		});
		frame.add(buttonOptions);
		frame.repaint();
	}

	/**
	 * Affiche la fenêtre des options
	 */
	public void optionsScreen() {
		setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));
		setBackground(UIManager.getColor ( "Panel.background" ));
		JFrame frame2 = new JFrame("Options");
		frame2.getContentPane().add(this);
		frame2.pack();
		frame2.setVisible(true);
		frame2.addKeyListener(this);
		frame2.setLayout(new GridBagLayout());

		//Bouton de suppression des scores
		JButton buttonDeleteScores = new JButton("Delete Bests scores");
		buttonDeleteScores.setBounds(0,0,150,40);
		buttonDeleteScores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Mode classique
				System.out.println("Suppresion des meilleurs scores");
				int n = JOptionPane.showConfirmDialog(frame2, "Are you sure you want deleted? (final action)", "Removal bestscores", JOptionPane.YES_NO_OPTION);
				if (n == 0) {
					System.out.println("oui");
					ArrayList<String> emptyLines = new ArrayList<>();
					emptyLines.add("0");
					emptyLines.add("0");
					emptyLines.add("0");
					emptyLines.add("0");
					ManageFile.createFile(bestScoreFileName, emptyLines);
				}
				else {
					System.out.println("non");
				}
			}
		});
		frame2.add(buttonDeleteScores);
		buttonDeleteScores.repaint();

		//Bouton pour interchanger le type d'affichage (Minimalist : carré de couleurs, Normal : Sprites)
		JButton buttonSwitchGraphics = new JButton("Switch Minimalist/Normal graphics");
		buttonSwitchGraphics.setBounds(0,0,150,40);
		buttonSwitchGraphics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ManageFile.getLineFile(optionsFileName, 0).equals("true")) {
					ManageFile.rewriteFile(optionsFileName, 0, "false");
				}
				else {
					ManageFile.rewriteFile(optionsFileName, 0, "true");
				}
			}
		});
		frame2.add(buttonSwitchGraphics);
		buttonSwitchGraphics.repaint();
	}

	/**
	 * Affiche l'écran des scores en temps réel
	 * Et également l'écran du timer si le mode contre la montre est activé
	 */
	public void scoreScreen() {
		field.setBounds(0,0,240,32);
		field.setEditable(false);
		field.setFocusable(false);
		field.setFont(new Font("Verdana", 1, 13));
		if (infinity) {
			field.setText("Score : " + this.score + "\t Best Score : " + this.bestScore);
		}
		else {
			field.setText("Score : " + this.classiqueScore + "s" + "\t Best Score : " + this.bestScore+ "s");
		}

		field.setForeground(Color.GREEN);
		field.setBackground(Color.GRAY);
		field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		frame.getContentPane().add(field);
		field.repaint();

		if (timerMode) {
			timeField.setBounds(width*pixelByCase-100,0,100,32);
			timeField.setFont(new Font("Verdana", Font.BOLD, 13));
			timeField.setText("Time left :" + this.timeLeft);
			timeField.setEditable(false);
			timeField.setFocusable(false);

			timeField.setForeground(Color.GREEN);
			timeField.setBackground(Color.GRAY);
			timeField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			frame.getContentPane().add(timeField);
			timeField.repaint();
		}
	}

	/**
	 * Affiche l'écran de fin de partie
	 * @param s le texte à afficher
	 */
	public void endGameScreen(String s) {
		frame.remove(field);
		frame.remove(timeField);
		frame.remove(this);
		frame.repaint();

		JLabel label = new JLabel(s);
		label.setFont(new Font("Verdana", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setSize(this.getSize());

		JButton button = new JButton("MENU");
		button.setBounds((width*pixelByCase)/2-75, (height*pixelByCase)/2+20,150,40);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		frame.getContentPane().add(label);
		frame.add(button);
		frame.repaint();
	}
}
