package UI;

import javax.swing.*;

public class Home {
    private JPanel homeMainPanel;
    private JComboBox tablasComboBox;
    private JButton botonConsultarDatos;
    private JButton calcularEstadisticasButton;
    private JButton consultarEstadisticasButton;
    private JComboBox coleccionesComboBox;
    private JLabel consultarDatosEnunciado;
    private JLabel calcularEstadisticasEnunciado;
    private JLabel consultarEstadisticasEnunciado;

    public void loadForm(){
        JFrame f = new JFrame("Home");
        f.setContentPane(new Home().homeMainPanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocation(380, 60);
        f.setVisible(true);
        f.setResizable(false);
    }
}
