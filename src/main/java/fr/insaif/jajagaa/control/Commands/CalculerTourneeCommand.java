/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.control.Commands;

import java.util.LinkedList;
import java.util.List;

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.control.HorsPlageException;
import fr.insaif.jajagaa.model.Chemin;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.tsp.SolutionState;

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
        System.out.println("chemins.size() : " + chemins.size());
    }

    
    public void execute() {
        if(cheminsAvant == null){
            
            if(chemins != null){
                cheminsAvant = new LinkedList<>();
                for(Chemin Ch : chemins){
                    cheminsAvant.add(new Chemin(Ch));
                }
            }
            
            
            System.out.println("Appel de solve");
            try {
            	tournee.solve((time));
            } catch ( HorsPlageException hpe) {
            	Controleur.getInstance().notifyError(hpe);
            }
            
            System.out.println("Retour de solve");
            if(tournee.getSolutionState() == SolutionState.OPTIMAL_SOLUTION_FOUND ||
                    tournee.getSolutionState() == SolutionState.SOLUTION_FOUND){
                chemins = tournee.getCheminsResultats();

                float totalCost = 0;
                for (Chemin c : chemins) {
                    totalCost += c.getCost();
                }
                System.out.println("coût total: " + totalCost);
                
                cheminsApres = new LinkedList<>();
                for(Chemin Ch : chemins){
                    cheminsApres.add(new Chemin(Ch));
                }
            }
            else{
                cheminsAvant = null;
            }
            
            
        }
        //TODO arranger le catch
        else {
            chemins = new LinkedList<>();
            for(Chemin Ch : cheminsApres){
                chemins.add(new Chemin(Ch));
            }
        }
    }

    @Override
    public void undo() {
        chemins = new LinkedList<>();
            for(Chemin Ch : cheminsAvant){
                chemins.add(new Chemin(Ch));
            }    
    }

    public List<Chemin> getChemins() {
        return chemins;
    }
}
