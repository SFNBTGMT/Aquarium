import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.lang.Math;
import java.util.Collections;

public class Board extends JPanel implements ActionListener {

	private final int B_WIDTH = 600;
	private final int B_HEIGHT = 400;
	private static final int ZONE_ROWS = 4; 
	private static final int ZONE_COLS = 4; 
	private int zoneWidth = B_WIDTH / ZONE_COLS; // Largeur d'une zone
	private int zoneHeight = B_HEIGHT / ZONE_ROWS; // Hauteur d'une zone
	private final int DOT_SIZE = 10;
	private final int RAND_POS = 29;
	private final int DELAY = 140;
	private final int INITIAL_COINS = 10; 
	private final int INITIAL_INSECTS = 5;

	private int pos_x;
	private int pos_y;

	private int coinCounter;
	private int insectCounter;
	// private ArrayList<Coin> coinList ;
	private ArrayList<FixedGameElement> fixedGameElementList;
	HashMap<String, ImageIcon> fixedGameElementImageMap;
	private ArrayList<MoveGameElement> moveGameElementList;


	private boolean leftDirection = false;
	private boolean rightDirection = false;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;

	private String temperature = "tiède"; // Température par défaut
	private String mode = "normal"; // Mode par défaut
	private boolean[] stoppedFish = new boolean[4]; // Tableau pour gérer les poissons stoppés

	private Timer timer;
	private Image ball;
	private Image coin;
	private Image head;
	private Image fishO;
	private Image fishP;
	private Image fishB;
	private Image fishR;

	private int score;
	private int void_x = -1 * B_WIDTH;
	private int void_y = -1 * B_HEIGHT;

	private int target_x = 0;
	private int target_y = 0;

	private CommandHandler commandHandler; // Instance de CommandHandler

	public Board() {

		commandHandler = new CommandHandler(); // Initialiser CommandHandler
		initBoard();
	}

	private void initBoard() {

		addKeyListener(new TAdapter());
		setBackground(Color.blue);
		setFocusable(true);

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		loadImages();
		initGame();
	}

	private void loadImages() {

		fixedGameElementImageMap = new HashMap<String, ImageIcon>();

		ImageIcon iic = new ImageIcon(Coin.getPathToImage());
		fixedGameElementImageMap.put("coin", iic);

		ImageIcon iii = new ImageIcon(Insect.getPathToImage());
		fixedGameElementImageMap.put("insect", iii);

		ImageIcon iid = new ImageIcon(Decoration.getPathToImage());
		fixedGameElementImageMap.put("decoration", iii);

		ImageIcon iih = new ImageIcon("head.png");
		head = iih.getImage();

		ImageIcon iifo = new ImageIcon(FishOrange.getPathToImage());
		// MoveGameElementImageMap.put("Fish", iifo);
		fishO = iifo.getImage();

		ImageIcon iifp = new ImageIcon(FishPurple.getPathToImage()); 
		fishP = iifp.getImage();

		ImageIcon iifb = new ImageIcon(FishBlue.getPathToImage()); 
		fishB = iifb.getImage();

		ImageIcon iifr = new ImageIcon(FishRed.getPathToImage()); 
		fishR = iifr.getImage();

	}

