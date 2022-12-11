import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JLabel;

public class GameService implements Runnable{
    
	private Socket s;
	private Scanner in;
	private Frog frog;
	
	private Car carsBot[];
	private Car carsMid[];
	private Car carsTop[];
	
	private Log logsBot[];
	private Log logsMid[];
	private Log logsTop[];
	
	private JLabel car1Label[], car2Label[],car3Label[],log1Label[], log2Label[], log3Label[] ;

	

	public GameService(Socket s, Frog frog, Car[] carsBot, Car[] carsMid, Car[] carsTop, Log[] logsBot,
			Log[] logsMid, Log[] logsTop, JLabel[] car1Label, JLabel[] car2Label, JLabel[] car3Label,
			JLabel[] log1Label, JLabel[] log2Label, JLabel[] log3Label) {
		super();
		this.s = s;
		this.frog = frog;
		this.carsBot = carsBot;
		this.carsMid = carsMid;
		this.carsTop = carsTop;
		this.logsBot = logsBot;
		this.logsMid = logsMid;
		this.logsTop = logsTop;
		this.car1Label = car1Label;
		this.car2Label = car2Label;
		this.car3Label = car3Label;
		this.log1Label = log1Label;
		this.log2Label = log2Label;
		this.log3Label = log3Label;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			in = new Scanner(s.getInputStream());
			processRequest( );
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	public void processRequest () throws IOException {
		//if next request is empty then return
		while(true) {
			if(!in.hasNext( )){
				return;
			}
			String command = in.next();
			if (command.equals("Quit")) {
				return;
			} else {
				executeCommand(command);
			}
		}
	}
	
public void executeCommand(String command) throws IOException{
		if ( command.equals("FROG_POSITION")) {
			int frogX = in.nextInt();
			String space = in.next();
			int frogY = in.nextInt();
			this.frog.setX(frogX);
			this.frog.setY(frogY);
			System.out.println("FROG_POSITION " +frogX + " " + frogY);

		} else if (command.equals("GETCARS")) {
			int car1X = in.nextInt();
			String space1 = in.next();
			int car1Y = in.nextInt();
			String space2 = in.next();
			this.carsBot[1].setX(car1X);
			this.carsBot[1].setY(car1Y);
			this.car1Label[1].setLocation(carsBot[1].getX(), carsBot[1].getY());

			int car2X = in.nextInt();
			String space3 = in.next();
			int car2Y = in.nextInt();
			String space4 = in.next();
			this.carsBot[2].setX(car2X);
			this.carsBot[2].setY(car2Y);
			
			int car3X = in.nextInt();
			String space5 = in.next();
			int car3Y = in.nextInt();
			String space6 = in.next();
			this.carsBot[3].setX(car3X);
			this.carsBot[3].setY(car3Y);
			
			int car4X = in.nextInt();
			String space7 = in.next();
			int car4Y = in.nextInt();
			String space8 = in.next();
			this.carsMid[1].setX(car4X);
			this.carsMid[1].setY(car4Y);
			
			int car5X = in.nextInt();
			String space9 = in.next();
			int car5Y = in.nextInt();
			String space10 = in.next();
			this.carsMid[2].setX(car5X);
			this.carsMid[2].setY(car5Y);
			
			int car6X = in.nextInt();
			String space11 = in.next();
			int car6Y = in.nextInt();
			String space12 = in.next();
			this.carsMid[3].setX(car6X);
			this.carsMid[3].setY(car6Y);
			
			int car7X = in.nextInt();
			String space13 = in.next();
			int car7Y = in.nextInt();
			String space14 = in.next();
			this.carsTop[1].setX(car7X);
			this.carsTop[1].setY(car7Y);
			
			int car8X = in.nextInt();
			String space15 = in.next();
			int car8Y = in.nextInt();
			String space16 = in.next();
			this.carsTop[2].setX(car8X);
			this.carsTop[2].setY(car8Y);
			
			int car9X = in.nextInt();
			String space17 = in.next();
			int car9Y = in.nextInt();
			String space18 = in.next();
			this.carsTop[3].setX(car9X);
			this.carsTop[3].setY(car9Y);
			
			//update graphic
			for (int i = 0; i < 3; i++) {
				this.car1Label[i].setLocation(carsBot[i].getX(), carsBot[i].getY());
				this.car2Label[i].setLocation(carsMid[i].getX(), carsMid[i].getY());
				this.car3Label[i].setLocation(carsTop[i].getX(), carsTop[i].getY());
			}
			
		} else if (command.equals("GETLOGS")) {
			int log1X = in.nextInt();
			String space1 = in.next();
			int log1Y = in.nextInt();
			String space2 = in.next();
			this.logsBot[1].setX(log1X);
			this.logsBot[1].setY(log1Y);
			
			int log2X = in.nextInt();
			String space3 = in.next();
			int log2Y = in.nextInt();
			String space4 = in.next();
			this.logsBot[2].setX(log2X);
			this.logsBot[2].setY(log2Y);
			
			int log3X = in.nextInt();
			String space5 = in.next();
			int log3Y = in.nextInt();
			String space6 = in.next();
			this.logsBot[3].setX(log3X);
			this.logsBot[3].setY(log3Y);
			
			int log4X = in.nextInt();
			String space7 = in.next();
			int log4Y = in.nextInt();
			String space8 = in.next();
			this.logsMid[1].setX(log4X);
			this.logsMid[1].setY(log4Y);
			
			int log5X = in.nextInt();
			String space9 = in.next();
			int log5Y = in.nextInt();
			String space10 = in.next();
			this.logsMid[2].setX(log5X);
			this.logsMid[2].setY(log5Y);
			
			int log6X = in.nextInt();
			String space11 = in.next();
			int log6Y = in.nextInt();
			String space12 = in.next();
			this.logsMid[3].setX(log6X);
			this.logsMid[3].setY(log6Y);
			
			int log7X = in.nextInt();
			String space13 = in.next();
			int log7Y = in.nextInt();
			String space14 = in.next();
			this.logsTop[1].setX(log7X);
			this.logsTop[1].setY(log7Y);
			
			int log8X = in.nextInt();
			String space15 = in.next();
			int log8Y = in.nextInt();
			String space16 = in.next();
			this.logsTop[2].setX(log8X);
			this.logsTop[2].setY(log8Y);
			
			//update graphic
			for (int i = 0; i < 3; i++) {
				this.log1Label[i].setLocation(logsBot[i].getX(), logsBot[i].getY());
				this.log2Label[i].setLocation(logsMid[i].getX(), logsMid[i].getY());
			}
			
			for (int i = 0; i < 2; i++) {
				this.log3Label[i].setLocation(logsTop[i].getX(), logsTop[i].getY());
			}
			
		} else if (command.equals("STARTGAME")) {
			
			
		} else if (command.equals("STOPGAME")) {
			
			
		}



	}
	
	
}