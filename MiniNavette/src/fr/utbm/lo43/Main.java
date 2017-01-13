package fr.utbm.lo43;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import fr.utbm.lo43.logic.Map;

public class Main {
	public static void main(String[] args) {
		try {
			AppGameContainer container = new AppGameContainer(new GameWindow("Mini Navette (UTBM)"), Map.WIDTH,
					Map.HEIGHT, false);
			container.setTargetFrameRate(60);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
