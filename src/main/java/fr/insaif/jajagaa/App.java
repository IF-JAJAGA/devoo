package fr.insaif.jajagaa;

import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.ZoneGeographique;

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
        FileInputStream zoneInputStream = null,livInputStream = null;
        try {
            zoneInputStream = new FileInputStream("./src/test/resources/plan10x10.xml");
        	livInputStream = new FileInputStream("./src/test/resources/livraison10x10-1.xml");
        	ZoneGeographique zoneGeo = Parseur.lirePlan(zoneInputStream);
            Parseur.lireLivraison(livInputStream,zoneGeo);
        } finally {
            if (livInputStream != null) {
                livInputStream.close();
            }
            if (zoneInputStream != null) {
            	zoneInputStream.close();
            }
        }
    }
}
