package com.noah.breakit.gamestate;

import com.noah.breakit.game.Game;
import com.noah.breakit.game.Hud;
import com.noah.breakit.graphics.Screen;
import com.noah.breakit.input.Keyboard;
import com.noah.breakit.sound.music.Jukebox;
import com.noah.breakit.transition.PixelSpatter;
import com.noah.breakit.util.ColorFlasher;

public class GameOver extends GameState {

	private Keyboard key;

	private String[] hiScoreStr = new String[10];
	private int rank;
	
	private int count;
	
	private PixelSpatter pixelSpatter = new PixelSpatter();

	public GameOver(Keyboard key, int rank) {
		this.key = key;
		this.rank = rank;

		for (int i = 0; i < 10; i++) {
			hiScoreStr[i] = "";
			hiScoreStr[i] = Hud.parseScore(Game.hiScores.get(i));
		}
	}

	public void updateGS() {
		
		if(!Jukebox.playing())
			Jukebox.play("gameoversong", true);

		key.update();
		if (key.enter || count++ == 60 * 15){
			captureScreen();
			transition = true;
		}
	}

	public void renderGS(Screen screen) {

		for (int i = 0; i < 128; i++)
			screen.fillRect(random.nextInt(screen.getWidth()), random.nextInt(screen.getHeight()), 1, 1,
					random.nextInt(0xffffff));

		String string = "game over";
		screen.renderString8x8((screen.getWidth() >> 1) - ((string.length() << 3) >> 1), (screen.getHeight() >> 3) + 4,
				ColorFlasher.col, string);

		String title = "-high scores-";
		int titlex = (screen.getWidth() >> 1) - ((title.length() << 3) >> 1);
		int titley = ((screen.getHeight() >> 1) - 4) - ((hiScoreStr.length >> 1) << 3) - 4;
		screen.renderString8x8(titlex, titley - 8 - 4, ~ColorFlasher.col, title);

		int hudx = (screen.getWidth() >> 1) - ((hiScoreStr[0].length() << 3) >> 1);
		int hudy = ((screen.getHeight() >> 1) - 4) - ((hiScoreStr.length >> 1) << 3);

		int ofs = 0;
		for (int i = 0; i < hiScoreStr.length; i++) {
			int col = 0xffffff;
			if (i == rank){
				col = ColorFlasher.col;
				screen.renderString8x8(hudx - 8, hudy +(i<<3) + ofs, ~col, "@");
				screen.renderString8x8(hudx + (hiScoreStr[i].length() << 3) + 1, hudy +(i<<3) + ofs, ~col, "@");
			}
			Hud.renderScore(screen, hudx, hudy + (i << 3) + ofs, col, hiScoreStr[i]);
			ofs += 4;
		}
	}
	
	public void updateTX() {
		pixelSpatter.pixelSpatter(0xff00ff, pixels);
		finished = Jukebox.fadeToBlack() &&  pixelSpatter.isFinished();
		if(finished) loadNextGameState();
	}

	public void renderTX(Screen screen) {
		renderScreenCap(screen);
	}
	
	protected void loadNextGameState() {
		nextGameState = new TitleScreen(key);
	}
}