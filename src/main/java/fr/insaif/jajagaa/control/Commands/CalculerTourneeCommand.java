/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.control.Commands;

import fr.insaif.jajagaa.model.Chemin;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.tsp.SolutionState;
import java.util.List;

/**
 * Ce qui change pour un objet tournee :
 * - cheminsResultats
 * - 
 * @author Jérôme
 */
public class CalculerTourneeCommand implements Command{
    private final Tournee tournee;
    /**
     * En millisecondes
     */
    private final int time;
    private List<Chemin> cheminsAvant;
    private List<Chemin> chemins;
    private List<Chemin> cheminsApres;
    
    public CalculerTourneeCommand(Tournee tournee, int time){
        this.time = time;
        this.tournee = tournee;
        chemins = tournee.getCheminsResultats();
    }

    @Override
    public void execute() {
        if(cheminsAvant == null){
            cheminsAvant = chemins;
            System.out.println("Appel de solve");
            tournee.solve((time));
            System.out.println("Retour de solve");
            if(tournee.getSolutionState() == SolutionState.OPTIMAL_SOLUTION_FOUND ||
                    tournee.getSolutionState() == SolutionState.SOLUTION_FOUND){
                chemins = tournee.getCheminsResultats();
            }
            else{
                cheminsAvant = null;
            }
            
            cheminsApres = chemins;
        }
        //TODO arranger le catch
        else {
            chemins = cheminsApres;
        }
    }

    @Override
    public void undo() {
        chemins = cheminsAvant;    
    }

    public List<Chemin> getChemins() {
        return chemins;
    }
}
