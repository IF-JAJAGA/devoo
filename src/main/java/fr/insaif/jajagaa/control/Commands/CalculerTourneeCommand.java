package fr.insaif.jajagaa.control.Commands;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    
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
 * @author H4201
 */
public class CalculerTourneeCommand implements Command{
    private final Tournee tournee;
    /**
     * Temps associé au calcul de la Tournée
     * Exprimé en millisecondes.
     */
    private final int time;
    
    /**
     * List<Chemin> servant de sauvegarde.
     */
    private List<Chemin> cheminsAvant;
    
    /**
     * Liste de Chemins utilisée pour calculer la Tournée.
     */
    private List<Chemin> chemins;
    
    /**
     * List<Chemin> obtenu après calcul de la Tournée.
     */
    private List<Chemin> cheminsApres;
    
    /**
     * Constructeur du Calcul de la Tournée dans le design pattern Command
     * @param tournee à calculer
     * @param time 
     */
    public CalculerTourneeCommand(Tournee tournee, int time){
        this.time = time;
        this.tournee = tournee;
        chemins = tournee.getCheminsResultats();
    }

    /**
     * Méthode permettant l'exécution du calcul de la Tournée dans le design
     * pattern Command.
     */
    @Override
    public void execute() {
        if(cheminsAvant == null){
            
            if(chemins != null){
                cheminsAvant = new LinkedList<>();
                for(Chemin Ch : chemins){
                    cheminsAvant.add(new Chemin(Ch));
                }
            }
            
            try {
            	tournee.solve((time));
            } catch ( HorsPlageException hpe) {
            	Controleur.getInstance().notifyError(hpe);
            }
            
            if(tournee.getSolutionState() == SolutionState.OPTIMAL_SOLUTION_FOUND ||
                    tournee.getSolutionState() == SolutionState.SOLUTION_FOUND){
                chemins = tournee.getCheminsResultats();

                float totalCost = 0;
                for (Chemin c : chemins) {
                    totalCost += c.getCost();
                }
                
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

    /**
     * Méthode UNDO du calcul de la Tournée dans le design pattern Command.
     */
    @Override
    public void undo() {
        chemins = new LinkedList<>();
            for(Chemin Ch : cheminsAvant){
                chemins.add(new Chemin(Ch));
            }    
    }

    /**
     * Accesseur de la liste de Chemins de la Tournée
     * @return List<Chemin>
     */
    public List<Chemin> getChemins() {
        return chemins;
    }
}
