package fr.utbm.lo43.logic;

public class Inventory {
	
	private int remaining_bus;
	
	/** Constructeur privé */
	private Inventory()
	{
		remaining_bus = 3;
	}
 
	/** Instance unique pré-initialisée */
	private static Inventory INSTANCE = null;
 

	/** Point d'accès pour l'instance unique du singleton */
	public static Inventory getInstance()
	{	
		if(INSTANCE == null)
			INSTANCE = new Inventory();
		
		return INSTANCE;
	}
	
	public int getRemainingBus(){
		return remaining_bus;
	}

	public void setRemainingBus(int value){
		remaining_bus+= value;
	}
	
	
}
