package com.noah.breakit.gamestate;

import com.noah.breakit.game.Game;
import com.noah.breakit.graphics.Screen;
import com.noah.breakit.input.Keyboard;
import com.noah.breakit.panel.Panel;
import com.noah.breakit.panel.Selection;
import com.noah.breakit.sound.music.Jukebox;
import com.noah.breakit.util.ColorFlasher;

public class PauseMenu extends GameState {

	private ColorFlasher colorFlash = new ColorFlasher(0x0000ff);
	
	private Keyboard key;
	
	private Panel panel;
	
	private int w = 120;
	private int h = 90;
	
	public PauseMenu(Keyboard key, int[] pixels) {
		this.key = key;
		this.pixels = pixels;
		
		int x = Game.width / 2;
		int y = Game.height / 2;
		
		int x1 = x - ("pause".length() *8) / 2;
		int y1 = y - h / 2 + 8;
		
		int x2 = x - ("music".length() * 8) / 2;
		int y2 = y - h / 2 + 28;
		
		int x3 = x - ("quit to title".length() * 8) / 2;
		int y3 = y - h / 2 + 44;
		
		int x4 = x - ("exit program".length() * 8) / 2;
		int y4 = y - h / 2 + 60;
		
		panel = new Panel(x - w / 2, y - h / 2, w, h, key, 
						  new Selection(x1, y1, "pause"),
						  new Selection(x2, y2, "music"),
						  new Selection(x3, y3, "quit to title"),
						  new Selection(x4, y4, "exit program")
						  );
	}
	
	public void updateGS() {
		
		colorFlash.update();
		
		key.update();
		if(key.esc && !key.escLast) {
			finished = true;
			Jukebox.play();
		}
	}

	public void renderGS(Screen screen) {
		renderScreenCap(screen);
		
		panel.render(screen);
//		screen.fillRect((Game.width >> 1) - (w >> 1) , (Game.height >> 1) - (h >> 1), w, h, 0x000000);
//		screen.drawRect((Game.width >> 1) - (w >> 1), (Game.height >> 1) - (h >> 1), w, h, colorFlash.col);
//		screen.renderString8x8((Game.width >> 1) - (("pause".length() << 3) >> 1) , (Game.height >> 1) - (h >> 1) + 8, colorFlash.col, "pause");
//		screen.renderString8x8((Game.width >> 1) - (("music".length() << 3) >> 1) , (Game.height >> 1) - (h >> 1) + 12 + 16, 0xffffff, "music");
//		screen.renderString8x8((Game.width >> 1) - (("quit to title".length() << 3) >> 1) , (Game.height >> 1) - (h >> 1) + 12 + 32, 0xffffff, "quit to title");
//		screen.renderString8x8((Game.width >> 1) - (("exit program".length() << 3) >> 1) , (Game.height >> 1) - (h >> 1) + 12 + 48, 0xffffff, "exit program");
	}

	public void updateTX() {
		
	}

	public void renderTX(Screen screen) {
		
	}

	public GameState getNextGameState() {
		return null;
	}
}