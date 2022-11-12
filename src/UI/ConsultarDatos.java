package UI;

import javax.swing.*;
import java.sql.*;

public class ConsultarDatos {
    private JPanel consultarDatosForm;
    private final DefaultListModel modelo = new DefaultListModel<>();
    private JList listaNombres;

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
                System.out.println(output);
                modelo.addElement(output);
                listaNombres.setModel(modelo);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadForm(String tabla){
        JFrame f = new JFrame("Consultar Datos");
        f.setContentPane(new ConsultarDatos().consultarDatosForm);
        consultarDatos(tabla);
        f.pack();
        f.setLocation(400, 65);
        f.setVisible(true);
        f.setResizable(false);
    }
}