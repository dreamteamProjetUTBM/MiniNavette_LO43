package fr.utbm.lo43.logic;

public class Score {
	/** Constructeur privé */
	private Score()
	{}
 
	/** Instance unique pré-initialisée */
	private static Score INSTANCE = new Score();
 
	/** Point d'accès pour l'instance unique du singleton */
	public static Score getInstance()
	{	return INSTANCE;
	}

}
