/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.insaif.jajagaa.view.panelDroite;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 *
 * @author jeje
 */
public class ListNoeuds extends JList {
    private DefaultListModel dlm = new DefaultListModel();

    public ListNoeuds() {
        setModel(dlm);
        dlm.addElement("Noeud 1");
        dlm.addElement("Noeud 2");
    }

    
}
