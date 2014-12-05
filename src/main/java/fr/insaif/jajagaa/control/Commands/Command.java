package fr.insaif.jajagaa.control.Commands;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Interface du design pattern Command
 * @author alicia
 */
public interface Command {
    
    /**
     * Méthode utilisée pour réaliser l'action voulue.
     */
    public void execute();
    
    /**
     * Méthode utilisée pour annuler la dernière action.
     */
    public void undo();
}