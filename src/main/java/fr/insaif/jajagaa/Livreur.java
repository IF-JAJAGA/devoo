/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.insaif.jajagaa;

import fr.insaif.jajagaa.control.Controleur;

/**
 * Point d'entrée de l'application.
 * @author Jérôme
 */
public class Livreur {
    public static void main(String... args){
        Controleur.getInstance();
    }
}
