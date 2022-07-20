package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import ui.CreateGameScreen;
import ui.GameScreen;
import ui.Screen;
import ui.StartScreen;
import util.Character;
import util.State;



public class GamePanel extends JPanel implements Runnable {
	
	private String os;
	//Sekunden zu Nanosekunden
	private double s2ns = Math.pow(10, 9);
	
	//frames per second
	private int FPS = 60;

	//Thread ist die Zeit
	private Thread gameThread;
	// verwaltet die Tastenangabe
	private KeyHandler keyHandler = new KeyHandler();
	
	private Team me;
	private Team enemyTeam;
	
	public State state;
	private Map<State, Screen> screens = new HashMap<>();
	private Screen currentScreen;
	
	private int screenWidth, screenHeight;
	private final int maxWorldRows = 32;
	private final int maxWorldCols = 32;
	
	private int screenX, screenY;
	
	
	
	public GamePanel(Team me) {
		
		this.me = me;
		this.enemyTeam = new Team(me.getEnemy1(), me.getEnemy2());
		
		os = System.getProperty("os.name");
		
		// erh�ht die Rendering Performance
		this.setDoubleBuffered(true);	
		
		this.setBackground(Color.white);
		 
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
		
		CreateGameScreen createGameScreen = new CreateGameScreen(this);
		GameScreen gameScreen = new GameScreen(this, keyHandler, me, enemyTeam);
		StartScreen startScreen = new StartScreen(this);
		
		screens.put(State.CreateGame, createGameScreen);
		screens.put(State.Running, gameScreen);
		screens.put(State.Start, startScreen);
		
		state = State.Running;
		
		//Fuer Jana: Folgendes Auskommentieren
		//state = State.Start;
		
		currentScreen = screens.get(state);

		
		me.getPlayer1().setGamePanel(this);
		me.getPlayer2().setGamePanel(this);
		
		enemyTeam.getPlayer1().setGamePanel(this);
		enemyTeam.getPlayer2().setGamePanel(this);
		
		System.out.println("after setting both");
		
	}
	 
	public void startGameThread() {
		//startet die Zeit
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		//Anzahl an Nanosekunden, die pro Zyklus vergehen dürfen, damit man auf die entsprechende Anzahl an Bildern pro Sekunde kommt
		double nsPerTick = s2ns / FPS;
		double lastTime = System.nanoTime();
		//Artikel der das Prinzip von delta time erklärt: https://drewcampbell92.medium.com/understanding-delta-time-b53bf4781a03
		double delta = 0;
		
		//Für das Zählen der Bilder pro Sekunde (fps)
		int framesCounter = 0;
		double timePassed = 0;
		
		
		//game loop
		while(gameThread != null) {
			
			double currentTime = System.nanoTime();
			//Addiert zur Deltazeit, den Bruchteil von: 
			//	Wie viel Zeit beim Rendern des letzten Bildes vergangen ist,
			//	-------------------------------------------------------------------------------------------------------
			//	Wie viel Zeit maximal vergehen darf, damit man auf die entsprechende Anzahl an Bilder pro Sekunde kommt
			
			delta += (currentTime - lastTime) / nsPerTick;
			
			//Vergangen Zeit zwischen Jetzt und dem davor gerenderten Bild
			double timeInterval = (currentTime - lastTime);
			
			//Addiert zur vergangenen Zeit für fps counter
			timePassed += timeInterval;
			
			lastTime = currentTime;
			
			//Sobald die Zeit überschritten wurde, um die entsprechende Anzahl an Bilder/s zu erreichen, wird das neue Bild gerendert.
			if(delta >= 1) {
				update();
				repaint();
				
				//Notwendig zum Zählen der fps
				framesCounter++;
				
				//Optimierung für Linux
				if(os.contains("Linux")) {
					Toolkit.getDefaultToolkit().sync();
				}
				//Delta zurücksetzen
				delta--;
			}
			
			if(timePassed >= s2ns) {
				//System.out.println("FPS: " + framesCounter);
				framesCounter = 0;
				timePassed = 0;
			}
			
		}
	}
	
	public Screen updateCurrentScreen() {
		switch(state) {
			case Running:
				currentScreen = screens.get(State.Running);
				break;
			case Start:
				currentScreen = screens.get(State.Start);
				break;
			case Pause:
				currentScreen = screens.get(State.Pause);
				break;
			case CreateGame:
				currentScreen = screens.get(State.CreateGame);
				break;
			default:
				System.out.println("Etwas ist falsch mit dem Statemanagement");
				//fuer compiler
				currentScreen = screens.get(State.Start);
				break;
		}
		
		return currentScreen;
	}

	
	public void update() {

		screenWidth = super.getWidth();
		screenHeight = super.getHeight();
		
		screenX = screenWidth / 2;
		screenY = screenHeight / 2;
		
		me.updateTeamInformation();
		
		updateCurrentScreen();
		currentScreen.update();
	}
	
	public void paintComponent(Graphics g) {
		// Methode repaint (Warum die jetzt anders hei�t, wei� ich auch nicht)
		// Die folgenden 2 Zeilen verstehe ich nicht
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		currentScreen.draw(g2);
		
		//Alles was hinter dispose steht wird nicht mehr gerendert
		g2.dispose();
		
	}

	public int getMaxWorldRows() {
		return maxWorldRows;
	}

	public int getMaxWorldCols() {
		return maxWorldCols;
	}

	public Team getMe() {
		return me;
	}
	
	public int getScreenX() {
		return screenX;
	}
	
	public int getScreenY() {
		return screenY;
	}
	
	public State getState() {
		return state;
	}

}
