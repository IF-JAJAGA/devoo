package fr.insaif.jajagaa.control;

/**
 * Classe désignant les exceptions relevées par le Parseur
 * @author H4201
 */
public class ParseurException extends Exception{
	
        /**
         * Création de l'exception qui devra être renvoyé avec le message passé 
         * en paramètre
         * @param message 
         */
	public ParseurException(String message){
		super(message);
	}

}
