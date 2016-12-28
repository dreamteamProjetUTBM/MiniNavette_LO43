package fr.utbm.lo43.logic;

public class Map {
	/** Constructeur privé */
	private Map()
	{}
 
	/** Instance unique pré-initialisée */
	private static Map INSTANCE = new Map();
 
	/** Point d'accès pour l'instance unique du singleton */
	public static Map getInstance()
	{	return INSTANCE;
	}

}
