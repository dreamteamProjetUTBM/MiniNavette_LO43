package fr.utbm.lo43.logic;

/**
 * @author Quentin Nahil Thomas Jeremy 
 * Singleton score, classe enregistrant le score et pouvant faire office de stratégie pour changer le décompte des points
 */
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
	}
	
	public int getScore()
	{
		return passengersArrived;
	}
}
