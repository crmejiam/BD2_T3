package UI;

import com.mongodb.DBObject;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

public class CalcularEstadisticas {
    public void calcularEstadisticas(){
        try {
            //Conexion con oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
            Statement stmt = con.createStatement();
            //Conexion con mongo
            String uri = "mongodb://localhost:27017";
            MongoClient mongoClient = MongoClients.create(uri);

            //Creamos la DB de mongo (se creará una vez se inserten datos en ella)
            MongoDatabase database = mongoClient.getDatabase("DB2_T3");

            //Creamos las colecciones (se crearan una vez se inserten datos en ellas)
            MongoCollection<Document> coleccionMarcas = database.getCollection("coleccionMarcas");
            MongoCollection<Document> coleccionGeneros = database.getCollection("coleccionGeneros");
            MongoCollection<Document> coleccionVendedores = database.getCollection("coleccionVendedores");

            ResultSet result = stmt.executeQuery("SELECT distinct(marca) FROM producto");
            while (result.next()) {
                String marcaJson = crearDocumentoMarca(con, result).toString();
                System.out.println(marcaJson);
                Document marca = Document.parse(marcaJson);
                InsertOneResult insertResult = coleccionMarcas.insertOne(marca);
                System.out.println(insertResult.getInsertedId());
            }

            result = stmt.executeQuery("SELECT distinct(genero) FROM cliente");
            while (result.next()) {
                String generoJson = crearDocumentoGenero(con, result).toString();
                System.out.println(generoJson);
                Document genero = Document.parse(generoJson);
                InsertOneResult insertResult = coleccionGeneros.insertOne(genero);
                System.out.println(insertResult.getInsertedId());
            }

            result = stmt.executeQuery("SELECT codigo, nombre FROM vendedor");
            while (result.next()) {
                String vendedorJson = crearDocumentoVendedor(con, result).toString();
                System.out.println(vendedorJson);
                Document vendedor = Document.parse(vendedorJson);
                InsertOneResult insertResult = coleccionVendedores.insertOne(vendedor);
                System.out.println(insertResult.getInsertedId());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private JSONObject crearDocumentoMarca(Connection con, ResultSet result){
        JSONObject jsonMarcas = new JSONObject();
        try {
                String nombreMarca = result.getString(1);
                jsonMarcas.put("nombreMarca", nombreMarca);
                ArrayList<JSONObject> listaVentas = new ArrayList<>();
                int grantotal = 0;
                ResultSet ventas = con.createStatement().executeQuery("SELECT * FROM venta");
                while (ventas.next()) {
                    JSONObject venta = new JSONObject();
                    ResultSet comando;
                    int sucursal = ventas.getInt(2);
                    int producto = ventas.getInt(5);
                    int TotalUni = ventas.getInt(6);

                    comando = con.createStatement().executeQuery("SELECT marca FROM producto WHERE codigo=" + String.valueOf(producto));
                    comando.next();
                    String marcaProducto = comando.getString(1);

                    if (!marcaProducto.equals(nombreMarca)) {
                        continue;
                    }

                    grantotal += TotalUni;
                    comando = con.createStatement().executeQuery("SELECT nombre FROM sucursal WHERE codigo=" + String.valueOf(sucursal));
                    comando.next();
                    String nomsucursal = comando.getString(1);
                    comando = con.createStatement().executeQuery("SELECT tipo FROM producto WHERE codigo=" + String.valueOf(producto));
                    comando.next();
                    String TipoProd = comando.getString(1);

                    venta.put("nomsucursal", nomsucursal);
                    venta.put("TipoProd", TipoProd);
                    venta.put("TotalUni", TotalUni);

                    listaVentas.add(venta);
                }
                jsonMarcas.put("misVentas", listaVentas);
                jsonMarcas.put("grantotal", grantotal);
        } catch (Exception e) {
            System.out.println(e);
        }
        return jsonMarcas;
    }

    private JSONObject crearDocumentoGenero(Connection con, ResultSet result){
        JSONObject jsonGeneros = new JSONObject();
        try {
            String genero = result.getString(1);
            if (genero == null){
                jsonGeneros.put("genero", JSONObject.NULL);
            } else {
                jsonGeneros.put("genero", genero);
            }
            ArrayList<JSONObject> listaVentas = new ArrayList<>();
            int grantotal = 0;
            ResultSet ventas = con.createStatement().executeQuery("SELECT * FROM venta");
            while (ventas.next()) {
                JSONObject venta = new JSONObject();
                ResultSet comando;
                int sucursal = ventas.getInt(2);
                int cliente = ventas.getInt(4);
                int producto = ventas.getInt(5);
                int TotalUni = ventas.getInt(6);

                comando = con.createStatement().executeQuery("SELECT genero FROM cliente WHERE codigo=" + String.valueOf(cliente));
                comando.next();
                String generoCliente = comando.getString(1);

                if (!generoCliente.equals(genero)) {
                    continue;
                }

                grantotal += TotalUni;
                comando = con.createStatement().executeQuery("SELECT nombre FROM sucursal WHERE codigo=" + String.valueOf(sucursal));
                comando.next();
                String nomsucursal = comando.getString(1);
                comando = con.createStatement().executeQuery("SELECT tipo FROM producto WHERE codigo=" + String.valueOf(producto));
                comando.next();
                String TipoProd = comando.getString(1);

                venta.put("nomsucursal", nomsucursal);
                venta.put("TipoProd", TipoProd);
                venta.put("TotalUni", TotalUni);

                listaVentas.add(venta);
            }
            jsonGeneros.put("misVentas", listaVentas);
            jsonGeneros.put("grantotal", grantotal);
        } catch (Exception e) {
            System.out.println(e);
        }
        return jsonGeneros;
    }

    private JSONObject crearDocumentoVendedor(Connection con, ResultSet result){
        JSONObject jsonVendedores = new JSONObject();
        try {
            int codvendedor = result.getInt(1);
            String nomvend = result.getString(2);
            jsonVendedores.put("codvendedor", codvendedor);
            jsonVendedores.put("nomvend", nomvend);
            ArrayList<JSONObject> listaVentas = new ArrayList<>();
            int grantotal = 0;
            ResultSet ventas = con.createStatement().executeQuery("SELECT * FROM venta");
            while (ventas.next()) {
                JSONObject venta = new JSONObject();
                ResultSet comando;
                int sucursal = ventas.getInt(2);
                int vendedor = ventas.getInt(3);
                int TotalUni = ventas.getInt(6);

                if (vendedor != codvendedor) {
                    continue;
                }

                grantotal += TotalUni;
                comando = con.createStatement().executeQuery("SELECT nombre FROM sucursal WHERE codigo=" + String.valueOf(sucursal));
                comando.next();
                String nomsucursal = comando.getString(1);

                venta.put("nomsucursal", nomsucursal);
                venta.put("TotalUni", TotalUni);

                listaVentas.add(venta);
            }
            jsonVendedores.put("misVentas", listaVentas);
            jsonVendedores.put("grantotal", grantotal);
        } catch (Exception e) {
            System.out.println(e);
        }
        return jsonVendedores;
    }

}
