/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.insaif.jajagaa.view.panelDroite;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;


/**
 *
 * @author jeje
 */
public class ConteneurDroite extends JPanel{
    private ListNoeuds listeNoeuds = null;

    public ConteneurDroite() {
        super();
        add(new JLabel("Liste des vueNoeuds ici"));
        
        
        listeNoeuds = new ListNoeuds(null);
        add(listeNoeuds);

    }
    
}
