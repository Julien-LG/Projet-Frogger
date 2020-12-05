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
	//private ArrayList<LaneInf> roadsToDisplay;
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

	/*private BufferedImage frogSprit = null;
	private BufferedImage rightCar = null;
	private BufferedImage leftCar = null;
	private BufferedImage rightCar2 = null;
	private BufferedImage leftCar2 = null;
	private BufferedImage rightCar3 = null;
	private BufferedImage leftCar3 = null;*/

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
		if (ManageFile.getLineFile("Options.txt", 0).equals("true")) {
			System.out.println("True");
			this.minimalistMode = true;
		}
		else {
			System.out.println("False");
			this.minimalistMode = false;
		}
		System.out.println(minimalistMode);
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
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Random randomGen = new Random();

		Graphics2D g2d2 = (Graphics2D) g;
		/*g2d2.drawImage(colorLane, 0/, 26*pixelByCase,26*pixelByCase,pixelByCase,null);
		g2d2.drawImage(colorRoad, 0, 25*pixelByCase,26*pixelByCase,pixelByCase,null);
		g2d2.drawImage(colorRoad, 0, 24*pixelByCase,26*pixelByCase,pixelByCase,null);*/

		/*for (LaneInf l : roadsToDisplay) {
			g2d2.drawImage(l.getSprite(), 0, l.getOrd()*pixelByCase,26*pixelByCase,pixelByCase,null);
		}*/
		if (!menu) {
			if (!minimalistMode) {
				if (this.infinity) {
					//Affiche uniquement des routes (pour le mode infinity)
					for (int i = 0; i < height; i++) {
						g2d2.drawImage(colorRoad, 0, i*pixelByCase,26*pixelByCase,pixelByCase,null);
					}
				}
				else {
					g2d2.drawImage(colorLane, 0, 0,26*pixelByCase,pixelByCase,null);
					for (int i = 1; i < height-1; i++) {
						g2d2.drawImage(colorRoad, 0, i*pixelByCase,26*pixelByCase,pixelByCase,null);
					}
					g2d2.drawImage(colorLane, 0, (height-1)*pixelByCase,26*pixelByCase,pixelByCase,null);
				}
			}

			for (Element e : elementsToDisplay) {
				if (minimalistMode) {
					g.setColor(e.color);
					g.fillRect(pixelByCase * e.absc, pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase - 1);
				}
				else {
					Graphics2D g2d = (Graphics2D) g;
					g2d.drawImage(e.sprit, pixelByCase * e.absc,pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase, null);
				}
			}
			this.scoreScreen();
		}
		else {

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

	public void menuScreen() {
		frame.remove(this);

		//Affichage logo frogger
		ImageIcon iconFrogger = new ImageIcon("frogger.png");
		JLabel labelIconFrogger = new JLabel();
		labelIconFrogger.setIcon(new ImageIcon(iconFrogger.getImage().getScaledInstance(iconFrogger.getIconWidth()/3,iconFrogger.getIconHeight()/3, Image.SCALE_SMOOTH)));
		labelIconFrogger.setFont(new Font("Verdana", Font.BOLD, 20));
		labelIconFrogger.setHorizontalAlignment(SwingConstants.CENTER);
		labelIconFrogger.setVerticalAlignment(SwingConstants.TOP);
		labelIconFrogger.setSize(this.getSize());
		frame.getContentPane().add(labelIconFrogger);

		//Affichage logo frog
		ImageIcon iconFrog = new ImageIcon("frog.png");
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
		ImageIcon gear = new ImageIcon("engrenage.png");
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

		/*JLabel labelScore1 = new JLabel("Classique : 0");
		labelScore1.setLocation(10,10);
		labelScore1.setForeground(Color.GREEN);
		labelScore1.setFont(new Font("Verdana", 1, 13));
		JLabel labelScore2 = new JLabel("Infinity : 0");
		JLabel labelScore3 = new JLabel("Infinity Timer : 0");*/
		/*JTextField t = new JTextField("yo");
		t.setBounds(0,0,150,40);*/


		frame.repaint();
	}

	public void optionsScreen() {
		setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));
		setBackground(UIManager.getColor ( "Panel.background" ));
		//Color.OPAQUE
		JFrame frame2 = new JFrame("Options");
		frame2.getContentPane().add(this);
		frame2.pack();
		frame2.setVisible(true);
		frame2.addKeyListener(this);
		frame2.setLayout(new GridBagLayout());


		/*//Affichage logo frogger
		ImageIcon icon = new ImageIcon("engrenage.png");
		Image image = icon.getImage();
		//Image newimg = image.getScaledInstance(120,120, Image.SCALE_SMOOTH);
		Image newimg = image.getScaledInstance(100,100, Image.SCALE_SMOOTH);
		ImageIcon i = new ImageIcon(newimg);

		JLabel label = new JLabel();
		label.setIcon(i);
		label.setFont(new Font("Verdana", Font.BOLD, 20));
		/*label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.TOP);*/
		/*label.setHorizontalAlignment(0);
		label.setVerticalAlignment(0);
		label.setSize(this.getSize());
		frame.getContentPane().add(label);*/


		JButton button = new JButton("Delete Bests scores");
		button.setBounds(0,0,150,40);
		//button.setBounds((width*pixelByCase)/3,(height*pixelByCase)/2,180,40);
		//button.setBounds(0,0,0,0);
		button.addActionListener(new ActionListener() {
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
		frame2.add(button);
		button.repaint();

		JButton button2 = new JButton("Switch Minimalist/Normal graphics");
		button2.setBounds(0,0,150,40);
		//button.setBounds((width*pixelByCase)/3,(height*pixelByCase)/2,180,40);
		//button.setBounds(0,0,0,0);
		button2.addActionListener(new ActionListener() {
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

		frame2.add(button2);
		button2.repaint();


		/*JLabel label = new JLabel("Bionjour");
		label.setFont(new Font("Verdana", 1, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.GREEN);
		label.setSize(this.getSize());
		frame2.getContentPane().add(label);*/

		/*JTextField j = new JTextField("Oui");
		j.setBounds(0,0,150,40);*/
		/*JButton j = new JButton("Oui");
		j.setBounds(0,0,150,40);
		//frame2.getContentPane().add(j);
		frame2.add(j);*/
	}

	public void scoreScreen() {
		/*JButton button = new JButton("Test");
		button.setBounds(150,50,150,40);
		button.setFocusable(false);
		frame.add(button);
		button.repaint();

		JLabel lab = new JLabel("TESTETSTTSTSTSTS");
		lab.setFont(new Font("Verdana", 1, 20));
		lab.setForeground(Color.GREEN);
		lab.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(lab);
		//frame.getContentPane().add(lab);
		lab.repaint();*/

		//field = new JTextField();
		field.setBounds(0,0,240,32);
		field.setEditable(false);
		field.setFocusable(false);
		field.setFont(new Font("Verdana", 1, 13));
		//field.setText("ALED");
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
		//frame.remove(field);
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
		/*frame.remove(this);
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
			/*	System.exit (0);
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
		frame.repaint();*/
		//frame.removeAll();

		frame.remove(field);
		frame.remove(timeField);
		frame.remove(this);
		frame.repaint();

		//frame.remove(10);

		JLabel label = new JLabel(s);
		label.setFont(new Font("Verdana", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setSize(this.getSize());

		JButton button = new JButton("MENU");
		button.setBounds(/*100*/(width*pixelByCase)/2-75,/*100*/(height*pixelByCase)/2+20,150,40);
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
