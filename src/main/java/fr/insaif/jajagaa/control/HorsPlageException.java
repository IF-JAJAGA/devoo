package fr.insaif.jajagaa.control;

import fr.insaif.jajagaa.model.Livraison;

/**
 * Classe comprenant les exceptions reportées lorsqu'une livraison se trouve en dehors des possibilités 
 * de la plage horaire à laquelle elle est censée appartenir
 * @author H4201
 */
public class HorsPlageException extends Exception{
    
    /**
     * Livraison sur laquelle on va relever l'exception si cela est nécessaire.
     */
    protected Livraison liv;

    /**
     * Exception qui sera relevée si nécessaire sur la Livraison passée en paramètre
     * @param liv 
     */
    public HorsPlageException(Livraison liv){
            super("Toutes les livraisons ne peuvent pas être livrées dans la plage correspondante.\n"+
    "Veuillez modifier le fichier des livraisons");

            this.liv = liv;
    }

    /**
     * Accesseur de la livraison ayant subie l'exception
     * @return 
     */
    public Livraison getLiv() {
        return liv;
    }
        
}	
