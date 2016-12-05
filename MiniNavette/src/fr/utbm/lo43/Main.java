package fr.utbm.lo43;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main 
{
	public static void main(String[] args) 
	{
		try 
		{
			AppGameContainer container = new AppGameContainer(new GameWindow("Mini Navette (UTBM)"));
			container.setTargetFrameRate(60);
			container.start();
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}
}
