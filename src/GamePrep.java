import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GamePrep extends JFrame implements KeyListener, ActionListener {
	
	//instances of our data classes (store position, etc here)
	private Frog frog;
	private Car cars1[], cars2[], cars3[];
	private Log logs1[], logs2[], logs3[];

	
	//graphic elements
	private Container content;
	private JLabel frogLabel, car1Label[], car2Label[],car3Label[],log1Label[], log2Label[], log3Label[], backgroundLabel ;
	private ImageIcon frogImage, car1Image[], car2Image[], car3Image[], log1Image[], log2Image[], log3Image[], backgroundImage;
	
	//buttons
	private JButton StartButton;
	
	//score label and x, y initial position
	private JLabel scoreLabel;
	private int score = 50;
	private int xInitial = 375;
	private int yInitial = 500;
	private boolean moving;
	
	//ports for connection
	final static int CLIENT_PORT = 5656;
	final static int SERVER_PORT = 5556;
	
	//main 
	public static void main( String args []) throws IOException{
		GamePrep myGame = new GamePrep();
		myGame.setVisible(true);
	}
	
	public GamePrep() throws IOException {
		//set up background
		backgroundImage = new ImageIcon(this.getClass().getResource("/background.png"));
		backgroundLabel = new JLabel(backgroundImage);
		backgroundLabel.setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT);

		//set up frog
		frog = new Frog(375, 500, 50, 50, GameProperties.CHARACTER_STEP, "frog.png");


		//set up array of car 1 (the bottom lane)
		cars1= new Car[3];
		for(int i=0;i<cars1.length;i++){
			cars1[i]= new Car();
			cars1[i].setX(0 + 290*i);
			cars1[i].setY(450);
			cars1[i].setWidth(100);
			cars1[i].setHeight(50);
			cars1[i].setSpeed(30);
			cars1[i].setMoving(false);
			cars1[i].setImage("car_right.png");
			cars1[i].setFrog(frog);
		}
		
		//set up array of car 2 (the mid lane)
		cars2= new Car[3];
		for(int i=0;i<cars2.length;i++){
			cars2[i]= new Car();
			cars2[i].setX(0 + 270*i);
			cars2[i].setY(385);
			cars2[i].setWidth(100);
			cars2[i].setHeight(50);
			cars2[i].setSpeed(-25);
			cars2[i].setMoving(false);
			cars2[i].setImage("car_left.png");
			cars2[i].setFrog(frog);
		}
		
		//set up array of car 3 (the top lane)
		cars3= new Car[3];
		for(int i=0;i<cars3.length;i++){
			cars3[i]= new Car();
			cars3[i].setX(0 + 250*i);
			cars3[i].setY(320);
			cars3[i].setWidth(100);
			cars3[i].setHeight(40);
			cars3[i].setSpeed(40);
			cars3[i].setMoving(false);
			cars3[i].setImage("car_right.png");
			cars3[i].setFrog(frog);
		}
		
		//set up array of log 1  (the bottom lane of river)
		logs1 = new Log[3];
		for(int i=0;i<logs1.length;i++){
			logs1[i]= new Log();
			logs1[i].setX(0+i*300);
			logs1[i].setY(190);
			logs1[i].setWidth(150);
			logs1[i].setHeight(60);
			logs1[i].setSpeed(25);
			logs1[i].setMoving(false);
			logs1[i].setImage("log.png");
			logs1[i].setFrog(frog);
			
		}

		//set up array of log 2 (the mid lane of river)
		logs2 = new Log[3];
		for(int i=0;i<logs2.length;i++){
			logs2[i]= new Log();
			logs2[i].setX(0+i*350);
			logs2[i].setY(125);
			logs2[i].setWidth(150);
			logs2[i].setHeight(60);
			logs2[i].setSpeed(-35);
			logs2[i].setMoving(false);
			logs2[i].setImage("log.png");
			logs2[i].setFrog(frog);
			
		}
		
		//set up array of log 3 (the top lane of river)
		logs3 = new Log[2];
		for(int i=0;i<logs3.length;i++){
			logs3[i]= new Log();
			logs3[i].setX(0+i*330);
			logs3[i].setY(60);
			logs3[i].setWidth(150);
			logs3[i].setHeight(60);
			logs3[i].setSpeed(35);
			logs3[i].setMoving(false);
			logs3[i].setImage("log.png");
			logs3[i].setFrog(frog);
		}
		
		//set up screen
		setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT);
		content = getContentPane();
		//content.setBackground(backgroundLabel);
		setLayout(null);
		
		//graphic element instantiation and add to screen
		frogLabel = new JLabel();
		frogImage = new ImageIcon(getClass().getResource(frog.getImage()));
		frogLabel.setIcon(frogImage);
		frogLabel.setSize(frog.getWidth(), frog.getHeight());
		frogLabel.setLocation(frog.getX(), frog.getY());
		for(int i=0;i<cars1.length;i++){
			cars1[i].setFrogLabel(frogLabel);
		}
		for(int i=0;i<cars2.length;i++){
			cars2[i].setFrogLabel(frogLabel);
		}
		for(int i=0;i<cars3.length;i++){
			cars3[i].setFrogLabel(frogLabel);
		}
		
		for(int i=0;i<logs1.length;i++){
			logs1[i].setFrogLabel(frogLabel);
		}
		for(int i=0;i<logs2.length;i++){
			logs2[i].setFrogLabel(frogLabel);
		}
		for(int i=0;i<logs3.length;i++){
			logs3[i].setFrogLabel(frogLabel);
		}
		
		car1Label = new JLabel[3];
		car1Image = new ImageIcon[3];
		for(int i=0;i<cars1.length;i++){
			car1Label[i] = new JLabel();
			car1Image[i] = new ImageIcon(getClass().getResource(cars1[i].getImage()));
			car1Label[i].setIcon(car1Image[i]);
			car1Label[i].setSize(cars1[i].getWidth(), cars1[i].getHeight());
			car1Label[i].setLocation(cars1[i].getX(), cars1[i].getY());
			cars1[i].setCarLabel(car1Label[i]);
		}

		car2Label = new JLabel[3];
		car2Image = new ImageIcon[3];
		for(int i=0;i<cars2.length;i++){
			car2Label[i] = new JLabel();
			car2Image[i] = new ImageIcon(getClass().getResource(cars2[i].getImage()));
			car2Label[i].setIcon(car2Image[i]);
			car2Label[i].setSize(cars2[i].getWidth(), cars2[i].getHeight());
			car2Label[i].setLocation(cars2[i].getX(), cars2[i].getY());
			cars2[i].setCarLabel(car2Label[i]);
		}
		
		car3Label = new JLabel[3];
		car3Image = new ImageIcon[3];
		for(int i=0;i<cars3.length;i++){
			car3Label[i] = new JLabel();
			car3Image[i] = new ImageIcon(getClass().getResource(cars3[i].getImage()));
			car3Label[i].setIcon(car3Image[i]);
			car3Label[i].setSize(cars3[i].getWidth(), cars3[i].getHeight());
			car3Label[i].setLocation(cars3[i].getX(), cars3[i].getY());
			cars3[i].setCarLabel(car3Label[i]);
		}
		
		log1Label = new JLabel[3];
		log1Image = new ImageIcon[3];
		for(int i=0;i<logs1.length;i++){
			log1Label[i] = new JLabel();
			log1Image[i] = new ImageIcon(getClass().getResource(logs1[i].getImage()));
			log1Label[i].setIcon(log1Image[i]);
			log1Label[i].setSize(logs1[i].getWidth(), logs1[i].getHeight());
			log1Label[i].setLocation(logs1[i].getX(), logs1[i].getY());
			logs1[i].setLogLabel(log1Label[i]);
		}

		log2Label = new JLabel[3];
		log2Image = new ImageIcon[3];
		for(int i=0;i<logs2.length;i++){
			log2Label[i] = new JLabel();
			log2Image[i] = new ImageIcon(getClass().getResource(logs2[i].getImage()));
			log2Label[i].setIcon(log2Image[i]);
			log2Label[i].setSize(logs2[i].getWidth(), logs2[i].getHeight());
			log2Label[i].setLocation(logs2[i].getX(), logs2[i].getY());
			logs2[i].setLogLabel(log2Label[i]);
		}

		log3Label = new JLabel[2];
		log3Image = new ImageIcon[2];
		for(int i=0;i<logs3.length;i++){
			log3Label[i] = new JLabel();
			log3Image[i] = new ImageIcon(getClass().getResource(logs3[i].getImage()));
			log3Label[i].setIcon(log3Image[i]);
			log3Label[i].setSize(logs3[i].getWidth(), logs3[i].getHeight());
			log3Label[i].setLocation(logs3[i].getX(), logs3[i].getY());
			logs3[i].setLogLabel(log3Label[i]);
		}

		
		//add a start button
		StartButton = new JButton ("Start");
		StartButton.setSize(100, 100);
		StartButton.setLocation(GameProperties.SCREEN_WIDTH-100, 
				                GameProperties.SCREEN_HEIGHT-200);
		StartButton.setFocusable(false);
		
		for(int i=0;i<cars1.length;i++){
			cars1[i].setStartButton(StartButton);
		}
		for(int i=0;i<cars2.length;i++){
			cars2[i].setStartButton(StartButton);
		}
		for(int i=0;i<cars3.length;i++){
			cars3[i].setStartButton(StartButton);
		}
		
		
		for(int i=0;i<logs1.length;i++){
			logs1[i].setStartButton(StartButton);
		}
		for(int i=0;i<logs2.length;i++){
			logs2[i].setStartButton(StartButton);
		}
		for(int i=0;i<logs3.length;i++){
			logs3[i].setStartButton(StartButton);
		}
		
		//set size and location score label
		scoreLabel = new JLabel();
		scoreLabel.setSize(100, 40);
		scoreLabel.setLocation(GameProperties.SCREEN_WIDTH - 100, 0);

		//populate screen
		content.add(StartButton);
		StartButton.addActionListener(this);

		content.add(scoreLabel);
		content.add(frogLabel);
		
		for(int i=0;i<cars1.length;i++){
			content.add(car1Label[i]);
		}
		for(int i=0;i<cars2.length;i++){
			content.add(car2Label[i]);
		}
		for(int i=0;i<cars3.length;i++){
			content.add(car3Label[i]);
		}
		
		for(int i=0;i<logs1.length;i++){
			content.add(log1Label[i]);
		}
		for(int i=0;i<logs2.length;i++){
			content.add(log2Label[i]);
		}
		for(int i=0;i<logs3.length;i++){
			content.add(log3Label[i]);
		}
		content.add(backgroundLabel);
		
		moving = false;
		
		//thread
			//Send GETFROG\n to server every 500ms
		//thread
			//Send GETCARS\n to server every 500ms
		//thread
			//Send GETLOGS\n to server every 500ms
		
		content.addKeyListener(this);
		content.setFocusable(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final ServerSocket client = new ServerSocket(CLIENT_PORT);
		
		//set up listening server
		Thread t1 = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized(this) {
					
					System.out.println("Waiting for server responses...");
					while(true) {
						Socket s2;
						try {
							s2 = client.accept();
							GameService myService = new GameService(s2, frog);
							Thread t = new Thread(myService);
							t.start();
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("client connected");
					}

				}
			}
		});
		t1.start( );
		
		//set up listening server to get frog position
		Thread t2 = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized(this) {
					
					while(true) {
						System.out.println("Getting Frog from server");
						try {
							String command = "GETFROG\n";
							//set up a communication socket
							Socket s = new Socket("localhost", SERVER_PORT);
						
							//Initialize data stream to send data out
							OutputStream outstream = s.getOutputStream();
							PrintWriter out = new PrintWriter(outstream);
						
							out.println(command);
							out.flush();
							out.close();
							s.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
						try {
							Thread.sleep(500);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
			}
		});
		t2.start( );
		
		
		//set up listening server to get cars position
		Thread t3 = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized(this) {
							
					while(true) {
						System.out.println("Getting Cars from server");
						try {
							String command = "GETCARS\n";
							//set up a communication socket
							Socket s = new Socket("localhost", SERVER_PORT);
								
							//Initialize data stream to send data out
							OutputStream outstream = s.getOutputStream();
							PrintWriter out = new PrintWriter(outstream);
								
							out.println(command);
							out.flush();
							out.close();
							s.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
								
						try {
							Thread.sleep(500);
						} catch (Exception e) {
									e.printStackTrace();
						}
					}
							
				}
			}
		});
		t3.start( );
			
		/*
		//set up listening server to get logs position
		Thread t4 = new Thread ( new Runnable () {
			public void run ( ) {
				synchronized(this) {
							
					while(true) {
						System.out.println("Getting Logs from server");
						try {
							String command = "GETLOGS\n";
							//set up a communication socket
							Socket s = new Socket("localhost", SERVER_PORT);
								
							//Initialize data stream to send data out
							OutputStream outstream = s.getOutputStream();
							PrintWriter out = new PrintWriter(outstream);
								
							out.println(command);
							out.flush();
							out.close();
							s.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
								
						try {
							Thread.sleep(500);
						} catch (Exception e) {
									e.printStackTrace();
						}
					}
							
				}
			}
		});
		t4.start( );	
		*/	
	}


	public void Resetfrogger() throws IOException {
		Score.INSTANCE.minusScore(score);
		scoreLabel.setText("Score: " + Score.INSTANCE.getScore());
		frog.setX(xInitial); frog.setY(yInitial);
		frogLabel.setLocation(frog.getX(), frog.getY());
	}
	
	public void winGame() throws IOException {
		Score.INSTANCE.addScore(score);
		scoreLabel.setText("Score: " + Score.INSTANCE.getScore());
		frog.setX(xInitial); frog.setY(yInitial);
		frogLabel.setLocation(frog.getX(), frog.getY());
	}
	
	public void AddToScore() {
		Score.INSTANCE.addScore(score);
		scoreLabel.setText("Score: " + Score.INSTANCE.getScore());
	}
	

	@Override
	public void keyTyped(KeyEvent e) {}


	@Override
	public void keyPressed(KeyEvent e) {
		int x = frog.getX();
		int y = frog.getY();
		
		//modify position
		String direction = null;
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			direction = "UP";

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			direction = "DOWN";
			
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			direction = "LEFT";
			
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			direction = "RIGHT";

		} else {
			System.out.println("Invalid operation");
		}
		
		try {
			//set up a communication socket
			Socket s = new Socket("localhost", SERVER_PORT);
		
			//Initialize data stream to send data out
			OutputStream outstream = s.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);
		
			String command = "MOVE_FROG " + direction;
			out.println("Sending: " + command);
			out.flush();
			out.close();
			s.close();
			
			//update graphic
			frogLabel.setLocation(frog.getX(), frog.getY());
			
			if (frog.getY() <= 30) {
				winGame();
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

	public void stopGame() throws UnknownHostException, IOException {
		//set up a communication socket
		Socket s = new Socket("localhost", SERVER_PORT);
	
		//Initialize data stream to send data out
		OutputStream outstream = s.getOutputStream();
		PrintWriter out = new PrintWriter(outstream);

		String command = "STOPGAME\n";
		
		System.out.println("Sending: " + command);
		out.println(command);
		out.flush();
		
		for (Thread t : Thread.getAllStackTraces().keySet()) {
			if(t.getName().equals("myService")) {
				t.interrupt();
			}
		}	
		
		s.close();
		out.close();
	}
	
	public void startGame() throws UnknownHostException, IOException {
		//set up a communication socket
		Socket s = new Socket("localhost", SERVER_PORT);
	
		//Initialize data stream to send data out
		OutputStream outstream = s.getOutputStream();
		PrintWriter out = new PrintWriter(outstream);

		String command = "STARTGAME\n";
		
		System.out.println("Sending: " + command);
		out.println(command);
		out.flush();
		
		for (Thread t : Thread.getAllStackTraces().keySet()) {
			if(t.getName().equals("myService")) {
				t.resume();
			}
		}	
		
		s.close();
		out.close();
	}

	@Override
	public void keyReleased(KeyEvent e) {}


	@Override
	public void actionPerformed(ActionEvent e) {
		//distinguish among buttons
		if (e.getSource() == StartButton) {
			//set up a communication socket
			Socket s;
			try {
				s = new Socket("localhost", SERVER_PORT);
				//Initialize data stream to send data out
				OutputStream outstream = s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
				
				if(!moving) {

					StartButton.setText("Stop");
					String command = "START";
					//SEND STARTGAME\n to server
					System.out.println("Sending: " + command);
					out.println(command);
					out.flush();
					moving = true;
					
				}else {
					StartButton.setText("Start");
					String command = "STOP";
					//SEND STOPGAME\n to server
					System.out.println("Sending: " + command);
					out.println(command);
					out.flush();
					moving = false;
				}
				
				s.close();
				out.close();
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		

			

	
		}
		
	}
	
	
	
}