package graphicalElements;

import javax.imageio.ImageIO;
import javax.swing.*;

import environment.LaneInf;
import gameCommons.IFrog;
import util.Direction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class FroggerGraphic extends JPanel implements IFroggerGraphics, KeyListener {
	private ArrayList<Element> elementsToDisplay;
	//private ArrayList<LaneInf> roadsToDisplay;
	private int pixelByCase = 32;
	private int width;
	private int height;
	private IFrog frog;
	private JFrame frame;
	private boolean infinity;
	private boolean timerMode;
	private int classiqueScore;
	private int timeLeft = 60;
	//private JLabel labelScore;

	private int score = 0;
	private int bestScore = 0;

	BufferedImage colorLane = null;
	BufferedImage colorRoad = null;


	/*private BufferedImage frogSprit = null;
	private BufferedImage rightCar = null;
	private BufferedImage leftCar = null;
	private BufferedImage rightCar2 = null;
	private BufferedImage leftCar2 = null;
	private BufferedImage rightCar3 = null;
	private BufferedImage leftCar3 = null;*/

	public FroggerGraphic(int width, int height, boolean infinity, boolean timerMode) {
		this.width = width;
		this.height = height;
		elementsToDisplay = new ArrayList<Element>();
		this.infinity = infinity;
		this.timerMode = timerMode;

		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));

		JFrame frame = new JFrame("Frogger");
		this.frame = frame;
		//this.labelScore = new JLabel("");


		//ImageIcon logo = new ImageIcon("/frogger.ico");/src/image
		//ImageIcon logo = new ImageIcon("/src/image/frogger.ico");
		ImageIcon logo = new ImageIcon("frog.png");
		frame.setIconImage(logo.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		frame.addKeyListener(this);


		try {
			colorLane = ImageIO.read(new File("colorLane.png"));
			colorRoad = ImageIO.read(new File("colorRoad.png"));
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		/*try {
			frogSprit = ImageIO.read(new File("frog.png"));
			rightCar = ImageIO.read(new File("rightCar.png"));
			leftCar = ImageIO.read(new File("leftCar.png"));
			rightCar2 = ImageIO.read(new File("rightCar2.png"));
			leftCar2 = ImageIO.read(new File("leftCar2.png"));
			rightCar3 = ImageIO.read(new File("rightCar3.png"));
			leftCar3 = ImageIO.read(new File("leftCar3.png"));
		} catch (IOException ioException) {
			System.out.println(ioException);
		}*/
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Random randomGen = new Random();
		this.scoreScreen();

		Graphics2D g2d2 = (Graphics2D) g;
		/*g2d2.drawImage(colorLane, 0/, 26*pixelByCase,26*pixelByCase,pixelByCase,null);
		g2d2.drawImage(colorRoad, 0, 25*pixelByCase,26*pixelByCase,pixelByCase,null);
		g2d2.drawImage(colorRoad, 0, 24*pixelByCase,26*pixelByCase,pixelByCase,null);*/

		/*for (LaneInf l : roadsToDisplay) {
			g2d2.drawImage(l.getSprite(), 0, l.getOrd()*pixelByCase,26*pixelByCase,pixelByCase,null);
		}*/
		if (this.infinity) {
			//Affiche uniquement des routes (pour le mode infinity)
			for (int i = 0; i < height; i++) {
				g2d2.drawImage(colorRoad, 0, i*pixelByCase,26*pixelByCase,pixelByCase,null);
			}
		}
		else {
			g2d2.drawImage(colorLane, 0, 0*pixelByCase,26*pixelByCase,pixelByCase,null);
			for (int i = 1; i < height-1; i++) {
				g2d2.drawImage(colorRoad, 0, i*pixelByCase,26*pixelByCase,pixelByCase,null);
			}
			//g2d2.drawImage(colorLane, 0, 19*pixelByCase,26*pixelByCase,pixelByCase,null);
			g2d2.drawImage(colorLane, 0, (height-1)*pixelByCase,26*pixelByCase,pixelByCase,null);
		}



		for (Element e : elementsToDisplay) {

			//ImageIcon spriteFrog = new ImageIcon("frog.png");
			/*BufferedImage frofro = ImageIO.read(new File("frog.png"));
			g.drawImage(frofro, e.absc, e.ord);*/

			Graphics2D g2d = (Graphics2D) g;
			//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


			//g2d.drawImage(image, 0,0, pixelByCase, pixelByCase, null);



			//g.setColor(e.color);
			//g.fillRect(pixelByCase * e.absc, pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase - 1);

			/*if (e.color == Color.green) {
				g2d.drawImage(frogSprit, pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase, null);
			}
			else if (e.color == Color.black) { //Voiture venant de la gauche
				int ran = randomGen.nextInt(2);
				/*if (ran == 0) {
					g2d.drawImage(leftCar, pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase, null);
				}
				else if (ran == 1) {
					g2d.drawImage(leftCar2, pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase, null);
				}
				else if (ran == 2) {
					g2d.drawImage(leftCar3, pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase, null);
				}*/
			/*	g2d.drawImage(leftCar, pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase, null);

			}
			else if (e.color == Color.blue) { //Voiture venant de la droite
				int ran = randomGen.nextInt(2);
				/*if (ran == 0) {
					g2d.drawImage(rightCar, pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase, null);
				}
				else if (ran == 1) {
					g2d.drawImage(rightCar2, pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase, null);
				}
				else if (ran == 2) {
					g2d.drawImage(rightCar3, pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase, null);
				}*/
				/*g2d.drawImage(rightCar, pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase, null);
			}*/

			g2d.drawImage(e.sprit, pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase, null);
			//g2d.drawImage(colorLane, 13, 0,26,pixelByCase,null);

		}


		/*JButton button = new JButton("Test");
		button.setBounds(505,255,150,40);
		//frame.add(button);
		frame.getContentPane().add(button);
		button.repaint();

		 //Affichage d'une textBox fonctionnel
		JTextField field = new JTextField();
		field.setBounds(1,1,100,100);
		field.setText("ALED");
		field.setForeground(Color.GREEN);
		field.setBackground(Color.GRAY);
		field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		//field.setOpaque(false);
		frame.getContentPane().add(field);
		field.repaint();*/

		/*JLabel lab = new JLabel();
		lab.setFont(new Font("Verdana", 1, 20));
		lab.setBounds(1,1,100,100);
		lab.setText("Score : 01");
		lab.setForeground(Color.GREEN);
		frame.getContentPane().add(lab);
		lab.repaint();*/


		/*JLabel label = new JLabel("TESTETSTTSTSTSTS");
		label.setFont(new Font("Verdana", 1, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.GREEN);
		label.setSize(this.getSize());
		frame.getContentPane().add(label);*/
		//frame.add(new Label("ALED PLS"));
		//frame.repaint();
		//label.repaint();
		//frame.add(label);
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

	/*public void addRoads( ArrayList<LaneInf> roads) {
		this.roadsToDisplay = roads;
	}*/

	public void setFrog(IFrog frog) {
		this.frog = frog;
	}

	public void getScore(int maxScore, int bestScore) {
		/*this.labelScore.setText("Score : " + maxScore + "\n Best Score : " + bestScore);
		//JLabel labelScore = new JLabel("Score : " + maxScore + "\n Best Score : " + bestScore);
		labelScore.setFont(new Font("Verdana", 1, 20));
		labelScore.setForeground(Color.GREEN);
		labelScore.setVerticalAlignment(SwingConstants.TOP);
		labelScore.setHorizontalAlignment(SwingConstants.LEFT);
		labelScore.setSize(this.getSize());
		//frame.getContentPane().add(labelScore);
		frame.add(labelScore);
		labelScore.repaint();*/
		this.score = maxScore;
		this.bestScore = bestScore;
	}

	public void getTimer(int timer, int gameTime) {
		if (timerMode) {
			if (timeLeft >= 0) {
				this.timeLeft = gameTime - timer;
			}
		}
		else {
			this.classiqueScore = timer;
		}


	}

	public void scoreScreen() {
		/*JButton button = new JButton("Test");
		button.setBounds(150,50,150,40);
		frame.add(button);
		button.repaint();

		JLabel lab = new JLabel("TESTETSTTSTSTSTS");
		lab.setFont(new Font("Verdana", 1, 20));
		lab.setForeground(Color.GREEN);
		lab.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(lab);
		lab.repaint();*/
		JTextField field = new JTextField();
		field.setBounds(0,0,240,32);
		field.setFont(new Font("Verdana", 1, 13));
		//field.setText("ALED");
		if (infinity) {
			field.setText("Score : " + this.score + "\t Best Score : " + this.bestScore);
		}
		else {
			field.setText("Score : " + this.classiqueScore + "\t Best Score : " + this.bestScore);
		}


		field.setForeground(Color.GREEN);
		field.setBackground(Color.GRAY);
		field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		//field.setOpaque(false);
		frame.getContentPane().add(field);
		field.repaint();


		if (timerMode) {
			JTextField timeField = new JTextField();
			timeField.setBounds(20*pixelByCase,0,100,32);
			timeField.setFont(new Font("Verdana", 1, 13));
			timeField.setText("Time left :" + this.timeLeft);

			timeField.setForeground(Color.GREEN);
			timeField.setBackground(Color.GRAY);
			timeField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			frame.getContentPane().add(timeField);
			timeField.repaint();
		}
	}

	/*public void timeScreen() {
		JTextField timeField = new JTextField();
		timeField.setBounds(width,0,100,32);
		timeField.setFont(new Font("Verdana", 1, 13));
		timeField.setText("Time left :" + this.timeLeft);

		timeField.setForeground(Color.GREEN);
		timeField.setBackground(Color.GRAY);
		timeField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		frame.getContentPane().add(timeField);
		timeField.repaint();
	}*/

	/*public  void testLable() {
		/*frame.remove(this);
		JLabel lab = new JLabel("TESTETSTTSTSTSTS");
		lab.setFont(new Font("Verdana", 1, 20));
		lab.setForeground(Color.GREEN);
		lab.setVerticalAlignment(SwingConstants.TOP);
		lab.setHorizontalAlignment(SwingConstants.LEFT);
		//lab.setHorizontalAlignment(SwingConstants.CENTER);
		//frame.add(lab);
		frame.getContentPane().add(lab);
		lab.repaint();*/



		/*JPanel pan = new JPanel();
		pan.setLayout(new FlowLayout());
		JLabel l = new JLabel("TEST TEST TEST");
		//l.setText("TEST TEST TEST");
		//l.setHorizontalAlignment(SwingConstants.CENTER);
		pan.add(l);*/


		/*Label la = new Label("TEST TEST TEST", Label.CENTER);
		la.*/
		//la.setText("TEST TEST TEST");

		/*frame.remove(this);

		JLabel label = new JLabel("TESTETSTTSTSTSTS");
		label.setFont(new Font("Verdana", 1, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setSize(this.getSize());
		frame.getContentPane().add(label);


	}*/

	public void endGameScreen(String s) {
		frame.remove(this);
		JLabel label = new JLabel(s);
		label.setFont(new Font("Verdana", 1, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setSize(this.getSize());

		JButton button = new JButton("Replay Classique");
		button.setBounds(150,50,150,40);
		JButton button2 = new JButton("Replay Infinity");
		button2.setBounds(150,100,150,40);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Appuie sur le bouton replay classique");
				//frame.getDefaultCloseOperation();
				try {
					Runtime.getRuntime().exec("java -jar out/artifacts/projetFrogger_jar/projetFrogger.jar");
					//Pour la version Runable avec le .JAR
					//Runtime.getRuntime().exec("java -jar projetFrogger.jar");
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
				//System.out.println("Working Directory = " + System.getProperty("user.dir"));
				/*try {
					PrintWriter writer = new PrintWriter("name.txt", StandardCharsets.UTF_8);
					System.out.println("ok");
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}*/
				System.exit (0);
			}
		});
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Appuie sur le bouton replay infinity");

				try {
					Runtime.getRuntime().exec("java -jar out/artifacts/projetFrogger_jar/projetFrogger.jar infinity");
					//Pour la version Runable avec le .JAR
					//Runtime.getRuntime().exec("java -jar projetFrogger.jar infinity");
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		});
		frame.add(button);
		frame.add(button2);
		frame.getContentPane().add(label);
		frame.repaint();

	}

}
