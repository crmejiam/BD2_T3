package UI;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class ConsultarDatos {
    private JPanel consultarDatosForm;
    private DefaultListModel modelo = new DefaultListModel();
    ArrayList<String> miModelo = new ArrayList<String>();
    private JList lista;

    public static void main(String[] args) {
    }

    public void consultarDatos(String tabla) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","1234");
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tabla);
            while(result.next()){
                String output = "";
                switch (tabla){
                    case "sucursal":
                        output = result.getInt(1) + " " + result.getString(2);
                        break;
                    case "vendedor":
                        output = result.getInt(1) + " " + result.getString(2);
                        break;
                    case "cliente":
                        output = result.getInt(1) + " " + result.getString(2) + " " + result.getString(3);
                        break;
                    case "producto":
                        output = result.getInt(1) + " " + result.getString(2) + " " + result.getString(3)+ " " + result.getString(4);
                        break;
                    case "venta":
                        output = result.getInt(1) + " " + result.getInt(2) + " " + result.getInt(3)+ " " + result.getInt(4) + " " + result.getInt(5) + " " + result.getInt(6);
                        break;
                }
                miModelo.add(output);
            }
            modelo.addAll(miModelo);
            lista.setModel(modelo);
            System.out.println(lista.getModel());
            System.out.println(lista.getModel().getSize());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadForm(String tabla){
        JFrame f = new JFrame("Consultar datos");
        f.setContentPane(new ConsultarDatos().consultarDatosForm);
        f.pack();
        f.setLocation(400, 65);
        f.setVisible(true);
        f.setResizable(false);
    }
}