package fr.insaif.jajagaa.control;

import fr.insaif.jajagaa.model.Livraison;

public class HorsPlageException extends Exception{
	
    protected Livraison liv;

    public HorsPlageException(Livraison liv){
            super("Toutes les livraisons ne peuvent pas être livrées dans la plage correspondante.\n"+
    "Veuillez modifier le fichier des livraisons");

            this.liv = liv;
    }

    public Livraison getLiv() {
        return liv;
    }
        
}	
