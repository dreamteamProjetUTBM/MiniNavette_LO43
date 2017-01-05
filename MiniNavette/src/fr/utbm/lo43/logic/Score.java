package fr.utbm.lo43.logic;

public class Score {

	private int passengersArrived ;
	/** Constructeur privé */
	private Score()
	{
		passengersArrived = 0;
	}
 
	/** Instance unique pré-initialisée */
	private static Score INSTANCE = new Score();
 
	/** Point d'accès pour l'instance unique du singleton */
	public static Score getInstance()
	{	return INSTANCE;
	}

	public void incrementScore()
	{
		passengersArrived ++ ;
	}
	
	public int getScore()
	{
		return passengersArrived;
	}
}
