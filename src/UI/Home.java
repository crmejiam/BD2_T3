package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public Home(){
        botonConsultarDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConsultarDatos datosTablas = new ConsultarDatos();
                datosTablas.loadForm(tablasComboBox.getSelectedItem().toString());
            }
        });
        calcularEstadisticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CalcularEstadisticas estadisticas = new CalcularEstadisticas();
                estadisticas.calcularEstadisticas();
            }
        });
        consultarEstadisticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConsultarEstadisticas datosEstadisticas = new ConsultarEstadisticas();
                datosEstadisticas.consultarEstadisticas(coleccionesComboBox.getSelectedItem().toString());
            }
        });
    }

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
