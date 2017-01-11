package fr.utbm.lo43.logic;

public class Score {

	private int passengersArrived ;
	/** Constructeur privé */
	private Score()
	{
		passengersArrived = 0;
	}
 
	/** Instance unique pré-initialisée */
	private static Score INSTANCE = null;
 
	/** Point d'accès pour l'instance unique du singleton */
	public static Score getInstance()
	{	
		if(INSTANCE == null)
			INSTANCE = new Score();
		return INSTANCE;
	}

	public static void reInit(){
		INSTANCE = null;
	}
	
	public void incrementScore()
	{
		passengersArrived ++ ;
		System.out.println("Un passager arrivé à destination ! Le score est de : "+getScore());
	}
	
	public int getScore()
	{
		return passengersArrived;
	}
}
