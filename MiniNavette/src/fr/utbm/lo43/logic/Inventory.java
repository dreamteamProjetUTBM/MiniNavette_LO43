package fr.utbm.lo43.logic;

public class Inventory {
	/** Constructeur privé */
	private Inventory()
	{}
 
	/** Instance unique pré-initialisée */
	private static Inventory INSTANCE = new Inventory();
 
	/** Point d'accès pour l'instance unique du singleton */
	public static Inventory getInstance()
	{	return INSTANCE;
	}

}
