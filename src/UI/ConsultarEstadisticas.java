package UI;

import com.mongodb.client.*;
import org.bson.Document;

import javax.swing.*;
import java.util.Iterator;

public class ConsultarEstadisticas {


    private JPanel consultarEstadisticasForm;

    public void consultarEstadisticas(String coleccion) {
        //Conexion con mongo
        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);

        //Obtenemos la DB de mongo
        MongoDatabase database = mongoClient.getDatabase("DB2_T3");

        int superGranTotal = 0;

        String collectionName = "coleccion" + coleccion;
        MongoCollection<Document> output = database.getCollection(collectionName);

        FindIterable<Document> iterDoc = output.find();
        for (Document doc: iterDoc) {
            superGranTotal += (int)doc.get("grantotal");
            System.out.println(doc);
        }
        System.out.println("Super gran total:");
        System.out.println(superGranTotal);
    }

    public void loadForm(String coleccion){
        JFrame f = new JFrame("Consultar Estadisticas");
        f.setContentPane(new ConsultarEstadisticas().consultarEstadisticasForm);
        consultarEstadisticas(coleccion);
        f.pack();
        f.setLocation(400, 65);
        f.setVisible(true);
        f.setResizable(false);
    }
}
