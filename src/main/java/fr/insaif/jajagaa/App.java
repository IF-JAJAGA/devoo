package fr.insaif.jajagaa;

import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.model.Livraison;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Point d'entrée de l'application
 * @author gustavemonod
 */
public class App {
    public static void main(String ... args) throws Exception {
        Thread.sleep(6000);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("./src/test/resources/livraison10x10-1.xml");
            Parseur.lireLivraison(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