	private void initGame() {
		Random random = new Random();
		score = 0;

		// Position aléatoire pour le joueur
		 do {
		        pos_x = getRandomCoordinate();
		        pos_y = getRandomCoordinate();
		    } while (!isValidPosition(pos_x, pos_y)); 
		 
		// Initialiser les listes
		fixedGameElementList = new ArrayList<>();
		moveGameElementList = new ArrayList<>();

		// Ajouter des pastilles comestibles
		for (int i = 0; i < INITIAL_COINS; i++) {
	        int x, y;
	        do {
	            x = getRandomCoordinate();
	            y = getRandomCoordinate();
	        } while (!isPositionFree(x, y)); // Répéter jusqu'à trouver une position libre
	        fixedGameElementList.add(new Coin(x, y));
		}

		// Ajouter des insectes
		for (int i = 0; i < insectCounter; i++) {
			int zoneX = i % ZONE_COLS; // Zone horizontale
			int zoneY = i / ZONE_COLS; // Zone verticale

			int x, y;
			do {
				x = zoneX * zoneWidth + random.nextInt(zoneWidth);
				y = zoneY * zoneHeight + random.nextInt(zoneHeight);
				x = (x / DOT_SIZE) * DOT_SIZE; // Aligner sur la grille
				y = (y / DOT_SIZE) * DOT_SIZE; // Aligner sur la grille
			} while (!isPositionFree(x, y)); // Répéter jusqu'à trouver une position libre
			fixedGameElementList.add(new Insect(x, y));
		}

		// Ajouter des décorations
		for (int i = 0; i < 5; i++) {
	        int x, y;
	        do {
	            x = getRandomCoordinate();
	            y = getRandomCoordinate();
	        } while (!isPositionFree(x, y)); // Répéter jusqu'à trouver une position libre
	        fixedGameElementList.add(new Decoration(x, y));
		}

		// Ajouter des poissons
		
		moveGameElementList.add(new FishOrange(getRandomCoordinate(), getRandomCoordinate()));
		moveGameElementList.add(new FishPurple(getRandomCoordinate(), getRandomCoordinate()));
		moveGameElementList.add(new FishBlue(getRandomCoordinate(), getRandomCoordinate()));
		moveGameElementList.add(new FishRed(getRandomCoordinate(), getRandomCoordinate()));

	    repaint();

		
		// Démarrer le timer
		timer = new Timer(DELAY, this);
		timer.start();
	}
	private boolean isPositionFree(int x, int y) {
	    // Vérifier les éléments fixes (pastilles, insectes, décorations)
	    for (FixedGameElement elem : fixedGameElementList) {
	        if (elem.getPosX() == x && elem.getPosY() == y) {
	            return false; // La position est déjà occupée
	        }
	    }

	    // Vérifier les éléments mobiles (poissons)
	    for (MoveGameElement elem : moveGameElementList) {
	        if (elem.getPosX() == x && elem.getPosY() == y) {
	            return false; // La position est déjà occupée
	        }
	    }

	    return true; // La position est libreF
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);
	}

	private void doDrawing(Graphics g) {

		if (inGame) {

			for (FixedGameElement elem : fixedGameElementList) {
				g.drawImage(fixedGameElementImageMap.get(elem.getType()).getImage(), elem.getPosX(), elem.getPosY(),
						this);
			}
			

			for (MoveGameElement fish : moveGameElementList) {
				String type = fish.getTag();
				ImageIcon icon = null;
				switch (type) {
				case "fishOrange":
					icon = new ImageIcon(FishOrange.getPathToImage());
					break;
				case "fishPurple":
					icon = new ImageIcon(FishPurple.getPathToImage());
					break;
				case "fishBlue":
					icon = new ImageIcon(FishBlue.getPathToImage());
					break;
				case "fishRed":
					icon = new ImageIcon(FishRed.getPathToImage());
					break;
				}
				if (icon != null) {
					g.drawImage(icon.getImage(), fish.getPosX(), fish.getPosY(), this);
				}
			}
			Toolkit.getDefaultToolkit().sync();

		} else {

			gameOver(g);
		}
	}

	private void gameOver(Graphics g) {

		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);

		g.setColor(Color.BLACK);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
	}

	private void checkFixedGameElementCollision() {

		for (FixedGameElement elem : fixedGameElementList) {
			if ((pos_x == elem.getPosX()) && (pos_y == elem.getPosY())) {
				elem.setPosX(void_x);
				elem.setPosY(void_y);

				elem.triggerAction(this);

				System.out.println(coinCounter);
				System.out.println(score);
			}
		}

		for (MoveGameElement fish : moveGameElementList) {
			if (fish != null && pos_x == fish.getPosX() && pos_y == fish.getPosY()) {
				fish.triggerAction(this); // Diminuer la vie du joueur
			}
		}
	}

	public void incScore(int valueToIncrease) {
		score += valueToIncrease;
	}

	public void decreaseCoinAmount() {
		coinCounter -= 1;
	}

	private void move() {

		ArrayList<Integer> x_moveOptions = new ArrayList<Integer>();
		ArrayList<Integer> y_moveOptions = new ArrayList<Integer>();
		ArrayList<Double> distances = new ArrayList<Double>();

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int test_pos_x = pos_x + i * DOT_SIZE;
				int test_pos_y = pos_y + j * DOT_SIZE;
				if (isValidPosition(test_pos_x, test_pos_y)) {
					x_moveOptions.add(test_pos_x);
					y_moveOptions.add(test_pos_y);
				}
			}
		}
		if (target_x == pos_x && target_y == pos_y) {
			// Generate a new random position target
			int index = (int) (Math.random() * x_moveOptions.size());
			target_x = x_moveOptions.get(index);
			target_y = y_moveOptions.get(index);
		}

		for (int i = 0; i < x_moveOptions.size(); i++) {
			Double distance = getDistance(target_x, target_y, x_moveOptions.get(i), y_moveOptions.get(i));
			distances.add(distance);
		}
		if (!distances.isEmpty()) {
			int minDistanceIndex = distances.indexOf(Collections.min(distances));
			pos_x = x_moveOptions.get(minDistanceIndex);
			pos_y = y_moveOptions.get(minDistanceIndex);

		}

		
		if (pos_x == B_WIDTH || pos_x == 0 || pos_y == B_HEIGHT || pos_y == 0) {
			target_x = B_WIDTH - target_x;
			target_y = B_HEIGHT - target_y;
		}

		checkFixedGameElementCollision();

	}
	/*     ////////////////////////////////////////////////   
	 *                 add new method here  
	 *    ////////////////////////////////////////////////               */	

	private void moveFish() {
		for (MoveGameElement fish : moveGameElementList) {
			int newPosX = fish.getPosX();
			int newPosY = fish.getPosY();

			switch (fish.getDirection()) {
			case "left":
				newPosX -= fish.getSpeed();
				break;
			case "right":
				newPosX += fish.getSpeed();
				break;
			case "up":
				newPosY -= fish.getSpeed();
				break;
			case "down":
				newPosY += fish.getSpeed();
				break;
			case "up-left":
				newPosX -= fish.getSpeed();
				newPosY -= fish.getSpeed();
				break;
			case "up-right":
				newPosX += fish.getSpeed();
				newPosY -= fish.getSpeed();
				break;
			case "down-left":
				newPosX -= fish.getSpeed();
				newPosY += fish.getSpeed();
				break;
			case "down-right":
				newPosX += fish.getSpeed();
				newPosY += fish.getSpeed();
				break;
			}

			// Vérifier que la nouvelle position est valide
			if (isValidPosition(newPosX, newPosY)) {
				fish.setPosX(newPosX);
				fish.setPosY(newPosY);
			} else {
				// Changer de direction si le poisson atteint un bord
				changeFishDirection(fish);
			}
		}
	}

	private void changeFishDirection(MoveGameElement fish) {
		String[] directions = {"left", "right", "up", "down", "up-left", "up-right", "down-left", "down-right"};
		Random random = new Random();
		String newDirection = directions[random.nextInt(directions.length)];
		fish.setDirection(newDirection);
	}


	private int countDecorations() {
		int decorationCount = 0;
		for (FixedGameElement elem : fixedGameElementList) {
			// Supposons que les décorations ont le type "decoration"
			if (elem.getType().equals("decoration")) { 
				decorationCount++;
			}
		}
		return decorationCount;
	}

	private void updatePurpleFishSpeed() {
		int decorationCount = countDecorations();
		for (MoveGameElement fish : moveGameElementList) {
			if (fish instanceof FishPurple) {
				((FishPurple) fish).updateSpeed(decorationCount);
			}
		}
	}

	/*            stop here                     */	

	private void checkCollision() {
		inGame = isValidPosition(pos_x, pos_y);
		if (!inGame) {
			timer.stop();
		}
	}

	private int getRandomCoordinate() {
	    Random random = new Random();
	    // Générer une position aléatoire dans les limites de l'aquarium
	    return random.nextInt(B_WIDTH / DOT_SIZE) * DOT_SIZE;
	}

	boolean isValidPosition(int pos_x, int pos_y) {

		boolean res = true;
		if (pos_y >= B_HEIGHT) {
			res = false;
		}

		if (pos_y < 0) {
			res = false;
		}

		if (pos_x >= B_WIDTH) {
			res = false;
		}

		if (pos_x < 0) {
			res = false;
		}
		return res;
	}

	private double getDistance(int pos_x0, int pos_y0, int pos_x1, int pos_y1) {
		int x_dist = pos_x1 - pos_x0;
		int y_dist = pos_y1 - pos_y0;
		return Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (inGame) {
			updateFishSpeed();
			updatePurpleFishSpeed(); // Mettre à jour la vitesse des poissons mauves
			checkFixedGameElementCollision();
			checkCollision();
			//checkFishCollision();
			move();
			moveFish(); // Déplacer les poissons

		}

		repaint();
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			switch (key) {
			case KeyEvent.VK_0:
				commandHandler.resetAquarium(Board.this);
				break;
			case KeyEvent.VK_1:
				commandHandler.setTemperature(Board.this, "froid");
				break;
			case KeyEvent.VK_2:
				commandHandler.setTemperature(Board.this, "tiède");
				break;
			case KeyEvent.VK_3:
				commandHandler.setTemperature(Board.this, "chaud");
				break;
			case KeyEvent.VK_4:
				commandHandler.addInsect(Board.this);
				break;
			case KeyEvent.VK_5:
				commandHandler.addCoin(Board.this);
				break;
			case KeyEvent.VK_6:
				commandHandler.setMode(Board.this, "insectivore");
				break;
			case KeyEvent.VK_7:
				commandHandler.setMode(Board.this, "pastille");
				break;
			case KeyEvent.VK_8:
				commandHandler.setMode(Board.this, "reproduction");
				break;
			case KeyEvent.VK_9:
				commandHandler.addRandomFish(Board.this);
				break;
			}

			// Gestion des touches alphabétiques
			switch (e.getKeyChar()) {
			case 'r':
				commandHandler.stopAllFishExcept(Board.this, "fishRed");
				break;
			case 'b':
				commandHandler.stopAllFishExcept(Board.this, "fishBlue");
				break;
			case 'm':
				commandHandler.stopAllFishExcept(Board.this, "fishPurple");
				break;
			case 'o':
				commandHandler.stopAllFishExcept(Board.this, "fishOrange");
				break;
			}
			move();
		}
	}

	public void decreaseLife() {
		// TODO Auto-generated method stub

	}

	public void resetAquarium() {
		// TODO Auto-generated method stub
		// Réinitialiser le score et d'autres variables si nécessaire
	    score = 0;
	    coinCounter = INITIAL_COINS;
	    insectCounter = INITIAL_INSECTS;

	    // Réinitialiser la température et le mode
	    temperature = "tiède";
	    mode = "normal";

	    // Réinitialiser le fond
	    repaint();

	    // Réinitialiser le jeu
	    initGame();
		System.out.println("Aquarium reset.");

	}

	public void setTemperature(String temp) {
		// TODO Auto-generated method stub
		   this.temperature = temp;
		    System.out.println("Température définie sur : " + temp);

		    // Changer la couleur du fond en fonction de la température
		    switch (temp) {
		        case "froid":
		            setBackground(Color.BLUE); // Fond bleu pour le froid
		            break;
		        case "tiède":
		            setBackground(Color.CYAN); // Fond cyan pour le tiède
		            break;
		        case "chaud":
		            setBackground(Color.ORANGE); // Fond orange pour le chaud
		            break;
		    }

		    // Mettre à jour la vitesse des poissons rouges
		    updateFishSpeed();

	}

	private void updateFishSpeed() {
	    for (MoveGameElement fish : moveGameElementList) {
	        if (fish instanceof FishRed) {
	            FishRed redFish = (FishRed) fish;
	            switch (temperature) {
	                case "froid":
	                    redFish.setSpeed(redFish.getBaseSpeed() - 2); // Malus de vitesse
	                    break;
	                case "tiède":
	                    redFish.setSpeed(redFish.getBaseSpeed()); // Vitesse de base
	                    break;
	                case "chaud":
	                    redFish.setSpeed(redFish.getBaseSpeed() + 2); // Bonus de vitesse
	                    break;
	            }
	        }
	    }
	}
	public void addInsect() {
		// TODO Auto-generated method stub
		int x = getRandomCoordinate();
		int y = getRandomCoordinate();
		fixedGameElementList.add(new Insect(x, y));
		System.out.println("Insecte ajouté à (" + x + ", " + y + ")");

	}

	public void addCoin() {
		// TODO Auto-generated method stub
		int x = getRandomCoordinate();
		int y = getRandomCoordinate();
		fixedGameElementList.add(new Coin(x, y));
		System.out.println("Pastille comestible ajoutée à (" + x + ", " + y + ")");

	}

	public void setMode(String mode2) {
		// TODO Auto-generated method stub
		this.mode = mode;
		System.out.println("Mode défini sur : " + mode);

	}

	public void addRandomFish() {
		// TODO Auto-generated method stub
		int x = getRandomCoordinate();
		int y = getRandomCoordinate();
		Random random = new Random();
		int fishType = random.nextInt(4); // 0: Orange, 1: Purple, 2: Blue, 3: Red

		switch (fishType) {
		case 0:
			moveGameElementList.add(new FishOrange(x, y));
			break;
		case 1:
			moveGameElementList.add(new FishPurple(x, y));
			break;
		case 2:
			moveGameElementList.add(new FishBlue(x, y));
			break;
		case 3:
			moveGameElementList.add(new FishRed(x, y));
			break;
		}
		System.out.println("Poisson ajouté à (" + x + ", " + y + ")");

	}

	public void stopAllFishExcept(String fishType) {
		// TODO Auto-generated method stub
		for (MoveGameElement fish : moveGameElementList) {
			if (fish.getTag().equals(fishType)) {
				fish.setSpeed(0); // Arrêter le poisson
			} else {
				fish.setSpeed(fish instanceof FishBlue ? 8 : 5); // Rétablir la vitesse par défaut
			}
		}
		System.out.println("Tous les poissons sauf " + fishType + " sont stoppés.");

	}
}
