package fr.insaif.jajagaa.control;

public class HorsPlageException extends Exception{
	
	public HorsPlageException(){
		super("Toutes les livraisons ne peuvent pas être livrées dans la plage correspondante.");
	}
}	
