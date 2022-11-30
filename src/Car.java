import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

//this is the object that as the thread
public class Car extends Object {

	protected Boolean visible, moving;
	protected Thread t;
	protected JLabel CarLabel, FrogLabel;
	protected Frog frog;
	protected JButton StartButton;
	
	public Car() {
		super(0, 0, 100, 50, GameProperties.CHARACTER_STEP, "car_right.png");
		this.visible = true;
		this.moving = false;
	}
	
	public void setCarLabel(JLabel temp) {
		this.CarLabel = temp;
	}
	
	public void setFrog(Frog temp) {
		this.frog = temp;
	}

	public void setFrogLabel(JLabel temp) {
		this.FrogLabel = temp;
	}

	public void setStartButton(JButton temp) {
		this.StartButton = temp;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getMoving() {
		return moving;
	}

	public void setMoving(Boolean moving) {
		this.moving = moving;
	}
	
	public void show() {
		this.visible = true;
	}
	
	public void hide() {
		this.visible = true;
	}
	
	public void Display () {
		System.out.println("Car - x,y: " + this.x + "," + this.y);
		System.out.println("width,height: " + this.width + "," + height);
		System.out.println("image: " + this.image);
		//super.Display();
		System.out.println("visible: " + this.visible);
		System.out.println("moving: " + this.moving);
	}
	
	public void startMoving() {
		System.out.println("Move!");

	}

	public String setCarType(){
		if(this.speed>0){
			return "car_right.png";
		} else {
			return "car_left.png";
		}
	}
	



}