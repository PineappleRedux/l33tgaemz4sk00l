package common.l33tGaem.init;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.imageio.ImageIO;

import common.l33tGaem.entities.EntityManager;
import common.l33tGaem.graphics.GaemPanel;
import common.l33tGaem.reference.Reference;

@SuppressWarnings("serial")
public class Gaem extends Canvas implements Runnable{
	
	
	private Thread thread;
	public boolean running = false;
	public static Gaem gaem; //gaem object
	
	public Gaem() {
		new GaemPanel(Reference.HEIGHT, Reference.WIDTH, Reference.SCALE, this);
		gaem = this;
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try{ 
			thread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() { //Ticking system
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
		long now = System.nanoTime();
		delta += (now - lastTime) / ns;
		lastTime = now;
		while(delta >= 1){
		tick();
		delta--;
		}
		if(running)
			try {
				render();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frames++;

			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	public void tick() {
		EntityManager.manage();
		
	}
	
	public void render() throws IOException { //dont need the throws exception
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//put render things here
		g.setColor(Color.black);
		g.fillRect(0, 0, Reference.HEIGHT, Reference.WIDTH);
		EntityManager.render(g);
		//g.drawImage(ImageIO.read(Gaem.class.getResource("abcd.png")), 1, 1, 256 * 3 / 2, 256 * 3 / 2, null);
		//and end here
		g.dispose();
		bs.show();
	}
	
	public static Gaem getGaem() {
		return gaem;
	}
	
	public static void main(String[] args) {
		new Gaem();
		
	}	
}
