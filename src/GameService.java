import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class GameService implements Runnable{

	private Socket s;
	private Scanner in;
	private Frog frog;
	
	public GameService (Socket aSocket, Frog myFrog) {
		this.s = aSocket;
		this.frog = myFrog;
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
			int frogY = in.nextInt();
			this.frog.setX(frogX);
			this.frog.setY(frogY);
			System.out.println("FROG_POSITION " +frogX + " " + frogY);

		} 

	}
	
	
}