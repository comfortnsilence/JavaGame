package comesandgoes.inwaves.ciccarelli;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{
	
	public InputHandler(Game game){		
		// Attach inputHandler to game
		game.addKeyListener(this);		
	}
	
	// Key Class
	public class Key{
		private boolean isPressed = false;
		private int numTimesPressed = 0;
		
		public void toggle(boolean input){
			isPressed = input;	
			if (input)numTimesPressed++;
		}
		
		public boolean returnInput(){
			return isPressed;
		}
	}
	
	//Usable Keys
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	

	//Methods
	@Override
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);		
	}

	@Override
	public void keyTyped(KeyEvent e){
		
	}
	
	public void toggleKey(int keyCode, boolean isPressed){
		if (keyCode == KeyEvent.VK_W){ up.toggle(isPressed);}
		if (keyCode == KeyEvent.VK_A){ left.toggle(isPressed);}
		if (keyCode == KeyEvent.VK_S){ down.toggle(isPressed);}
		if (keyCode == KeyEvent.VK_D){ right.toggle(isPressed);}		
		}
	}