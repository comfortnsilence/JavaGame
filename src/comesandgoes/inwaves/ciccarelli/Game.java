package comesandgoes.inwaves.ciccarelli;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import comesandgoes.inwaves.ciccarelli.gfx.Screen;
import comesandgoes.inwaves.ciccarelli.gfx.SpriteSheet;

public class Game extends Canvas implements Runnable{
	
	// FINAL STATICS
	private static final long serialVersionUID = 1L;	
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 6;
	public static final String NAME = "In Waves";	
	// END FINAL STATICS
	
	// Parameters
	private Screen screen;
	public InputHandler input;
	private JFrame frame;	
	public boolean isRunning = false;
	public int tickCount = 0;	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public static void main(String[] args){
		new Game().start();
	}
		
	public Game(){
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		frame = new JFrame(NAME);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	}
	
	public void init(){
		screen = new Screen(WIDTH, HEIGHT, 	new SpriteSheet("/sprite_sheet.png"));
		input = new InputHandler(this);
	}
	
	public synchronized void start() {
		isRunning = true;
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		isRunning = false;		
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000/60D;		
		int ticks = 0;
		int frames = 0;		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		init();
		
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;
			
			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (shouldRender){
				frames++;
				render();	
			}
					
			
			if (System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer += 1000;
				System.out.println("Frames: "+ frames + " | Ticks: " + ticks);
				frames = 0;
				ticks = 0;
			}
		}
		
	}
	
	public void tick(){
		tickCount++;		
		
		if (input.up.returnInput()){
			screen.yOffset--;
		}
		if (input.down.returnInput()){
			screen.yOffset++;
		}
		if (input.right.returnInput()){
			screen.xOffset++;
		}
		if (input.left.returnInput()){
			screen.xOffset--;
		}
		
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null){
			createBufferStrategy(3);
			return;
		}
		
		screen.render(pixels, 0, WIDTH);
		
		Graphics g = bs.getDrawGraphics();		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0,  getWidth(), getHeight());		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);		
		g.dispose();
		bs.show();		
	}	
}



